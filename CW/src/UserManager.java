import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class contains methods for managing all users.
 * @author Olivia Gray
 */

public class UserManager {
	
	private static List<User> users;
	private static CustomerGUI cGUI;
	private static AdminGUI aGUI;
	private static LoginGUI lGUI;
	
	/**
	 * This is the main method. The whole program runs from here.
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					users = readUserDatabase();
				} catch (Exception e) {
					e.printStackTrace();
				}
				lGUI = new LoginGUI(users);
				lGUI.setVisible(true);
			}
		});
	}
	
	/**
	 * This method creates a list of all users from useraccounts.txt.
	 * @return userList list of all users.
	 * @throws FileNotFoundException
	 */
	public static List<User> readUserDatabase() throws FileNotFoundException {
		List<User> userList = new ArrayList<User>();
		
		File userFile = new File("UserAccounts.txt");
		Scanner scanner = new Scanner(userFile);
		
		// Read from UserAccounts.txt file
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] userData = line.split(", ");
			
			// Create user's address
			Address addr = new Address(userData[3], userData[4], userData[5]);
			
			// Create the user object and add it to the user list 
			// Should differentiate between a user being an admin or customer so we can downcast later
			if (userData[6].equals("admin")) {
				Admin user = new Admin(userData[0], userData[1], userData[2], addr);
				userList.add(user);
			}
			else if (userData[6].equals("customer")) {
				Customer user = new Customer(userData[0], userData[1], userData[2], addr);
				userList.add(user);
			}
		}
		scanner.close();
		return userList;
	}
	
	public void openLoginFrame() {
		if (cGUI != null) {
			cGUI.setVisible(false);		
		}
		if (aGUI != null) {
			aGUI.setVisible(false);		
		}
		lGUI.setVisible(true);
	}
	
	/**
	 * This method opens the frame for the customer when they log in.
	 * @param user the user that the frame needs to be opened for.
	 */
	public void openCustomerFrame(User user) {
		// Get the user's details from their username
		Customer customer = (Customer)user;
		cGUI = new CustomerGUI(customer);
		customer.setGUI(cGUI);
		cGUI.setVisible(true);
		lGUI.setVisible(false);
	}
	
	/**
	 * This method opens the frame for the administrator.
	 */
	public void openAdminFrame() {
		aGUI = new AdminGUI();
		aGUI.setVisible(true);		
		lGUI.setVisible(false);
	}
	
	/**
	 * This method opens the add to basket frame when the customer presses the add to basket button in the CustomerGUI.
	 * @param user the user currently logged in.
	 * @param barcode the barcode of the product to add to basket.
	 * @param type whether the product is mouse or keyboard.
	 * @param brand the brand of the product.
	 * @param colour the colour of the product.
	 * @param maxQuantity the maximum quantity the user can add to their basket.
	 */
	public void openBasketFrame(Customer user, String barcode, String type, String brand, String colour, int maxQuantity) {
		AddBasketGUI bGUI = new AddBasketGUI(user, barcode, type, brand, colour, maxQuantity);
		bGUI.setVisible(true);
	}
}
