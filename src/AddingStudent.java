/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddingStudent extends JDialog {
    private JTextField tfDepartment;
    private JTextField tfEnrolment;
    private JButton btAdd;
    private JPanel AddingStudentPanel;

    public AddingStudent(JFrame parent) {
        super(parent, "Add Student", true);
        setContentPane(AddingStudentPanel);
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudent();
            }

            private void AddStudent() {
                String department = tfDepartment.getText();
                String enrolment = tfEnrolment.getText();

                if (department.isEmpty() || enrolment.isEmpty()) {
                    JOptionPane.showMessageDialog(AddingStudent.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {


                    if (checkDepartment(department)) {
                        addStudent(enrolment, department);
                        JOptionPane.showMessageDialog(AddingStudent.this, "Student added successfully.");
                    } else {
                        JOptionPane.showMessageDialog(AddingStudent.this, "Department does not exist.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddingStudent.this, "An error occurred while adding the student.");
                }
            }

            private boolean checkDepartment(String department) throws SQLException {
                final String URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";
                String checkDeptQuery = "SELECT * FROM department WHERE Department = ?";

                try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                     PreparedStatement checkDeptStmt = connection.prepareStatement(checkDeptQuery)) {

                    checkDeptStmt.setString(1, department);
                    try (ResultSet deptResultSet = checkDeptStmt.executeQuery()) {
                        return deptResultSet.next();
                    }
                }
            }

            private void addStudent(String enrolment, String department) {
                final String URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";
                String addStudentQuery = "INSERT INTO addedstudent (Enrolment, Department) VALUES (?, ?)";

                try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                     PreparedStatement addStudentStmt = connection.prepareStatement(addStudentQuery)) {

                    addStudentStmt.setString(1, enrolment);
                    addStudentStmt.setString(2, department);
                    addStudentStmt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        AddingStudent addingStudent = new AddingStudent(null);
    }
}
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddingStudent extends JDialog {
    private JTextField tfDepartment;
    private JTextField tfEnrolment;
    private JButton btAdd;
    private JPanel AddingStudentPanel;

    public AddingStudent(JFrame parent) {
        super(parent, "Add Student", true);
        setContentPane(AddingStudentPanel);
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudent();
            }

            private void AddStudent() {
                String department = tfDepartment.getText();
                String enrolment = tfEnrolment.getText();

                if (department.isEmpty() || enrolment.isEmpty()) {
                    JOptionPane.showMessageDialog(AddingStudent.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    if (checkDepartment(department)) {
                        boolean added = addStudent(enrolment, department);
                        if (added) {
                            JOptionPane.showMessageDialog(AddingStudent.this, "Student added successfully.");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(AddingStudent.this, "Failed to add student.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(AddingStudent.this, "Department does not exist.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddingStudent.this, "An error occurred while adding the student: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            private boolean checkDepartment(String department) throws SQLException {
                final String URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";
                String checkDeptQuery = "SELECT * FROM department WHERE Department = ?";

                try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                     PreparedStatement checkDeptStmt = connection.prepareStatement(checkDeptQuery)) {

                    checkDeptStmt.setString(1, department);
                    try (ResultSet deptResultSet = checkDeptStmt.executeQuery()) {
                        return deptResultSet.next(); // returns true if department exists, false otherwise
                    }
                }
            }

            private boolean addStudent(String enrolment, String department) {
                final String URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";
                String addStudentQuery = "INSERT INTO addedstudent (Enrolment, Department) VALUES (?, ?)";

                try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                     PreparedStatement addStudentStmt = connection.prepareStatement(addStudentQuery)) {

                    addStudentStmt.setString(1, enrolment);
                    addStudentStmt.setString(2, department);
                    addStudentStmt.executeUpdate();
                    return true;
                } catch (SQLException ex) {
                    // Check for SQL state or error code specific to unique constraint violations
                    if (ex.getSQLState().equals("23000")) { // SQL state for unique constraint violation
                        JOptionPane.showMessageDialog(AddingStudent.this, "Enrollment number already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ex.printStackTrace();
                    }
                    return false;
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        AddingStudent addingStudent = new AddingStudent(null);
    }
}
