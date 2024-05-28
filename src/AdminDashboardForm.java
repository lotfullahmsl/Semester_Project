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
        cbChoise.addItem("12. Remove Department");
        cbChoise.addItem("13. Search Student Record");
        cbChoise.addItem("14. Check Amount of Students");


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
                   // RemoveCourseForm removeCourseForm = new RemoveCourseForm(null);
                }
                if (choise.equals("3. Section Wise Time Table")) {
                   // SectionWiseTimeTableForm sectionWiseTimeTableForm = new SectionWiseTimeTableForm(null);
                }
                if (choise.equals("4. Day Wise Time Table")) {
                   // DayWiseTimeTableForm dayWiseTimeTableForm = new DayWiseTimeTableForm(null);
                }
                if (choise.equals("5. Room Wise Time Table")) {
                   // RoomWiseTimeTableForm roomWiseTimeTableForm = new RoomWiseTimeTableForm(null);
                }
                if (choise.equals("6. Teacher Wise Time Table")) {
                   // TeacherWiseTimeTableForm teacherWiseTimeTableForm = new TeacherWiseTimeTableForm(null);
                }
                if (choise.equals("7. Add Student")) {
                   // AddStudentForm addStudentForm = new AddStudentForm(null);
                }
                if (choise.equals("8. Remove Student")) {
                   // RemoveStudentForm removeStudentForm = new RemoveStudentForm(null);
                }
                if (choise.equals("9. Add Room")) {
                    AddingRoom addingRoom = new AddingRoom(null);
                   // AddRoomForm addRoomForm = new AddRoomForm(null);
                }
                if (choise.equals("10. Remove Room")) {
                   // RemoveRoomForm removeRoomForm = new RemoveRoomForm(null);
                }
                if (choise.equals("11. Add Department")) {
                   // AddDepartmentForm addDepartmentForm = new AddDepartmentForm(null);
                }
                if (choise.equals("12. Remove Department")) {
                   // RemoveDepartmentForm removeDepartmentForm = new RemoveDepartmentForm(null);
                }
                if (choise.equals("13. Search Student Record")) {
                   // SearchStudentRecordForm searchStudentRecordForm = new SearchStudentRecordForm(null);
                }
                if (choise.equals("14. Check Amount of Students")) {
                   // CheckAmountOfStudentsForm checkAmountOfStudentsForm = new CheckAmountOfStudentsForm(null);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        AdminDashboardForm adminDashboardForm = new AdminDashboardForm(null);
    }


}
