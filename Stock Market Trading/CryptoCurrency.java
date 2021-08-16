

import java.io.IOException;
import java.util.Random;



public final class CryptoCurrency extends Currency {
    
    private double cap;
    private double difficulty;
    private double difficultyChange;
    
    public CryptoCurrency(String name, String abbreviation, double cap, double difficulty, double sellPrice) {
        super(name, abbreviation, "CryptoCurrency",sellPrice);
        this.cap = cap;
        this.difficulty = difficulty;
        changeVolatility();
        setQuoteCurrency(name);
        setBaseCurrency(name);
        //set the type of currency in the super class
        
        
    }
    
    
    public double getCap() {
        return cap;
    }
    
    public double getDifficulty() {
        return difficulty;
    }
    
    //the difficulty of crypto, i.e computational power to mine increases
    public void changeDifficulty() {

        double MAX = 0.001;
        double MIN = 0.0001;
        
        int number = (int)Math.random()*(10);
        if (number%2==0){
            difficulty+=Math.random()*(MAX-MIN)+MIN;
    
        }
        else{
            difficulty-=Math.random()*(MAX-MIN)+MIN;
        }

        
    }
    
    
    
    @Override
    public String toString() {
        return(getName()+"\t"+ getAbbreviation()+"\t Sell Price:"+getSellPrice()+"\t Buy Price:"+getBuyPrice()+"\t Change:"+(Math.round(getChange())+"%")+"\t Difficulty:"+getDifficulty()+"\t Cap:"+getCap());
    }
    
    @Override
     public void changeVolatility(){
        Random random = new Random();
        //Increased Volatility since crypto is more volatile
        setVolatility( random.nextDouble() + 5);
        
    }
    
    
    
    
    
    @Override
    //This is overridden since cryptocurrencies are known to be more volatile than a fiat currency, the changes in the difficulty of the currency also affect the price of the cryptocurrency
    public void changeSellPrice() {
        changeVolatility();
        setOldSellPrice(getSellPrice());
        super.changeSellPrice();
        


    }
    
    @Override
    public void algorithmPrice() {
        //due to the nature of cryptocurrencies, the volatility is increased
        
        double randomFactor = Math.random()*(0.1-0.001)+0.001;
        double changePercent = 2 * getVolatility() * randomFactor;
        if (changePercent > getVolatility()) {
            changePercent -= (2 * getVolatility());
        }
        double changeAmount = getOldSellPrice() * changePercent/100;
        double newPrice = getOldSellPrice() + changeAmount;
        double difficulty = getDifficulty(); //if the difficulty changes, according to economic theory, the price should be affected too due to demand 
        if (difficulty>0){
            
            newPrice+= Math.random()*(0.01)*getSellPrice();
            
            
        }
        else{
          
            newPrice-=Math.random()*(0.005)*getSellPrice();
            
        }
        
        
        
        
        super.setSellPrice(RoundNumber.round2DP(newPrice));
    }
    
    
    
    
    
    
    
    @Override
    //this will inform the user of any recent news, for cryptocurrency, the change we expect should be greater for there to be news
    public String getAlerts() {
        if (getChange()>1) {
            String[] phrase = {" is booming. Will this continue?", " rises. Should you invest now?", " is on the rise. Will the bubble burst?", " leaps. What will happen next?"};
            
            int choose = (int)Math.random()*3;
            return getName()+phrase[choose]+" Change:"+RoundNumber.round2DP(getChange())+"%";
        }
        else if (-1>getChange()) {
            String[] phrase = {" is falling. How long will this continue?", " tumbles. What will happen next?", " dips. Will they bounce back?", " melts away. Is its shimmer fading?"," falls. Will the currency crash?"};
            int choose = (int)Math.random()*4;
            return getName()+phrase[choose]+" Change:"+RoundNumber.round2DP(getChange())+"%";
        }
        return "";
    }

    
    
    @Override
    //for the cryptocurrency class, the "difficulty" of the currency should also be changed
    public void updateData() throws IOException {
        
         //the difficulty changes in a cryptocurrency due to the increasing number of miners
        double oldDifficulty = getDifficulty();
        changeDifficulty();
        difficultyChange = ((getDifficulty() - oldDifficulty)/oldDifficulty)*100;
 
        super.updateData(); //polymorphism, late dynamic binding
        
       
        
    }
    
        //the subclasses can then obtain a more generic version about the info
    public String getBasicInfo() {
        //late dynamic binding
        return super.getBasicInfo();
    }
    
    
    

        
        
    
    
}
