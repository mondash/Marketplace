import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JList;

public class Window extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel loginPanel = new JPanel();
		tabbedPane.addTab("New tab", null, loginPanel, null);
		
		JPanel loginEnclosingPanel = new JPanel();
		loginPanel.add(loginEnclosingPanel);
		loginEnclosingPanel.setLayout(new BoxLayout(loginEnclosingPanel, BoxLayout.PAGE_AXIS));
		
		JLabel loginLabel = new JLabel("Login");
		loginEnclosingPanel.add(loginLabel);
		
		JPanel textEnclosingPanel = new JPanel();
		loginEnclosingPanel.add(textEnclosingPanel);
		textEnclosingPanel.setLayout(new BoxLayout(textEnclosingPanel, BoxLayout.PAGE_AXIS));
		
		JPanel usernameEnclosingPanel = new JPanel();
		textEnclosingPanel.add(usernameEnclosingPanel);
		usernameEnclosingPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Username");
		usernameEnclosingPanel.add(lblNewLabel);
		
		textField = new JTextField();
		usernameEnclosingPanel.add(textField);
		textField.setColumns(10);
		
		JPanel passwordEnclosingPanel = new JPanel();
		textEnclosingPanel.add(passwordEnclosingPanel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		passwordEnclosingPanel.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordEnclosingPanel.add(passwordField);
		
		JPanel buttonEnclosingPanel = new JPanel();
		loginEnclosingPanel.add(buttonEnclosingPanel);
		buttonEnclosingPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton loginButton = new JButton("Login");
		buttonEnclosingPanel.add(loginButton);
		
		JButton registerButton = new JButton("Register");
		buttonEnclosingPanel.add(registerButton);
		
		JPanel buyerPanel = new JPanel();
		tabbedPane.addTab("New tab", null, buyerPanel, null);
		buyerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel infoEnclosingPanel = new JPanel();
		buyerPanel.add(infoEnclosingPanel);
		infoEnclosingPanel.setLayout(new BoxLayout(infoEnclosingPanel, BoxLayout.PAGE_AXIS));
		
		JLabel productsLabel = new JLabel("Products");
		infoEnclosingPanel.add(productsLabel);
		
		JPanel panel = new JPanel();
		infoEnclosingPanel.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JList list = new JList();
		panel.add(list);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		panel.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("New button");
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		buyerPanel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.PAGE_AXIS));
		
		JMenuBar menuBar = new JMenuBar();
		panel_1.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JTextPane txtpnThisIsThe = new JTextPane();
		txtpnThisIsThe.setText("This is the sample text");
		panel_1.add(txtpnThisIsThe);
	}

}
