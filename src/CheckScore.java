import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckScore extends JDialog{
    private JPanel MainPanel;
    private JTextField tfEnrollment;
    private JPasswordField tfPassword;
    private JButton btView;
    private JScrollPane Pane;
    private JTable table;

    public CheckScore (JFrame parent){
        super(parent, "Check Score", true);
        setContentPane(MainPanel);
        setSize(900, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        table.setModel(new DefaultTableModel(new Object[]{"Department", "Course","ASN1", "QZ1", "ASN2", "QZ2", "MID", "ASN3", "QZ3", "ASN4", "QZ4", "Final", "Total", "Grade"}, 0) {

        });


        btView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ((DefaultTableModel) table.getModel()).setRowCount(0);

                String enrollment = tfEnrollment.getText();
                String password = tfPassword.getText();

                if (enrollment.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(CheckScore.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    varifyStudent(enrollment, password);
                }
            }

            private void varifyStudent(String enrollment, String password) {
                final  String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String DB_USER = "root";
                final String DB_PASSWORD = "Muslimwal@2004";

                try {
                    Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    String query = "SELECT * FROM registerstudent WHERE Enrolment = ? AND Password = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, enrollment);
                    ps.setString(2, password);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()){
                        String query1 = "SELECT * FROM scores WHERE Enrollment = ?";
                        PreparedStatement ps1 = conn.prepareStatement(query1);
                        ps1.setString(1, enrollment);
                        ResultSet rs1 = ps1.executeQuery();
                        while (rs1.next()){
                            ((DefaultTableModel) table.getModel()).addRow(new Object[]{rs1.getString("Department"), rs1.getString("Course"), rs1.getString("Assignment1"), rs1.getString("Quiz1"), rs1.getString("Assignment2"), rs1.getString("Quiz2"), rs1.getString("Mid"), rs1.getString("Assignment3"), rs1.getString("Quiz3"), rs1.getString("Assignment4"), rs1.getString("Quiz4"), rs1.getString("Final"), rs1.getString("Total"), rs1.getString("Grade")});
                        }
                        if (((DefaultTableModel) table.getModel()).getRowCount() == 0) {
                            JOptionPane.showMessageDialog(CheckScore.this, "No record found", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                    }
                    else {
                        JOptionPane.showMessageDialog(CheckScore.this, "Invalid Enrollment or Password", "Error", JOptionPane.ERROR_MESSAGE);
                    }


                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CheckScore.this, "An error occurred while checking the score.");
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        CheckScore checkScore = new CheckScore(null);
    }
}
