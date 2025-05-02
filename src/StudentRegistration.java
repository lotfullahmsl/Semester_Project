import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentRegistration extends JDialog {
    private JTextField tfEnroll;
    private JTextField tfDepartment;
    private JButton tbRegister;
    private JButton tbSignin;
    private JPanel StudentRegisterPanel;
    private JPasswordField tfPassword;
    private JPasswordField tfCPassword;
    private JPanel StudentLoginPanel;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Muslimwal@2004";

    public StudentRegistration(JFrame parent) {
        super(parent, "Student Registration", true);
        setContentPane(StudentRegisterPanel);
        setSize(550, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        tbRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enroll = tfEnroll.getText();
                String department = tfDepartment.getText();
                String password = new String(tfPassword.getPassword());
                String confirmPassword = new String(tfCPassword.getPassword());

                if (enroll.isEmpty() || department.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(StudentRegistration.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(StudentRegistration.this, "Password does not match", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    if (checkEnrollmentAndDepartment(enroll, department)) {
                        addStudent(enroll, department, password);
                        JOptionPane.showMessageDialog(StudentRegistration.this, "Student added successfully.");
                        StudentLogin studentLogin = new StudentLogin(null);
                    } else {
                        JOptionPane.showMessageDialog(StudentRegistration.this, "Student is not Added by admin.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(StudentRegistration.this, "An error occurred while registering the student.");
                }
            }

            private boolean checkEnrollmentAndDepartment(String enroll, String department) {
                // Checking if the student is added by admin
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM addedstudent WHERE Enrolment = ? AND Department = ?")) {
                    stmt.setString(1, enroll);
                    stmt.setString(2, department);
                    ResultSet rs = stmt.executeQuery();
                    return rs.next();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return false;
                }
            }

            private void addStudent(String enroll, String department, String password) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO registerstudent (Enrolment, Department, Password) VALUES (?, ?, ?)")) {
                    stmt.setString(1, enroll);
                    stmt.setString(2, department);
                    stmt.setString(3, password);
                    stmt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        tbSignin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                StudentLogin studentLogin = new StudentLogin(null);
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        StudentRegistration studentRegistration = new StudentRegistration(null);
    }
}
