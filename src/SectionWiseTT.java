import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SectionWiseTT extends JDialog {
    private JTextField btSectionChoise;
    private JPanel SectionWiseTTPanel;
    private JButton searchButton;
    private JTable tableSectionWise;
    private JScrollPane scrollPane;

    public SectionWiseTT(JFrame parent) {
        super(parent, "Section Wise Time Table", true);
        setContentPane(SectionWiseTTPanel);
        setSize(1200, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        // Ensure the table has a default model //help of chat gpt
        tableSectionWise.setModel(new DefaultTableModel(new String[]{
                "CourseName", "CourseID", "CourseType", "Teacher", "Room",
                "Department", "Time", "CreditHours", "Day", "ClassType"}, 0));

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchSectionWiseTT();
            }

            private void SearchSectionWiseTT() {
                String section = btSectionChoise.getText();

                if (section.isEmpty()) {
                    JOptionPane.showMessageDialog(SectionWiseTT.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                searchSectionWiseTT(section);
            }

            private void searchSectionWiseTT(String section) {
                final String URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

                    String query = "SELECT * FROM courses WHERE Department = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, section);
                    ResultSet rs = stmt.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) tableSectionWise.getModel();
                    model.setRowCount(0); // Clear existing data
                    if (rs.next()) {
                        do {
                            model.addRow(new Object[]{
                                    rs.getString("CourseName"), rs.getString("CourseID"), rs.getString("CourseType"),
                                    rs.getString("Teacher"), rs.getString("Room"), rs.getString("Department"),
                                    rs.getString("Time"), rs.getString("CreditHours"), rs.getString("Day"), rs.getString("ClassType")
                            });
                        } while (rs.next());
                    } else {
                        JOptionPane.showMessageDialog(SectionWiseTT.this, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(SectionWiseTT.this, "Error in fetching data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        SectionWiseTT sectionWiseTT = new SectionWiseTT(null);
    }
}
