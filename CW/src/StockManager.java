import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry; 

/**
 * This class contains methods for reading and writing to stock.txt and various other methods relating to the stock.
 * @author Olivia Gray
 */

public class StockManager {

	Map<String,Product> productHashMap = new HashMap<String, Product>();
	
	/**
	 * This method reads all data from stock.txt and puts it into a hashmap that contains all products.
	 * @return productHashMap
	 * @throws FileNotFoundException
	 */
	public Map<String,Product> readStockDatabase() throws FileNotFoundException{
		
		productHashMap = new HashMap<String, Product>();
		
		File stockFile = new File("stock.txt");
		Scanner scanner = new Scanner(stockFile);
		
		// Read each line from stock.txt and split the data into keyboard and mouse objects
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] productData = line.split(", ");
			
			// If the product is out of stock, do not hold it in the map as it would then be shown to user
			if (Integer.valueOf(productData[6]) > 0){
				if (productData[1].equals("keyboard")) {
					Keyboard product = new Keyboard(productData[0],
													productData[3], 
													productData[4], 
													Connectivity.valueOf(productData[5].toUpperCase()), 
													Integer.valueOf(productData[6]), 
													Float.valueOf(productData[7]), 
													Float.valueOf(productData[8]), 
													KeyboardType.valueOf(productData[2].toUpperCase()), 
													KeyboardLayout.valueOf(productData[9].toUpperCase()));
					// Here, upcast to Product to consolidate keyboards and mice into one map
					// We can downcast later, when needed
					productHashMap.put(productData[0],product);
				}
				else if (productData[1].equals("mouse")) {
					Mouse product = new Mouse(productData[0],
											  productData[3], 
											  productData[4], 
											  Connectivity.valueOf(productData[5].toUpperCase()), 
											  Integer.valueOf(productData[6]), 
											  Float.valueOf(productData[7]), 
											  Float.valueOf(productData[8]), 
											  MouseType.valueOf(productData[2].toUpperCase()), 
											  Integer.valueOf(productData[9].toUpperCase()));
					productHashMap.put(productData[0],product);
				}
			}
		}
		scanner.close();
		return productHashMap;
	}
	
	/**
	 * This method writes all the contents from the product hash map to the stock.txt file. 
	 * @throws FileNotFoundException
	 */
	private void writeStockDatabase() throws FileNotFoundException {
		BufferedWriter bw = null;
		try
		{
			bw = new BufferedWriter(new FileWriter("stock.txt"));
			for (Product p : productHashMap.values()) {
				String line = ""; 
				// If the item is not stocked, do not write it to stock.txt
				if (p.getQuantity() > 0) {
					if (p instanceof Keyboard) {
						Keyboard kb = (Keyboard)p; 
						line = kb.getBarcode() + ", " 
							   + "keyboard" + ", " 
							   + kb.getType() + ", " 
							   + kb.getBrand() + ", " 
							   + kb.getColour() + ", " 
							   + kb.getConnectivity() + ", " 
							   + kb.getQuantity() + ", " 
							   + kb.getOriginalPrice() + ", " 
							   + kb.getRetailPrice() + ", " 
							   + kb.getAdditionalInfo() + "\n";
					}
					else if (p instanceof Mouse) {
						Mouse m = (Mouse)p;
						line = m.getBarcode() + ", " 
							   + "mouse" + ", " 
							   + m.getType() + ", "
							   + m.getBrand() + ", "
							   + m.getColour() + ", "
							   + m.getConnectivity() + ", "
							   + m.getQuantity() + ", "
							   + m.getOriginalPrice() + ", "
							   + m.getRetailPrice() + ", "
							   + m.getAdditionalInfo() + "\n";
					}
					bw.write(line);
				}
			}
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(bw != null)
				{
					bw.close();
				}
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method sorts the product map in ascending order by retail price.
	 * @return sortedProductMap new map with every product sorted.
	 */
	public Map<String,Product> sortStockByRetailPrice() {
		// First, get all the contents from stock.txt 
		// We want to do this every time this function is called to account for the admin adding new products
		try {
			readStockDatabase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Convert hash map into list   
		List<Entry<String,Product>> productList = new LinkedList<Entry<String,Product>>(productHashMap.entrySet());
		// Sort the new linked list  
		Collections.sort(productList, new Comparator<Entry<String,Product>>()   
		{  
			public int compare(Entry<String,Product> e1, Entry<String,Product> e2)   
			{   
				// Compare the retail prices of the current entry and the next one and sort in ascending order  
				return (Float.compare(e1.getValue().getRetailPrice(), e2.getValue().getRetailPrice()));
			}
		});
		// Convert linked list back to hash map then return
		Map<String, Product> sortedProductMap = new LinkedHashMap<String, Product>();  
		for (Entry<String, Product> entry : productList)   
		{  
		sortedProductMap.put(entry.getKey(), entry.getValue());  
		}      
		return sortedProductMap;
	}
	
	/**
	 * This method sorts the product map in alphabetical order by brand name.
	 * @return sortedProductMap the new map with all products sorted.
	 */
	public Map<String,Product> sortStockByBrand() {
		// First, get all the contents from stock.txt 
		// We want to do this every time this function is called to account for the admin adding new products
		try {
			readStockDatabase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Convert hash map into list   
		List<Entry<String,Product>> productList = new LinkedList<Entry<String,Product>>(productHashMap.entrySet());  
		// Sort the new linked list  
		Collections.sort(productList, new Comparator<Entry<String,Product>>()   
		{  
			public int compare(Entry<String,Product> e1, Entry<String,Product> e2)   
			{   
				// Compare the retail prices of the current entry and the next one and sort in ascending order  
				return e1.getValue().getBrand().compareTo(e2.getValue().getBrand());
			}
		});
		// Convert linked list back to hash map then return
		Map<String, Product> sortedProductMap = new LinkedHashMap<String, Product>();  
		for (Entry<String, Product> entry : productList)   
		{  
		sortedProductMap.put(entry.getKey(), entry.getValue()); 
		}      
		return sortedProductMap;
	}
	
	/**
	 * This method sorts a map of only mice in  ascending order by the number of buttons on them.
	 * @return sortedProductMap the map of all mice sorted in ascending order.
	 */
	public Map<String,Mouse> sortMiceByButtons() {
		// First, get all the contents from stock.txt 
		// We want to do this every time this function is called to account for the admin adding new products
		try {
			readStockDatabase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Convert hash map into list   
		HashMap<String,Mouse> mice = new HashMap();
		for (Map.Entry<String, Product> e : productHashMap.entrySet()) {
			if (e.getValue() instanceof Mouse) {
				mice.put(e.getKey(), (Mouse)e.getValue());
			}
		}
		List<Entry<String,Mouse>> productList = new LinkedList<Entry<String,Mouse>>(mice.entrySet());  
		// Sort the new linked list  
		Collections.sort(productList, new Comparator<Entry<String,Mouse>>()   
		{  
			public int compare(Entry<String,Mouse> e1, Entry<String,Mouse> e2)   
			{   
				// Compare the retail prices of the current entry and the next one and sort in ascending order  
				return Integer.compare(e1.getValue().getAdditionalInfo(),(e2.getValue().getAdditionalInfo()));
			}
		});
		// Convert linked list back to hash map then return
		Map<String, Mouse> sortedProductMap = new LinkedHashMap<String, Mouse>();  
		for (Entry<String, Mouse> entry : productList)   
		{  
		sortedProductMap.put(entry.getKey(), entry.getValue()); 
		}      
		return sortedProductMap;
	}

	/**
	 * This method determines if the barcode the user has inputted already exists in the product map or not.
	 * @param barcode the barcode to be checked for pre-existence.
	 * @return productExists if the product exists or not.
	 */
	public Boolean doesBarcodeExistInStock(String barcode) {
		return productHashMap.containsKey(barcode);
	}

	/**
	 * This method updates the quantity of a product in the product map.
	 * @param barcode the barcode of the product to be updated.
	 * @param quantityChange the amount to add or taken away from the current quantity.
	 */
	public void updateMap(String barcode, int quantityChange) {
		// Update quantity of a given product in the product map
		Product product = productHashMap.get(barcode);
		int newQuantity = product.getQuantity() + quantityChange;
		product.setQuantity(newQuantity);
		productHashMap.put(barcode, product);
		try {
			writeStockDatabase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method adds a completely new product to the database.
	 * @param barcode the barcode of the new product.
	 * @param product the product object to be added.
	 */
	public void addProductToMap(String barcode, Product product) {
		// Adds a new product to the product map
		productHashMap.put(barcode, product);
		try {
			writeStockDatabase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method returns a list of distinct brands in the product map.
	 * @return brands list containing one of every brand in the product map.
	 */
	public List<String> getBrands(){
		// Gets a list of all unique brands in the database
		Map<String, Product> products = sortStockByBrand();
		List<String> brands = new ArrayList<String>();
		for (Product p: products.values()) {
			if (!brands.contains(p.getBrand())) {
				brands.add(p.getBrand());
			}
		}
		return brands;
	}
	
	/**
	 * This method returns a list of distinct number of buttons in the product map.
	 * @return buttons list containing one of every number of buttons in the product map.
	 */
	public List<String> getButtons(){
		// Gets a list of all unique numbers of buttons in the database
		List<String> buttons = new ArrayList<String>();
		Map<String,Mouse> mice = sortMiceByButtons();
		for (Mouse m : mice.values()) {
			if (!buttons.contains(String.valueOf(m.getAdditionalInfo()))) {
				buttons.add(String.valueOf(m.getAdditionalInfo()));
			}
		}
		return buttons;
	}
}
