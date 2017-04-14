import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class GUI extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private Marketplace store;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;

	private JPanel loginPanel;

	public GUI() {
		super();

		store = new Marketplace();
		initLoginPanel();
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setSize(WIDTH, HEIGHT);
		setLayout(new BorderLayout());
		setTitle("Marketplace");
		// setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(loginPanel, BorderLayout.CENTER);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		addWindowListener(this);
		setVisible(true);
	}

	private void initLoginPanel() {

		loginPanel = new JPanel();

		JLabel titleLabel = new JLabel("Marketplace");
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));

		JPanel enclosingPanel = new JPanel();
		enclosingPanel.setLayout(new BoxLayout(enclosingPanel, BoxLayout.PAGE_AXIS));

		JLabel usernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		JTextField usernameArea = new JTextField(20);
		JPasswordField passwordArea = new JPasswordField(20);
		JButton loginButton = new JButton("Login");
		JButton registerButton = new JButton("Register");

		class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Login")) {
					store.login(usernameArea.getText(), passwordArea.getPassword());
				} else if (e.getActionCommand().equals("Register")) {
					initRegisterFrame();
				}
			}
		}

		ButtonListener listener = new ButtonListener();
		loginButton.addActionListener(listener);
		registerButton.addActionListener(listener);

		JPanel usernamePanel = new JPanel();
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameArea);
		JPanel passwordPanel = new JPanel();
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordArea);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(loginButton);
		buttonPanel.add(registerButton);

		enclosingPanel.add(usernamePanel);
		enclosingPanel.add(passwordPanel);
		enclosingPanel.add(buttonPanel);

		loginPanel.add(enclosingPanel);
	}

	private void initRegisterFrame() {
		JFrame registerFrame = new JFrame();
		registerFrame.setSize(WIDTH / 2, HEIGHT / 2);
		registerFrame.setLayout(new BorderLayout());
		registerFrame.setTitle("Register");
		// setIconImage(icon.getImage());

		JPanel registerPanel = new JPanel();

		JPanel enclosingPanel = new JPanel();
		enclosingPanel.setLayout(new BoxLayout(enclosingPanel, BoxLayout.PAGE_AXIS));

		JLabel usernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		JTextField usernameArea = new JTextField(20);
		JTextField passwordArea = new JTextField(20);
		JButton cancelButton = new JButton("Cancel");
		JButton registerButton = new JButton("Register");

		JRadioButton buyerButton = new JRadioButton("Buyer");
		buyerButton.setActionCommand("Buyer");
		JRadioButton sellerButton = new JRadioButton("Seller");
		sellerButton.setActionCommand("Seller");
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(buyerButton);
		buttonGroup.add(sellerButton);
		buttonGroup.setSelected(buyerButton.getModel(), true);

		JPanel usernamePanel = new JPanel();
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameArea);
		JPanel passwordPanel = new JPanel();
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordArea);
		JPanel radioPanel = new JPanel();
		radioPanel.add(buyerButton);
		radioPanel.add(sellerButton);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(cancelButton);
		buttonPanel.add(registerButton);

		enclosingPanel.add(usernamePanel);
		enclosingPanel.add(passwordPanel);
		enclosingPanel.add(radioPanel);
		enclosingPanel.add(buttonPanel);

		registerPanel.add(enclosingPanel);

		class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Cancel")) {
					registerFrame.dispose();
				} else if (e.getActionCommand().equals("Register")) {
					if (usernameArea.getText() != null && !usernameArea.getText().equals("")) {
						if (!store.getDir().inDir(usernameArea.getText())) {
							if (passwordArea.getText() != null && !passwordArea.getText().equals("")) {
								store.addAccount(usernameArea.getText(), passwordArea.getText().toCharArray(),
										buttonGroup.getSelection().getActionCommand());
							} else {
								passwordArea.setText("Invalid Password!!");
							}
						} else {
							usernameArea.setText("Username already taken!!");
						}
					} else {
						usernameArea.setText("Invalid Username!!");
					}
					System.out.println(usernameArea.getText());
					System.out.println(passwordArea.getText());
					System.out.println(buttonGroup.getSelection().getActionCommand());

				}
			}
		}

		ButtonListener listener = new ButtonListener();
		cancelButton.addActionListener(listener);
		registerButton.addActionListener(listener);

		registerFrame.add(registerPanel);
		registerFrame.setResizable(false);
		registerFrame.pack();
		registerFrame.setLocationRelativeTo(this);
		registerFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new GUI();
	}
}
