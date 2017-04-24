package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;
	private Marketplace store;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	private static final String inventoryDir = "res//products//";
	private static final String directoryDir = "res//accounts//";
	private static final String constantsDir = "res//constants.txt";

	private JPanel loginPanel;
	private JPanel menuPanel;

	public GUI() {
		super();

		this.store = new Marketplace();
		this.store.loadResources(constantsDir, inventoryDir, directoryDir);
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
		loginPanel.setBackground(new Color(0, 200, 255));

		JLabel titleLabel = new JLabel("Marketplace");
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));

		JPanel enclosingPanel = new JPanel();
		enclosingPanel.setLayout(new BoxLayout(enclosingPanel, BoxLayout.PAGE_AXIS));
		enclosingPanel.setBackground(new Color(0, 200, 255));

		JLabel infoLabel = new JLabel("Enter your login information");
		JLabel usernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		JTextField usernameArea = new JTextField(20);
		JPasswordField passwordArea = new JPasswordField(20);
		JButton loginButton = new JButton("Login");
		JButton registerButton = new JButton("Register");

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Login")) {
					if (store.tryLogin(usernameArea.getText(), passwordArea.getPassword())) {
						initMenuPanel();
						getContentPane().remove(loginPanel);
						getContentPane().add(menuPanel);
						revalidate();
					} else {
						infoLabel.setText("Invalid username/password");
					}
				} else if (e.getActionCommand().equals("Register")) {
					initRegisterFrame();
				}
			}
		};
		loginButton.addActionListener(listener);
		registerButton.addActionListener(listener);

		JPanel usernamePanel = new JPanel();
		usernamePanel.setBackground(new Color(0, 200, 255));
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameArea);
		JPanel passwordPanel = new JPanel();
		passwordPanel.setBackground(new Color(0, 200, 255));
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordArea);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(0, 200, 255));
		buttonPanel.add(loginButton);
		buttonPanel.add(registerButton);

		enclosingPanel.add(infoLabel);
		enclosingPanel.add(usernamePanel);
		enclosingPanel.add(passwordPanel);
		enclosingPanel.add(buttonPanel);

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("LoginStuff");
		menu.add(new JMenuItem("Function 1"));
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
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

		JLabel infoLabel = new JLabel("Enter your credentials");
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

		enclosingPanel.add(infoLabel);
		enclosingPanel.add(usernamePanel);
		enclosingPanel.add(passwordPanel);
		enclosingPanel.add(radioPanel);
		enclosingPanel.add(buttonPanel);

		registerPanel.add(enclosingPanel);

		ActionListener listener = new ActionListener() {
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
								registerFrame.dispose();
							} else {
								infoLabel.setText("Invalid Password!!");
							}
						} else {
							infoLabel.setText("Username already taken!!");
						}
					} else {
						infoLabel.setText("Invalid Username!!");
					}
				}
			}
		};
		cancelButton.addActionListener(listener);
		registerButton.addActionListener(listener);

		registerFrame.add(registerPanel);
		registerFrame.setResizable(false);
		registerFrame.pack();
		registerFrame.setLocationRelativeTo(this);
		registerFrame.setVisible(true);
	}

	// Yay cleanup!!
	private void initMenuPanel() {

		menuPanel = new JPanel();

		JPanel productPanel = initProductPanel();

		JPanel cartPanel = initCartPanel();

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu Stuff");
		menu.add(new JMenuItem("Function 1"));
		menuBar.add(menu);

		this.setJMenuBar(menuBar);
		menuPanel.add(productPanel);
		menuPanel.add(cartPanel);
	}

	private JPanel initProductPanel() {
		JPanel productPanel = new JPanel();

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		JLabel productLabel = new JLabel("Products");
		JPanel productInfoPanel = new JPanel();
		productInfoPanel.setLayout(new BoxLayout(productInfoPanel, BoxLayout.PAGE_AXIS));

		String[] catagories = store.getInventory().getCategories();
		JCheckBox[] catBoxes = new JCheckBox[catagories.length];
		JPanel itemEnclosingPanel = new JPanel();
		JList<String> itemList = new JList<String>(this.store.getInventory().getProductNames());
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemList.setLayoutOrientation(JList.VERTICAL);
		itemList.setVisibleRowCount(-1);
		itemList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				@SuppressWarnings("unchecked")
				JList<String> list = (JList<String>) e.getSource();
				String productName = list.getSelectedValue();

				if (productName == null) {
					productInfoPanel.setVisible(false);
					menuPanel.revalidate();
				} else {
					if (e.getValueIsAdjusting()) {
						productInfoPanel.removeAll();
						productInfoPanel.setVisible(true);

						Product prod = store.getInventory().get(productName);
						JLabel nameLabel = new JLabel(productName);
						JLabel descriptionLabel = new JLabel(prod.getDescription());
						JLabel catagoryLabel = new JLabel(prod.getCategory());
						JLabel idLabel = new JLabel("" + prod.getItemID());
						JLabel sellerIDLabel = new JLabel("" + prod.getSellerID());
						JLabel quantityLabel = new JLabel("" + prod.getQuantity());
						JLabel priceLabel = new JLabel("" + prod.getPrice());

						productInfoPanel.add(nameLabel);
						productInfoPanel.add(descriptionLabel);
						productInfoPanel.add(catagoryLabel);
						productInfoPanel.add(idLabel);
						productInfoPanel.add(sellerIDLabel);
						productInfoPanel.add(quantityLabel);
						productInfoPanel.add(priceLabel);
						menuPanel.revalidate();
					}
				}
			}
		});
		JScrollPane productScroller = new JScrollPane(itemList);
		productScroller.setPreferredSize(new Dimension(150, 250));

		for (int i = 0; i < catBoxes.length; i++) {
			catBoxes[i] = new JCheckBox(catagories[i]);
			catBoxes[i].setActionCommand(catagories[i]);
			catBoxes[i].setSelected(true);
			buttonPanel.add(catBoxes[i]);
			catBoxes[i].addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					ArrayList<String> catagories = new ArrayList<String>();
					for (JCheckBox box : catBoxes) {
						if (box.isSelected()) {
							catagories.add(box.getText());
						}
					}
					String[] catNames = new String[catagories.size()];
					catNames = catagories.toArray(catNames);
					itemList.setListData(store.getInventory().getProductNames(catNames));
				}
			});
		}

		JPanel itemPanel = new JPanel();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.PAGE_AXIS));
		itemEnclosingPanel.add(buttonPanel);
		itemEnclosingPanel.add(productScroller);

		itemPanel.add(productLabel);
		itemPanel.add(itemEnclosingPanel);
		itemPanel.add(productInfoPanel);

		productPanel.add(itemPanel);

		return productPanel;
	}

	private JPanel initCartPanel() {
		JPanel cartPanel = new JPanel();
		cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.PAGE_AXIS));

		JLabel cartLabel = new JLabel("Cart");

		JPanel cartPanels = new JPanel();
		cartPanels.setBackground(Color.RED);
		cartPanels.setLayout(new BoxLayout(cartPanels, BoxLayout.PAGE_AXIS));
		for (String label : store.getCurrentAccount().getCartLabels()) {
			String[] labelParts = label.split(" ");
			JPanel itemPanel = initCartItemPanel(Integer.parseInt(labelParts[0]), Integer.parseInt(labelParts[1]));
			cartPanels.add(itemPanel);
		}

		// removed here

		JPanel cartFunctionPanel = new JPanel();

		cartPanel.add(cartLabel);
		cartPanel.add(cartPanels);
		cartPanel.add(cartFunctionPanel);

		return cartPanel;
	}

	private JPanel initCartItemPanel(int productID, int quantity) {

		JPanel cartItemPanel = new JPanel();

		JLabel cartItemLabel = new JLabel(store.getInventory().get(productID).getName());
		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				store.getCurrentAccount().removeFromCart(productID);
				cartItemPanel.removeAll();
				cartItemPanel.setVisible(false);
			}
		});

		JSpinner quantitySpinner = new JSpinner(
				new SpinnerNumberModel(quantity, 0, store.getInventory().get(productID).getQuantity(), 1));
		quantitySpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int newQuantity = (int) quantitySpinner.getModel().getValue();
				store.getCurrentAccount().updateCartWith(productID, newQuantity);
			}
		});

		cartItemPanel.add(cartItemLabel);
		cartItemPanel.add(quantitySpinner);
		cartItemPanel.add(removeButton);

		return cartItemPanel;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.store.saveResources(constantsDir, inventoryDir, directoryDir);
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	public static void main(String[] args) {
		new GUI();
	}
}
