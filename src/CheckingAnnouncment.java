import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckingAnnouncment extends JDialog{
    private JPanel MainPanel;
    private JTextField tfSection;
    private JButton btCheck;
    private JScrollPane Pane;
    private JTable table;
    private JScrollPane pane;
    private JTextArea ta1;
    private JTextField tfDate;
    private JButton btLoad;
    private JButton btPrevios;
    private JButton btNext;



    public CheckingAnnouncment (JFrame parent) {
        super(parent, "Checking Announcment", true);
        setContentPane(MainPanel);
        setSize(780, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        table.setModel(new DefaultTableModel(new Object[]{"Teacher Name", "Date"}, 0) {
        });



        btLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((DefaultTableModel) table.getModel()).setRowCount(0);
                String section = tfSection.getText();
                if (section.isEmpty()) {
                    JOptionPane.showMessageDialog(CheckingAnnouncment.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    checkAnnouncment(section);
                }

            }

            private void checkAnnouncment(String section) {
                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String DB_USER = "root";
                final String DB_PASSWORD = "Muslimwal@2004";

                try {
                    Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    String query = "SELECT * FROM announcements WHERE Department = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, section);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        //set row to zero to avoid duplication

                        ((DefaultTableModel) table.getModel()).addRow(new Object[]{rs.getString("TeacherName"), rs.getString("Date")});
                       // ta1.setText(rs.getString("Announcement"));

                        // table should be not editable

                        ta1.setEditable(false);
                    }
                    if (table.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(CheckingAnnouncment.this, "No announcement found", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }



                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        btCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = tfDate.getText();
                if (date.isEmpty()) {
                    JOptionPane.showMessageDialog(CheckingAnnouncment.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    checkAnnouncment(date);
                }


            }

            private void checkAnnouncment(String date) {
                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String DB_USER = "root";
                final String DB_PASSWORD = "Muslimwal@2004";

                try {
                    Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    String query = "SELECT * FROM announcements WHERE Date = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, date);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        ta1.setText(rs.getString("Announcement"));
                    }
                    if (ta1.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(CheckingAnnouncment.this, "No announcement found", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        CheckingAnnouncment checkingAnnouncment = new CheckingAnnouncment(null);
    }
}
