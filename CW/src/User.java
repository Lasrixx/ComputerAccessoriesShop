/**
 * This abstract class provides methods and attributes for the user.
 * @author Olivia Gray
 */

public abstract class User {
	private String userID;
	private String username;
	private String name;
	private Address address;
	
	/**
	 * This method is the constructor for the user class.
	 * @param userID a unique identifier for the user.
	 * @param username a name for the user that is not necessarily unique.
	 * @param name the user's given name.
	 * @param address the postal / billing address of the user.
	 */
	public User(String userID, String username, String name, Address address) {
		this.userID = userID;
		this.username = username;
		this.name = name;
		this.address = address;
	}
	
	/**
	 * This method returns the user's ID.
	 * @return this.userID
	 */
	public String getID() {
		return this.userID;
	}
	
	/**
	 * This method returns the user's username.
	 * @return this.username
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * This method returns the user's address.
	 * @return address
	 */
	public String getAddress() {
		return this.address.toString();
	}
	
}
