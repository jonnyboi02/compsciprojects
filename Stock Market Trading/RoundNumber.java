import java.math.RoundingMode;
import java.text.DecimalFormat;

public class RoundNumber {
    //rounds the price to 4Dp
    public static double round4DP(double toRound) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return Double.parseDouble(df.format(toRound));
        
    }
    
    //rounds the price to 2Dp
    public static double round2DP(double toRound) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return Double.parseDouble(df.format(toRound));
        
    }
    

}
