import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class creates the pop-up frame for when the user wants to add more of a product than there is in stock.
 * @author Olivia Gray
 */

public class OutOfStockGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * This is constructor for the frame.
	 * @param maxQuantity the quantity the product in the user's basket will be defaulted to.
	 */
	public OutOfStockGUI(int maxQuantity) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel infoLabel = new JLabel("New label");
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		infoLabel.setBounds(10, 54, 416, 132);
		contentPane.add(infoLabel);
		// This label should inform the user that the quantity they have requested is unavailable
		// It should tell them that it has defaulted to the max quantity in stock
		infoLabel.setText("Maximum quantity is " + maxQuantity + ": defaulted to maximum value");
		
		JLabel stockTitle = new JLabel("Oh No! Item you requested is out of stock");
		stockTitle.setHorizontalAlignment(SwingConstants.CENTER);
		stockTitle.setForeground(new Color(204, 0, 0));
		stockTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		stockTitle.setBounds(10, 10, 416, 34);
		contentPane.add(stockTitle);
		
		JButton continueButton = new JButton("OK");
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		continueButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		continueButton.setBounds(183, 221, 68, 21);
		contentPane.add(continueButton);
	}

}
