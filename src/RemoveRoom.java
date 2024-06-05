import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RemoveRoom extends JDialog{
    private JTextField tfRemove;
    private JButton btRemove;
    private JPanel RemoveRoomPanel;

    public RemoveRoom(JFrame parent){
        super(parent, "Remove Room", true);
        setContentPane(RemoveRoomPanel);
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeRoom();

            }

            private void removeRoom() {
                String room = tfRemove.getText();
                if (room.isEmpty()) {
                    JOptionPane.showMessageDialog(RemoveRoom.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //numaric value

                removeRoomFromDB(room);
            }

            private void removeRoomFromDB(String room) {
                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    String query = "DELETE FROM room WHERE Room = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, room);
                    stmt.executeUpdate();
                    if (stmt.getUpdateCount() == 0) {
                        JOptionPane.showMessageDialog(RemoveRoom.this, "Room not found", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else {
                        JOptionPane.showMessageDialog(RemoveRoom.this, "Room removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        conn.close();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(RemoveRoom.this, "Room not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        RemoveRoom removeRoom = new RemoveRoom(null);
    }
}
