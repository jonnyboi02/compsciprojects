import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class DemoUserFundGUI extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField textMoneytoUse;
    private JButton btnEnterTheStock = new JButton("Enter the Stock Market");
    private User user;



    public DemoUserFundGUI(User user) {
        
        super("Demo User Funds");
        
        this.user = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 307, 183);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblEnterTheAmount = new JLabel("Enter the Amount to be used:");
        lblEnterTheAmount.setBounds(35, 35, 247, 15);
        contentPane.add(lblEnterTheAmount);
        
        textMoneytoUse = new JTextField();
        textMoneytoUse.setBounds(45, 57, 194, 19);
        contentPane.add(textMoneytoUse);
        textMoneytoUse.setColumns(10);
        
        //JButton btnEnterTheStock = new JButton("Enter the Stock Market");
        btnEnterTheStock.setBounds(35, 88, 206, 25);
        btnEnterTheStock.addActionListener(this);
        contentPane.add(btnEnterTheStock);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Double fundsToUse= Double.parseDouble(textMoneytoUse.getText());
            user.setBalance(fundsToUse);
            this.setVisible(false);
            new MainMenu(user);
            
        }catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input Entered");
                textMoneytoUse.setText("");
        }
    }
        
}

