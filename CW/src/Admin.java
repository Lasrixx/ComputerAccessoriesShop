/**
 * This class creates a blueprint for the admin object. It inherits from User class.
 * @author Olivia Gray
 */

public class Admin extends User{
	/**
	 * This is a constructor for the Admin class.
	 * @param userID a unique identifier for the user.
	 * @param username a name for the user (not necessarily their given name, nor does it have to be unique).
	 * @param name the user's given name.
	 * @param address the user's billing and postal address.
	 */
	public Admin(String userID, String username, String name, Address address) {
		super(userID, username, name, address);
	}
}
