import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JLabel;

public class StockInfo extends JFrame{

    private JPanel contentPane;
    private JTextPane textPane;
    private Stock stock;


    public StockInfo(Stock currentStock) {
        
        stock = currentStock;
        
        
        setBounds(100, 100, 535, 189);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBounds(24, 25, 478, 101);
        contentPane.add(textPane);
        textPane.setText(stock.toString());
        autoupdateInfo(stock);
        
        JLabel lblInfoAboutThe = new JLabel("Info about the stock:");
        lblInfoAboutThe.setBounds(24, 0, 180, 15);
        contentPane.add(lblInfoAboutThe);
        setVisible(true);
    }
    
    
    public void autoupdateInfo(Stock stock) {
        Timer intervals = new Timer();
        intervals.schedule(new TimerTask(){
            public void run() {
                updateStockInfo();
                
            }
        },0,500);
        
    }
    
    public void updateStockInfo() {
        textPane.setText("");
        textPane.setText(stock.toString());
        
    }

    
}
