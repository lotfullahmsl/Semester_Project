import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RemoveCourse extends JDialog {
    private JTextField tfSection;
    private JTextField tfCourseName;
    private JButton btDelete;
    private JPanel RemoveCoursePanel;

    public RemoveCourse(JFrame parent) {
        super(parent, "Remove Course", true);
        setContentPane(RemoveCoursePanel);
        setSize(500, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btDelete.addActionListener(e -> {
           deleteCourse();
        });

        setVisible(true);
    }

    private void deleteCourse() {
        String section = tfSection.getText();
        String courseName = tfCourseName.getText();
        if (section.isEmpty() || courseName.isEmpty()) {
            JOptionPane.showMessageDialog(RemoveCourse.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        deleteCourseFromDB(section, courseName);
    }

    private void deleteCourseFromDB(String section, String courseName) {
        final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
        final String USER = "root";
        final String PASS = "Muslimwal@2004";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "DELETE FROM courses WHERE Department = ? AND CourseName = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, section);
            stmt.setString(2, courseName);
            stmt.executeUpdate();
            if (stmt.getUpdateCount() == 0) {
                JOptionPane.showMessageDialog(RemoveCourse.this, "Course not found", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
           else {
                JOptionPane.showMessageDialog(RemoveCourse.this, "Course removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                conn.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(RemoveCourse.this, "Course not found", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    public static void main(String[] args) {
        RemoveCourse removeCourse = new RemoveCourse(null);
    }
}
