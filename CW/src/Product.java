/**
 * This abstract class stores attributes and methods relevant to all products.
 * @author Olivia Gray
 */

public abstract class Product {
	private String barcode;
	private String brand;
	private String colour;
	private Connectivity connectivity;
	private int quantity;
	private float originalPrice;
	private float retailPrice;
	
	/**
	 * Constructor for product.
	 * @param barcode barcode of product.
	 * @param brand brand of product.
	 * @param colour product's colour.
	 * @param connectivity product's connectivity type.
	 * @param quantity quantity of product.
	 * @param originalPrice original price of product.
	 * @param retailPrice price product is being sold for in system.
	 */
	public Product(String barcode, String brand, String colour, Connectivity connectivity, int quantity, float originalPrice, float retailPrice) {
		this.barcode = barcode;
		this.brand = brand;
		this.colour = colour;
		this.connectivity = connectivity;
		this.quantity = quantity;
		this.originalPrice = originalPrice;
		this.retailPrice = retailPrice;
	}
	
	/**
	 * This method returns the product's barcode.
	 * @return this.barcode
	 */
	public String getBarcode() {
		return this.barcode;
	}
	
	/**
	 * This method returns the product's brand.
	 * @return this.brand
	 */
	public String getBrand() {
		return this.brand;
	}
	
	/**
	 * This method returns the product's colour.
	 * @return this.colour
	 */
	public String getColour() {
		return this.colour;
	}
	
	/**
	 * This method returns the product's connection type.
	 * @return this.connectivity
	 */
	public Connectivity getConnectivity() {
		return this.connectivity;
	}
	
	/**
	 * This method returns the quantity of the product in the StockManager.
	 * @return this.quantity
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * This method returns the original price of the product. 
	 * @return this.originalPrice
	 */
	public float getOriginalPrice() {
		return this.originalPrice;
	}
	
	/**
	 * This method returns the retail price of the product.
	 * @return this.retailPrice
	 */
	public float getRetailPrice() {
		return this.retailPrice;
	}

	/**
	 * This method updates the quantity of the product.
	 * @param newQuantity
	 */
	public void setQuantity(int newQuantity) {
		this.quantity = newQuantity;
	}
	
	/**
	 * This method takes one of the enumerator attributes and formats it so only the first letter is capitalised.
	 * @param inp the attribute being formatted.
	 * @return capitalised enumerator.
	 */
	public String formatString(String inp) {
		return inp.substring(0, 1).toUpperCase() + inp.substring(1).toLowerCase();
	}
	
	/**
	 * This method formats prices so that they will always have two decimal points. 
	 * @param inp the price being formatted.
	 * @return price.
	 */
	public String formatFloat(float inp) {
		// Make all prices have 2 decimal places
		// This means values with more than 2, will be rounded to the nearest hundreth
		return String.format("%.02f", inp);
	}
}
