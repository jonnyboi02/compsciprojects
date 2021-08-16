import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class MainMenu extends JFrame implements ActionListener{
    private JTextField sharesToBuy;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane pane;
    private JButton btnExecuteOrder;
    private JLabel lblFunds;
    private JLabel lblProfit;
    private JButton btnStockNotifications;
    private JLabel orderOutcome;
    private JButton btnViewPortfolio;
    private JButton btnStockInfo;
    
    private ArrayList<Stock> shares;
    private User user;
    private Transactions transactions;
    
    
    private ArrayList<String> notifications;
    //to add, add the instance of shares, user and transactions
    
    
    //constructor for the mainmenu.
    public MainMenu(User user) {
        super("Stock Market"); //sets the name of the window
        shares = new ArrayList<>();
        notifications = new ArrayList<>();
        
          
        //creates a few stocks by calling the constructor and storing them into the shares arraylist
        Stock GBPUSD = new Currency("GBP/USD", "GBP/USD", 1.38, "GBP", "USD");
        shares.add(GBPUSD);
        
        Stock apple = new Company("Apple", "AAPL", 134.50,  "Tim Cook" );
        shares.add(apple);
        
        
        Stock amazon = new Company("Amazon", "AMZN", 3379.09 , "Jeff Bezos");
        shares.add(amazon);
        
        Stock litecoin = new CryptoCurrency("Litecoin", "LTC/GBP", 84000000, 5, 206);
        shares.add(litecoin);
        
        Stock bitcoin = new CryptoCurrency("Bitcoin", "BTC/GBP", 21000000, 8, 45000);
        shares.add(bitcoin);
        
        Stock oil = new Commodities("Oil", "CL", 65, 1650585140000L); //we must use L for the long literal
        shares.add(oil);
        
        Stock microsoft = new Company("Microsoft", "MSFT", 260, "Bill Gates");
        shares.add(microsoft);
        
        Stock tesla = new Company("Tesla", "TSLA", 740, "Elon Musk");
        shares.add(tesla);
        
        this.user = user;
        transactions = new Transactions(); //creates instance of Transaction class
        transactions.readInStocks(user, shares);
        
        
        
        sharesToBuy = new JTextField();
        model = new NonEditableTable();
        table = new JTable();
        pane = new JScrollPane(table);
        btnExecuteOrder = new JButton("Execute Order");
        lblFunds = new JLabel("Funds:");
        lblProfit = new JLabel("Profit:");
        btnStockNotifications = new JButton("Stock Notifications");
        orderOutcome = new JLabel("");
        btnViewPortfolio = new JButton("View Portfolio");
        btnStockInfo = new JButton("Stock Info");
        
        //calling the methods to update the prices and the 
        autoupdateBalance(user);
        autoUpdatePrices(shares);

        getContentPane().setForeground(Color.white);
        setBounds(100,100,757,610);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        
        Object[] Columns = {"Stock Name", "Type", "Price", "Change"};
        
        model.setColumnIdentifiers(Columns);
        
        table.setBounds(40, 472, 690, -442);
        table.setModel(model);
        table.setRowHeight(30);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false); //prevents user from rearranging columns
        table.getTableHeader().setEnabled(false); //prevents rows being organised

        pane.setForeground(Color.red);
        pane.setBackground(Color.white);
        pane.setBounds(10,10,725,443);
        getContentPane().add(pane);
        
        btnExecuteOrder.setBounds(49, 492, 172, 25);
        getContentPane().add(btnExecuteOrder);
        
        btnExecuteOrder.addActionListener(this);
        
        JLabel lblSharesToBuy = new JLabel("Shares to buy:");
        lblSharesToBuy.setBounds(10, 465, 124, 15);
        getContentPane().add(lblSharesToBuy);
        
        sharesToBuy.setBounds(129, 463, 114, 19);
        getContentPane().add(sharesToBuy);
        sharesToBuy.setColumns(10);
        
        
        
        JLabel lblUserInformation = new JLabel("User Information:");
        lblUserInformation.setBounds(276, 465, 124, 15);
        getContentPane().add(lblUserInformation);
        
        lblFunds.setBounds(276, 492, 140, 15);
        getContentPane().add(lblFunds);
        
        lblProfit.setBounds(276, 519, 193, 15);
        getContentPane().add(lblProfit);
        
        btnViewPortfolio = new JButton("View Portfolio");
        btnViewPortfolio.setBounds(497, 465, 165, 42);
        btnViewPortfolio.addActionListener(this); 
        getContentPane().add(btnViewPortfolio);
        
        
        btnStockNotifications.setBounds(276, 546, 211, 25);
        getContentPane().add(btnStockNotifications);
        btnStockNotifications.addActionListener(this);
        
        orderOutcome = new JLabel("");
        orderOutcome.setBounds(10, 539, 260, 15);
        getContentPane().add(orderOutcome);
        
        
        btnStockInfo.setBounds(545, 546, 117, 25);
        getContentPane().add(btnStockInfo);
        btnStockInfo.addActionListener(this);
        
        
        revalidate();
        setVisible(true);
        
        
    }
    

    public void updateNotifications(ArrayList<Stock> shares) {
         for (int x = 0; x<shares.size();x++) {
                String checkAlert = shares.get(x).getAlerts(); //dynamic binding, 1% change for a currency is more significant than a company for example given the nature of the type of stock
                if (checkAlert.equals("")) {
                    continue; //if there is no news to report
                }
                else {
                    //user notifications are updated
                    notifications.add(checkAlert + " - " + getDate());
                }
            }
        
    }
    
    
    
    
    
    //automatically updates the price of the users profit etc. 
    public void autoUpdatePrices(ArrayList<Stock> shares) {
        Timer intervals = new Timer();
        intervals.schedule(new TimerTask(){
            public void run() {
                try {
                    updateMarket(shares);
                } catch (IOException e) {
                    
                }
                updateNotifications(shares);
                
            }
        },0,30000); //the api call is limited to 8 per minute thus the increase 
        
    }
    
    
    //updates the prices
    public void updateMarket(ArrayList<Stock> shares) throws IOException {
        model.setRowCount(0);//removes the rows from a table
        
        
        for (int x = 0;x<shares.size();x++) {
                shares.get(x).updateData(); //polymorphism, late dynamic binding
                changeMarketTable(shares.get(x)); //changes the gui of the table
               
                
                
                
        }
        model.setRowCount(shares.size());
    }
    
    
    public void changeMarketTable(Stock share) {
        Object[] row = {share.getName(), share.getType(), share.getSellPrice(), share.getChange()};
        model.addRow(row); //adds the stock to the row
        
    }
        

    //automatically updates the price of the users profit etc. 
    public void autoupdateBalance(User user) {
        Timer intervals = new Timer();
        intervals.schedule(new TimerTask(){
            public void run() {
                updateDetails(user);
                
            }
        },0,500);
        
    }
    
    //update the users profit
    public void updateDetails(User user) {
        
        
        if (transactions.getSharesPurchased().size()==0) {
            
        }
        else {
            user.updateProfit(transactions);
        }
        lblFunds.setText("Funds: "+user.getBalance());
        lblProfit.setText("Profit: "+user.getProfit());
        
    }

    @Override //determine behaviour of when the button is pressed
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Execute Order")) {
            
            orderOutcome.setText(""); //so that this can be updated
            
            try {
                
                int index = table.getSelectedRow();
                
                if (sharesToBuy.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "Nothing is entered in the shares text box");
                }
                
                else if (index>=0 && user.getBalance()<(Double.parseDouble(sharesToBuy.getText()))*(shares.get(index).getBuyPrice())) {
                    
                    orderOutcome.setText("Insufficient Funds");
                }

                //checks if the user can purchase the desired number of shares
                else if (index>=0) {
                    double sharesToPurchase = Double.parseDouble(sharesToBuy.getText());
                    double totalBuyPrice = sharesToPurchase*shares.get(index).getBuyPrice();
                    addTransaction(totalBuyPrice,shares, index , sharesToPurchase , user ,transactions);
                    
                    user.updateProfit(transactions);
                    JOptionPane.showMessageDialog(this, "Purchase Completed. Your new Balance is: "+RoundNumber.round2DP(user.getBalance()));
                    
                }

                else {
                    JOptionPane.showMessageDialog(this, "No Row Selected");
                    sharesToBuy.setText("");
                }
            }catch(NumberFormatException e1 ) {
                JOptionPane.showMessageDialog(this, "Invalid Input");
                sharesToBuy.setText(""); //resets the box
                
            }catch (IOException e1) {
                JOptionPane.showConfirmDialog(this, "Error in prcessing transaction to account");
                sharesToBuy.setText(""); 
            }

        }
        else if (e.getActionCommand().equals("View Portfolio")) {
            new ViewPortfolio(transactions, user);
        }
        
        else if(e.getActionCommand().equals("Stock Notifications")) {
            new NotificationsGUI(notifications);
        }
        
        else if(e.getActionCommand().equals("Stock Info")) {
            try {
                int selectedRow = table.getSelectedRow();
                new StockInfo(shares.get(selectedRow)); 
            }catch(IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "No Row has been selected");
            }
        }
            
        
        
    }
    
    
       //method that record the current transaction that was bought
    public void addTransaction(double price, ArrayList<Stock> shares, int index, double sharesToPurchase, User user, Transactions transactions) throws IOException {
        Stock currentShare = shares.get(index);
        String detailstoAdd = currentShare.getName()+" "+currentShare.getAbbreviation()+" bought for "+ RoundNumber.round2DP(price);
        orderOutcome.setText(detailstoAdd); //changes the text in the gui
       
        transactions.addTransaction(user,sharesToPurchase, shares.get(index));
    }
     
   

    
    //obtains the time 
    public static String getDate() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        while (true) {
            Date date = new Date(System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return (formatter.format(date));
            
        }
    }
}
