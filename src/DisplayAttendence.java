import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DisplayAttendence extends JDialog{
    private JTextField tfEnrollment;
    private JPasswordField tfPassword;
    private JTextField tfCourse;
    private JButton btView;
    private JPanel Panel;
    private JTable Table;
    private JScrollPane ScrollPane;


    public DisplayAttendence (JFrame parent){
        super(parent, "Display Attendence", true);
        setContentPane(Panel);
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        Table.setModel(new DefaultTableModel(new Object[]{"Enrollment", "Course", "Department", "Status", "Date"}, 0) {
        });


        btView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clean table
                ((DefaultTableModel) Table.getModel()).setRowCount(0);

                String enrollment = tfEnrollment.getText();
                String password = tfPassword.getText();
                String course = tfCourse.getText();

                if (enrollment.isEmpty() || password.isEmpty() || course.isEmpty()) {
                    JOptionPane.showMessageDialog(DisplayAttendence.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    varifyStudent(enrollment, password, course);
                }
            }

            private void varifyStudent(String enrollment, String password, String course) {

                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String DB_USER = "root";
                final String DB_PASSWORD = "Muslimwal@2004";

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "SELECT * FROM registerstudent WHERE Enrolment = ? AND Password = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, enrollment);
                    ps.setString(2, password);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        String query1 = "SELECT * FROM attendances WHERE Enrollment = ? AND Course = ?";
                        PreparedStatement ps1 = conn.prepareStatement(query1);
                        ps1.setString(1, enrollment);
                        ps1.setString(2, course);
                        ResultSet rs1 = ps1.executeQuery();
                        while (rs1.next()) {
                            String enrollment1 = rs1.getString("Enrollment");
                            String department = rs1.getString("Department");
                            String course1 = rs1.getString("Course");
                            String status = rs1.getString("present");
                            if (status.equals("1")) {
                                status = "Present";
                            } else {
                                status = "Absent";
                            }
                            String date = rs1.getString("Date");
                            ((DefaultTableModel) Table.getModel()).addRow(new Object[]{enrollment1, course1, department, status, date});
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(DisplayAttendence.this, "Invalid Enrollment or Password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(DisplayAttendence.this, "An error occurred while verifying the student.");
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        DisplayAttendence displayAttendence = new DisplayAttendence(null);
    }
}
