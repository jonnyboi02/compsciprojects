import java.io.IOException;
import java.util.Random;

public final class Commodities extends Stock{
    
    long reservesLeft; //the remaining number of barrels left, using long due to the bits needed...
    double reservesLeftChange; //the change in the reserves
    public Commodities(String name, String abbreviation, double sellPrice, long reservesLeft) {
        super(name, abbreviation, "Commodity", sellPrice);
        this.reservesLeft = reservesLeft;
        
    }
    
    
    public double getReservesLeftChange(){
        return reservesLeftChange;
    }
    
    public void setReservesLeftChange(double change){
         this.reservesLeftChange   = change;
    }
    
    //returns the reserves left for that commodity
    public long getReserves() {
        return reservesLeft;
    }
    
    public void setReserves(long reserves) {
        this.reservesLeft = reserves;
    }
    
    
    //gets a more detailed version of the info about a stock
    public String toString() {
        return super.toString()+"\t Reserves Left: "+getReserves();
    }
    
    //reduces the reserves 
    public void reduceReserves() {
        int MAX = 9000; 
        int MIN = 8000;
        int toReduce = (int) (Math.random()*(MAX-MIN)+MIN);
        reservesLeft-=toReduce; //reduces the number of reserves for a commodity
        
        
    }
    
    //overriding this method so that the number of reserves of the commodity are also changed
    @Override
    public void updateData() throws IOException {
        long oldReservesLeft = reservesLeft;
        reduceReserves();
        reservesLeftChange = ((getReserves()-oldReservesLeft)/oldReservesLeft)*100;
        super.updateData(); //dynamic binding
    }
    
    
    
    
    //the subclasses can then obtain a more generic version about the info
    public String getBasicInfo() {
        return super.toString();
    }
    
    @Override
    public void changeSellPrice() {
        
        
        
        
        //so that we know the previous price of that stock
        setOldSellPrice(getSellPrice());
        Double oldsellPrice = getOldSellPrice();
        
        //calculates the price of currency, doesn't change much
        double newPrice = oldsellPrice;
        Random random = new Random();
        double MAX = 0.01;
        double MIN = 0.001;
        
        if (random.nextInt()%2==0) {
            newPrice+=oldsellPrice*Math.random()*(MAX-MIN)+MIN;
        }
        else {
            newPrice-=oldsellPrice*Math.random()*(MAX-MIN)+MIN;
        }
        
        
        //how drastic the change can effect price
        if (Math.abs(reservesLeftChange)>0.000003){
             newPrice+=oldsellPrice*Math.random()*0.0002;   
        }
        else{
            newPrice-=oldsellPrice*Math.random()*0.0021;
        }
        
        
        
        setSellPrice(RoundNumber.round2DP(newPrice));


    }
    
    
    
    
    
    
    
}
