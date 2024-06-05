import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentLogin extends JDialog{
    private JPanel StudentLoginPanel;
    private JTextField tfEnroll;
    private JPasswordField tfPassword;
    private JTextField tfDepartment;
    private JButton btLogin;

    public StudentLogin(JFrame parent) {
        super(parent, "Student Login", true);
        setContentPane(StudentLoginPanel);
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btLogin.addActionListener(e -> {
            String enroll = tfEnroll.getText();
            String department = tfDepartment.getText();
            String password = new String(tfPassword.getPassword());

            if (enroll.isEmpty() || department.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(StudentLogin.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            studentLogin(enroll, department, password);
        });
        setVisible(true);



    }

    private void studentLogin(String enroll, String department, String password) {

        final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
        final String USER = "root";
        final String PASS = "Muslimwal@2004";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "SELECT * FROM registerstudent WHERE Enrolment = ? AND Department = ? AND Password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, enroll);
            stmt.setString(2, department);
            stmt.setString(3, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(StudentLogin.this, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(StudentLogin.this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(StudentLogin.this, "An error occurred", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String[] args) {
        StudentLogin studentLogin = new StudentLogin(null);
    }

}
