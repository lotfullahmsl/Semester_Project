import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentScores extends JDialog{
    private JTextField tfEnrollment;
    private JTextField tfDepartment;
    private JTextField tfCourse;
    private JTextField tfAsn1;
    private JTextField tfQ1;
    private JTextField tfAns2;
    private JTextField tfQ2;
    private JTextField tfMid;
    private JTextField tfAsn3;
    private JTextField tfQ3;
    private JTextField tfAsn4;
    private JTextField tfQ4;
    private JTextField tfFinal;
    private JButton btAdd;
    private JPanel ScorePanel;
    double total;
    String grade;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Muslimwal@2004";

    public StudentScores (JFrame parent){
        super(parent, "Student Scores", true);
        setContentPane(ScorePanel);
        setSize(700, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enrollment = tfEnrollment.getText();
                String department = tfDepartment.getText();
                String course = tfCourse.getText();
                String asn1 = tfAsn1.getText();
                String q1 = tfQ1.getText();
                String ans2 = tfAns2.getText();
                String q2 = tfQ2.getText();
                String mid = tfMid.getText();
                String asn3 = tfAsn3.getText();
                String q3 = tfQ3.getText();
                String asn4 = tfAsn4.getText();
                String q4 = tfQ4.getText();
                String finalScore = tfFinal.getText();
                double total;
                String grade;

                if (enrollment.isEmpty() || department.isEmpty() || course.isEmpty() || asn1.isEmpty() || q1.isEmpty() || ans2.isEmpty() || q2.isEmpty() || mid.isEmpty() || asn3.isEmpty() || q3.isEmpty() || asn4.isEmpty() || q4.isEmpty() || finalScore.isEmpty()) {
                    JOptionPane.showMessageDialog(StudentScores.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // limit for quiz is 0 to 2.5 and for assignment its 0 to 5 and for mid its 0 to 20 and for final its 0 to 50 it should not take negative values


                if (Double.parseDouble(q1) < 0 || Double.parseDouble(q1) > 2.5) {
                    JOptionPane.showMessageDialog(StudentScores.this, "Quiz 1 score should be between 0 and 2.5", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Double.parseDouble(q2) < 0 || Double.parseDouble(q2) > 2.5) {
                    JOptionPane.showMessageDialog(StudentScores.this, "Quiz 2 score should be between 0 and 2.5", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Double.parseDouble(q3) < 0 || Double.parseDouble(q3) > 2.5) {
                    JOptionPane.showMessageDialog(StudentScores.this, "Quiz 3 score should be between 0 and 2.5", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Double.parseDouble(q4) < 0 || Double.parseDouble(q4) > 2.5) {
                    JOptionPane.showMessageDialog(StudentScores.this, "Quiz 4 score should be between 0 and 2.5", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Double.parseDouble(asn1) < 0 || Double.parseDouble(asn1) > 5) {
                    JOptionPane.showMessageDialog(StudentScores.this, "Assignment 1 score should be between 0 and 5", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Double.parseDouble(ans2) < 0 || Double.parseDouble(ans2) > 5) {
                    JOptionPane.showMessageDialog(StudentScores.this, "Assignment 2 score should be between 0 and 5", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Double.parseDouble(asn3) < 0 || Double.parseDouble(asn3) > 5) {
                    JOptionPane.showMessageDialog(StudentScores.this, "Assignment 3 score should be between 0 and 5", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Double.parseDouble(asn4) < 0 || Double.parseDouble(asn4) > 5) {
                    JOptionPane.showMessageDialog(StudentScores.this, "Assignment 4 score should be between 0 and 5", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Double.parseDouble(mid) < 0 || Double.parseDouble(mid) > 20) {
                    JOptionPane.showMessageDialog(StudentScores.this, "Mid score should be between 0 and 20", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Double.parseDouble(finalScore) < 0 || Double.parseDouble(finalScore) > 50) {
                    JOptionPane.showMessageDialog(StudentScores.this, "Final score should be between 0 and 50", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                total = Double.parseDouble(asn1) + Double.parseDouble(q1) + Double.parseDouble(ans2) + Double.parseDouble(q2) + Double.parseDouble(mid) + Double.parseDouble(asn3) + Double.parseDouble(q3) + Double.parseDouble(asn4) + Double.parseDouble(q4) + Double.parseDouble(finalScore);

                if (total >= 90) {
                    grade = "A+";
                } else if (total >= 85) {
                    grade = "A";
                } else if (total >= 80) {
                    grade = "A-";
                } else if (total >= 75) {
                    grade = "B+";
                } else if (total >= 70) {
                    grade = "B";
                } else if (total >= 65) {
                    grade = "B-";
                } else if (total >= 60) {
                    grade = "C+";
                } else if (total >= 55) {
                    grade = "C";
                } else if (total >= 50) {
                    grade = "C-";
                } else if (total >= 45) {
                    grade = "D+";
                } else if (total >= 40) {
                    grade = "D";
                } else {
                    grade = "F";
                }


                try {
                    if (checkEnrollmentAndDepartment(enrollment, department)) {
                        if (checkCourseAndDepartment(course, department)) {

                           if(addScores(enrollment, department, course, asn1, q1, ans2, q2, mid, asn3, q3, asn4, q4, finalScore, total, grade)){
                                 JOptionPane.showMessageDialog(StudentScores.this, "Scores added successfully.");
                            }
                            else {

                                JOptionPane.showMessageDialog(StudentScores.this, "Student Score is already added");
                                //print message that student score is already added do you want to edit or not

                            }
                        } else {
                            JOptionPane.showMessageDialog(StudentScores.this, "Course is not added by admin.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(StudentScores.this, "Student is not added by admin.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(StudentScores.this, "An error occurred while adding the scores.");
                }



            }


            private boolean addScores(String enrollment, String department, String course, String asn1, String q1, String ans2, String q2, String mid, String asn3, String q3, String asn4, String q4, String finalScore, double total, String grade) {
                boolean isAdded = false;
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "INSERT INTO scores (Enrollment, Department, Course, Assignment1, Quiz1, Assignment2, Quiz2, Mid, Assignment3, Quiz3, Assignment4, Quiz4, Final, Total, Grade) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, enrollment);
                    stmt.setString(2, department);
                    stmt.setString(3, course);
                    stmt.setString(4, asn1);
                    stmt.setString(5, q1);
                    stmt.setString(6, ans2);
                    stmt.setString(7, q2);
                    stmt.setString(8, mid);
                    stmt.setString(9, asn3);
                    stmt.setString(10, q3);
                    stmt.setString(11, asn4);
                    stmt.setString(12, q4);
                    stmt.setString(13, finalScore);
                    stmt.setDouble(14, total);
                    stmt.setString(15, grade);

                    isAdded = stmt.executeUpdate() > 0;

                } catch (Exception e) {
                    e.printStackTrace();
                }


                return isAdded;
            }

            private boolean checkCourseAndDepartment(String course, String department) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "SELECT * FROM courses WHERE CourseName = ? AND Department = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, course);
                    stmt.setString(2, department);
                    ResultSet rs = stmt.executeQuery();
                    return rs.next();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            }
            private boolean checkEnrollmentAndDepartment(String enrollment, String department) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "SELECT * FROM registerstudent WHERE Enrolment = ? AND Department = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, enrollment);
                    stmt.setString(2, department);
                    ResultSet rs = stmt.executeQuery();
                    return rs.next();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });
        setVisible(true);
    }
    public static void main(String[] args) {
        StudentScores studentScores = new StudentScores(null);
    }
}