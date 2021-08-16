import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;



public abstract class User {
    
    private String userName;
    private String passWord;
    private double balance;
    private double profit;
    //Overloading
    public User(String userName, String passWord, double balance) {
        this.userName = userName;
        this.passWord = passWord;
        this.balance = balance;
        this.profit = 0;
        

    }
    
    //Overloading
    public User(String userName, String passWord, double balance, double profit) {
        this.userName = userName;
        this.passWord = passWord;
        this.balance = balance;
        this.profit = profit;

    }
    
    
    
    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        this.profit = 0;
        
    }
    
    public User() {
        this.userName = "";
        this.passWord = "";
        this.profit = 0;
    }
    
    
    public void setName(String userName) {
        this.userName=  userName;
    }
    public void setPassword(String passWord) {
        this.passWord = passWord;
    }
    
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    
    

    public String getName() {
        return userName;
    }
    
    public String getPassword() {
        return passWord;
    }
    
    public double getBalance() {
        return balance;
    }
    public double getProfit() {
        return profit;
    }
    

    
    //updates the balance of the user
    public void updateBalance(double transaction) {
        this.balance -= transaction;
        this.balance = RoundNumber.round2DP(balance);

    }
    
    
    public void updateProfit(Transactions transaction) {
        ArrayList<Double> pricePaid = new ArrayList<>();
        ArrayList<Stock> boughtStocks = new ArrayList<>();
        ArrayList<Double> sharesPurchased = new ArrayList<>();
        pricePaid = transaction.getStockpriceBought();
        boughtStocks = transaction.getStocks();
        sharesPurchased = transaction.getSharesPurchased();
        double total = 0;
        double paid  = 0;
        for (int x = 0;x<transaction.getSharesPurchased().size(); x++) {
            total +=boughtStocks.get(x).getBuyPrice()*sharesPurchased.get(x);
            paid +=pricePaid.get(x)*sharesPurchased.get(x);
        }
        
        profit = RoundNumber.round2DP(total - paid);
        
    }
    

    
    //checks if the user input is valid
    
    public abstract void Login() ;
    
    //user input for strings
    public static String inputString(String question) {
        System.out.println(question);
        Scanner scan  = new Scanner (System.in);
        return scan.nextLine();
    }



    
}


     

