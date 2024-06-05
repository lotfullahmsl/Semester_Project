import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddingCourse extends JDialog {
    private JTextField tfCourseName;
    private JTextField tfCourseID;
    private JComboBox<String> cbCourseType;
    private JTextField tfTeacherName;
    private JComboBox<String> cbTiming;
    private JComboBox<String> cbCredit;
    private JComboBox<String> cbDay;
    private JComboBox<String> cbClassType;
    private JButton addButton;
    private JButton cancelButton;
    private JPanel AddingCoursePannel;
    private JTextField tfDepartment;
    private JTextField tfRoom;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/projectdata"; // Update with your DB URL
    private static final String DB_USER = "root"; // Update with your DB username
    private static final String DB_PASSWORD = "Muslimwal@2004"; // Update with your DB password

    public AddingCourse(JFrame parent) {
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

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = tfCourseName.getText();
                String courseID = tfCourseID.getText();
                String courseType = cbCourseType.getSelectedItem().toString();
                String teacherName = tfTeacherName.getText();
                String timing = cbTiming.getSelectedItem().toString();
                String credit = cbCredit.getSelectedItem().toString();
                String day = cbDay.getSelectedItem().toString();
                String classType = cbClassType.getSelectedItem().toString();
                String department = tfDepartment.getText();
                String room = tfRoom.getText();

                // some help of ChatGPT

                try {
                    if (checkDepartmentAndRoom(department, room)) {
                        addCourse(courseName, courseID, courseType, teacherName, timing, credit, day, classType, department, room);
                        JOptionPane.showMessageDialog(AddingCourse.this, "Course added successfully.");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(AddingCourse.this, "Department or room does not exist.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddingCourse.this, "An error occurred while adding the course.");
                }
            }
        });

        setVisible(true);
    }

    private boolean checkDepartmentAndRoom(String department, String room) throws SQLException {
        Connection connection = null;
        PreparedStatement checkDeptStmt = null;
        PreparedStatement checkRoomStmt = null;
        ResultSet deptResultSet = null;
        ResultSet roomResultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Check if department exists
            String checkDeptQuery = "SELECT COUNT(*) FROM department WHERE department = ?";
            checkDeptStmt = connection.prepareStatement(checkDeptQuery);
            checkDeptStmt.setString(1, department);
            deptResultSet = checkDeptStmt.executeQuery();

            if (deptResultSet.next() && deptResultSet.getInt(1) == 0) {
                return false;
            }

            // Check if room exists
            String checkRoomQuery = "SELECT COUNT(*) FROM room WHERE room = ?";
            checkRoomStmt = connection.prepareStatement(checkRoomQuery);
            checkRoomStmt.setString(1, room);
            roomResultSet = checkRoomStmt.executeQuery();

            if (roomResultSet.next() && roomResultSet.getInt(1) == 0) {
                return false;
            }

            return true;

        } finally {
            // Close all resources
            if (deptResultSet != null) deptResultSet.close();
            if (roomResultSet != null) roomResultSet.close();
            if (checkDeptStmt != null) checkDeptStmt.close();
            if (checkRoomStmt != null) checkRoomStmt.close();
            if (connection != null) connection.close();
        }
    }

    private void addCourse(String courseName, String courseID, String courseType, String teacherName, String timing, String credit, String day, String classType, String department, String room) throws SQLException {
        Connection connection = null;
        PreparedStatement insertCourseStmt = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String insertCourseQuery = "INSERT INTO courses (CourseName, CourseID, CourseType, Teacher, Time, CreditHours, Day, ClassType, Department, Room) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            insertCourseStmt = connection.prepareStatement(insertCourseQuery);
            insertCourseStmt.setString(1, courseName);
            insertCourseStmt.setString(2, courseID);
            insertCourseStmt.setString(3, courseType);
            insertCourseStmt.setString(4, teacherName);
            insertCourseStmt.setString(5, timing);
            insertCourseStmt.setString(6, credit);
            insertCourseStmt.setString(7, day);
            insertCourseStmt.setString(8, classType);
            insertCourseStmt.setString(9, department);
            insertCourseStmt.setString(10, room);
            insertCourseStmt.executeUpdate();
        } finally {
            // Close all resources
            if (insertCourseStmt != null) insertCourseStmt.close();
            if (connection != null) connection.close();
        }
        //it should add the course to the department table as well
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String insertCourseQuery = "INSERT INTO " + department + " (CourseName, CourseID, CourseType, Teacher, Time, CreditHours, Day, ClassType, Department, Room) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            insertCourseStmt = connection.prepareStatement(insertCourseQuery);
            insertCourseStmt.setString(1, courseName);
            insertCourseStmt.setString(2, courseID);
            insertCourseStmt.setString(3, courseType);
            insertCourseStmt.setString(4, teacherName);
            insertCourseStmt.setString(5, timing);
            insertCourseStmt.setString(6, credit);
            insertCourseStmt.setString(7, day);
            insertCourseStmt.setString(8, classType);
            insertCourseStmt.setString(9, department);
            insertCourseStmt.setString(10, room);
            insertCourseStmt.executeUpdate();
        } finally {
            // Close all resources
            if (insertCourseStmt != null) insertCourseStmt.close();
            if (connection != null) connection.close();
        }
    }

    public static void main(String[] args) {
        AddingCourse addingCourse = new AddingCourse(null);
    }
}
