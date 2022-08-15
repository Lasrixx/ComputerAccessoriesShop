import java.util.HashMap;
import java.util.Map;

/**
 * This class contains methods used by the customer or for other classes to access data from the customer.
 * It inherits from User.
 * @author Olivia Gray
 */

public class Customer extends User {

	private Map<String,Integer> basket;
	private CustomerGUI cGUI;
	
	/**
	 * This is the customer constructor. 
	 * Also creates an empty basket for the user.
	 * @param userID Unique identifier of the user.
	 * @param username the name of the user.
	 * @param name the given / legal name of the user.
	 * @param address the billing / postal address of the user.
	 */
	public Customer(String userID, String username, String name, Address address) {
		super(userID, username, name, address);
		// Basket can simply be a list of barcodes as barcodes uniquely identify products so saves memory
		this.basket = new HashMap<String,Integer>();
	}
	
	/**
	 * This method takes a product and adds it to the user's basket.
	 * @param barcode the barcode of the product that is being added.
	 * @param quantity the quantity of the given product to be added.
	 */
	public void addItemToBasket(String barcode, int quantity) {
		this.basket.put(barcode, quantity);
	}
	
	/**
	 * This method empties out the user's basket.
	 */
	public void clearBasket() {
		this.basket = new HashMap<String, Integer>();
		cGUI.updateBasketTable(this);
	}
	
	/**
	 * This method returns the user's basket.
	 * @return this.basket
	 */
	public Map<String,Integer> getBasket(){
		return this.basket;
	}
	
	/**
	 * This method sets a GUI to the user as it is created so the user can log out and go back to it later.
	 * @param cGUI the CustomerGUI object that should be assigned to the user.
	 */
	public void setGUI(CustomerGUI cGUI) {
		this.cGUI = cGUI;
	}
}
