/**
 * This class contains methods relating specifically to the Keyboard object.
 * Inherits from Product class.
 * @author Olivia Gray
 */

public class Keyboard extends Product {
	private KeyboardType type;
	private KeyboardLayout layout;
	
	/**
	 * Constructor for this class.
	 * @param barcode the barcode of the keyboard.
	 * @param brand the keyboard's brand.
	 * @param colour the keyboard's colour.
	 * @param connectivity the keyboard's connection type.
	 * @param quantity the amount of keyboards with the given barcode.
	 * @param originalPrice the original cost of the keyboard.
	 * @param retailPrice the price the keyboard is sold at in this shop.
	 * @param type the type / general use case of the keyboard (e.g. gaming, standard).
	 * @param layout the layout of the keyboard (e.g. UK, US).
	 */
	public Keyboard(String barcode, String brand, String colour, Connectivity connectivity, int quantity, float originalPrice, float retailPrice, KeyboardType type, KeyboardLayout layout) {
		super(barcode, brand, colour, connectivity, quantity, originalPrice, retailPrice);
		this.type = type;
		this.layout = layout;
	}
	
	/**
	 * This method returns the keyboard's type (e.g. gaming, flexible).
	 * @return this.type
	 */
	public KeyboardType getType() {
		return this.type;
	}
	
	/**
	 * This method returns the keyboard's layout.
	 * @return this.layout
	 */
	public KeyboardLayout getAdditionalInfo() {
		return this.layout;
	}
}
