

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;



public abstract class Stock {
    private String name;
    private String abbreviation;
    private String type;
    private double sellPrice;
    private double oldsellPrice;
    private double buyPrice;
    private double change;

    
    public Stock(String name, String abbreviation, String type, double initialPrice) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.type = type;
        this.sellPrice = initialPrice;
    
        
    }
    public double getOldSellPrice() {
        return oldsellPrice;
    }
    
    public void setOldSellPrice(double price) {
        oldsellPrice = price;
    }
    
    public void setSellPrice(double price) {
        sellPrice = price;
    }

    public String getName() {
        return name;
    }
    
    public String getAbbreviation() {
        return abbreviation;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public double getSellPrice() {
        return sellPrice;
    }
    
    public double getBuyPrice() {
        return buyPrice;
    }
    
    public double getChange() {
        return change;
    }
    
    public String toString() {
        return(getName()+"\t"+ getAbbreviation()+"\t Sell Price:"+getSellPrice()+"\t Buy Price:"+getBuyPrice()+"\t Change:"+(RoundNumber.round2DP(getChange())+"%"));
    }
    
    //this will inform the user of any recent news
    public String getAlerts() {
        if (this.change>0.2) {
            String[] phrase = {" is booming. Will this continue?", " rises. Should you invest now?", " is on the rise. Will the bubble burst?", " leaps. What will happen next?"};
            
            int choose = (int)Math.random()*3;
            return this.name+phrase[choose]+" Change:"+RoundNumber.round2DP(this.change)+"%"; 
        }
        else if (-0.2>this.change) {
            String[] phrase = {" is falling. How long will this continue?", " tumbles. What will happen next?", " dips. Will they bounce back?", " melts away. Is its shimmer fading?"};
            int choose = (int)Math.random()*3;
            return this.name+phrase[choose]+" Change:"+RoundNumber.round2DP(this.change)+"%";
        }
        return "";
    }
    
    
    //changing of the sell price should be overridden by the subclass due to the different volatility depending on the stock
    public abstract void changeSellPrice() throws IOException;
    
    //changes the buy price for that stock
    public void changeBuyPrice() {
        
        buyPrice = sellPrice;
        
    }
    
    public String getBasicInfo() {
        return this.toString();
    }

    

    
    //calculates the percentage change after the newprice
    public void newChange() {
        
        change = RoundNumber.round2DP(((sellPrice - oldsellPrice )/ sellPrice)*100);
        
        
    }
    
    
    
    //updates the prices of each and calculates the new change as a result of the change
    public void updateData() throws IOException {

        changeSellPrice();
        changeBuyPrice();
        newChange();
        
    }


  
    

}
