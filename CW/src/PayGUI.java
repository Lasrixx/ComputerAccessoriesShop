import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * This class creates the GUI where the user can choose to pay by card or with paypal.
 * @author Olivia Gray
 */
public class PayGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * This is the constructor for the frame.
	 * @param user the user that is logged in and paying.
	 */
	public PayGUI(Customer user) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel payLabel = new JLabel("Select payment method:");
		payLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		payLabel.setBounds(137, 61, 175, 25);
		contentPane.add(payLabel);
		
		JButton cardButton = new JButton("Pay with Credit Card");
		cardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardGUI cGUI = new CardGUI(user);
				cGUI.setVisible(true);
				dispose();
			}
		});
		cardButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cardButton.setBounds(137, 121, 175, 25);
		contentPane.add(cardButton);
		
		JButton paypalButton = new JButton("Pay with PayPal");
		paypalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PayPalGUI pGUI = new PayPalGUI(user);
				pGUI.setVisible(true);
				dispose();
			}
		});
		paypalButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		paypalButton.setBounds(137, 156, 175, 25);
		contentPane.add(paypalButton);
	}
}
