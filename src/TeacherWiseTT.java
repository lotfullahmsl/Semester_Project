import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class TeacherWiseTT extends JDialog{
    private JPanel TeacherWiseTTPanel;
    private JTextField tfTeacher;
    private JButton btSearch;
    private JScrollPane TeacherTablePane;
    private JTable TeacherTable;

    public TeacherWiseTT (JFrame parent){
        super(parent, "Teacher Wise Time Table", true);
        setContentPane(TeacherWiseTTPanel);
        setSize(1200, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        // Ensure the table has a default model
        TeacherTable.setModel(new DefaultTableModel(new String[]{
            "CourseName", "CourseID", "CourseType", "Teacher", "Room",
            "Department", "Time", "CreditHours", "Day", "ClassType"}, 0));


        btSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchTeacherWiseTT();
            }

            private void SearchTeacherWiseTT() {
                String teacher = tfTeacher.getText();

                if (teacher.isEmpty()) {
                    JOptionPane.showMessageDialog(TeacherWiseTT.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                searchTeacherWiseTT(teacher);
            }

            private void searchTeacherWiseTT(String teacher) {
                final String URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

                    String query = "SELECT * FROM courses WHERE Teacher = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, teacher);
                    ResultSet rs = stmt.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) TeacherTable.getModel();
                    model.setRowCount(0);
                    if (rs.next()) {
                        // this part print data on tables
                        do {
                            model.addRow(new Object[]{
                                rs.getString("CourseName"),
                                rs.getString("CourseID"),
                                rs.getString("CourseType"),
                                rs.getString("Teacher"),
                                rs.getString("Room"),
                                rs.getString("Department"),
                                rs.getString("Time"),
                                rs.getString("CreditHours"),
                                rs.getString("Day"),
                                rs.getString("ClassType")
                            });
                        } while (rs.next());
                    } else {
                        JOptionPane.showMessageDialog(TeacherWiseTT.this, "No record found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace(); }

            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        TeacherWiseTT teacherWiseTT = new TeacherWiseTT(null);
    }

    }


