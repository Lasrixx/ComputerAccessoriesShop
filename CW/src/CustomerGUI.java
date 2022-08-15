import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;

/**
 * This class creates the frame for the customer to view all products, filter search, and view their basket.
 * @author Olivia Gray
 */
public class CustomerGUI extends JFrame {

	private JPanel contentPane;
	private JTable tblProduct;
	private DefaultTableModel dtmProducts;
	private JTable tblBasket;
	private DefaultTableModel dtmBasket;
	
	private StockManager stock = new StockManager();
	private UserManager um = new UserManager();
	
	private List<String> brandFilter = new ArrayList<String>();
	private List<String> buttonFilterInput = new ArrayList<String>();
	
	private float totalPrice;
	
	 // Create the frame
	 
	/**
	 * This method creates the customer's frame.
	 * @param user the user the frame has been created for.
	 */
	public CustomerGUI(Customer user) {
		// Frame
		setTitle("Shop");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton logOutButton = new JButton("Log Out");
		logOutButton.setBounds(1070, 10, 100, 20);
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				um.openLoginFrame();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(logOutButton);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 25, 1166, 528);
		contentPane.add(tabbedPane);
		
		// View products tab
		JPanel viewPanel = new JPanel();
		viewPanel.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Search", null, viewPanel, null);
		viewPanel.setLayout(null);
		
		//Filter search
		JPanel filterPanel = new JPanel();
		filterPanel.setBackground(new Color(255, 255, 255));
		filterPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		filterPanel.setBounds(10, 35, 1141, 89);
		viewPanel.add(filterPanel);
		filterPanel.setLayout(null);
		
		JLabel filterTitle = new JLabel("Filter Search");
		filterTitle.setBounds(10, 10, 100, 20);
		filterTitle.setBackground(new Color(240, 240, 240));
		filterTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		filterPanel.add(filterTitle);
		
		// Search by brands
		JLabel brandLabel = new JLabel("Search by brand:");
		brandLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		brandLabel.setBounds(37, 40, 117, 20);
		filterPanel.add(brandLabel);
		
		// User should be able to search by multiple brands
		// Scrollpane allows us to include many brands in the search easily
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(156, 26, 215, 53);
		filterPanel.add(scrollPane_1);
		
		JPanel brandPanel = new JPanel();
		brandPanel.setBackground(new Color(255, 255, 255));
		scrollPane_1.setViewportView(brandPanel);
		
		// Create one checkbox for each brand in stock.txt
		// First, need to create a data structure containing exactly one of each brand using a list
		List<String> brands = stock.getBrands();
		for (String brand : brands) {
			JCheckBox brandCheckBox = new JCheckBox(brand);
			brandPanel.add(brandCheckBox);
		}
		
		// Search by number of buttons on the mouse
		JLabel buttonLabel = new JLabel("Search for mouse by number of buttons:");
		buttonLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonLabel.setBounds(427, 40, 258, 20);
		filterPanel.add(buttonLabel);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(691, 26, 215, 53);
		filterPanel.add(scrollPane_2);
		
		JPanel buttonsPanel = new JPanel();
		scrollPane_2.setViewportView(buttonsPanel);
		buttonsPanel.setBackground(new Color(255, 255, 255));
		
		// One checkbox for each unique number of buttons entry for mice in stock.txt
		List<String> buttons = stock.getButtons();
		for (String amount : buttons) {
			JCheckBox buttonsCheckBox = new JCheckBox(amount);
			buttonsPanel.add(buttonsCheckBox);
		}
		
		JButton applyFilterButton = new JButton("Apply Filter");
		applyFilterButton.addActionListener(new ActionListener() {
			// Create a list of what brands and buttons have been checked in the filter section
			public void actionPerformed(ActionEvent e) {
				brandFilter = new ArrayList<String>();
				buttonFilterInput = new ArrayList<String>();
				for (Component c: brandPanel.getComponents()) {
					if (c instanceof JCheckBox) {
						if (((JCheckBox)c).isSelected()) {
							brandFilter.add(((JCheckBox)c).getText());
						}
					}
				}
				for (Component c: buttonsPanel.getComponents()) {
					if (c instanceof JCheckBox) {
						if (((JCheckBox)c).isSelected()) {
							buttonFilterInput.add(((JCheckBox)c).getText());
						}
					}
				}
				updateProductTable();
			}
		});
		applyFilterButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		applyFilterButton.setBounds(1014, 26, 117, 21);
		filterPanel.add(applyFilterButton);
		
		JButton clearFilterButton = new JButton("Clear Filter");
		clearFilterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Update the table without any filters applied to it
				brandFilter = new ArrayList<String>();
				buttonFilterInput = new ArrayList<String>();
				updateProductTable();
				
				// Clear all previous filters 
				for (Component c : brandPanel.getComponents()) {
					if (c instanceof JCheckBox) {
						((JCheckBox) c).setSelected(false);
					}
				}
				for (Component c : buttonsPanel.getComponents()) {
					if (c instanceof JCheckBox) {
						((JCheckBox)c).setSelected(false);
					}
				}
			}
		});
		clearFilterButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		clearFilterButton.setBounds(1014, 50, 117, 21);
		filterPanel.add(clearFilterButton);
				
		JLabel searchTitle = new JLabel("Products");
		searchTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		searchTitle.setBounds(10, 10, 85, 20);
		viewPanel.add(searchTitle);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 158, 1141, 333);
		viewPanel.add(scrollPane);
		
		tblProduct = new JTable();
		tblProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblProduct.getTableHeader().setReorderingAllowed(false);
		tblProduct.setDefaultEditor(Object.class, null);
		tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	// Only accept input if the user clicked the add to cart 'button' (final column of table)
		        int col = tblProduct.columnAtPoint(evt.getPoint());
		        if (col == tblProduct.getColumnCount() - 1) {
		        	// Get the product that was clicked and collect info to pass to add to basket frame
		        	int row = tblProduct.rowAtPoint(evt.getPoint());
		        	String barcode = (String)tblProduct.getValueAt(row, 0);
		        	String productType = (String)tblProduct.getValueAt(row, 1);
		        	String brand = (String)tblProduct.getValueAt(row, 3);
		        	String colour = (String)tblProduct.getValueAt(row, 4);
		        	int maxQuantity = (int)tblProduct.getValueAt(row, 6);
		        	um.openBasketFrame(user, barcode, productType, brand, colour, maxQuantity);
		        }
		    }
		});
		scrollPane.setViewportView(tblProduct);
		
		dtmProducts = new DefaultTableModel();
		dtmProducts.setColumnIdentifiers(new Object[] {"Barcode", "Device Name", "Device Type", "Brand", "Colour", "Connectivity", "Quantity", "Retail Price", "Layout/No of Buttons", ""});
		tblProduct.setModel(dtmProducts);
		
		JLabel resultsTitle = new JLabel("Results");
		resultsTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		resultsTitle.setBounds(10, 134, 85, 20);
		viewPanel.add(resultsTitle);
		
		JPanel cartPanel = new JPanel();
		cartPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Shopping Basket", null, cartPanel, null);
		cartPanel.setLayout(null);
		
		JLabel BasketTitle = new JLabel("Shopping Basket");
		BasketTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		BasketTitle.setBounds(10, 10, 244, 27);
		cartPanel.add(BasketTitle);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 49, 1141, 223);
		cartPanel.add(scrollPane_3);
		
		tblBasket = new JTable();
		scrollPane_3.setViewportView(tblBasket);
		
		dtmBasket = new DefaultTableModel();
		dtmBasket.setColumnIdentifiers(new Object[] {"Product", "Brand", "Colour", "Connectivity", "Quantity", "Retail Price", "Total Price"});
		tblBasket.setModel(dtmBasket);
		
		JButton clearButton = new JButton("Clear Basket");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user.clearBasket();
				updateBasketTable(user);
			}
		});
		clearButton.setBackground(new Color(255, 153, 153));
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		clearButton.setBounds(995, 287, 156, 38);
		cartPanel.add(clearButton);
		
		JButton payButton = new JButton("Pay");
		payButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (totalPrice != 0) {
					PayGUI pGUI = new PayGUI(user);
					pGUI.setVisible(true);
				}
			}
		});
		payButton.setBackground(new Color(204, 255, 153));
		payButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		payButton.setBounds(505, 365, 146, 56);
		cartPanel.add(payButton);
		
		updateProductTable();
		
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// Basket is automatically refreshed when shopping basket tab is pressed
			    JTabbedPane tabSource = (JTabbedPane) e.getSource();
			    String tab = tabSource.getTitleAt(tabSource.getSelectedIndex());
			    if (tab.equals("Shopping Basket")) {
					updateBasketTable(user);
			    }
			    else if (tab.equals("Search")) {
			    	updateProductTable();
			    }
			}
		});
	}
	
	/**
	 * This method gets all products from StockManager, applies any necessary filters, then displays the results in a JTable.
	 */
	private void updateProductTable() {
		// Remove previous results from table (prevents duplication)
		while (dtmProducts.getRowCount()>0)
        {
           dtmProducts.removeRow(0);
        }
		
		// Get product map, want to display everything except original price
		// Blank column at end will hold a button for adding to cart
		Map<String, Product> products = stock.sortStockByRetailPrice();
		
		// If the filters are empty, the customer should be shown everything so get all brands
		// and all amounts of buttons
		if (brandFilter.isEmpty()) {
			brandFilter = stock.getBrands();
		}
		for (Product product: products.values()) {
			List<String> buttonFilter = buttonFilterInput;
			if (brandFilter.contains(product.getBrand())) {
				// If button amount has been specified, assume the user does not want to see a keyboard
				if (buttonFilter.isEmpty()) {
					if (product instanceof Keyboard) {
						Keyboard kb = (Keyboard)product;
						Object[] rowData = new Object[] {kb.getBarcode(), 
													 "Keyboard", 
													 kb.formatString(kb.getType().name()),
													 kb.formatString(kb.getBrand()), 
													 kb.formatString(kb.getColour()),
													 kb.formatString(kb.getConnectivity().name()),
													 kb.getQuantity(),
													 "£" + kb.formatFloat(kb.getRetailPrice()),  
													 kb.getAdditionalInfo(),
													 "Add to Cart"};
						dtmProducts.addRow(rowData);
					}
					// If button filter is empty get all values so all mice will be returned
					buttonFilter = stock.getButtons();
				}
				if (product instanceof Mouse) {
					Mouse m = (Mouse)product;
					if (buttonFilter.contains(String.valueOf(m.getAdditionalInfo()))) {
						Object[] rowData = new Object[] {m.getBarcode(), 
								 "Mouse", 
								 m.formatString(m.getType().name()),
								 m.formatString(m.getBrand()), 
								 m.formatString(m.getColour()),
								 m.formatString(m.getConnectivity().name()),
								 m.getQuantity(),
								 "£" + m.formatFloat(m.getRetailPrice()), 
								 m.getAdditionalInfo(),
								 "Add to Cart"};
						dtmProducts.addRow(rowData);
					}
				}
			}
		}
	}

	/**
	 * Gets all products in the user's basket and displays them in a JTable.
	 * @param user the customer the basket belongs to.
	 */
	public void updateBasketTable(Customer user) {
		// Remove previous results from table (prevents duplication)
		while (dtmBasket.getRowCount()>0)
        {
           dtmBasket.removeRow(0);
        }
		
		// Get stock 
		Map<String,Product> products = stock.sortStockByRetailPrice();
		
		// Get contents of user's basket
		Map<String, Integer> basket = user.getBasket();
		
		// Set variable to keep track of total cost of basket
		totalPrice = 0f;
		
		for(Map.Entry<String, Integer> item: basket.entrySet()) {
			// Using barcode, we can get all the products' associated data from stock manager
			Product p = products.get(item.getKey());
			String keyboardMouse = "";
			if (p instanceof Keyboard) {
				keyboardMouse = "Keyboard";
			}
			else if (p instanceof Mouse) {
				keyboardMouse = "Mouse";
			}
			float price = p.getRetailPrice()*item.getValue();
			Object[] rowData = new Object[] {keyboardMouse,
											 p.formatString(p.getBrand()),
											 p.formatString(p.getColour()),
											 p.formatString(p.getConnectivity().name()),
											 item.getValue(),
											 p.formatFloat(p.getRetailPrice()),
											 p.formatFloat(price)
											};
			dtmBasket.addRow(rowData);
			totalPrice += price;
		}
		
		if (!basket.entrySet().isEmpty()) {
			Object[] totalPriceRow = new Object[] {null, null, null, null, null, null, String.format("%.02f",totalPrice)};
			dtmBasket.addRow(totalPriceRow);
		}
		
	}
}
