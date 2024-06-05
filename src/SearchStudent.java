import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchStudent extends JDialog{
    private JTextField tfStudent;
    private JButton btSearch;
    private JPanel StudentSPanel;
    private JScrollPane TablePane;
    private JTable StudentTable;

    public SearchStudent(JFrame parent){
        super(parent, "Search Student", true);
        setContentPane(StudentSPanel);
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        StudentTable.setModel(new DefaultTableModel(new String[]{
            "Enrolment", "Department"}, 0));


        btSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentSearch();
            }

            private void StudentSearch() {
                String student = tfStudent.getText();
                if (student.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchStudent.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                searchStudent(student);
            }

            private void searchStudent(String student) {
                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    String query = "SELECT * FROM addedstudent WHERE Enrolment = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, student);
                    ResultSet rs = stmt.executeQuery();
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(SearchStudent.this, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else {
                        DefaultTableModel model = (DefaultTableModel) StudentTable.getModel();
                        model.setRowCount(0);
                        do {
                            model.addRow(new Object[]{rs.getString("Enrolment"), rs.getString("Department")});
                        } while (rs.next());
                        conn.close();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(SearchStudent.this, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        SearchStudent searchStudent = new SearchStudent(new JFrame());
    }
}
