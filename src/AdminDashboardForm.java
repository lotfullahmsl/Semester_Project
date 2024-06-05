import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardForm extends JDialog{
    private JComboBox <String> cbChoise;
    private JButton btOK;
    private JButton btCancel;
    private JPanel AdminDashBoardPannel;
    private JPanel AddingCoursePannel;
    private JPanel AddingRoomPannel;
    private JPanel AddingDepartment;
    private JPanel RemoveCoursePannel;
    private JPanel SectionWiseTimeTablePannel;
    private JPanel DayWiseTTPannel;
    private JPanel RoomWiseTTPannel;
    private JPanel TeacherWiseTTPannel;
    private JPanel AddStudentPannel;
    private JPanel RemoveStudentPannel;
    private JPanel RemoveRoomPannel;
    private JPanel SearchStudentRecordPannel;

    public AdminDashboardForm(JFrame parent) {
        super(parent, "Admin Dashboard", true);
        Container AdminDashboardPanel;
        setContentPane(AdminDashBoardPannel);
        setSize(730, 630);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        cbChoise.addItem("Select Choise");
        cbChoise.addItem("1. Add Course");
        cbChoise.addItem("2. Remove Course");
        cbChoise.addItem("3. Section Wise Time Table");
        cbChoise.addItem("4. Day Wise Time Table");
        cbChoise.addItem("5. Room Wise Time Table");
        cbChoise.addItem("6. Teacher Wise Time Table");
        cbChoise.addItem("7. Add Student");
        cbChoise.addItem("8. Remove Student");
        cbChoise.addItem("9. Add Room");
        cbChoise.addItem("10. Remove Room");
        cbChoise.addItem("11. Add Department");
        cbChoise.addItem("12. Search Student Avilibility");


        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });



        btOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choise = cbChoise.getSelectedItem().toString();
                if (choise.equals("Select Choise")) {
                    JOptionPane.showMessageDialog(AdminDashboardForm.this, "Please select any choise", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (choise.equals("1. Add Course")) {
                    AddingCourse addingCourse = new AddingCourse(null);
                }
                if (choise.equals("2. Remove Course")) {
                    RemoveCourse removeCourse = new RemoveCourse(null);
                }
                if (choise.equals("3. Section Wise Time Table")) {
                    SectionWiseTT sectionWiseTT = new SectionWiseTT(null);
                }
                if (choise.equals("4. Day Wise Time Table")) {
                    DayWiseTT dayWiseTT = new DayWiseTT(null);

                }
                if (choise.equals("5. Room Wise Time Table")) {
                    RoomWiseTT roomWiseTT = new RoomWiseTT(null);
                }
                if (choise.equals("6. Teacher Wise Time Table")) {
                    TeacherWiseTT teacherWiseTT = new TeacherWiseTT(null);
                }
                if (choise.equals("7. Add Student")) {
                    AddingStudent addingStudent = new AddingStudent(null);
                }
                if (choise.equals("8. Remove Student")) {
                    RemoveStudent removeStudent = new RemoveStudent(null);
                }
                if (choise.equals("9. Add Room")) {
                    AddingRoom addingRoom = new AddingRoom(null);

                }
                if (choise.equals("10. Remove Room")) {
                    RemoveRoom removeRoom = new RemoveRoom(null);
                }
                if (choise.equals("11. Add Department")) {
                    AddingDepartment addDepartment = new AddingDepartment(null);
                }
                if (choise.equals("12. Search Student Avilibility")) {
                    SearchStudent searchStudent = new SearchStudent(null);
                }

            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        AdminDashboardForm adminDashboardForm = new AdminDashboardForm(null);
    }


}
