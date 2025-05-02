import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

public class FacultyForm extends JDialog {
    private JPanel FacultyPanel;
    private JPanel SigninPanel;
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JPasswordField tfCPassword;
    private JComboBox cbRoll;
    private JButton tbRegester;
    private JButton tbSignin;

    public FacultyForm(JFrame parent) {
        super(parent, "Faculty Form", true);
        setContentPane(FacultyPanel);

        setSize(550, 474);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //combo box text
        cbRoll.addItem("Select Roll");
        cbRoll.addItem("Head of Department");
        cbRoll.addItem("Student Adviser");
       // cbRoll.addItem("Teacher");


        tbRegester.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerFaculty();
            }

            private void registerFaculty() {
                //we Read the Boxes
                String email = tfEmail.getText();
                String password = tfPassword.getText();
                String cpassword = tfCPassword.getText();
                String roll = cbRoll.getSelectedItem().toString();
                if (email.isEmpty() || password.isEmpty() || cpassword.isEmpty() || roll.equals("Select Roll")) {
                    JOptionPane.showMessageDialog(FacultyForm.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //checking that password and confirm password are same
                if (!password.equals(cpassword)) {
                    JOptionPane.showMessageDialog(FacultyForm.this, "Password and Confirm Password must be same", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //passing value of faculty to store in Database
               addFacultyToDB(email, password, roll);

            }

               //method of adding faculty to database
               private void addFacultyToDB(String email, String password, String roll) {
               final String DB_URL = "jdbc:mysql://localhost:3306/facultyregistration";
               final String USER = "root";
               final String PASS = "Muslimwal@2004";

                try {
                     Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                     String query = "INSERT INTO data (Email, Password, Roll) VALUES (?, ?, ?)";
                     java.sql.PreparedStatement stmt = conn.prepareStatement(query);
                     stmt.setString(1, email);
                     stmt.setString(2, password);
                     stmt.setString(3, roll);
                     stmt.executeUpdate();
                     JOptionPane.showMessageDialog(FacultyForm.this, "Faculty registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                     FacultySignin facultySignin = new FacultySignin(null);
                     conn.close();

                }
                catch (Exception e) {
                     e.printStackTrace();
                }
            }
        });


        tbSignin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FacultySignin facultySignin = new FacultySignin(null);
            }
        });

        tfEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfEmail.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(FacultyForm.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.setVisible(true);
    }

    public static void main(String[] args) {

        FacultyForm facultyForm = new FacultyForm(null);
    }
}
