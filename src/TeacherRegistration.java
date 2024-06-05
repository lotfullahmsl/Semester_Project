import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TeacherRegistration extends JDialog {
    private JTextField tfName;
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JPasswordField tfCPassword;
    private JTextField tfCourse;
    private JButton btRegister;
    private JButton btLoginpage;
    private JPanel Teacherregister;
    private JPanel TeacherLoginPanel;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Muslimwal@2004";

    public TeacherRegistration(JFrame parent) {
        super(parent, "Teacher Registration", true);
        setContentPane(Teacherregister);
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfName.getText();
                String email = tfEmail.getText();
                String password = new String(tfPassword.getPassword());
                String cpassword = new String(tfCPassword.getPassword());
                String course = tfCourse.getText();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || cpassword.isEmpty() || course.isEmpty()) {
                    JOptionPane.showMessageDialog(TeacherRegistration.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!password.equals(cpassword)) {
                    JOptionPane.showMessageDialog(TeacherRegistration.this, "Password does not match", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    if (checkNameAndCourse(name, course)) {
                        if (addTeacher(name, email, password, course)) {
                            JOptionPane.showMessageDialog(TeacherRegistration.this, "Teacher added successfully.");
                            TeacherLogin teacherLogin = new TeacherLogin(null);
                        } else {
                            JOptionPane.showMessageDialog(TeacherRegistration.this, "Teacher could not be added. Email may already be in use.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(TeacherRegistration.this, "Teacher is not added by admin.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(TeacherRegistration.this, "An error occurred while registering the teacher.");
                }
            }
        });

        btLoginpage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TeacherLogin teacherLogin = new TeacherLogin(null);
            }
        });
        setVisible(true);
    }

    private boolean addTeacher(String name, String email, String password, String course) {
        boolean isAddedSuccessfully = false;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO teacher (teachername, teacheremail, password, coursename) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, course);
            int result = stmt.executeUpdate();
            isAddedSuccessfully = result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAddedSuccessfully;
    }

    private boolean checkNameAndCourse(String name, String course) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM courses WHERE Teacher = ? AND CourseName = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, course);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        TeacherRegistration teacherRegistration = new TeacherRegistration(null);
    }
}
