import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherDashboard extends JDialog {
    private JPanel Teacherdashboard;
    private JComboBox <String> cbChoice;
    private JButton btnSearch;
    private JPanel AnnouncementPanel;
    private JPanel SectionWiseTT;

    public TeacherDashboard(JFrame parent) {

        super(parent, "Teacher Dashboard", true);
        setContentPane(Teacherdashboard);
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        //ComboBox for selecting the choice
        cbChoice.addItem("Select Choice");
        cbChoice.addItem("Mark Attendance");
        cbChoice.addItem("Check Time Table");
        cbChoice.addItem("Announcement");
        cbChoice.addItem("Enter Student Marks");
        cbChoice.addItem("View Student Profile");





        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = cbChoice.getSelectedItem().toString();
                if (choice.equals("Select Choice")) {
                    JOptionPane.showMessageDialog(TeacherDashboard.this, "Please select a choice", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
               else if (choice.equals("Mark Attendance")) {
                    dispose();
                    AttendenceTeacher attendenceTeacher = new AttendenceTeacher(null);

                } else if (choice.equals("Check Time Table")) {
                   dispose();
                   SectionWiseTT sectionWiseTT = new SectionWiseTT(null);

                } else if (choice.equals("Announcement")) {
                   dispose();
                    TeacherAnoncment teacherAnoncment = new TeacherAnoncment(null);
                } else if (choice.equals("Enter Student Marks")) {
                   StudentScores studentScores = new StudentScores(null);
                } else if (choice.equals("View Student Profile")) {
                    StufentProfile stufentProfile = new StufentProfile(null);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        TeacherDashboard teacherDashboard = new TeacherDashboard(null);

    }
}
