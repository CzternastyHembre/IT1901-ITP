package SaveHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import user.User;

public class UserSaveHandler{
	
	public final static String SAVE_FILE = "../storage/userData/data.txt";

	
	public static void createUser(User user) {
		
		if (getUser(user.getUsername()) != null) {
			throw new IllegalStateException("User already exist");
		}
		
		try {
	        FileWriter fileWriter = new FileWriter(SAVE_FILE, true); //Set true for append mode
	        PrintWriter writer = new PrintWriter(fileWriter);

	        String formatString = user.getUsername() + ";" + user.getMoney();
			writer.println(formatString);  //New line
			writer.close();
			
			setActive(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setActive(User user) {
		UpdateUser(user);
	}
	
	public static void UpdateUser(User user){
		if (getUser(user.getUsername()) == null) {
			throw new IllegalArgumentException("No user with the name " + user.getUsername());
		}
		try {
			File file = new File(SAVE_FILE);
		    Scanner sc = new Scanner(file);
		    
	        List<String> users = new ArrayList<>();
	        
	        String formatString1 = sc.nextLine() + "\n" + sc.nextLine();
	        sc.nextLine();
	        String formatString2 = sc.nextLine();
	        
	        while (sc.hasNextLine()) {users.add(sc.nextLine());};
	        sc.close();
	        
	        FileWriter fileWriter = new FileWriter(SAVE_FILE); //Set true for append mode
	        PrintWriter writer = new PrintWriter(fileWriter);
	        
	        writer.println(formatString1);
	        writer.println(user.getUsername() + ";" + user.getMoney());
	        writer.println(formatString2);
	        
	        for (String userString : users) {
				if (userString.split(";")[0].equals(user.getUsername())) {
			        writer.println(user.getUsername() + ";" + user.getMoney());
				} else {
					writer.println(userString);
				}
			}
	        
	        
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();;
		}
	}
	
	public static User getActiveUser() {
		try {
			File file = new File(SAVE_FILE);
		    Scanner sc = new Scanner(file);
		    sc.nextLine(); //Skips the first and second line "the format lines"
		    sc.nextLine();		    

		    String[] userInfo = sc.nextLine().split(";");
	    	String currentName = userInfo[0];
    		double balance = Double.parseDouble(userInfo[1]);
    		return new User(currentName, balance);
		} catch (IOException e) {
			e.printStackTrace();;
		}
		return null;

	}

	public static User getUser(String userName) {
		try {
			File file = new File(SAVE_FILE);
		    Scanner sc = new Scanner(file);
		    sc.nextLine(); //Skips the first lines 
		    sc.nextLine(); 
		    sc.nextLine(); 
		    while (sc.hasNextLine()) {
		    	String[] userInfo = sc.nextLine().split(";");
		    	String currentName = userInfo[0];
		    	if (currentName.equals(userName)) {
		    		double balance = Double.parseDouble(userInfo[1]);
		    		return new User(userName, balance);
				}
		    }
		    sc.close();
		} catch (IOException e) {
			e.printStackTrace();;
		}
		return null;
	}
	


	public static void main(String[] args) {
		User erlend = new User("Erlend", 1000);
		User mattis = new User("Mattis", 99);
		User seb = new User("Seb", 100);
		
		UserSaveHandler.UpdateUser(mattis);
	}
}
