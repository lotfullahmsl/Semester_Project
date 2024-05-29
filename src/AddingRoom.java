import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddingRoom extends JDialog{
    private JPanel AddingRoomPanel;
    private JTextField tfRoom;
    private JButton btAdd;

    public AddingRoom(JFrame parent){
        super(parent, "Add Room", true);
        setContentPane(AddingRoomPanel);
        setSize(450, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoom();
            }

            private void addRoom() {
                String room = tfRoom.getText();
                if (room.isEmpty()) {
                    JOptionPane.showMessageDialog(AddingRoom.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                addRoomToDB(room);
            }

            private void addRoomToDB(String room) {
                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

               try {
                   Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        String query = "INSERT INTO room (Room) VALUES (?)";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setString(1, room);
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(AddingRoom.this, "Room added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(AddingRoom.this, "Room Already Added", "Error", JOptionPane.ERROR_MESSAGE);
                    }
            }
        });
        setVisible(true);

    }


    public static void main(String[] args) {
        AddingRoom addingRoom = new AddingRoom(null);
    }
}
