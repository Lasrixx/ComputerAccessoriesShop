import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * This class creates the frame for getting the user's card details when they are paying. 
 * @author Olivia Gray
 */

public class CardGUI extends JFrame {

	private JPanel contentPane;
	private JTextField numberInput;
	private JTextField codeInput;
	
	StockManager stock = new StockManager();
	UserManager um = new UserManager();

	/**
	 * This is the constructor for the frame.
	 * @param user the user that is paying.
	 */
	public CardGUI(Customer user) {
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel cardTitle = new JLabel("Payment by Card");
		cardTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cardTitle.setBounds(160, 43, 132, 25);
		contentPane.add(cardTitle);
		
		JLabel numberLabel = new JLabel("Enter card number:");
		numberLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		numberLabel.setBounds(48, 97, 132, 17);
		contentPane.add(numberLabel);
		
		numberInput = new JTextField();
		numberInput.setBounds(272, 98, 96, 19);
		contentPane.add(numberInput);
		numberInput.setColumns(10);
		
		JLabel codeLabel = new JLabel("Enter security code:");
		codeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		codeLabel.setBounds(48, 142, 132, 17);
		contentPane.add(codeLabel);
		
		codeInput = new JTextField();
		codeInput.setBounds(272, 143, 96, 19);
		contentPane.add(codeInput);
		codeInput.setColumns(10);
		
		JLabel numberError = new JLabel("Card number must be 6 digit value");
		numberError.setForeground(new Color(204, 0, 0));
		numberError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		numberError.setBounds(206, 116, 220, 17);
		contentPane.add(numberError);
		numberError.setVisible(false);
		
		JLabel codeError = new JLabel("Security code must be 3 digit value");
		codeError.setForeground(new Color(204, 0, 0));
		codeError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		codeError.setBounds(204, 160, 222, 17);
		contentPane.add(codeError);
		codeError.setVisible(false);
		
		JButton payButton = new JButton("Pay Now!");
		payButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get card details and validate: do not continue through payment if details incorrect
				String cardNumber = numberInput.getText();
				Pattern numberRegex = Pattern.compile("^[0-9]{6}$");
				Matcher numberMatcher = numberRegex.matcher(cardNumber);
				if (!numberMatcher.find()) {
					numberError.setVisible(true);
					return;
				}
				else {
					numberError.setVisible(false);
				}
				String securityCode = codeInput.getText();
				Pattern codeRegex = Pattern.compile("^[0-9]{3}$");
				Matcher codeMatcher = codeRegex.matcher(securityCode);
				if (!codeMatcher.find()) {
					codeError.setVisible(true);
					return;
				}
				else {
					codeError.setVisible(false);
				}
				
				Map<String, Product> products = stock.sortStockByRetailPrice();
				Map<String, Integer> basket = user.getBasket();
				// Calculate total price, remove the bought quantity of the products from the database, and empty user's basket
				float totalPrice = 0f;
				for(Map.Entry<String, Integer> item: basket.entrySet()) {
					Product p = products.get(item.getKey());
					totalPrice += p.getRetailPrice()*item.getValue();
					stock.updateMap(item.getKey(), -item.getValue());
				}
				user.clearBasket();
				ConfirmationGUI cGUI = new ConfirmationGUI(user, totalPrice, "card");
				cGUI.setVisible(true);
				dispose();
			}
		});
		payButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		payButton.setBounds(172, 204, 96, 25);
		contentPane.add(payButton);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PayGUI pGUI = new PayGUI(user);
				pGUI.setVisible(true);
				dispose();
			}
		});
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		backButton.setBounds(10, 232, 67, 21);
		contentPane.add(backButton);
	}
}
