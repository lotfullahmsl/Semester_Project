import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FacultySignin extends JDialog {
    private JPanel SigninPanel;
    private JPanel DesignPanel;
    private JPanel AdminDashBoardPannel; //we have added another panel it will be show after signin
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JComboBox <String> cbRoll;
    private JButton btSignin;
    private JButton btExit;

    public FacultySignin(JFrame parent) {
        super(parent, "Faculty Signin", true);
        setContentPane(SigninPanel);
        setSize(550, 474);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Combo box text
        cbRoll.addItem("Select Roll");
        cbRoll.addItem("Head of Department");
        cbRoll.addItem("Student Adviser");
        cbRoll.addItem("Teacher");

        btSignin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signinFaculty();
            }

            private void signinFaculty() {

                String email = tfEmail.getText();
                String password = new String(tfPassword.getPassword());
                String roll = cbRoll.getSelectedItem().toString();
                if (email.isEmpty() || password.isEmpty() || roll.equals("Select Roll")) {
                    JOptionPane.showMessageDialog(FacultySignin.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                checkFacultyInDB(email, password, roll);
            }

            private void checkFacultyInDB(String email, String password, String roll) {
                final String DB_URL = "jdbc:mysql://localhost:3306/facultyregistration";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try {

                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    String query = "SELECT * FROM data WHERE email = ? AND password = ? AND roll = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, email);
                    stmt.setString(2, password);
                    stmt.setString(3, roll);
                    ResultSet Reader = stmt.executeQuery();

                    if (Reader.next()) {
                        JOptionPane.showMessageDialog(FacultySignin.this, "Faculty signed in successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        AdminDashboardForm adminDashboardForm = new AdminDashboardForm(null);

                    }

                    else {
                        JOptionPane.showMessageDialog(FacultySignin.this, "Faculty not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }

                catch (Exception e) {
                    JOptionPane.showMessageDialog(FacultySignin.this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        btExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        FacultySignin facultySignin = new FacultySignin(null);
    }
}
