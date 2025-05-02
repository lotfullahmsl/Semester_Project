import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DayWiseTT extends JDialog{
    private JComboBox <String> cbDay;
    private JButton btSearch;
    private JScrollPane DayWiseTTPane;
    private JPanel DayWisePanel;
    private JTable table1;
    private JPanel AdminDashBoardPannel;


    public DayWiseTT(JFrame parent){
        super(parent, "Day Wise Time Table", true);
        setContentPane(DayWisePanel);
        setSize(1200, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cbDay.addItem("Select Day");
        cbDay.addItem("Monday");
        cbDay.addItem("Tuesday");
        cbDay.addItem("Wednesday");
        cbDay.addItem("Thursday");
        cbDay.addItem("Friday");


        // Ensure the table has a default model
        table1.setModel(new DefaultTableModel(new String[]{
            "CourseName", "CourseID", "CourseType", "Teacher", "Room",
            "Department", "Time", "CreditHours", "Day", "ClassType"}, 0));


        btSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchDayWiseTT();
            }

            private void SearchDayWiseTT() {
                String day = cbDay.getSelectedItem().toString();

                if (day.isEmpty()) {
                    JOptionPane.showMessageDialog(DayWiseTT.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (day == "Select Day"){
                    JOptionPane.showMessageDialog(DayWiseTT.this, "Please select any day", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                searchDayWiseTT(day);
            }

            private void searchDayWiseTT(String day) {
                final String URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

                    String query = "SELECT * FROM courses WHERE Day = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, day);
                    ResultSet rs = stmt.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    model.setRowCount(0);
                    if (rs.next()) {
                        // this part print data on tables
                        do {
                            model.addRow(new Object[]{
                                rs.getString("CourseName"),
                                rs.getString("CourseID"),
                                rs.getString("CourseType"),
                                rs.getString("Teacher"),
                                rs.getString("Room"),
                                rs.getString("Department"),
                                rs.getString("Time"),
                                rs.getString("CreditHours"),
                                rs.getString("Day"),
                                rs.getString("ClassType")
                            });
                        } while (rs.next());
                    } else {
                        JOptionPane.showMessageDialog(DayWiseTT.this, "No record found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        DayWiseTT dayWiseTT = new DayWiseTT(null);
    }
}
