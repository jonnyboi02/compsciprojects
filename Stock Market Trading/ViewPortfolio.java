import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ViewPortfolio extends JFrame implements ActionListener{
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane pane;
    
    private ArrayList<Stock> shares;
    private User user;
    private Transactions transactions;
    private JTextField sharestextField;
    
    private JLabel labelshareClosedInfo;
    private JButton btnSellCertainAmount;
    private JButton btnCloseSelectedShare;
    private JLabel lblEnterSharesToSell;
    
    
    //constructor for the mainmenu.
    public ViewPortfolio(Transactions transactions, User user) {
        super("View Portfolio"); //sets the name of the window
        
        model = new NonEditableTable();
        table = new JTable();
        pane = new JScrollPane(table);
        
        shares = new ArrayList<>();
        
        labelshareClosedInfo = new JLabel("");
        btnSellCertainAmount = new JButton("Sell Certain Amount of Shares");
        btnCloseSelectedShare = new JButton("Close Selected Share");
        lblEnterSharesToSell = new JLabel("Enter Shares to Sell");
        this.transactions = transactions;
        this.user = user;
        
        autoupdatePortfolio(); //update the price of the stock bought
        
        getContentPane().setForeground(Color.white);
        setBounds(100,100,758,482);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        
        Object[] Columns = {"Stock Bought", "Shares Owned", "Current Price", "Purchase Price", "Profit"};
        
        model.setColumnIdentifiers(Columns);
        
        table.setBounds(40, 472, 690, -442);
        table.setModel(model);
        table.setRowHeight(30);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false); //prevents user from rearranging columns
        table.getTableHeader().setEnabled(false); //prevents rows being organised

        
        
        
        pane.setForeground(Color.red);
        pane.setBackground(Color.white);
        pane.setBounds(10,10,725,291);
        getContentPane().add(pane);
        
        sharestextField = new JTextField();
        sharestextField.setBounds(262, 313, 176, 19);
        getContentPane().add(sharestextField);
        sharestextField.setColumns(10);
        
        
        btnSellCertainAmount.setBounds(129, 374, 279, 25);
        getContentPane().add(btnSellCertainAmount);
        btnSellCertainAmount.addActionListener(this);
        
        
        lblEnterSharesToSell.setBounds(96, 315, 216, 15);
        getContentPane().add(lblEnterSharesToSell);
        
        btnCloseSelectedShare.setBounds(480, 317, 210, 100);
        getContentPane().add(btnCloseSelectedShare);
        btnCloseSelectedShare.addActionListener(this);
        
        
        labelshareClosedInfo.setBounds(139, 414, 299, 15);
        getContentPane().add(labelshareClosedInfo);
        
        revalidate();
        setVisible(true);

    }
    

    
    //automatically updates the price of the users profit etc. 
    public void autoupdatePortfolio() {
        Timer intervals = new Timer();
        intervals.schedule(new TimerTask(){
            public void run() {
               updatePortfolio(transactions);
                
            }
        },0,3000);
        
    }
    
    //updates the rows of the table
    public void updatePortfolio(Transactions transaction) {
        model.setRowCount(0);
        ArrayList<Stock> boughtStocks = transaction.getStocks(); 
        ArrayList<Double> sharesPurchased = transaction.getSharesPurchased();
        ArrayList<Double> stockpriceBought = transaction.getStockpriceBought();
        
        for(int  x = 0;x<boughtStocks.size();x++) {
            Stock selectedStock = boughtStocks.get(x);
            Double Profit =(RoundNumber.round2DP(((boughtStocks.get(x).getSellPrice()*sharesPurchased.get(x))-(stockpriceBought.get(x)*sharesPurchased.get(x)))));
            Object[] rows = {selectedStock.getName(), sharesPurchased.get(x), selectedStock.getSellPrice(), stockpriceBought.get(x) , Profit};
            model.addRow(rows);
        }
        model.setRowCount(boughtStocks.size());
    }
    

    

    @Override //determine behaviour of when the button is pressed
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
            if (e.getActionCommand().equals("Sell Certain Amount of Shares")) {
                try {
                
                
        
                    int index = table.getSelectedRow();

            
                    
                    if (sharestextField.getText().equals("")) {
                        JOptionPane.showMessageDialog(this, "Nothing is entered");
                    }
                    else if ((index>=0) && Double.parseDouble(sharestextField.getText())>transactions.maxNumberOfShares((index)) || Double.parseDouble(sharestextField.getText())<0 ) {
                        JOptionPane.showMessageDialog(this, "You can only sell a maximum of "+ transactions.maxNumberOfShares((index)) +" of shares. Please enter a value in this range");
                        sharestextField.setText(""); //resets the text box
                        
                    }
                    
                    else if(index>=0) {
                        transactions.processTransaction(index, Double.parseDouble(sharestextField.getText()), user);                        
                        JOptionPane.showMessageDialog(this, sharestextField.getText()+" of shares closed");
                        labelshareClosedInfo.setText("Your new balance is :"+RoundNumber.round2DP(user.getBalance()));
                        //JOptionPane.showMessageDialog(this, "No Row selected");
                        updatePortfolio(transactions); //this changes the table
                    }

                    else {
                        JOptionPane.showMessageDialog(this, "No Row selected");
                    }
                }catch(NumberFormatException ep){
                    JOptionPane.showMessageDialog(this, "Invalid input entered");
                }catch( IOException ep) {
                    JOptionPane.showMessageDialog(this, "Error when processing transaction");
                }catch (IndexOutOfBoundsException ep) {
                    JOptionPane.showMessageDialog(this, "No Row selected");
                }
                
            }
            
            else if(e.getActionCommand().equals("Close Selected Share")) {
                //System.out.println("hi");
                
                
                int index = table.getSelectedRow();
                if (index>=0) {
                    double maximum = transactions.maxNumberOfShares((index));
                    try {
                        transactions.processTransaction(index, maximum, user);
                        updatePortfolio(transactions);//need to add method that adds the money
                        JOptionPane.showMessageDialog(this, maximum+" of shares closed");
                        labelshareClosedInfo.setText("Your new balance is :"+RoundNumber.round2DP(user.getBalance()));
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(this, "Error when processing transaction");
                    }        
                    
                    
    
                
                }
                else {
                    JOptionPane.showMessageDialog(this, "No row selected");
                }
            }

        
        
    }
    
    
    
    
    
}
