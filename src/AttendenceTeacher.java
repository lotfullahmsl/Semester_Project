import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;

public class AttendenceTeacher extends JDialog {

    private JTextField tfSection;
    private JButton btSelect;
    private JPanel Panel;
    private JScrollPane ScrollPanel;
    private JTable StudentTable;
    private JTextField tfName;
    private JTextField tfTName;
    private JTable CourseInfoTable;
    private JScrollPane CourseinfoPanel;
    private JComboBox<String> tfDay;
    private JComboBox<String> tfTiming;
    private JButton saveButton;

    public AttendenceTeacher(JFrame parent) {
        super(parent, "Attendance", true);
        setContentPane(Panel);
        setSize(1200, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tfDay.addItem("Select Day");
        tfDay.addItem("Monday");
        tfDay.addItem("Tuesday");
        tfDay.addItem("Wednesday");
        tfDay.addItem("Thursday");
        tfDay.addItem("Friday");

        tfTiming.addItem("Select Timing");
        tfTiming.addItem("8:30-9:25");
        tfTiming.addItem("9:30-10:25");
        tfTiming.addItem("10:30-11:25");
        tfTiming.addItem("11:30-12:25");
        tfTiming.addItem("12:30-1:25");
        tfTiming.addItem("1:30-2:25");
        tfTiming.addItem("2:30-3:25");
        tfTiming.addItem("3:30-4:25");

        CourseInfoTable.setModel(new DefaultTableModel(new String[]{"CourseName", "Teacher", "Credit Hours", "Department", "Day", "Timing"}, 0));
        //some help from chat GPT
        StudentTable.setModel(new DefaultTableModel(new Object[]{"Enrolment", "Department", "Present"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 2 ? Boolean.class : String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only the checkbox column is editable
            }
        });

        btSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfName.getText();
                String tname = tfTName.getText();
                String department = tfSection.getText();
                String day = tfDay.getSelectedItem().toString();
                String timing = tfTiming.getSelectedItem().toString();

                if (name.isEmpty() || tname.isEmpty() || department.isEmpty() || day.equals("Select Day") || timing.equals("Select Timing")) {
                    JOptionPane.showMessageDialog(AttendenceTeacher.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LoadCourse(name, tname, department, day, timing);
            }

            private void LoadCourse(String name, String tname, String department, String day, String timing) {
                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String DB_USER = "root";
                final String DB_PASSWORD = "Muslimwal@2004";

                DefaultTableModel courseModel = (DefaultTableModel) CourseInfoTable.getModel();
                DefaultTableModel studentModel = (DefaultTableModel) StudentTable.getModel();
                courseModel.setRowCount(0);
                studentModel.setRowCount(0);

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "SELECT * FROM courses WHERE CourseName = ? AND Teacher = ? AND Department = ? AND Day = ? AND Time = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, name);
                    stmt.setString(2, tname);
                    stmt.setString(3, department);
                    stmt.setString(4, day);
                    stmt.setString(5, timing);
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {
                        courseModel.addRow(new Object[]{rs.getString("CourseName"), rs.getString("Teacher"), rs.getString("CreditHours"), rs.getString("Department"), rs.getString("Day"), rs.getString("Time")});
                    }
                    if (courseModel.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(AttendenceTeacher.this, "No course found", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //searchSectionProfile(department);
                    searchSectionProfile(name);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AttendenceTeacher.this, "An error occurred while searching the record.");
                }
            }

            private void searchSectionProfile(String name) {
                final String URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                    String query = "SELECT * FROM registrecourse WHERE CourseName = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, name);
                    ResultSet rs = stmt.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) StudentTable.getModel();

                    while (rs.next()) {
                        model.addRow(new Object[]{rs.getString("Enrollment"), rs.getString("Department"), false});
                    }
                    if (model.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(AttendenceTeacher.this, "No student record found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AttendenceTeacher.this, "An error occurred while searching the student record.");
                }
            }
        });


        saveButton.addActionListener(new ActionListener() {


            java.util.Date date = new java.util.Date();



            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfName.getText();
                DefaultTableModel model = (DefaultTableModel) StudentTable.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String enrolment = model.getValueAt(i, 0).toString();
                    String department = model.getValueAt(i, 1).toString();
                    boolean present = (boolean) model.getValueAt(i, 2);
                    saveAttendance(enrolment, department, name, present, date);
                }

            }

            private void saveAttendance(String enrolment, String department, String name, boolean present, Date date) {
                final String URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                    String query = "INSERT INTO attendances (Enrollment, Department, Course, present, Date) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, enrolment);
                    stmt.setString(2, department);
                    stmt.setString(3, name);
                    stmt.setInt(4, present ? 1 : 0);
                    stmt.setDate(5, new java.sql.Date(date.getTime()));

                    stmt.executeUpdate();
                    if (present) {
                        JOptionPane.showMessageDialog(AttendenceTeacher.this, "Attendance saved successfully.");
                    }
                    else {
                        JOptionPane.showMessageDialog(AttendenceTeacher.this, "Attendance saved successfully.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AttendenceTeacher.this, "An error occurred while saving the attendance.");
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        AttendenceTeacher attendenceTeacher = new AttendenceTeacher(null);
    }
}
