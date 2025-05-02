import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TeacherLogin extends JDialog{
    private JPanel TeacherLoginPanel;
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JButton btLogin;
    private JPanel TeacherDashboardPanel;

    public TeacherLogin(JFrame parent) {
        super(parent, "Teacher Login", true);
        setContentPane(TeacherLoginPanel);
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);



        btLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = new String(tfPassword.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(TeacherLogin.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                teacherLogin(email, password);
            }

            private void teacherLogin(String email, String password) {
                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    String query = "SELECT * FROM teacher WHERE teacheremail = ? AND password = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, email);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(TeacherLogin.this, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        TeacherDashboard teacherDashboard = new TeacherDashboard(null);
                    } else {
                        JOptionPane.showMessageDialog(TeacherLogin.this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }



        });
        setVisible(true);

    }

    public static void main(String[] args) {
        TeacherLogin teacherLogin = new TeacherLogin(null);
    }
}
