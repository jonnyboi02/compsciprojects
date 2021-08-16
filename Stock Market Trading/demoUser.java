import java.util.Scanner;

public final class demoUser extends User{

    public demoUser() {
        super(); //calls the superclass constructor
        
    }
    
    public demoUser(String name, String password){
        setName(name);
        setPassword(password);
     
        
    }
    
    
    
    
    @Override
    //the login for a demo user would allow the user to enter the fund they want to enter the simulator with
    public void Login() {
        System.out.println("Demo User Account");
        String password = inputString("Enter the password");
        while (!(password.equals(getPassword()))){
            System.out.println("Enter the correct details");
           
            password = inputString("Enter the password");
        }
        double userFunds = Double.parseDouble(inputString("Enter the funds to use in the demo"));
        setBalance(userFunds);
    
        
    }
    
    public static String inputString(String question) {
        System.out.println(question);
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
    
    
    

}
