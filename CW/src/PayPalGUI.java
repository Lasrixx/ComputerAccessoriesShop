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
 * This class creates the GUI for paying with PayPal.
 * @author Olivia Gray
 */
public class PayPalGUI extends JFrame {

	private JPanel contentPane;
	private JTextField emailInput;
	
	StockManager stock = new StockManager(); 

	/**
	 * This constructor creates the frame.
	 * @param user the user that is paying.
	 */
	public PayPalGUI(Customer user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("Pay using PayPal");
		title.setFont(new Font("Tahoma", Font.PLAIN, 16));
		title.setBounds(150, 61, 136, 20);
		contentPane.add(title);
		
		JLabel emailLabel = new JLabel("Enter email address:");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		emailLabel.setBounds(28, 111, 162, 20);
		contentPane.add(emailLabel);
		
		emailInput = new JTextField();
		emailInput.setBounds(282, 114, 96, 19);
		contentPane.add(emailInput);
		emailInput.setColumns(10);
		
		JLabel emailError = new JLabel("Input valid email address");
		emailError.setForeground(new Color(204, 0, 0));
		emailError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		emailError.setBounds(264, 131, 162, 20);
		contentPane.add(emailError);
		emailError.setVisible(false);
		
		JButton payButton = new JButton("Pay Now!");
		payButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validate email address
				// If not valid, do not proceed with payment
				String email = emailInput.getText();
				// Using a simple custom email regex that will work for emails I can think of
				Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z]+(.[a-zA-Z]+)?$");
				Matcher emailMatcher = emailRegex.matcher(email);
				if (!emailMatcher.find()) {
					emailError.setVisible(true);
					return;
				}
				else {
					emailError.setVisible(false);
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
				ConfirmationGUI cGUI = new ConfirmationGUI(user, totalPrice, "paypal");
				cGUI.setVisible(true);
				dispose();
			}
		});
		payButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		payButton.setBounds(165, 186, 96, 21);
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
		backButton.setBounds(10, 232, 69, 21);
		contentPane.add(backButton);
	}

}
