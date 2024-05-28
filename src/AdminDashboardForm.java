import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardForm extends JDialog{
    private JComboBox <String> cbChoise;
    private JButton btOK;
    private JButton btCancel;
    private JPanel AdminDashBoardPannel;

    public AdminDashboardForm(JFrame parent) {
        super(parent, "Admin Dashboard", true);
        Container AdminDashboardPanel;
        setContentPane(AdminDashBoardPannel);
        setSize(730, 550);
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
        cbChoise.addItem("9. Search Student Record");
        cbChoise.addItem("10. Check Amount of Students");


        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        AdminDashboardForm adminDashboardForm = new AdminDashboardForm(null);
    }


}
