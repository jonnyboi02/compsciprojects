import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class LoginGUI extends JFrame implements ActionListener{
	private JTextField usernametextField;
	private JPasswordField passwordField;
	private JButton btnlogin ;
	private JLabel incorrectDetailsEntered ;
	private ArrayList<User> users;
	

	public static void main(String[] args) {
		
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new LoginGUI();
			}
		});
	}

	
	public LoginGUI() {
		super("Login Screen");
		
		
		try {
			users = createAccount();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error in retrieving accounts");
		}
		
		getContentPane().setLayout(null);
		
		JLabel lblLoginScreen = new JLabel("Login Screen");
		lblLoginScreen.setBounds(189, 40, 152, 15);
		getContentPane().add(lblLoginScreen);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(45, 90, 152, 15);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(45, 129, 125, 15);
		getContentPane().add(lblPassword);
		
		usernametextField = new JTextField();
		usernametextField.setBounds(167, 88, 187, 19);
		getContentPane().add(usernametextField);
		usernametextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(167, 127, 187, 19);
		getContentPane().add(passwordField);
		
		btnlogin = new JButton("Login");
		btnlogin.setBounds(141, 178, 199, 25);
		getContentPane().add(btnlogin);
		btnlogin.addActionListener(this);
		
		incorrectDetailsEntered = new JLabel("");
		incorrectDetailsEntered.setBounds(45, 230, 412, 15);
		getContentPane().add(incorrectDetailsEntered);
		
		
		
		
		getContentPane().setForeground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(500,500,500,300);
		
		setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Login")) {
			int indexOfUser = findUser(usernametextField.getText(), passwordField.getText(), users);
				//if the user has entered entered the incorrect details
				if (indexOfUser == -1) {
					incorrectDetailsEntered.setText("Incorrect Username or Password Entered");
				}
				else {
					User userLogin = users.get(indexOfUser);
					if (userLogin instanceof realMoneyUser) {
						this.setVisible(false);
						new MainMenu(userLogin);
					}
					else {
						this.setVisible(false);
						new DemoUserFundGUI(userLogin);
					}
				}
			}
		
	}
		
		
	
	
    //this checks if the user exists in the system
    public static int findUser(String username,String password, ArrayList<User> users2){
        for (int x = 0;x<users2.size();x++){
            User user = users2.get(x);
            if (user.getName().equals(username)&& user.getPassword().equals(password)){
                 return x;   
            }
        }
    return -1;
    
    }
    
    //reads from a file all the accounts 
    public static ArrayList<User> createAccount() throws IOException {
    	try {
	    	ArrayList<User> users = new ArrayList<>();
	    	BufferedReader inputStream = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/account.txt")); //need to be changed
	    	String currentLine = inputStream.readLine();
	    	while((currentLine !=null)) {
	
	    		
	    		if (currentLine.contains("real")) {
	    			String [] toAdd = currentLine.split(",");
	    			User a =  new realMoneyUser(toAdd[1], toAdd[2], Double.parseDouble(toAdd[3]), Double.parseDouble(toAdd[4]));
	    			users.add(a);
	    		}
	    		else if(currentLine.contains("demo")) {
	    			String [] toAdd = currentLine.split(",");
	    			User a = new demoUser(toAdd[1], toAdd[2]);
	    			users.add(a);
	    		}
	    	
	    		currentLine = inputStream.readLine();
	    		
	
	    	}
			return users;
    	}catch(	IOException e){
    		String fileLocation = System.getProperty("user.dir")+"/account.txt";
    		File file = new File(fileLocation); //creates a new file in the current directory
    		PrintWriter outputStream = new PrintWriter(new FileWriter(file));
    		String [] accounts = new String[] {"real,admin,admin,50000,0", "demo,david,password123", "demo,anne,london"};
    		for (int x =0;x<accounts.length;x++) {
    			outputStream.println(accounts[x]);
    		}
    		outputStream.close();
    		return readFile(fileLocation);
    		
    		
    		
    	}
    }
    
    
    public static ArrayList<User> readFile(String directory) throws IOException {
    	ArrayList<User> users = new ArrayList<>();
    	BufferedReader inputStream = new BufferedReader(new FileReader(directory));
    	String currentLine = inputStream.readLine();
    	while((currentLine !=null)) {
    		
    		
    		if (currentLine.contains("real")) {
    			String [] toAdd = currentLine.split(",");
    			User a =  new realMoneyUser(toAdd[1], toAdd[2], Double.parseDouble(toAdd[3]), Double.parseDouble(toAdd[4]));
    			users.add(a);
    		}
    		else if(currentLine.contains("demo")) {
    			String [] toAdd = currentLine.split(",");
    			User a = new demoUser(toAdd[1], toAdd[2]);
    			users.add(a);
    		}
    	
    		currentLine = inputStream.readLine();
    		

    	}
    	return users;
    	
    	
    }
}
