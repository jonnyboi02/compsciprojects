import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class NotificationsGUI extends JFrame implements ActionListener{

    private DefaultTableModel model = new NonEditableTable();
    private JTable table  = new JTable();
    private JScrollPane pane = new JScrollPane(table);
    private JButton btnDeleteNotification = new JButton("Delete Notification");
    private ArrayList<String> notifications;

    
    public NotificationsGUI(ArrayList<String> notifications) {
        
        super("Notifications");
        
        this.notifications = notifications;
        autoUpdateNotifications();
        
        getContentPane().setForeground(Color.white);
        setBounds(100,100,757,610);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        Object[] Columns = {"News"};
        model.setColumnIdentifiers(Columns);
        
        table.setBounds(40, 472, 690, -442);
        table.setModel(model);
        table.setRowHeight(30);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false); //prevents user from rearranging columns
        table.getTableHeader().setEnabled(false); //prevents rows being organised


        pane.setForeground(Color.red);
        pane.setBackground(Color.white);
        pane.setBounds(10,10,725,429);
        getContentPane().add(pane);
        
        
        btnDeleteNotification.setBounds(232, 507, 293, 25);
        btnDeleteNotification.addActionListener(this);
        
        getContentPane().add(btnDeleteNotification);
        
        
        
        //contentPane = new JPanel();
        setVisible(true);
    }
    
    //automatically updates the price of the users profit etc. 
    public void autoUpdateNotifications() {
        Timer intervals = new Timer();
        intervals.schedule(new TimerTask(){
            public void run() {
                updateNotifications();
                
            }
        },0,3000); 
        
    }
    
    public void updateNotifications() {
        
        model.setRowCount(0);
        for (int x  = notifications.size()-1; x>=0;x--) {
            Object[] toAdd = {notifications.get(x)};
            model.addRow(toAdd);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        try {
        //removing notification
            int row = table.getSelectedRow();
            model.removeRow(row);
            notifications.remove(notifications.size()-(1+row));
        }catch(IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, "No Row Selected");
        }
    }
    
    
    
    
}
