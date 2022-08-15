/**
 * This class provides a template for creating Address objects and their relevant methods.
 * @author Olivia Gray
 */

public class Address {
	private String houseNumber;
	private String postcode;
	private String city;
	
	/**
	 * This method is the constructor for Address class.
	 * @param houseNumber house number in user's address.
	 * @param postcode postcode in user's address.
	 * @param city city the user lives in.
	 */
	public Address(String houseNumber, String postcode, String city) {
		this.houseNumber = houseNumber;
		this.postcode = postcode;
		this.city = city;
	}
	
	/**
	 * This method converts the attributes of the Address to a formatted string.
	 * @return address as a string.
	 */
	@Override
	public String toString() {
		return (this.houseNumber + ", " + this.postcode + ", " + this.city);
	}
}
