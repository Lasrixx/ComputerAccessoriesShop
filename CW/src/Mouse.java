/**
 * This class contains methods relevant to the Mouse object.
 * @author Olivia Gray
 */
public class Mouse extends Product {
	private MouseType type;
	private int buttonAmount;
	
	/**
	 * This method is a constructor for the Mouse class.
	 * @param barcode mouse's barcode.
	 * @param brand mouse's brand.
	 * @param colour mouse's colour.
	 * @param connectivity mouse's connectivity type.
	 * @param quantity the quantity of the given barcode in the StockManager.
	 * @param originalPrice the original price of the mouse.
	 * @param retailPrice the price the mouse is being sold at in this shop.
	 * @param type the type of mouse (e.g. gaming, standard).
	 * @param buttonAmount the amount of buttons the mouse has.
	 */
	public Mouse(String barcode, String brand, String colour, Connectivity connectivity, int quantity, float originalPrice, float retailPrice, MouseType type, int buttonAmount) {
		super(barcode, brand, colour, connectivity, quantity, originalPrice, retailPrice);
		this.type = type;
		this.buttonAmount = buttonAmount;
	}
	
	/**
	 * This method returns the type of mouse (e.g. gaming, standard).
	 * @return this.type
	 */
	public MouseType getType() {
		return this.type;
	}
	
	/**
	 * This method returns the amount of buttons the mouse has.
	 * @return this.buttonAmount
	 */
	public int getAdditionalInfo() {
		return this.buttonAmount;
	}
}
