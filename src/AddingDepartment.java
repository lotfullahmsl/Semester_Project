import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddingDepartment extends JDialog{
    private JTextField tfDepartment;
    private JButton btAdd;
    private JPanel DepartmentAddingPanel;

    public AddingDepartment(JFrame parent){
        super(parent, "Add Department", true);
        setContentPane(DepartmentAddingPanel);
        setSize(600, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);




        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDepartment();
            }

            private void addDepartment() {
                String department = tfDepartment.getText();
                if (department.isEmpty()) {
                    JOptionPane.showMessageDialog(AddingDepartment.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                addDepartmentToDB(department);
            }

                private void addDepartmentToDB(String department) {
                final String DB_URL = "jdbc:mysql://localhost:3306/projectdata";
                final String USER = "root";
                final String PASS = "Muslimwal@2004";

                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    String query = "INSERT INTO department (Department) VALUES (?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, department);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(AddingDepartment.this, "Department added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                    String query2 = "CREATE TABLE `projectdata`.`"+department+"` (\n" +
                            "  `CourseName` VARCHAR(100) NOT NULL,\n" +
                            "  `CourseID` VARCHAR(100) NOT NULL,\n" +
                            "  `CourseType` VARCHAR(50) NOT NULL,\n" +
                            "  `Teacher` VARCHAR(100) NOT NULL,\n" +
                            "  `Room` VARCHAR(45) NOT NULL,\n" +
                            "  `Department` VARCHAR(45) NOT NULL,\n" +
                            "  `Time` VARCHAR(45) NOT NULL,\n" +
                            "  `CreditHours` VARCHAR(45) NOT NULL,\n" +
                            "  `Day` VARCHAR(45) NOT NULL,\n" +
                            "  `ClassType` VARCHAR(45) NOT NULL,\n" +
                            "  UNIQUE INDEX `unique_day_time` (`Department` ASC, `Time` ASC, `Day` ASC));";
                    PreparedStatement stmt2 = conn.prepareStatement(query2);
                    stmt2.executeUpdate();




                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(AddingDepartment.this, "Department Already Added", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        AddingDepartment addingDepartment = new AddingDepartment(null);
    }
}
