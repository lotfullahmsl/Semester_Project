import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JDialog{
    private JPanel MainPanel;
    private JButton btFaculty;
    private JButton btStudent;

    public MainForm (JFrame parent) {
        super(parent, "Main Form", true);
        setContentPane(MainPanel);
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);





        btFaculty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacultyForm facultyForm = new FacultyForm(null);

            }
        });

        this.setVisible(true);
    }




    public static void main(String[] args) {
        MainForm mainForm = new MainForm(null);
    }
}
