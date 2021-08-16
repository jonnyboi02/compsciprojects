
public final class realMoneyUser extends User {

    public realMoneyUser(String userName, String passWord, double balance) {
        super(userName, passWord, balance);
        
        
        
    }
    
    public realMoneyUser(String userName, String passWord, double balance, double profit) {
        super(userName, passWord, balance, profit);
    }

    @Override
    public void Login() {
        System.out.println("Real Money Account. Authorized users only!");
        String password = inputString("Enter the password");
        while (!(password.equals(getPassword()))){
            System.out.println("Enter the correct details");
            password = inputString("Enter the password");
        }
    }



}
