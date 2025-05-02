import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TeacherAnoncment extends JDialog{
    private JPanel AnoncmentPanel;
    private JTextField tfDate;
    private JTextField tfDepartment;
    private JTextField tfName;
    private JTextArea taAnnouncement;
    private JButton btAnnounce;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Muslimwal@2004";

    public TeacherAnoncment(JFrame parent){
        super(parent, "Teacher Announcement", true);
        setContentPane(AnoncmentPanel);
        setSize(800, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btAnnounce.addActionListener(e -> {
            String date = tfDate.getText();
            String department = tfDepartment.getText();
            String name = tfName.getText();
            String announcement = taAnnouncement.getText();

            if (date.isEmpty() || department.isEmpty() || name.isEmpty() || announcement.isEmpty()) {
                JOptionPane.showMessageDialog(TeacherAnoncment.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (checkTeacherAndDepartment(name, department)) {
                    if (addAnnouncement(date, department, name, announcement)) {
                        JOptionPane.showMessageDialog(TeacherAnoncment.this, "Announcement added successfully.");
                    } else {
                        JOptionPane.showMessageDialog(TeacherAnoncment.this, "Announcement could not be added.");
                    }
                } else {
                    JOptionPane.showMessageDialog(TeacherAnoncment.this, "Teacher is not added by admin.");
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(TeacherAnoncment.this, "An error occurred while adding the announcement.");
            }

        });
        setVisible(true);
    }

    private boolean addAnnouncement(String date, String department, String name, String announcement) {
        boolean isAdded = false;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO announcements (TeacherName, Department, Date, Announcement) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, department);
            stmt.setString(3, date);
            stmt.setString(4, announcement);
            isAdded = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return isAdded;
    }
    private boolean checkTeacherAndDepartment(String name, String department) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM courses WHERE Teacher = ? AND Department = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, department);
            return stmt.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        TeacherAnoncment teacherAnoncment = new TeacherAnoncment(null);
    }
}
