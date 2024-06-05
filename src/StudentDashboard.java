import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDashboard extends JDialog{
    private JPanel SDashboardPanel;
    private JComboBox <String> cbChoise;
    private JButton selectButton;

    public StudentDashboard (JFrame parent){
        super(parent, "Student Dashboard", true);
        setContentPane(SDashboardPanel);
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cbChoise.addItem("Select Choice");
        cbChoise.addItem("Check Time Table");
        cbChoise.addItem("Check Announcements");
        cbChoise.addItem("Register A Course");
        cbChoise.addItem("Check Scores");
        cbChoise.addItem("Check Attendence");



        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = cbChoise.getSelectedItem().toString();
                if (choice.equals("Select Choice")) {
                    JOptionPane.showMessageDialog(StudentDashboard.this, "Please select a choice", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else if (choice.equals("Check Time Table")) {
                   // dispose();
                    SectionWiseTT sectionWiseTT = new SectionWiseTT(null);
                } else if (choice.equals("Check Announcements")) {
                   // dispose();
                    CheckingAnnouncment checkingAnnouncment = new CheckingAnnouncment(null);

                } else if (choice.equals("Register A Course")) {
                   // dispose();
                    RegisterStudents registerStudents = new RegisterStudents(null);
                } else if (choice.equals("Check Scores")) {
                   // dispose();
                    CheckScore checkScore = new CheckScore(null);
                } else if (choice.equals("Check Attendence")) {
                   // dispose();
                  DisplayAttendence displayAttendence = new DisplayAttendence(null);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        StudentDashboard studentDashboard = new StudentDashboard(null);
    }
}
