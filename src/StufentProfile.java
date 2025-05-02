import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StufentProfile extends JDialog{
    private JTextField tfChoise;
    private JButton sectionButton;
    private JButton studentButton;
    private JTable Table;
    private JScrollPane ScrollPane;
    private JPanel StudentProfile;

    public StufentProfile (Frame parent){
        super( parent, "Student Profile", true);
        setContentPane(StudentProfile);
        setSize(1200, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Table.setModel(new DefaultTableModel(new String[]{
            "Enrollment", "Department", "Course", "ASN1", "QZ1", "ASN2", "QZ2", "MID", "ASN3", "QZ3", "ASN4", "QZ4", "Final", "Total", "Grade"}, 0));



        sectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SectionProfile();
            }

            private void SectionProfile() {
                String section = tfChoise.getText();

                if (section.isEmpty()) {
                    JOptionPane.showMessageDialog(StufentProfile.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                searchSectionProfile(section);
            }

            private void searchSectionProfile(String section) {
                final String URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try {
                    Connection conn = DriverManager.getConnection(URL, USER, PASS);
                    String query = "SELECT * FROM scores WHERE Department = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, section);
                    ResultSet rs = stmt.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) Table.getModel();
                    model.setRowCount(0);
                    if (rs.next()) {

                        do {
                            model.addRow(new Object[]{
                                rs.getString("Enrollment"),
                                rs.getString("Department"),
                                rs.getString("Course"),
                                rs.getString("Assignment1"),
                                rs.getString("Quiz1"),
                                rs.getString("Assignment2"),
                                rs.getString("Quiz2"),
                                rs.getString("Mid"),
                                rs.getString("Assignment3"),
                                rs.getString("Quiz3"),
                                rs.getString("Assignment4"),
                                rs.getString("Quiz4"),
                                rs.getString("Final"),
                                rs.getString("Total"),
                                rs.getString("Grade")

                            });
                        } while (rs.next());
                    } else {
                        JOptionPane.showMessageDialog(StufentProfile.this, "No data found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(StufentProfile.this, "An error occurred while searching the section profile.");

                }
            }
        });

        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentProfile();
            }

            private void StudentProfile() {
                String student = tfChoise.getText();

                if (student.isEmpty()) {
                    JOptionPane.showMessageDialog(StufentProfile.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                searchStudentProfile(student);
            }

            private void searchStudentProfile(String student) {
                final String URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try {
                    Connection conn = DriverManager.getConnection(URL, USER, PASS);
                    String query = "SELECT * FROM scores WHERE Enrollment = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, student);
                    ResultSet rs = stmt.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) Table.getModel();
                    model.setRowCount(0);
                    if (rs.next()) {
                        do {
                            model.addRow(new Object[]{
                                rs.getString("Enrollment"),
                                rs.getString("Department"),
                                rs.getString("Course"),
                                rs.getString("Assignment1"),
                                rs.getString("Quiz1"),
                                rs.getString("Assignment2"),
                                rs.getString("Quiz2"),
                                rs.getString("Mid"),
                                rs.getString("Assignment3"),
                                rs.getString("Quiz3"),
                                rs.getString("Assignment4"),
                                rs.getString("Quiz4"),
                                rs.getString("Final"),
                                rs.getString("Total"),
                                rs.getString("Grade")
                            });
                        } while (rs.next());
                    } else {
                        JOptionPane.showMessageDialog(StufentProfile.this, "No data found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(StufentProfile.this, "An error occurred while searching the student profile.");

                }
            }
        });
        setVisible(true);
    }
    public static void main(String[] args) {
       StufentProfile studentProfile = new StufentProfile(null);
    }
}
