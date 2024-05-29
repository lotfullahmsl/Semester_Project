import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Time extends JDialog{
    private JComboBox <String> cbDay;
    private JComboBox <String> cbTime;
    private JButton btAdd;
    private JPanel TimingPanel;

    public Time(JFrame parent){
        super(parent, "Add Time", true);
        setContentPane(TimingPanel);
        setSize(450, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cbDay.addItem("Select Day");
        cbDay.addItem("Monday");
        cbDay.addItem("Tuesday");
        cbDay.addItem("Wednesday");
        cbDay.addItem("Thursday");
        cbDay.addItem("Friday");

        cbTime.addItem("Select Time");
        cbTime.addItem("8:30-9:25");
        cbTime.addItem("9:30-10:25");
        cbTime.addItem("10:30-11:25");
        cbTime.addItem("11:30-12:25");
        cbTime.addItem("12:30-1:25");
        cbTime.addItem("1:30-2:25");
        cbTime.addItem("2:30-3:25");
        cbTime.addItem("3:30-4:25");

        btAdd.addActionListener(e -> {
            String day = cbDay.getSelectedItem().toString();
            String time = cbTime.getSelectedItem().toString();
            if (day.equals("Select Day") || time.equals("Select Time")) {
                JOptionPane.showMessageDialog(Time.this, "Please select all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            addTimeToDB(day, time);
        });
        setVisible(true);
    }

    private void addTimeToDB(String day, String time) {
        final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
        final String USER = "root";
        final String PASS = "Muslimwal@2004";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "INSERT INTO time (Day, Time) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, day);
            stmt.setString(2, time);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(Time.this, "Time added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Time.this, "Time Already Added", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Time time = new Time(null);
    }
}
