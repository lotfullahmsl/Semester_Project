import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RemoveStudent extends JDialog{
    private JPanel RemoveStudentPanel;
    private JTextField tfSection;
    private JTextField tfEnrolment;
    private JButton btDelete;

    public RemoveStudent(JFrame parent) {
        super(parent, "Remove Student", true);
        setContentPane(RemoveStudentPanel);
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);




        btDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }

            private void deleteStudent() {
                String section = tfSection.getText();
                String enrolment = tfEnrolment.getText();
                if (section.isEmpty() || enrolment.isEmpty()) {
                    JOptionPane.showMessageDialog(RemoveStudent.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                deleteStudentFromDB(section, enrolment);
            }

            private void deleteStudentFromDB(String section, String enrolment) {
                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    String query = "DELETE FROM addedstudent WHERE Department = ? AND Enrolment = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, section);
                    stmt.setString(2, enrolment);
                    stmt.executeUpdate();
                    if (stmt.getUpdateCount() == 0) {
                        JOptionPane.showMessageDialog(RemoveStudent.this, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else {
                        JOptionPane.showMessageDialog(RemoveStudent.this, "Student removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        conn.close();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(RemoveStudent.this, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        RemoveStudent removeStudent = new RemoveStudent(null);
    }
}
