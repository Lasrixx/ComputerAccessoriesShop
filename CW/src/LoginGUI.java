import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Color;

/**
 * This class is used to create the initial GUI for the user to pick their account and log in.
 * @author Olivia Gray
 */

public class LoginGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * This method creates the frame that users can log in on.
	 * @param users list of all users in UserManager. Creates one button for each user.
	 */
	public LoginGUI(List<User> users) {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel loginLabel = new JLabel("Select User:");
		loginLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loginLabel.setBounds(175, 30, 75, 25);
		contentPane.add(loginLabel);
		
		// Create one button for each user in the UserAccounts.txt file
		// Position them slightly apart from each other
		
		int yPos = 60;
		for (User user : users) {
			String userType = "";
			if (user instanceof Admin) {
				userType = "Admin";
			}
			else{
				userType = "Customer";
			}
			JButton loginButton = new JButton(userType + " - " + user.getUsername());
			loginButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UserManager um = new UserManager();
					if (user instanceof Customer) {
						um.openCustomerFrame(user);
					}
					else if (user instanceof Admin) {
						um.openAdminFrame();
					}
				}
			});
			loginButton.setBounds(130, yPos, 175, 20);
			contentPane.add(loginButton);
			yPos += 25;
		}
	}
}
