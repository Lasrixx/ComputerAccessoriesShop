import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class creates the frame specifically for the administrator. The admin can add and update the stock.
 * @author Olivia Gray
 */

public class AdminGUI extends JFrame {

	private JPanel contentPane;
	private JTable tblProducts;
	private DefaultTableModel dtmProducts;
	private JTextField barcodeInput;
	private JPanel productExistsPanel;
	private JPanel productNewPanel;
	private JTextField brandInput;
	private JTextField colourInput;
	private JTextField originalPriceInput;
	private JTextField retailPriceInput;
	private DefaultComboBoxModel typeModel;
	private JLabel layoutLabel;
	private JComboBox layoutInput;
	private JLabel buttonsLabel;
	private JSpinner buttonsInput;
	private JLabel colourError;
	private JLabel retailPriceError;
	private JLabel originalPriceError;
	private JLabel brandError;
	
	StockManager stock = new StockManager();
	UserManager um = new UserManager();

	/**
	 * This is the constructor for the administrator GUI.
	 */
	public AdminGUI() {
		setTitle("Stock");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("Log Out");
		btnNewButton.setBounds(1070, 10, 100, 20);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				um.openLoginFrame();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnNewButton);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 26, 1166, 527);
		contentPane.add(tabbedPane);
		
		// View Products panel includes table with all products stored in Stock.txt
		JPanel viewPanel = new JPanel();
		viewPanel.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("View Products", null, viewPanel, null);
		tabbedPane.setBackgroundAt(0, new Color(255, 255, 255));
		viewPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 1141, 446);
		viewPanel.add(scrollPane);
		
		// Table with all product information in 
		tblProducts = new JTable();	
		scrollPane.setViewportView(tblProducts);
		tblProducts.getTableHeader().setReorderingAllowed(false);
		tblProducts.setDefaultEditor(Object.class, null);
		tblProducts.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	// If admin clicks on a row it should move to update database with barcode prefilled
	        	int col = tblProducts.columnAtPoint(evt.getPoint());
	        	if (col == tblProducts.getColumnCount() - 1) {
		        	int row = tblProducts.rowAtPoint(evt.getPoint());
		        	tabbedPane.setSelectedIndex(1);
		        	barcodeInput.setText((String)tblProducts.getValueAt(row, 0));
		        	showHideStockPanels(true, false);
	        	}
		    }
		});
		
		// Table headers and structure
		dtmProducts = new DefaultTableModel();
		dtmProducts.setColumnIdentifiers(new Object[] {"Barcode", "Device Name", "Device Type", "Brand", "Colour", "Connectivity", "Quantity", "Original Price", "Retail Price", "Additional Info", ""});
		tblProducts.setModel(dtmProducts);
		
		JLabel lblNewLabel = new JLabel("All Products");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 10, 92, 24);
		viewPanel.add(lblNewLabel);
		
		// Get data from stock.txt and put it in the table
		updateStockTable();
		
		// Add products panel allows admin to add or update stock in stock.txt
		JPanel addPanel = new JPanel();
		addPanel.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Add Products", null, addPanel, null);
		tabbedPane.setBackgroundAt(1, new Color(255, 255, 255));
		addPanel.setLayout(null);
		
		JLabel addProductTitle = new JLabel("Add Product to Stock");
		addProductTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addProductTitle.setBounds(10, 10, 159, 20);
		addPanel.add(addProductTitle);
		
		JLabel successLabel = new JLabel("Database Updated");
		successLabel.setHorizontalAlignment(SwingConstants.CENTER);
		successLabel.setForeground(new Color(50, 205, 50));
		successLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		successLabel.setBounds(745, 247, 406, 29);
		addPanel.add(successLabel);
		successLabel.setVisible(false);
		
		JLabel barcodeLabel = new JLabel("Enter Product Barcode:");
		barcodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		barcodeLabel.setBounds(34, 40, 159, 20);
		addPanel.add(barcodeLabel);
		
		barcodeInput = new JTextField();
		barcodeInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		barcodeInput.setBounds(219, 43, 96, 19);
		addPanel.add(barcodeInput);
		barcodeInput.setColumns(10);
		
		JLabel barcodeError = new JLabel("Barcode must be sequence of 6 digits");
		barcodeError.setForeground(new Color(204, 0, 51));
		barcodeError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		barcodeError.setBounds(537, 41, 250, 19);
		addPanel.add(barcodeError);
		barcodeError.setVisible(false);
		
		JButton checkStockButton = new JButton("Check For Product");
		checkStockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String barcode = barcodeInput.getText();
				// Validate barcode text				
				// Barcode must only contain numbers	
				// Barcode must be 6 characters long
				Pattern barcodeRegex = Pattern.compile("^[0-9]{6}$");
				Matcher barcodeMatcher = barcodeRegex.matcher(barcode);
				if (!barcodeMatcher.find()) {
					showHideStockPanels(true, true);
					barcodeError.setVisible(true);
					return;
				}
				// If barcode exists in stock already, just need to get quantity being added
				// If barcode does not exist, need to get all information and submit to stock.txt
				barcodeError.setVisible(false);
				showHideStockPanels(stock.doesBarcodeExistInStock(barcode),false);
				buttonsLabel.setVisible(false);
				buttonsInput.setVisible(false);	
				successLabel.setVisible(false);
				colourError.setVisible(false);
				retailPriceError.setVisible(false);
				originalPriceError.setVisible(false);
				brandError.setVisible(false);
			}
		});
		checkStockButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		checkStockButton.setBounds(362, 40, 159, 21);
		addPanel.add(checkStockButton);
		
		// This panel will only be shown when the admin tries to add a pre-existing product to stock.txt
		productExistsPanel = new JPanel();
		productExistsPanel.setBackground(new Color(255, 255, 255));
		productExistsPanel.setBounds(10, 70, 1141, 63);
		addPanel.add(productExistsPanel);
		productExistsPanel.setLayout(null);
		
		JLabel productExistsLabel = new JLabel("Product with given barcode exists in database");
		productExistsLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		productExistsLabel.setBounds(10, 10, 307, 23);
		productExistsPanel.add(productExistsLabel);
		
		JLabel existsQuantityLabel = new JLabel("Enter quantity of product to add to database:");
		existsQuantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		existsQuantityLabel.setBounds(10, 32, 289, 23);
		productExistsPanel.add(existsQuantityLabel);
		
		JSpinner existsQuantityInput = new JSpinner();
		existsQuantityInput.setModel(new SpinnerNumberModel(1, 0, null, 1));
		existsQuantityInput.setBounds(335, 36, 112, 20);
		productExistsPanel.add(existsQuantityInput);
		
		JButton updateStockButton = new JButton("Update database");
		updateStockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Update product quantity in database
				int quantity = (int)existsQuantityInput.getValue();
				String barcode = barcodeInput.getText();
				stock.updateMap(barcode, quantity);
				successLabel.setVisible(true);
				successLabel.setText(quantity+"x" + "\'"+ barcode +"\' added to database");
				existsQuantityInput.setValue(1);
				barcodeInput.setText("");
				showHideStockPanels(true, true);
			}
		});
		updateStockButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		updateStockButton.setBounds(470, 32, 147, 23);
		productExistsPanel.add(updateStockButton);
		
		productNewPanel = new JPanel();
		productNewPanel.setBackground(new Color(255, 255, 255));
		productNewPanel.setBounds(10, 134, 725, 356);
		addPanel.add(productNewPanel);
		productNewPanel.setLayout(null);
		
		JLabel productNewLabel = new JLabel("Product with given barcode does not exist in database");
		productNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		productNewLabel.setBounds(10, 10, 363, 19);
		productNewPanel.add(productNewLabel);
		
		JLabel brandLabel = new JLabel("Enter product brand:");
		brandLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		brandLabel.setBounds(10, 68, 142, 19);
		productNewPanel.add(brandLabel);
		
		brandInput = new JTextField();
		brandInput.setBounds(208, 70, 96, 19);
		productNewPanel.add(brandInput);
		brandInput.setColumns(10);
		
		JLabel colourLabel = new JLabel("Enter product colour:");
		colourLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		colourLabel.setBounds(10, 97, 142, 19);
		productNewPanel.add(colourLabel);
		
		colourInput = new JTextField();
		colourInput.setBounds(208, 99, 96, 19);
		productNewPanel.add(colourInput);
		colourInput.setColumns(10);
		
		JLabel connectivityLabel = new JLabel("Enter product connectivity:");
		connectivityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		connectivityLabel.setBounds(10, 126, 186, 19);
		productNewPanel.add(connectivityLabel);
		
		JLabel keyboardMouseLabel = new JLabel("What type of product?");
		keyboardMouseLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		keyboardMouseLabel.setBounds(10, 39, 164, 19);
		productNewPanel.add(keyboardMouseLabel);
		
		JComboBox keyboardMouseInput = new JComboBox(new String[] {"Keyboard", "Mouse"});
		keyboardMouseInput.setBounds(208, 39, 96, 21);
		keyboardMouseInput.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				// Display certain inputs depending on whether mouse or keyboard has been selected
				String input = (String)keyboardMouseInput.getSelectedItem();
				if (input.equals("Keyboard")) {
					typeModel.removeAllElements();
					for (KeyboardType type : KeyboardType.values()) {
						typeModel.addElement(type);
					}
					buttonsLabel.setVisible(false);
					buttonsInput.setVisible(false);
					layoutLabel.setVisible(true);
					layoutInput.setVisible(true);
					
				}
				else if (input.equals("Mouse")) {
					typeModel.removeAllElements();
					for (MouseType type : MouseType.values()) {
						typeModel.addElement(type);
					}
					layoutLabel.setVisible(false);
					layoutInput.setVisible(false);
					buttonsLabel.setVisible(true);
					buttonsInput.setVisible(true);
				}
			}
		});
		productNewPanel.add(keyboardMouseInput);
		
		JLabel originalPriceLabel = new JLabel("Enter original price:");
		originalPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		originalPriceLabel.setBounds(10, 155, 143, 19);
		productNewPanel.add(originalPriceLabel);
		
		originalPriceInput = new JTextField();
		originalPriceInput.setBounds(208, 157, 96, 19);
		productNewPanel.add(originalPriceInput);
		originalPriceInput.setColumns(10);
		
		JLabel retailPriceLabel = new JLabel("Enter retail price:");
		retailPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		retailPriceLabel.setBounds(10, 184, 142, 19);
		productNewPanel.add(retailPriceLabel);
		
		retailPriceInput = new JTextField();
		retailPriceInput.setBounds(208, 186, 96, 19);
		productNewPanel.add(retailPriceInput);
		retailPriceInput.setColumns(10);
		
		JLabel typeLabel = new JLabel("Enter product type:");
		typeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		typeLabel.setBounds(10, 217, 164, 19);
		productNewPanel.add(typeLabel);
		
		typeModel = new DefaultComboBoxModel(KeyboardType.values());
		JComboBox typeInput = new JComboBox(typeModel);
		typeInput.setBounds(208, 215, 96, 21);
		productNewPanel.add(typeInput);
		
		JComboBox connectivityInput = new JComboBox(Connectivity.values());
		connectivityInput.setBounds(208, 128, 96, 21);
		productNewPanel.add(connectivityInput);
		
		layoutLabel = new JLabel("Enter keyboard layout:");
		layoutLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		layoutLabel.setBounds(10, 248, 186, 19);
		productNewPanel.add(layoutLabel);
		
		layoutInput = new JComboBox(KeyboardLayout.values());
		layoutInput.setBounds(208, 246, 96, 21);
		productNewPanel.add(layoutInput);
		
		JLabel newQuantityLabel = new JLabel("Enter quantity:");
		newQuantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		newQuantityLabel.setBounds(10, 277, 164, 21);
		productNewPanel.add(newQuantityLabel);
		
		JSpinner newQuantityInput = new JSpinner();
		newQuantityInput.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		newQuantityInput.setBounds(208, 280, 96, 20);
		productNewPanel.add(newQuantityInput);
		
		JButton addStockButton = new JButton("Add Product to Database");
		addStockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Collect inputs and validate as we go, if any don't meet criteria, give an error message
				String barcode = barcodeInput.getText();
				// Already validated barcode
				String keyboardMouse = (String)keyboardMouseInput.getSelectedItem();
				// Brand does not need validation - a brand could technically contain any characters in any sequence
				// It only needs to not be empty
				String brand = brandInput.getText();
				if (brand.length() == 0) {
					brandError.setVisible(true);
					return;
				}
				else {
					brandError.setVisible(false);
				}
				// Colour should only be made up of letters - this should prompt the user to only use named colours
				String colour = colourInput.getText();
				Pattern colourRegex = Pattern.compile("^[a-zA-Z]+$");
				Matcher colourMatcher = colourRegex.matcher(colour);
				if (!colourMatcher.find()) {
					colourError.setVisible(true);
					return;
				}
				else {
					colourError.setVisible(false);
				}
				Connectivity connectivity = Connectivity.valueOf(connectivityInput.getSelectedItem().toString().toUpperCase());
				int quantity = (int)newQuantityInput.getValue();
				// Prices must be numeric, can include decimal, may start with £
				String originalPrice = originalPriceInput.getText();
				String retailPrice = retailPriceInput.getText();
				Pattern priceRegex = Pattern.compile("^£?[0-9]+([,.][0-9]{1,2})?");
				Matcher originalMatcher = priceRegex.matcher(originalPrice);
				if (!originalMatcher.find()) {
					originalPriceError.setVisible(true);
					return;
				}
				else {
					originalPriceError.setVisible(false);
				}
				Matcher retailMatcher = priceRegex.matcher(retailPrice);
				if (!retailMatcher.find()) {
					retailPriceError.setVisible(true);
					return;
				}
				else {
					retailPriceError.setVisible(false);
				}
				
				// Adds the new product with all its details to the database
				if (keyboardMouse.equals("Keyboard")) {
					KeyboardType type = KeyboardType.valueOf(typeInput.getSelectedItem().toString().toUpperCase());
					KeyboardLayout layout = KeyboardLayout.valueOf(layoutInput.getSelectedItem().toString().toUpperCase());
					Keyboard product = new Keyboard(barcode,brand,colour,connectivity,quantity,Float.valueOf(originalPrice),Float.valueOf(retailPrice),type,layout);
					stock.addProductToMap(barcode, product);
				}
				else if (keyboardMouse.equals("Mouse")) {
					MouseType type = MouseType.valueOf(typeInput.getSelectedItem().toString().toUpperCase());
					int buttons = (int)buttonsInput.getValue();
					if (buttons < 0) {
						
					}
					Mouse product = new Mouse(barcode,brand,colour,connectivity,quantity,Float.valueOf(originalPrice),Float.valueOf(retailPrice),type,buttons);
					stock.addProductToMap(barcode, product);
				}
				successLabel.setVisible(true);
				successLabel.setText(quantity+"x" + "\'"+ barcode +"\' added to database");
				
				// Clear the previous inputs ready for the next product to be added
				barcodeInput.setText("");
				keyboardMouseInput.setSelectedIndex(0);
				brandInput.setText("");
				colourInput.setText("");
				connectivityInput.setSelectedIndex(0);
				originalPriceInput.setText("");
				retailPriceInput.setText("");
				typeInput.setSelectedIndex(0);
				layoutInput.setSelectedIndex(0);
				buttonsInput.setValue(1);
				showHideStockPanels(true, true);				
			}
		});
		addStockButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addStockButton.setBounds(10, 313, 294, 33);
		productNewPanel.add(addStockButton);
		
		buttonsLabel = new JLabel("Enter number of buttons:");
		buttonsLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonsLabel.setBounds(10, 250, 173, 15);
		productNewPanel.add(buttonsLabel);		
		
		buttonsInput = new JSpinner();
		buttonsInput.setModel(new SpinnerNumberModel(1, 0, null, 1));
		buttonsInput.setBounds(208, 246, 96, 20);
		productNewPanel.add(buttonsInput);	
		
		colourError = new JLabel("Colour must be named value (e.g. green)");
		colourError.setForeground(new Color(204, 0, 51));
		colourError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		colourError.setBounds(349, 97, 344, 19);
		productNewPanel.add(colourError);
		colourError.setVisible(false);
		
		retailPriceError = new JLabel("Retail price must be in format pp.pp");
		retailPriceError.setForeground(new Color(204, 0, 0));
		retailPriceError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		retailPriceError.setBounds(349, 184, 344, 19);
		productNewPanel.add(retailPriceError);
		retailPriceError.setVisible(false);
		
		originalPriceError = new JLabel("Original Price must be in format pp.pp");
		originalPriceError.setForeground(new Color(204, 0, 0));
		originalPriceError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		originalPriceError.setBounds(351, 155, 342, 19);
		productNewPanel.add(originalPriceError);
		
		brandError = new JLabel("Brand name must be inputted");
		brandError.setForeground(new Color(204, 0, 0));
		brandError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		brandError.setBounds(349, 68, 344, 21);
		productNewPanel.add(brandError);
		originalPriceError.setVisible(false);
		
		showHideStockPanels(true, true);
		
		// Add the event listener at the end to allow all elements to be created before we try alter them
		// This event listener means the stock table will be automatically updated when the user
		// presses on view products tab, rather than having to press a refresh table button
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			    JTabbedPane tabSource = (JTabbedPane) e.getSource();
			    String tab = tabSource.getTitleAt(tabSource.getSelectedIndex());
			    if (tab.equals("View Products")) {
					updateStockTable();
			    }
			}
		});
	}

	/**
	 * This method gets all the stock from StockManager and divides it up to display it in the stock JTable.
	 */
	private void updateStockTable() {
		// First, empty the previous contents of the table
		// This is necessary for refreshing the table during run-time
		while (dtmProducts.getRowCount()>0)
        {
           dtmProducts.removeRow(0);
        }
		// Convert each value in the stock hash map to an array containing the product's data
		// then insert it into the table in a captialised form
		Map<String,Product> stockHashMap = stock.sortStockByRetailPrice();
		for (Product product : stockHashMap.values()) {
			if (product instanceof Keyboard) {
				Keyboard kb = (Keyboard)product;
				Object[] rowData = new Object[] {kb.getBarcode(), 
											 "Keyboard", 
											 kb.formatString(kb.getType().name()),
											 kb.formatString(kb.getBrand()), 
											 kb.formatString(kb.getColour()),
											 kb.formatString(kb.getConnectivity().name()),
											 kb.getQuantity(), 
											 "£" + kb.formatFloat(kb.getOriginalPrice()),
											 "£" + kb.formatFloat(kb.getRetailPrice()), 
											 kb.getAdditionalInfo(),
											 "Update Quantity"};
				dtmProducts.addRow(rowData);
			}
			else if (product instanceof Mouse) {
				Mouse m = (Mouse)product;
				Object[] rowData = new Object[] {m.getBarcode(), 
						 "Mouse", 
						 m.formatString(m.getType().name()),
						 m.formatString(m.getBrand()), 
						 m.formatString(m.getColour()),
						 m.formatString(m.getConnectivity().name()),
						 m.getQuantity(), 
						 "£" + m.formatFloat(m.getOriginalPrice()),
						 "£" + m.formatFloat(m.getRetailPrice()), 
						 m.getAdditionalInfo(),
						 "Update Quantity"};
				dtmProducts.addRow(rowData);
			}
		}
	}
	
	/**
	 * This method hides or shows the 2 sections for inputting into the stock database
	 * depending on if the product already exists in the stock database or not.
	 * @param productExists signifies whether the product's barcode already exists in the database.
	 * @param inStartUp whether both sections should be hidden - true when no barcode / incorrect barcode is inputted.
	 */
	private void showHideStockPanels(Boolean productExists, Boolean inStartUp) {
		/* Shows and hides the 2 sections: for updating quantity, and for adding a new product
	 	Depending on whether the barcode exists in the database or not
	 	*/
		for (int i = 0; i < productExistsPanel.getComponentCount(); i++) {
			productExistsPanel.getComponents()[i].setVisible(productExists && !inStartUp);
		}
		for (int i = 0; i < productNewPanel.getComponentCount(); i++) {
			productNewPanel.getComponents()[i].setVisible(!productExists && !inStartUp);
		}
	}
}
