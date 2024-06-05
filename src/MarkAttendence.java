import javax.swing.*;

public class MarkAttendence extends JDialog{
    private JPanel AttendencePanel;
    private JScrollPane ScrollPanel;
    private JPanel Panel2;
    private JTextField tfDepartment;
    private JTextField tfCourse;
    private JButton btSearch;

    public MarkAttendence(JFrame parent) {
        super(parent, "Mark Attendence", true);
        setContentPane(AttendencePanel);
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btSearch.addActionListener(e -> {
            String department = tfDepartment.getText();
            String course = tfCourse.getText();

            if (department.isEmpty() || course.isEmpty()) {
                JOptionPane.showMessageDialog(MarkAttendence.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            markAttendence(department, course);
        });
        setVisible(true);
    }

    private void markAttendence(String department, String course) {
        // Mark attendence logic

    }

}
