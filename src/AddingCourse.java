import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddingCourse extends JDialog {
    private JTextField tfCourseName;
    private JTextField tfCourseID;
    private JComboBox <String> cbCourseType;
    private JTextField tfTeacherName;
    private JComboBox <String> cbTiming;
    private JComboBox <String> cbCredit;
    private JComboBox <String> cbDay;
    private JComboBox <String> cbClassType;
    private JButton addButton;
    private JButton cancelButton;
    private JPanel AddingCoursePannel;
    private JTextField tfDepartment;
    private JTextField tfRoom;

    public AddingCourse (JFrame parent) {
        super(parent, "Add Course", true);
        setContentPane(AddingCoursePannel);
        setSize(730, 550);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cbCourseType.addItem("Select Course Type");
        cbCourseType.addItem("Theory");
        cbCourseType.addItem("Lab");

        cbTiming.addItem("Select Timing");
        cbTiming.addItem("8:30-9:25");
        cbTiming.addItem("9:30-10:25");
        cbTiming.addItem("10:30-11:25");
        cbTiming.addItem("11:30-12:25");
        cbTiming.addItem("12:30-1:25");
        cbTiming.addItem("1:30-2:25");
        cbTiming.addItem("2:30-3:25");
        cbTiming.addItem("3:30-4:25");


        cbCredit.addItem("Select Credit Hours");
        cbCredit.addItem("0");
        cbCredit.addItem("1");
        cbCredit.addItem("2");
        cbCredit.addItem("3");

        cbDay.addItem("Select Day");
        cbDay.addItem("Monday");
        cbDay.addItem("Tuesday");
        cbDay.addItem("Wednesday");
        cbDay.addItem("Thursday");
        cbDay.addItem("Friday");

        cbClassType.addItem("Select Class Type");
        cbClassType.addItem("Regular");
        cbClassType.addItem("Make up");



        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        AddingCourse addingCourse = new AddingCourse(null);
    }
}
