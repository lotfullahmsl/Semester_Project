import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterStudents extends JDialog {
    private JTextField tfEnrolment;
    private JTextField tfCourse;
    private JTextField tfDepartment;
    private JButton registerButton;
    private JPanel Panel;

    public RegisterStudents(JFrame parent) {
        super(parent, "Register Students", true);
        setContentPane(Panel);
        setSize(600, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);




        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enrolment = tfEnrolment.getText();
                String course = tfCourse.getText();
                String department = tfDepartment.getText();

                if (enrolment.isEmpty() || course.isEmpty() || department.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterStudents.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    if (checkCourse(course, department)) {

                        if(checkStudent(enrolment, department))
                        {
                            if (registerStudent(enrolment, course, department)) {
                                JOptionPane.showMessageDialog(RegisterStudents.this, "Student registered successfully.");
                                dispose();
                            }
                            else {
                                JOptionPane.showMessageDialog(RegisterStudents.this, "Student could not be registered.");
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(RegisterStudents.this, "Student is not added by admin.");
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(RegisterStudents.this, "An error occurred while registering the student.");
                }
            }

            private boolean registerStudent(String enrolment, String course, String department) {
                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String DB_USER = "root";
                final String DB_PASSWORD = "Muslimwal@2004";

                boolean isAdded = false;

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "INSERT INTO registrecourse (Enrollment, CourseName, Department) VALUES (?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, enrolment);
                    stmt.setString(2, course);
                    stmt.setString(3, department);
                    isAdded = stmt.executeUpdate() > 0;

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return isAdded;
            }

            private boolean checkStudent(String enrolment, String department) {
                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String DB_USER = "root";
                final String DB_PASSWORD = "Muslimwal@2004";

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "SELECT * FROM registerstudent WHERE Enrolment = ? AND Department = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, enrolment);
                        stmt.setString(2, department);
                        try (ResultSet rs = stmt.executeQuery()) {
                            return rs.next();
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return false;
                }
            }

            private boolean checkCourse(String course, String department) {
                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String DB_USER = "root";
                final String DB_PASSWORD = "Muslimwal@2004";

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "SELECT * FROM courses WHERE CourseName = ? AND Department = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, course);
                        stmt.setString(2, department);
                        try (ResultSet rs = stmt.executeQuery()) {
                            return rs.next();
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return false;
                }

            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterStudents(new JFrame());
    }
}
