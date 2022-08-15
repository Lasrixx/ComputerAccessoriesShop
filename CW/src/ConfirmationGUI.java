import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * This class creates the frame that confirms payment has been received from the user.
 * @author Olivia Gray
 */

public class ConfirmationGUI extends JFrame {

	private JPanel contentPane;

	UserManager um = new UserManager();
	
	/**
	 * This constructor creates the frame.
	 * @param user the user that has just paid. Used to get their address.
	 * @param totalPrice the amount the user has just paid.
	 * @param payment the type of payment used (credit card or paypal).
	 */
	public ConfirmationGUI(Customer user, float totalPrice, String payment) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Create payment confirmation text
		String text = "";
		if (payment.equals("card")) {
			text = "£" + String.format("%.02f",totalPrice) + " paid using Credit Card,";
		}
		else if (payment.equals("paypal")) {
			text = "£" + String.format("%.02f",totalPrice) + " paid using PayPal,";
		}
		
		JLabel confirmationPrice = new JLabel(text);
		confirmationPrice.setHorizontalAlignment(SwingConstants.CENTER);
		confirmationPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		confirmationPrice.setBounds(10, 81, 416, 32);
		contentPane.add(confirmationPrice);
		
		JLabel title = new JLabel("Thank You!");
		title.setFont(new Font("Tahoma", Font.PLAIN, 16));
		title.setBounds(175, 51, 87, 20);
		contentPane.add(title);
		
		JButton continueButton = new JButton("Continue Shopping");
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		continueButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		continueButton.setBounds(137, 181, 170, 32);
		contentPane.add(continueButton);
		
		JButton logOutButton = new JButton("Log Out");
		logOutButton.addActionListener(new ActionListener() {
			// Return to log in menu
			public void actionPerformed(ActionEvent e) {
				um.openLoginFrame();
				dispose();
			}
		});
		logOutButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		logOutButton.setBounds(177, 223, 85, 21);
		contentPane.add(logOutButton);
		
		JLabel confirmationText2 = new JLabel("and the delivery address is");
		confirmationText2.setHorizontalAlignment(SwingConstants.CENTER);
		confirmationText2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		confirmationText2.setBounds(10, 111, 416, 20);
		contentPane.add(confirmationText2);
		
		JLabel confirmationAddress = new JLabel(user.getAddress());
		confirmationAddress.setHorizontalAlignment(SwingConstants.CENTER);
		confirmationAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		confirmationAddress.setBounds(10, 129, 426, 30);
		contentPane.add(confirmationAddress);
	}

}
