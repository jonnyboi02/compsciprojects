import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class Transactions {
    
    
    private ArrayList<Stock> boughtStocks;
    private ArrayList<Double> sharesPurchased;
    private ArrayList<Double> stockpriceBought;


    
    
    public Transactions() {
        
        boughtStocks = new ArrayList<>();
        sharesPurchased = new ArrayList<>();
        stockpriceBought = new ArrayList<>();
        
    }
    
    public ArrayList<Stock> getStocks(){
        return boughtStocks;
    }
    
    public ArrayList<Double> getSharesPurchased(){
        return sharesPurchased;
    }
    
    public ArrayList<Double> getStockpriceBought(){
        return stockpriceBought;
    }

    
    //this instance method will change number of stock bought
    public void processTransaction(int index, double sharesToSell, User user) throws IOException {
        Stock stockToProcess = boughtStocks.get(index);
        double totalSellPrice = sharesToSell *boughtStocks.get(index).getBuyPrice();
        
        String toAdd  = stockToProcess.getName()+" "+stockToProcess.getAbbreviation() + " sold for "+totalSellPrice+" "+MainMenu.getDate();
        
        
        sharesPurchased.set(index, sharesPurchased.get(index)-sharesToSell);
    
        
        
        //if all the shares are sold by the user
        if (sharesPurchased.get(index)==0) {
            boughtStocks.remove(index);
            sharesPurchased.remove(index);
            stockpriceBought.remove(index);
        }
        
       
        
        
        
        user.updateBalance(-totalSellPrice);
        user.updateProfit(this);
      
        
        if(user instanceof realMoneyUser) {
            updateUserBalanceFile(user.getBalance(), user.getProfit(), user.getName());
            
        }
        boughtStocksFile(user);
        
        
        
    }

    
    //returns the number of shares that the user can enter to sell
    public Double maxNumberOfShares(int index) {
        
        return sharesPurchased.get(index);
    
    }

    
    
    //adds the next transaction
    public void addTransaction(User user, double sharesBought, Stock stock) throws IOException {
        user.updateBalance(RoundNumber.round2DP(sharesBought* stock.getBuyPrice()) );
        boughtStocks.add(stock);
        sharesPurchased.add(sharesBought);
        stockpriceBought.add(stock.getSellPrice());
        boughtStocksFile(user); //update the text file of the users inventory
        if (user instanceof realMoneyUser) {
            updateUserBalanceFile(user.getBalance(),user.getProfit(), user.getName());
            
        }
    
        

    }
    
    
    //save the users stocks so that it can be used for future sessions
    public void boughtStocksFile(User user) throws IOException {
        PrintWriter outputStream = new PrintWriter(new FileWriter(System.getProperty("user.dir")+"/"+user.getName())); //saves the stocks bought for real user
        for (int x =0; x<boughtStocks.size(); x++) {
            String toSave = boughtStocks.get(x).getName()+","+sharesPurchased.get(x)+","+stockpriceBought.get(x);
            outputStream.println(toSave);
            
        }
        outputStream.close();
            
        
    }
    

    public void readInStocks(User user, ArrayList<Stock> shares) {
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+user.getName()));
            String currentLine = inputStream.readLine();
            while (currentLine!=null) {
                String[] splitLine = currentLine.split(",");
                for (int x = 0; x<shares.size(); x++) {
                    if (shares.get(x).getName().equals(splitLine[0])) {
                        boughtStocks.add(shares.get(x)); //add the current stock to the users portfolio
                        break;
                    }
                }
                sharesPurchased.add(Double.parseDouble(splitLine[1]));
                stockpriceBought.add(Double.parseDouble(splitLine[2]));
                
                currentLine = inputStream.readLine();
                
                
                
                
                
                
            }
        } catch (IOException e) {
            
        }
        
    }
    
 
    
    
    @Override
    //overriding the toString method
    //outputs the total of shares bought
    public String toString() {
        for (int x = 0; x<boughtStocks.size();x++) {
            System.out.println((x+1)+"."+"\t Stock Name:"+boughtStocks.get(x).getName()+ "\t Shares:"+sharesPurchased.get(x)+"\t Profit if closed now:"+(RoundNumber.round2DP((stockpriceBought.get(x)*sharesPurchased.get(x))-((boughtStocks.get(x).getSellPrice()*sharesPurchased.get(x))))));
        }
        return "";
    
    }
    
    //update the balance for those on the real Account
    public static void updateUserBalanceFile(double balance, double profit, String name) throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/account.txt"));
        ArrayList<String> userAccounts = new ArrayList<>();
        String currentLine = inputStream.readLine();
        while (currentLine!=null) {
            userAccounts.add(currentLine);
            currentLine = inputStream.readLine();
            
        }
        
        String newDetails="";
        for (int x =0;x<userAccounts.size(); x++) {
            if (userAccounts.get(x).contains(name)) {
                String [] splitDetails = userAccounts.get(x).split(",");
                splitDetails [3] = Double.toString(RoundNumber.round2DP(balance));
                splitDetails [4] = Double.toString(RoundNumber.round2DP(profit));
                newDetails = splitDetails[0]+","+splitDetails[1]+","+splitDetails[2]+","+splitDetails[3]+","+splitDetails [4];
                userAccounts.remove(x);
                break;
                
            }
        }
        PrintWriter writeToAccounts = new PrintWriter(new FileWriter(System.getProperty("user.dir")+"/account.txt"));
        for (int x = 0 ;x<userAccounts.size(); x++) {
            writeToAccounts.println(userAccounts.get(x));
        }
        writeToAccounts.println(newDetails);
        writeToAccounts.close();
        
        
        
    }
    


    

    

}
