import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

/**
 * This class creates the GUI using JSwing for the frame where the user needs to add an item to their basket.
 * @author Olivia Gray
 */

public class AddBasketGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * This method is a constructor for the frame.
	 * @param user the user currently logged into the system.
	 * @param barcode barcode of the item being added to the basket.
	 * @param product the type of product being added to the basket - either mouse or keyboard.
	 * @param brand the product's brand.
	 * @param colour the colour of the product being added to the basket.
	 * @param maxQuantity the maximum possible quantity the user can add to their basket.
	 * If the quantity the user wants is greater than the amount in stock, create a pop up to tell them it has been reduced to max quantity. 
	 */
	public AddBasketGUI(Customer user, String barcode, String product, String brand, String colour, int maxQuantity) {
		setBackground(new Color(255, 255, 255));
		setTitle("Add To Basket");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel basketLabel = new JLabel("Add to Basket");
		basketLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		basketLabel.setBounds(10, 10, 166, 20);
		contentPane.add(basketLabel);
		
		JLabel productTitle = new JLabel("Product Details");
		productTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		productTitle.setBounds(10, 40, 100, 18);
		contentPane.add(productTitle);
		
		JLabel productLabel = new JLabel("Product Type: " + product);
		productLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		productLabel.setBounds(35, 68, 356, 20);
		contentPane.add(productLabel);
		
		JLabel brandLabel = new JLabel("Brand: " + brand);
		brandLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		brandLabel.setBounds(35, 98, 356, 20);
		contentPane.add(brandLabel);
		
		JLabel colourLabel = new JLabel("Colour: " + colour.substring(0, 1).toUpperCase() + colour.substring(1));
		colourLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		colourLabel.setBounds(35, 128, 356, 20);
		contentPane.add(colourLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 158, 416, 95);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel quantityLabel = new JLabel("Select Quantity:");
		quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		quantityLabel.setBounds(75, 7, 251, 17);
		panel.add(quantityLabel);
		quantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		// For JSpinner need to get initial value
		// If the user already has the given product in their basket, initial value should be quantity in basket
		// Else, initial value should be 1
		int currentQuantity = 1;
		if (user.getBasket().containsKey(barcode)) {
			currentQuantity = user.getBasket().get(barcode);
		}
		
		JSpinner quantityInput = new JSpinner();
		quantityInput.setModel(new SpinnerNumberModel(currentQuantity, 1, null, 1));
		quantityInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		quantityInput.setBounds(131, 34, 148, 20);
		panel.add(quantityInput);
		
		JButton addButton = new JButton("Add to Basket");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int quantity = (int)quantityInput.getValue(); 
				if (quantity > maxQuantity) {
					// Create pop-up to notify the user that the item they want does not have enough stock
					quantity = maxQuantity;
					OutOfStockGUI oGUI = new OutOfStockGUI(maxQuantity);
					oGUI.setVisible(true);
				}
				user.addItemToBasket(barcode, quantity);
				dispose();
			}
		});
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addButton.setBounds(75, 64, 251, 21);
		panel.add(addButton);
		
		// Set text of quantityLabel and addButton depending on if a new product is being added to the basket
		// Or whether the quantity of a pre-existing product is being updated
		if (user.getBasket().containsKey(barcode)) {
			quantityLabel.setText("Item already in basket");
			quantityLabel.setForeground(new Color(250,0,0));
			addButton.setText("Update Quantity in Basket");
		}
		else{
			quantityLabel.setText("Add quantity to basket:");
			addButton.setText("Add to Basket");
		}
		
	}
}
