import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoomWiseTT extends JDialog{
    private JTextField tfRoomNumber;
    private JButton btSearch;
    private JScrollPane tablePane;
    private JTable RoomTable;
    private JPanel RoomWiseTTPanel;

    public RoomWiseTT (JFrame parent){
        super(parent, "Room Wise Time Table", true);
        setContentPane(RoomWiseTTPanel);
        setSize(1200, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ensure the table has a default model
        RoomTable.setModel(new DefaultTableModel(new String[]{
            "CourseName", "CourseID", "CourseType", "Teacher", "Room",
            "Department", "Time", "CreditHours", "Day", "ClassType"}, 0));

        btSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchRoomWiseTT();
            }

            private void SearchRoomWiseTT() {
                String room = tfRoomNumber.getText();

                if (room.isEmpty()) {
                    JOptionPane.showMessageDialog(RoomWiseTT.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                searchRoomWiseTT(room);
            }

            private void searchRoomWiseTT(String room) {
                final String URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

                    String query = "SELECT * FROM courses WHERE Room = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, room);
                    ResultSet rs = stmt.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) RoomTable.getModel();
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
                        JOptionPane.showMessageDialog(RoomWiseTT.this, "No record found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(RoomWiseTT.this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        RoomWiseTT roomWiseTT = new RoomWiseTT(null);
    }
}
