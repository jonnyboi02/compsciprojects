import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class Currency extends Stock{
    private String baseCurrency;
    private String quoteCurrency;
    private double volatility;
    

    public Currency(String name, String abbreviation ,double initialPrice, String baseCurrency, String quoteCurrency) {
        super(name, abbreviation, "Currency", initialPrice);
        setBaseCurrency(baseCurrency);
        setQuoteCurrency(quoteCurrency);
        this.volatility = 0;
    }
    
    public Currency(String name, String abbreviation, String type, double sellPrice){
        super(name, abbreviation, type, sellPrice);

    }
    
    public void changeVolatility(){
        Random random = new Random();
        volatility = random.nextDouble() + 0.4;
        
    }
    
    public void setVolatility(double volatility){
        this.volatility=volatility;
        
    }
    
    public double getVolatility(){
         return volatility;   
        
    }
    
    
    public void setBaseCurrency(String currency){
        baseCurrency = currency;   
    }
    
    public String getBaseCurrency(){
         return baseCurrency;   
    }
    
    
    public void setQuoteCurrency(String currency){
        quoteCurrency = currency;   
        
    }
    
    public String getQuoteCurrency(){
         return quoteCurrency;   
    }
    
    
    public String toString(){
        
        return super.toString()+"\t Base Currency: "+baseCurrency+"\t Quote Currency: "+quoteCurrency;
    }
    
    
    
    //the subclasses can then obtain a more generic version about the info
    public String getBasicInfo() {
        //late dynamic binding
        return super.toString();
    }
    
    
    
    @Override
    //this will inform the user of any recent news, for currency, the change we expect should be greater but less than companies for example for there to be news
    public String getAlerts() {
        //a change greater than 1% for currency is considered big
        if (getChange()>1) { 
            String[] phrase = {" is booming. Will this continue?", " rises. Should you invest now?", " is on the rise. Will the bubble burst?", " leaps. What will happen next?"};
            
            int choose = (int)Math.random()*3;
            return getName()+phrase[choose]+" Change:"+RoundNumber.round2DP(getChange())+"%";
        }
        else if (-1>getChange()) {
            String[] phrase = {" is falling. How long will this continue?", " tumbles. What will happen next?", " dips. Will they bounce back?", " melts away. Is its shimmer fading?"};
            int choose = (int)Math.random()*3;
            return getName()+phrase[choose]+" Change:"+RoundNumber.round2DP(getChange())+"%";
        }
        return "";
    }

    
    
    @Override
    //fiat currency is not as volatile, so we must change the way the calculation is made
    public void changeSellPrice() {
        changeVolatility(); //changes the volatility of the currency
        
        //so that we know the previous price of that stock
        setOldSellPrice(getSellPrice());
    
        
        String apiURL = "https://api.twelvedata.com/price?symbol="+super.getAbbreviation()+"&apikey=0bff865c8cee4fbd833c77ec62ab5cec";
        
        
        try {
            URL urlCall = new URL(apiURL); 
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(urlCall.openStream()));
            String priceURLQuote = inputStream.readLine();
            Double newPriceQuote = Double.parseDouble(priceURLQuote.substring(10, priceURLQuote.length()-2));
            setSellPrice(RoundNumber.round2DP(newPriceQuote));
            
        } catch (MalformedURLException e) {
            //If there are no more api calls that can be made
            algorithmPrice();
        } catch (IOException e) {
            //If there are no more api calls that can be made
            algorithmPrice();    
        } catch(NumberFormatException e) {
            algorithmPrice();
        }
        


    }
    
    public void algorithmPrice() {
        Random random = new Random();
        //calculates the price of currency, doesn't change much
        double newPrice = getOldSellPrice();
        double randomFactor = random.nextDouble();
        double changePercent = 2 * volatility * randomFactor;
        if (changePercent > volatility) {
            changePercent -= (2 * volatility);
        }
        double changeAmount = getOldSellPrice() * changePercent/100;
        newPrice+=changeAmount;
        //random factor
        if (newPrice < getOldSellPrice()*1.0001) {
            newPrice += Math.abs(changeAmount) * 2;
        } else if (newPrice > getOldSellPrice()*0.9999) {
            newPrice -= Math.abs(changeAmount) * 2;
        }
        
        setSellPrice(RoundNumber.round2DP(newPrice));
    }
    

    
}
