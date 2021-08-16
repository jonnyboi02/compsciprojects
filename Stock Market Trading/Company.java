import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class Company extends Stock {
    
    //the owner of the company
    private String owner;


    public Company(String name, String abbreviation,double sellPrice, double change) {
        super(name, abbreviation, "Company", sellPrice);
        this.owner = "N/A";
    
        
    }
    //overloading
    public Company(String name, String abbreviation,double sellPrice, String owner) {
        super(name, abbreviation, "Company", sellPrice);
        this.owner = owner;
    }

    
    //the subclasses can then obtain a more generic version about the info
    public String getBasicInfo() {
        return super.toString();
    }
    
    public String getOwner() {
        return this.owner;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    
    @Override
    public String toString() {
        
        //late dynamic binding
        return super.toString()+"\t Owner:"+getOwner();
    }
    
    //changes the sellprice of that stock using some algorithm
    
    @Override
    public void changeSellPrice()  {
        
        
        
        
        //so that we know the previous price of that stock
        setOldSellPrice(getSellPrice());
      
        
        
        
        
        String apiURL = "https://api.twelvedata.com/price?symbol="+super.getAbbreviation()+"&apikey=63417ccbcc51418a822214ed9607e173";
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
        //If there are no more api calls that can be made
        
       
        double newPrice = getOldSellPrice();
        Random random = new Random();
        double MAX = 0.01;
        double MIN = 0.001;
        
        if (random.nextInt()%2==0) {
            newPrice+=newPrice*Math.random()*(MAX-MIN)+MIN;
        }
        else {
            newPrice-=newPrice*Math.random()*(MAX-MIN)+MIN;
        }
        setSellPrice(RoundNumber.round2DP(newPrice));
        
    }
    
    
    
    
    
    

    
    

}
