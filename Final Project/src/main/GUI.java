package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author MattOndash
 * @author NatePellant
 *
 *         <p>
 *         GUI is the starting point for launching the application and it
 *         handles all of the visual output/some of the logic of the program
 */
public class GUI extends JFrame implements WindowListener {

	// member variables
	private static final long serialVersionUID = 1L;
	private Marketplace store;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 625;
	private static final String inventoryDir = "res//products//";
	private static final String directoryDir = "res//accounts//";
	private static final String constantsDir = "res//constants.txt";
	private static final ImageIcon icon = new ImageIcon("res//Marketplace Icon.png");
	private static final ImageIcon logo = new ImageIcon("res//Marketplace Logo.png");

	private JPanel loginPanel;

	private JPanel menuPanel;
	private CategoryPanel categoryPanel;
	private InventoryPanel inventoryPanel;
	private CartPanel cartPanel;

	/**
	 * GUI Constructor: initializes the Marketplace and displays it on the frame
	 */
	public GUI() {
		super();

		this.store = new Marketplace();
		this.store.loadResources(constantsDir, inventoryDir, directoryDir);
		super.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		super.setSize(WIDTH, HEIGHT);
		super.setLayout(new BorderLayout());
		super.setTitle("Generic Marketplace");
		super.setIconImage(icon.getImage());
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initLoginPanel();
		add(loginPanel, BorderLayout.CENTER);
		super.setResizable(false);
		super.pack();
		super.setLocationRelativeTo(null);
		addWindowListener(this);
		setVisible(true);
	}

	/**
	 * Initializes the JPanel on which to display login information
	 */
	private void initLoginPanel() {

		loginPanel = new JPanel(new BorderLayout());

		JLabel titleLabel = new JLabel("Marketplace");
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));

		JLabel logoLabel = new JLabel(logo);

		JPanel enclosingPanel = new JPanel();
		enclosingPanel.setLayout(new BoxLayout(enclosingPanel, BoxLayout.PAGE_AXIS));
		enclosingPanel.setBorder(BorderFactory.createTitledBorder("Login"));

		JLabel infoLabel = new JLabel("Enter your Login Information");
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
						infoLabel.setText("Invalid Username / Password");
					}
				} else if (e.getActionCommand().equals("Register")) {
					showRegisterDialog();
				}
			}
		};
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

		enclosingPanel.add(infoLabel);
		infoLabel.setAlignmentX(CENTER_ALIGNMENT);
		enclosingPanel.add(usernamePanel);
		enclosingPanel.add(passwordPanel);
		enclosingPanel.add(buttonPanel);

		this.loginPanel.add(enclosingPanel, BorderLayout.PAGE_START);
		this.loginPanel.add(logoLabel, BorderLayout.CENTER);
	}

	/**
	 * Initializes and displays the frame necessary to register a new Account
	 */
	private void showRegisterDialog() {

		JDialog dialog = new JDialog(this, "Register", true);

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
					dialog.dispose();
				} else if (e.getActionCommand().equals("Register")) {
					if (usernameArea.getText() != null && !usernameArea.getText().equals("")) {
						if (!store.getDir().inDir(usernameArea.getText())) {
							if (passwordArea.getText() != null && !passwordArea.getText().equals("")) {
								store.addAccount(usernameArea.getText(), passwordArea.getText().toCharArray(),
										buttonGroup.getSelection().getActionCommand());
								dialog.dispose();
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

		dialog.add(registerPanel);
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	/**
	 * Initializes the main panel of the application and initializes its
	 * subcomponents
	 */
	private void initMenuPanel() {

		this.menuPanel = new JPanel();
		this.menuPanel.setLayout(new BoxLayout(this.menuPanel, BoxLayout.LINE_AXIS));

		categoryPanel = new CategoryPanel();
		inventoryPanel = new InventoryPanel();
		cartPanel = new CartPanel();

		JMenuBar menuBar = initMenuBar();

		this.setJMenuBar(menuBar);
		this.menuPanel.add(this.categoryPanel);
		this.menuPanel.add(this.inventoryPanel);
		this.menuPanel.add(this.cartPanel);
	}

	/**
	 * Creates a new menubar with specified functions
	 * 
	 * @return the menubar
	 */
	private JMenuBar initMenuBar() {

		JMenuBar menuBar = new JMenuBar();

		JMenu settingsMenu = new JMenu("Settings");

		ActionListener settingsMenuListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Logout")) {
					store.logout();
					menuPanel.removeAll();
					remove(menuPanel);
					initLoginPanel();
					add(loginPanel, BorderLayout.CENTER);
					revalidate();
				} else if (e.getActionCommand().equals("Edit Name")) {
					showChangeNameDialog();
				} else if (e.getActionCommand().equals("Edit Password")) {
					showChangePasswordDialog();
				} else if (e.getActionCommand().equals("Display Funds")) {
					JOptionPane.showConfirmDialog(null, String.format("$%.2f", store.getCurrentAccount().getMoney()), "Funds", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
				} else if (e.getActionCommand().equals("Add Funds")) {
					showAddFundsDialog();
				} else if (e.getActionCommand().equals("Withdraw Funds")) {
					showRemoveFundsDialog();
				} else if (e.getActionCommand().equals("Add Product")) {
					showAddProductDialog();
				} else if (e.getActionCommand().equals("Edit Product")) {
					showEditProductDialog(store.getCurrentAccount().getType());
				} else if (e.getActionCommand().equals("Add Admin Account")) {
					showAddAdminDialog();
				} else if (e.getActionCommand().equals("Edit Revenue Percentage")) {
					showEditRevenuePercentageDialog();
				}
			}
		};

		JMenuItem logoutItem = new JMenuItem("Logout");
		logoutItem.addActionListener(settingsMenuListener);

		JMenu accountInfoMenu = new JMenu("Account Info");

		JMenuItem editNameItem = new JMenuItem("Edit Name");
		editNameItem.addActionListener(settingsMenuListener);
		JMenuItem editPasswordItem = new JMenuItem("Edit Password");
		editPasswordItem.addActionListener(settingsMenuListener);

		accountInfoMenu.add(editNameItem);
		accountInfoMenu.add(editPasswordItem);

		JMenu fundsMenu = new JMenu("Manage Funds");

		JMenuItem displayFundsItem = new JMenuItem("Display Funds");
		displayFundsItem.addActionListener(settingsMenuListener);
		JMenuItem addFundsItem = new JMenuItem("Add Funds");
		addFundsItem.addActionListener(settingsMenuListener);
		JMenuItem withdrawFundsItem = new JMenuItem("Withdraw Funds");
		withdrawFundsItem.addActionListener(settingsMenuListener);

		fundsMenu.add(displayFundsItem);
		fundsMenu.add(addFundsItem);
		fundsMenu.add(withdrawFundsItem);

		JMenu sellerFunctionsMenu = new JMenu("Seller Functions");

		JMenuItem addProductItem = new JMenuItem("Add Product");
		addProductItem.addActionListener(settingsMenuListener);
		JMenuItem editProductItem = new JMenuItem("Edit Product");
		editProductItem.addActionListener(settingsMenuListener);

		if (!store.getCurrentAccount().getType().equals("Seller")) {
			sellerFunctionsMenu.setEnabled(false);
		}

		sellerFunctionsMenu.add(addProductItem);
		sellerFunctionsMenu.add(editProductItem);

		JMenu adminFunctionsMenu = new JMenu("Admin Functions");

		JMenuItem addAdminAccountItem = new JMenuItem("Add Admin Account");
		addAdminAccountItem.addActionListener(settingsMenuListener);
		JMenuItem editAllProductsItem = new JMenuItem("Edit Product");
		editAllProductsItem.addActionListener(settingsMenuListener);
		JMenuItem editRevenuePercentageItem = new JMenuItem("Edit Revenue Percentage");
		editRevenuePercentageItem.addActionListener(settingsMenuListener);

		if (!store.getCurrentAccount().getType().equals("Admin")) {
			adminFunctionsMenu.setEnabled(false);
		}

		adminFunctionsMenu.add(addAdminAccountItem);
		adminFunctionsMenu.add(editAllProductsItem);
		adminFunctionsMenu.add(editRevenuePercentageItem);

		settingsMenu.add(logoutItem);
		settingsMenu.add(accountInfoMenu);
		settingsMenu.add(fundsMenu);
		settingsMenu.add(sellerFunctionsMenu);
		settingsMenu.add(adminFunctionsMenu);

		menuBar.add(settingsMenu);

		return menuBar;
	}

	/**
	 * 
	 * @author MattOndash
	 * 
	 *         <p>
	 *         CategoryPanel extends JPanel and allows greater flexibility for
	 *         displaying categories on menu panel than a standard JPanel
	 */
	class CategoryPanel extends JPanel {

		// serial ID
		private static final long serialVersionUID = 1L;

		// member variables
		private JScrollPane categoryScroller;
		private JPanel checkBoxPanel;
		private JCheckBox[] checkBoxes;
		private String[] catNames;

		/**
		 * CategoryPanel Constructor: initializes all subcomponents of the panel
		 */
		public CategoryPanel() {
			super(new BorderLayout());

			checkBoxPanel = new JPanel();
			checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.PAGE_AXIS));

			categoryScroller = new JScrollPane(checkBoxPanel);
			categoryScroller.setBorder(BorderFactory.createTitledBorder("Categories"));

			catNames = store.getInventory().getCategories();
			checkBoxes = new JCheckBox[catNames.length];

			ItemListener checkBoxListener = new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					ArrayList<String> selectedCategories = new ArrayList<String>();
					for (JCheckBox box : checkBoxes) {
						if (box.isSelected()) {
							selectedCategories.add(box.getText());
						}
					}
					catNames = new String[selectedCategories.size()];
					catNames = selectedCategories.toArray(catNames);
					updateData();
					inventoryPanel.updateData();
				}
			};
			for (int i = 0; i < catNames.length; i++) {
				checkBoxes[i] = new JCheckBox(catNames[i]);
				checkBoxes[i].setActionCommand(catNames[i]);
				checkBoxes[i].setSelected(true);
				checkBoxPanel.add(checkBoxes[i]);
				checkBoxes[i].addItemListener(checkBoxListener);
				updateData();
			}
			super.add(categoryScroller);
			super.revalidate();
		}

		/**
		 * reloads and redraws the panel
		 */
		public void updateData() {
			checkBoxPanel.revalidate();
			checkBoxPanel.repaint();
		}

		/**
		 * @return an Array of each category that is activated
		 */
		public String[] getCategories() {
			return this.catNames;
		}
	}

	/**
	 * 
	 * @author MattOndash
	 * @author NatePellant
	 * 
	 *         <p>
	 *         InventoryPanel extends JPanel and allows greater flexibility for
	 *         displaying categories on menu panel than a standard JPanel
	 */
	class InventoryPanel extends JPanel {

		// serial ID
		private static final long serialVersionUID = 1L;

		// member variables
		private JPanel productsPanel;
		private JScrollPane inventoryScroller;
		private int[] productIDs;

		/**
		 * InventoryPanel Constructor: initializes all subcomponents of
		 * Inventory panel
		 */
		public InventoryPanel() {
			super(new BorderLayout());

			productsPanel = new JPanel();
			productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.PAGE_AXIS));

			inventoryScroller = new JScrollPane(productsPanel);
			inventoryScroller.setBorder(BorderFactory.createTitledBorder("Inventory"));

			updateData();

			this.add(inventoryScroller);
		}

		/**
		 * Initializes and adds a JPanel representation of a Product to the
		 * Product scroller
		 * 
		 * @param productID
		 *            - the ID of the Product to add
		 */
		private void addProductPanel(int productID) {

			JPanel productPanel = new JPanel();

			Product p = store.getInventory().get(productID);

			JLabel identifierLabel = new JLabel(p.getIdentifier());

			ActionListener listener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("More Info")) {
						initProductInfoFrame(productID);
					} else if (e.getActionCommand().equals("Add to Cart")) {
						store.addToCart(p.getItemID());
						inventoryPanel.updateData();
						cartPanel.updateData();
					}
				}
			};

			JButton moreInfoButton = new JButton("More Info");
			moreInfoButton.addActionListener(listener);
			JButton addToCartButton = new JButton("Add to Cart");
			addToCartButton.addActionListener(listener);
			if (p.getQuantity() == 0) {
				addToCartButton.setEnabled(false);
			}

			productPanel.add(identifierLabel);
			productPanel.add(moreInfoButton);
			productPanel.add(addToCartButton);

			productPanel.setMaximumSize(productPanel.getPreferredSize());
			productPanel.setAlignmentX(RIGHT_ALIGNMENT);
			productsPanel.add(productPanel, Component.RIGHT_ALIGNMENT);
		}

		/**
		 * reloads and repaints the panel
		 */
		public void updateData() {
			productIDs = store.getInventory().getProductIDs(categoryPanel.getCategories());

			productsPanel.removeAll();
			for (int prodID : productIDs) {
				addProductPanel(prodID);
			}

			productsPanel.revalidate();
			productsPanel.repaint();
		}
	}

	/**
	 * 
	 * @author MattOndash
	 * @author NatePellant
	 *
	 *         <p>
	 *         CartPanel extends JPanel and allows greater flexibility for
	 *         displaying categories on menu panel than a standard JPanel
	 */
	class CartPanel extends JPanel {

		// serial ID
		private static final long serialVersionUID = 1L;

		// member variables
		private JPanel itemPanels;
		private JPanel infoPanel;
		private JScrollPane cartScroller;
		private JLabel cartTotalLabel;
		private JButton checkoutButton;
		private double cartTotal;

		/**
		 * CartPanel Constructor: initializes all subcomponents
		 */
		public CartPanel() {
			super(new BorderLayout());

			itemPanels = new JPanel();
			itemPanels.setLayout(new BoxLayout(itemPanels, BoxLayout.PAGE_AXIS));

			cartScroller = new JScrollPane(itemPanels);
			cartScroller.setBorder(BorderFactory.createTitledBorder("Cart"));

			checkoutButton = new JButton("Checkout");
			checkoutButton.setAlignmentX(CENTER_ALIGNMENT);
			checkoutButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Checkout");
					store.checkOut();
					updateData();
					menuPanel.revalidate();
				}
			});

			cartTotal = store.getCartTotal();
			cartTotalLabel = new JLabel("$" + String.format("%.2f", cartTotal));

			infoPanel = new JPanel();
			infoPanel.add(cartTotalLabel);
			infoPanel.add(checkoutButton);

			updateData();

			this.add(cartScroller, BorderLayout.CENTER);
			this.add(infoPanel, BorderLayout.PAGE_END);
			this.revalidate();
		}

		/**
		 * Initializes and adds item panel to the scroller
		 * 
		 * @param productID
		 *            - the ID of the Product
		 * @param quantity
		 *            - the quantity of the Product
		 */
		private void addCartItemPanel(int productID, int quantity) {

			JPanel cartItemPanel = new JPanel();

			Product p = store.getInventory().get(productID);

			JLabel cartItemLabel = new JLabel(p.getIdentifier());
			JButton removeButton = new JButton("Remove");
			removeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					store.getCurrentAccount().removeFromCart(productID);
					updateData();
					cartPanel.cartScroller.revalidate();
				}
			});

			JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(quantity, 1, p.getQuantity(), 1));
			quantitySpinner.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					int newQuantity = (int) quantitySpinner.getModel().getValue();
					store.getCurrentAccount().updateCartWith(productID, newQuantity);
					updateData();
				}
			});

			cartItemPanel.add(cartItemLabel);
			cartItemPanel.add(quantitySpinner);
			cartItemPanel.add(removeButton);

			cartItemPanel.setMaximumSize(cartItemPanel.getPreferredSize());
			cartItemPanel.setAlignmentX(RIGHT_ALIGNMENT);
			itemPanels.add(cartItemPanel, Component.RIGHT_ALIGNMENT);
		}

		/**
		 * reloads and repaints the panel
		 */
		public void updateData() {
			itemPanels.removeAll();

			for (String label : store.getCurrentAccount().getCartLabels()) {
				if (label != null && !label.equals("")) {
					String[] labelParts = label.split(" ");
					int productID = Integer.parseInt(labelParts[0]);
					int quantity = Integer.parseInt(labelParts[1]);
					addCartItemPanel(productID, quantity);
				}
			}

			cartTotal = store.getCartTotal();
			cartTotalLabel.setText("$" + String.format("%.2f", cartTotal));

			checkoutButton.setEnabled(true);
			if (cartTotal == 0 || cartTotal > store.getCurrentAccount().getMoney()) {
				checkoutButton.setEnabled(false);
			}

			itemPanels.revalidate();
			itemPanels.repaint();
			infoPanel.revalidate();
			infoPanel.repaint();
		}
	}

	/**
	 * Displays the dialog which allows a user to change their name
	 */
	private void showChangeNameDialog() {

		JDialog dialog = new JDialog(this, "Edit Name", true);

		JLabel infoLabel = new JLabel(" ");
		JLabel nameLabel = new JLabel("Enter new name");
		JTextField nameField = new JTextField(20);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("OK")) {
					String name = nameField.getText();
					if (name == null || name.equals("") || store.getDir().inDir(name)) {
						infoLabel.setText("Invalid name / Name already taken");
					} else {
						store.getCurrentAccount().setName(name);
						dialog.dispose();
						menuPanel.revalidate();
						menuPanel.repaint();
					}
				} else if (e.getActionCommand().equals("Cancel")) {
					dialog.dispose();
				}
			}
		};

		JButton acceptButton = new JButton("OK");
		acceptButton.addActionListener(listener);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(listener);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(acceptButton);
		buttonPanel.add(cancelButton);

		JPanel namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(nameField);

		JPanel enclosingPanel = new JPanel();
		enclosingPanel.setLayout(new BoxLayout(enclosingPanel, BoxLayout.PAGE_AXIS));
		enclosingPanel.add(infoLabel);
		enclosingPanel.add(namePanel);
		enclosingPanel.add(buttonPanel);
		enclosingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		dialog.add(enclosingPanel);
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	/**
	 * Displays the dialog which allows a user to change their password
	 */
	private void showChangePasswordDialog() {

		JDialog dialog = new JDialog(this, "Edit Password", true);

		JLabel infoLabel = new JLabel(" ");
		JLabel passordLabel = new JLabel("Enter new password");
		JTextField passwordField = new JTextField(20);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("OK")) {
					String password = passwordField.getText();
					if (password == null || password.equals("")) {
						infoLabel.setText("Invalid password!");
					} else {
						store.getCurrentAccount().setPassword(password);
						dialog.dispose();
					}
				} else if (e.getActionCommand().equals("Cancel")) {
					dialog.dispose();
				}
			}
		};

		JButton acceptButton = new JButton("OK");
		acceptButton.addActionListener(listener);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(listener);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(acceptButton);
		buttonPanel.add(cancelButton);

		JPanel passwordPanel = new JPanel();
		passwordPanel.add(passordLabel);
		passwordPanel.add(passwordField);

		JPanel enclosingPanel = new JPanel();
		enclosingPanel.setLayout(new BoxLayout(enclosingPanel, BoxLayout.PAGE_AXIS));
		enclosingPanel.add(infoLabel);
		enclosingPanel.add(passwordPanel);
		enclosingPanel.add(buttonPanel);
		enclosingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		dialog.add(enclosingPanel);

		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	/**
	 * Displays the dialog which allows a user to add funds
	 */
	private void showAddFundsDialog() {

		JDialog dialog = new JDialog(this, "Add Funds", true);

		JPanel addFundsPanel = new JPanel();
		addFundsPanel.setLayout(new BoxLayout(addFundsPanel, BoxLayout.PAGE_AXIS));

		JLabel infoLabel = new JLabel("Select number of funds to add");

		JSpinner fundsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("OK")) {
					store.getCurrentAccount().addMoney((int) fundsSpinner.getValue());
					dialog.dispose();
				} else if (e.getActionCommand().equals("Cancel")) {
					dialog.dispose();
				}
			}
		};

		JButton acceptButton = new JButton("OK");
		acceptButton.addActionListener(listener);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(listener);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(acceptButton);
		buttonPanel.add(cancelButton);

		addFundsPanel.add(infoLabel);
		addFundsPanel.add(fundsSpinner);
		addFundsPanel.add(buttonPanel);

		dialog.add(addFundsPanel);
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	/**
	 * Displays the dialog which allows a user to remove funds
	 */
	private void showRemoveFundsDialog() {

		JDialog dialog = new JDialog(this, "Remove Funds", true);

		JPanel removeFundsPanel = new JPanel();
		removeFundsPanel.setLayout(new BoxLayout(removeFundsPanel, BoxLayout.PAGE_AXIS));

		JLabel infoLabel = new JLabel("Select number of funds to withdraw");

		JSpinner fundsSpinner = new JSpinner(
				new SpinnerNumberModel(0.0, 0.0, store.getCurrentAccount().getMoney(), 1));

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("OK")) {
					store.getCurrentAccount().removeMoney((double) fundsSpinner.getValue());
					dialog.dispose();
				} else if (e.getActionCommand().equals("Cancel")) {
					dialog.dispose();
				}
			}
		};

		JButton acceptButton = new JButton("OK");
		acceptButton.addActionListener(listener);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(listener);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(acceptButton);
		buttonPanel.add(cancelButton);

		removeFundsPanel.add(infoLabel);
		removeFundsPanel.add(fundsSpinner);
		removeFundsPanel.add(buttonPanel);

		dialog.add(removeFundsPanel);
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	/**
	 * Shows the frame necessary to add a new Product to the Marketplace
	 */
	private void showAddProductDialog() {

		JDialog dialog = new JDialog(this, "Add Product", true);

		JPanel addProductPanel = new JPanel();
		addProductPanel.setLayout(new BoxLayout(addProductPanel, BoxLayout.PAGE_AXIS));

		JLabel infoLabel = new JLabel("Enter Product Information");

		JPanel namePanel = new JPanel();

		JLabel nameLabel = new JLabel("Name");
		JTextField nameField = new JTextField(20);

		namePanel.add(nameLabel);
		namePanel.add(nameField);

		JPanel descriptionPanel = new JPanel();

		JLabel descriptionLabel = new JLabel("Description");
		JTextArea descriptionArea = new JTextArea();
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		JScrollPane textScroller = new JScrollPane(descriptionArea);
		textScroller.setPreferredSize(new Dimension(200, 80));

		descriptionPanel.add(descriptionLabel);
		descriptionPanel.add(textScroller);

		JPanel categoryDispPanel = new JPanel();

		JLabel categoryLabel = new JLabel("Category");
		JTextField categoryField = new JTextField(20);

		categoryDispPanel.add(categoryLabel);
		categoryDispPanel.add(categoryField);

		JPanel pricePanel = new JPanel();

		JLabel priceLabel = new JLabel("Price");
		JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 10000.0, 1.0));

		pricePanel.add(priceLabel);
		pricePanel.add(priceSpinner);

		JPanel quantityPanel = new JPanel();

		JLabel quantityLabel = new JLabel("Quantity");
		JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));

		quantityPanel.add(quantityLabel);
		quantityPanel.add(quantitySpinner);

		JPanel buttonPanel = new JPanel();

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Add")) {
					String name = nameField.getText();
					if (name != null && !name.equals("")) {
						String description = descriptionArea.getText();
						if (description != null && !description.equals("")) {
							String category = categoryField.getText();
							if (category != null && !category.equals("")) {
								double price = (double) priceSpinner.getValue();
								if (price > 0.0) {
									int quantity = (int) quantitySpinner.getValue();

									store.addProduct(store.getCurrentAccount().getID(), name, description, category,
											price, quantity);
									dialog.dispose();
									categoryPanel.updateData();
									inventoryPanel.updateData();
									menuPanel.revalidate();
									menuPanel.repaint();
								} else {
									infoLabel.setText("Price cannot be 0");
								}
							} else {
								infoLabel.setText("Invalid category");
							}
						} else {
							infoLabel.setText("Invalid description");
						}
					} else {
						infoLabel.setText("Invalid name");
					}
				} else if (e.getActionCommand().equals("Cancel")) {
					dialog.dispose();
				}
			}
		};

		JButton addButton = new JButton("Add");
		addButton.addActionListener(listener);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(listener);

		buttonPanel.add(addButton);
		buttonPanel.add(cancelButton);
		addProductPanel.add(infoLabel);
		addProductPanel.add(namePanel);
		addProductPanel.add(descriptionPanel);
		addProductPanel.add(categoryDispPanel);
		addProductPanel.add(pricePanel);
		addProductPanel.add(quantityPanel);
		addProductPanel.add(buttonPanel);

		dialog.add(addProductPanel);
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	/**
	 * Initializes the frame necessary to edit a Product in the Marketplace
	 * 
	 * @param type
	 *            - the type of the Account editing said Product
	 */
	private void showEditProductDialog(String type) {

		String[] productIdentifiers;
		String productIdentifier = "";

		if (type.equals("Seller")) {
			productIdentifiers = store.getInventory().getProductIdentifiers(store.getCurrentAccount().getID());
			productIdentifier = (String) JOptionPane.showInputDialog(menuPanel, "Select product to edit",
					"Edit Product", JOptionPane.PLAIN_MESSAGE, null, productIdentifiers, productIdentifiers[0]);
		} else if (type.equals("Admin")) {
			productIdentifiers = store.getInventory().getProductIdentifiers();
			productIdentifier = (String) JOptionPane.showInputDialog(menuPanel, "Select product to edit",
					"Edit Product", JOptionPane.PLAIN_MESSAGE, null, productIdentifiers, productIdentifiers[0]);
		}

		if (productIdentifier != null && !productIdentifier.equals("")) {

			JDialog dialog = new JDialog(this, "Edit Product", true);

			JPanel editProductPanel = new JPanel();
			editProductPanel.setLayout(new BoxLayout(editProductPanel, BoxLayout.PAGE_AXIS));

			String[] identifierParts = productIdentifier.split(" ");
			Product p = store.getInventory().get(Integer.parseInt(identifierParts[identifierParts.length - 1]));

			JLabel infoLabel = new JLabel("Edit Product Fields");

			JPanel namePanel = new JPanel();

			JLabel nameLabel = new JLabel("Name");
			JTextField nameField = new JTextField(20);
			nameField.setText(p.getName());

			namePanel.add(nameLabel);
			namePanel.add(nameField);

			JPanel descriptionPanel = new JPanel();

			JLabel descriptionLabel = new JLabel("Description");
			JTextArea descriptionArea = new JTextArea();
			descriptionArea.setLineWrap(true);
			descriptionArea.setWrapStyleWord(true);
			descriptionArea.setText(p.getDescription());
			JScrollPane textScroller = new JScrollPane(descriptionArea);
			textScroller.setPreferredSize(new Dimension(200, 80));

			descriptionPanel.add(descriptionLabel);
			descriptionPanel.add(textScroller);

			JPanel categoryPanel = new JPanel();

			JLabel categoryLabel = new JLabel("Category");
			JTextField categoryField = new JTextField(20);
			categoryField.setText(p.getCategory());

			categoryPanel.add(categoryLabel);
			categoryPanel.add(categoryField);

			JPanel pricePanel = new JPanel();

			JLabel priceLabel = new JLabel("Price");
			JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(p.getPrice(), 0.0, 10000.0, 1.0));

			pricePanel.add(priceLabel);
			pricePanel.add(priceSpinner);

			JPanel quantityPanel = new JPanel();

			JLabel quantityLabel = new JLabel("Quantity");
			JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(p.getQuantity(), 0, 10000, 1));

			quantityPanel.add(quantityLabel);
			quantityPanel.add(quantitySpinner);

			JPanel buttonPanel = new JPanel();

			ActionListener listener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("Edit")) {
						String name = nameField.getText();
						if (name != null && !name.equals("")) {
							String description = descriptionArea.getText();
							if (description != null && !description.equals("")) {
								String category = categoryField.getText();
								if (category != null && !category.equals("")) {
									double price = (double) priceSpinner.getValue();
									if (price > 0.0) {
										int quantity = (int) quantitySpinner.getValue();

										p.setName(name);
										p.setDescription(description);
										p.setCategory(category);
										p.setPrice(price);
										p.setQuantity(quantity);

										dialog.dispose();
										inventoryPanel.updateData();
									} else {
										infoLabel.setText("Price cannot be 0");
									}
								} else {
									infoLabel.setText("Invalid category");
								}
							} else {
								infoLabel.setText("Invalid description");
							}
						} else {
							infoLabel.setText("Invalid name");
						}
					} else if (e.getActionCommand().equals("Cancel")) {
						dialog.dispose();
					}
				}
			};

			JButton editButton = new JButton("Edit");
			editButton.addActionListener(listener);
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(listener);

			buttonPanel.add(editButton);
			buttonPanel.add(cancelButton);
			editProductPanel.add(infoLabel);
			editProductPanel.add(namePanel);
			editProductPanel.add(descriptionPanel);
			editProductPanel.add(categoryPanel);
			editProductPanel.add(pricePanel);
			editProductPanel.add(quantityPanel);
			editProductPanel.add(buttonPanel);

			dialog.add(editProductPanel);
			dialog.pack();
			dialog.setLocationRelativeTo(this);
			dialog.setVisible(true);

		}
	}

	/**
	 * Initializes the frame necessary for an Admin to add a new Administrative
	 * Account
	 */
	private void showAddAdminDialog() {

		JDialog dialog = new JDialog(this, "Add Admin", true);

		JPanel addAdminPanel = new JPanel();

		JPanel enclosingPanel = new JPanel();
		enclosingPanel.setLayout(new BoxLayout(enclosingPanel, BoxLayout.PAGE_AXIS));

		JLabel infoLabel = new JLabel("Enter credentials");
		JLabel usernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		JTextField usernameArea = new JTextField(20);
		JTextField passwordArea = new JTextField(20);
		JButton cancelButton = new JButton("Cancel");
		JButton registerButton = new JButton("Register");

		JPanel usernamePanel = new JPanel();
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameArea);
		JPanel passwordPanel = new JPanel();
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordArea);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(cancelButton);
		buttonPanel.add(registerButton);

		enclosingPanel.add(infoLabel);
		enclosingPanel.add(usernamePanel);
		enclosingPanel.add(passwordPanel);
		enclosingPanel.add(buttonPanel);

		addAdminPanel.add(enclosingPanel);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Cancel")) {
					dialog.dispose();
				} else if (e.getActionCommand().equals("Register")) {
					if (usernameArea.getText() != null && !usernameArea.getText().equals("")) {
						if (!store.getDir().inDir(usernameArea.getText())) {
							if (passwordArea.getText() != null && !passwordArea.getText().equals("")) {
								store.addAccount(usernameArea.getText(), passwordArea.getText().toCharArray(), "Admin");
								dialog.dispose();
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

		dialog.add(addAdminPanel);
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	/**
	 * Initializes and displays the frame necessary for an Admin to change the
	 * percentage of profits they receive from sales
	 */
	private void showEditRevenuePercentageDialog() {

		JDialog dialog = new JDialog(this, "Edit Revenue Percentage");

		JPanel editRevenuePercentagePanel = new JPanel();
		editRevenuePercentagePanel.setLayout(new BoxLayout(editRevenuePercentagePanel, BoxLayout.PAGE_AXIS));

		JLabel infoLabel = new JLabel("Enter percentage (in decimal notation)");

		JSpinner percentageSpinner = new JSpinner(
				new SpinnerNumberModel(Marketplace.getSellerRevenuePercentage(), 0, 1, .01));

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("OK")) {
					Marketplace.setSellerRevenuePercentage((double) percentageSpinner.getValue());
					dialog.dispose();
				} else if (e.getActionCommand().equals("Cancel")) {
					dialog.dispose();
				}
			}
		};

		JPanel buttonPanel = new JPanel();
		JButton acceptButton = new JButton("OK");
		acceptButton.addActionListener(listener);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(listener);

		buttonPanel.add(acceptButton);
		buttonPanel.add(cancelButton);

		editRevenuePercentagePanel.add(infoLabel);
		editRevenuePercentagePanel.add(percentageSpinner);
		editRevenuePercentagePanel.add(buttonPanel);

		dialog.add(editRevenuePercentagePanel);
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	/**
	 * Initializes and displays the Product's info on its own frame
	 * 
	 * @param productID
	 *            - the ID of the Product to display
	 */
	private void initProductInfoFrame(int productID) {

		JDialog dialog = new JDialog(this, "More Info", false);

		JPanel productInfoPanel = new JPanel();
		productInfoPanel.setLayout(new BoxLayout(productInfoPanel, BoxLayout.PAGE_AXIS));
		productInfoPanel.setBorder(BorderFactory.createTitledBorder("Product"));

		Product p = store.getInventory().get(productID);

		JLabel nameLabel = new JLabel("Name: " + p.getName());

		JLabel descriptionLabel = new JLabel("Description:");
		JTextArea descriptionArea = new JTextArea(p.getDescription());
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setText(p.getDescription());
		descriptionArea.setFont(UIManager.getFont("Label.font"));
		descriptionArea.setEditable(false);
		JScrollPane descriptionScroller = new JScrollPane(descriptionArea);
		descriptionScroller.setPreferredSize(new Dimension(200, 100));

		JPanel descriptionPanel = new JPanel();
		descriptionPanel.add(descriptionLabel);
		descriptionPanel.add(descriptionScroller);

		JLabel sellerLabel = new JLabel("SellerID: " + p.getSellerID());
		JLabel categoryLabel = new JLabel("Category: " + p.getCategory());
		JLabel priceLabel = new JLabel("Price: $" + p.getPrice());
		JLabel quantityLabel = new JLabel("Quantity: " + p.getQuantity());

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		};

		JButton acceptButton = new JButton("OK");
		acceptButton.addActionListener(listener);

		productInfoPanel.add(nameLabel);
		productInfoPanel.add(descriptionLabel);
		productInfoPanel.add(descriptionPanel);
		productInfoPanel.add(sellerLabel);
		productInfoPanel.add(categoryLabel);
		productInfoPanel.add(priceLabel);
		productInfoPanel.add(quantityLabel);
		productInfoPanel.add(acceptButton);

		dialog.add(productInfoPanel);
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	/**
	 * Saves the resources on the closing of the window
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		this.store.saveResources(constantsDir, inventoryDir, directoryDir);
	}

	/**
	 * Attempts to set the look and feel of the project on window activation
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
			// ex.printStackTrace();
		}
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

	// starting point for program
	public static void main(String[] args) {
		new GUI();
	}
}