package main;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
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
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
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
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.LINE_AXIS));

		JPanel productPanel = initProductPanel2();

		JPanel cartPanel = initCartPanel();

		JMenuBar menuBar = initMenuBar();

		this.setJMenuBar(menuBar);
		menuPanel.add(productPanel);
		menuPanel.add(cartPanel);
	}

	private void revalidateMenuPanel() {
		JPanel productPanel = initProductPanel2();

		JPanel cartPanel = initCartPanel();

		JMenuBar menuBar = initMenuBar();

		menuPanel.removeAll();
		this.setJMenuBar(menuBar);
		menuPanel.add(productPanel);
		menuPanel.add(cartPanel);
		menuPanel.revalidate();
	}

	private void initChangeNameFrame() {

		JFrame changeNameFrame = new JFrame();

		JPanel changeNamePanel = new JPanel();
		changeNamePanel.setLayout(new BoxLayout(changeNamePanel, BoxLayout.PAGE_AXIS));

		JLabel infoLabel = new JLabel("Enter new name");

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
						changeNameFrame.dispose();
						revalidateMenuPanel();
					}
				} else if (e.getActionCommand().equals("Cancel")) {
					changeNameFrame.dispose();
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

		changeNamePanel.add(infoLabel);
		changeNamePanel.add(nameField);
		changeNamePanel.add(buttonPanel);

		changeNameFrame.add(changeNamePanel);

		changeNameFrame.setResizable(false);
		changeNameFrame.pack();
		changeNameFrame.setLocationRelativeTo(this);
		changeNameFrame.setVisible(true);
	}

	private void initChangePasswordFrame() {

		JFrame changePasswordFrame = new JFrame();

		JPanel changePasswordPanel = new JPanel();
		changePasswordPanel.setLayout(new BoxLayout(changePasswordPanel, BoxLayout.PAGE_AXIS));

		JLabel infoLabel = new JLabel("Enter new password");

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
						changePasswordFrame.dispose();
					}
				} else if (e.getActionCommand().equals("Cancel")) {
					changePasswordFrame.dispose();
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

		changePasswordPanel.add(infoLabel);
		changePasswordPanel.add(passwordField);
		changePasswordPanel.add(buttonPanel);

		changePasswordFrame.add(changePasswordPanel);

		changePasswordFrame.setResizable(false);
		changePasswordFrame.pack();
		changePasswordFrame.setLocationRelativeTo(this);
		changePasswordFrame.setVisible(true);
	}

	private void initAddFundsFrame() {

		JFrame addFundsFrame = new JFrame();

		JPanel addFundsPanel = new JPanel();
		addFundsPanel.setLayout(new BoxLayout(addFundsPanel, BoxLayout.PAGE_AXIS));

		JLabel infoLabel = new JLabel("Select number of funds to add");

		JSpinner fundsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("OK")) {
					store.getCurrentAccount().addMoney((int) fundsSpinner.getValue());
					addFundsFrame.dispose();
				} else if (e.getActionCommand().equals("Cancel")) {
					addFundsFrame.dispose();
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

		addFundsFrame.add(addFundsPanel);
		addFundsFrame.setResizable(false);
		addFundsFrame.pack();
		addFundsFrame.setLocationRelativeTo(this);
		addFundsFrame.setVisible(true);
	}

	private void initRemoveFundsFrame() {

		JFrame removeFundsFrame = new JFrame();

		JPanel removeFundsPanel = new JPanel();
		removeFundsPanel.setLayout(new BoxLayout(removeFundsPanel, BoxLayout.PAGE_AXIS));

		JLabel infoLabel = new JLabel("Select number of funds to withdraw");

		JSpinner fundsSpinner = new JSpinner(
				new SpinnerNumberModel(0, 0, String.format("$%.2d", store.getCurrentAccount().getMoney()), 1));

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("OK")) {
					store.getCurrentAccount().removeMoney((double) fundsSpinner.getValue());
					removeFundsFrame.dispose();
				} else if (e.getActionCommand().equals("Cancel")) {
					removeFundsFrame.dispose();
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

		removeFundsFrame.add(removeFundsPanel);
		removeFundsFrame.setResizable(false);
		removeFundsFrame.pack();
		removeFundsFrame.setLocationRelativeTo(this);
		removeFundsFrame.setVisible(true);
	}

	private void initAddProductFrame() {

		JFrame addProductFrame = new JFrame();

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

		JPanel categoryPanel = new JPanel();

		JLabel categoryLabel = new JLabel("Catagory");
		JTextField categoryField = new JTextField(20);

		categoryPanel.add(categoryLabel);
		categoryPanel.add(categoryField);

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
					if (name != null && !name.equals("") && !(store.getInventory().get(name) == null)) {
						String description = descriptionArea.getText();
						if (description != null && !description.equals("")) {
							String category = categoryField.getText();
							if (category != null && !category.equals("")) {
								double price = (double) priceSpinner.getValue();
								if (price > 0.0) {
									int quantity = (int) quantitySpinner.getValue();

									store.addProduct(store.getCurrentAccount().getID(), name, description, category,
											price, quantity);
									addProductFrame.dispose();
									revalidateMenuPanel();
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
					addProductFrame.dispose();
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
		addProductPanel.add(categoryPanel);
		addProductPanel.add(pricePanel);
		addProductPanel.add(quantityPanel);
		addProductPanel.add(buttonPanel);

		addProductFrame.add(addProductPanel);
		addProductFrame.setResizable(true);
		addProductFrame.pack();
		addProductFrame.setLocationRelativeTo(this);
		addProductFrame.setVisible(true);

	}

	private void initEditProductFrame(String type) {

		String[] productNames;
		String productName = "";

		if (type.equals("Seller")) {
			productNames = store.getInventory().getProductNames(store.getCurrentAccount().getID());
			productName = (String) JOptionPane.showInputDialog(menuPanel, "Select product to edit", "Edit Product",
					JOptionPane.PLAIN_MESSAGE, null, productNames, productNames[0]);
		} else if (type.equals("Admin")) {
			productNames = store.getInventory().getProductNames();
			productName = (String) JOptionPane.showInputDialog(menuPanel, "Select product to edit", "Edit Product",
					JOptionPane.PLAIN_MESSAGE, null, productNames, productNames[0]);
		}

		if (productName != null && !productName.equals("")) {

			JFrame editProductFrame = new JFrame();

			JPanel editProductPanel = new JPanel();
			editProductPanel.setLayout(new BoxLayout(editProductPanel, BoxLayout.PAGE_AXIS));

			Product p = store.getInventory().get(productName);

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

			JLabel categoryLabel = new JLabel("Catagory");
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

										editProductFrame.dispose();
										revalidateMenuPanel();
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
						editProductFrame.dispose();
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

			editProductFrame.add(editProductPanel);
			editProductFrame.setResizable(true);
			editProductFrame.pack();
			editProductFrame.setLocationRelativeTo(this);
			editProductFrame.setVisible(true);

		}
	}

	private void initAddAdminFrame() {
		JFrame addAdminFrame = new JFrame();
		addAdminFrame.setLayout(new BorderLayout());
		addAdminFrame.setTitle("Register");

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
					addAdminFrame.dispose();
				} else if (e.getActionCommand().equals("Register")) {
					if (usernameArea.getText() != null && !usernameArea.getText().equals("")) {
						if (!store.getDir().inDir(usernameArea.getText())) {
							if (passwordArea.getText() != null && !passwordArea.getText().equals("")) {
								store.addAccount(usernameArea.getText(), passwordArea.getText().toCharArray(), "Admin");
								addAdminFrame.dispose();
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

		addAdminFrame.add(addAdminPanel);
		addAdminFrame.setResizable(false);
		addAdminFrame.pack();
		addAdminFrame.setLocationRelativeTo(this);
		addAdminFrame.setVisible(true);
	}

	private void initEditRevenuePercentageFrame() {

		JFrame editRevenuePercentageFrame = new JFrame();

		JPanel editRevenuePercentagePanel = new JPanel();
		editRevenuePercentagePanel.setLayout(new BoxLayout(editRevenuePercentagePanel, BoxLayout.PAGE_AXIS));

		JLabel infoLabel = new JLabel("Enter percentage (in decimal notation) of sale profits to sellers");

		JSpinner percentageSpinner = new JSpinner(
				new SpinnerNumberModel(Marketplace.getSellerRevenuePercentage(), 0, 1, .01));

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("OK")) {
					Marketplace.setSellerRevenuePercentage((double) percentageSpinner.getValue());
					editRevenuePercentageFrame.dispose();
				} else if (e.getActionCommand().equals("Cancel")) {
					editRevenuePercentageFrame.dispose();
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

		editRevenuePercentageFrame.add(editRevenuePercentagePanel);
		editRevenuePercentageFrame.setResizable(false);
		editRevenuePercentageFrame.pack();
		editRevenuePercentageFrame.setLocationRelativeTo(this);
		editRevenuePercentageFrame.setVisible(true);
	}

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
					initChangeNameFrame();
				} else if (e.getActionCommand().equals("Edit Password")) {
					initChangePasswordFrame();
				} else if (e.getActionCommand().equals("Display Funds")) {
					JOptionPane.showMessageDialog(null, String.format("$%.2f", store.getCurrentAccount().getMoney()));
				} else if (e.getActionCommand().equals("Add Funds")) {
					initAddFundsFrame();
				} else if (e.getActionCommand().equals("Withdraw Funds")) {
					initRemoveFundsFrame();
				} else if (e.getActionCommand().equals("Add Product")) {
					initAddProductFrame();
				} else if (e.getActionCommand().equals("Edit Product")) {
					initEditProductFrame(store.getCurrentAccount().getType());
				} else if (e.getActionCommand().equals("Add Admin Account")) {
					initAddAdminFrame();
				} else if (e.getActionCommand().equals("Edit Revenue Percentage")) {
					initEditRevenuePercentageFrame();
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

	private JPanel initProductPanel2() {
		JPanel productPanel = new JPanel();

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		JLabel productLabel = new JLabel("Products");
		productLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		productLabel.setAlignmentX(CENTER_ALIGNMENT);

		String[] catagories = store.getInventory().getCategories();
		JCheckBox[] catBoxes = new JCheckBox[catagories.length];

		JPanel itemEnclosingPanel = new JPanel();

		JPanel productsPanel = new JPanel();
		productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.PAGE_AXIS));
		JScrollPane productScroller = new JScrollPane(productsPanel);
		productScroller.setPreferredSize(new Dimension(350, 500));

		for (int i = 0; i < catBoxes.length; i++) {
			catBoxes[i] = new JCheckBox(catagories[i]);
			catBoxes[i].setActionCommand(catagories[i]);
			catBoxes[i].setSelected(true);
			buttonPanel.add(catBoxes[i]);
			catBoxes[i].addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					System.out.println("Item change");
					ArrayList<String> catagories = new ArrayList<String>();
					for (JCheckBox box : catBoxes) {
						if (box.isSelected()) {
							catagories.add(box.getText());
						}
					}
					String[] catNames = new String[catagories.size()];
					catNames = catagories.toArray(catNames);
					productsPanel.removeAll();
					for (String prodName : store.getInventory().getProductNames(catNames)) {
						JPanel productItemPanel = initProductItemPanel(prodName);
						productItemPanel.setMaximumSize(productItemPanel.getPreferredSize());
						productItemPanel.setAlignmentX(RIGHT_ALIGNMENT);
						productsPanel.add(productItemPanel);
						
					}
					productScroller.revalidate();
				}
			});
		}

		for (String prodName : store.getInventory().getProductNames(catagories)) {
			JPanel productItemPanel = initProductItemPanel(prodName);
			productItemPanel.setMaximumSize(productItemPanel.getPreferredSize());
			productItemPanel.setAlignmentX(RIGHT_ALIGNMENT);
			productsPanel.add(productItemPanel, Component.RIGHT_ALIGNMENT);
			
		}
		productScroller.revalidate();

		JPanel itemPanel = new JPanel();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.PAGE_AXIS));

		itemEnclosingPanel.add(buttonPanel);
		itemEnclosingPanel.add(productScroller);

		itemPanel.add(productLabel);
		itemPanel.add(itemEnclosingPanel);

		productPanel.add(itemPanel);

		return productPanel;
	}

	private void initProductInfoFrame(String productName) {

		JFrame productInfoFrame = new JFrame();

		JPanel productInfoPanel = new JPanel();
		productInfoPanel.setLayout(new BoxLayout(productInfoPanel, BoxLayout.PAGE_AXIS));

		Product p = store.getInventory().get(productName);

		JLabel nameLabel = new JLabel("Name: " + p.getName());

		JLabel descriptionLabel = new JLabel("Description:");
		JTextArea descriptionArea = new JTextArea(p.getDescription());
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setText(p.getDescription());
		JScrollPane descriptionScroller = new JScrollPane(descriptionArea);
		descriptionScroller.setPreferredSize(new Dimension(200, 80));

		JLabel catagoryLabel = new JLabel("Catagory: " + p.getCategory());

		JLabel priceLabel = new JLabel("Price: " + p.getPrice());

		JLabel quantityLabel = new JLabel("Quantity: " + p.getQuantity());

		productInfoPanel.add(nameLabel);
		productInfoPanel.add(descriptionLabel);
		productInfoPanel.add(descriptionScroller);
		productInfoPanel.add(catagoryLabel);
		productInfoPanel.add(priceLabel);
		productInfoPanel.add(quantityLabel);

		productInfoFrame.add(productInfoPanel);
		productInfoFrame.setResizable(false);
		productInfoFrame.pack();
		productInfoFrame.setLocationRelativeTo(this);
		productInfoFrame.setVisible(true);
	}

	private JPanel initProductItemPanel(String productName) {

		JPanel productItemPanel = new JPanel();

		Product p = store.getInventory().get(productName);

		JLabel nameLabel = new JLabel(p.getName());

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("More Info")) {
					initProductInfoFrame(productName);
				} else if (e.getActionCommand().equals("Add to Cart")) {
					store.addToCart(p.getItemID(), 1);
					revalidateMenuPanel();
				}
			}
		};

		JButton moreInfoButton = new JButton("More Info");
		moreInfoButton.addActionListener(listener);
		JButton addToCartButton = new JButton("Add to Cart");
		addToCartButton.addActionListener(listener);

		productItemPanel.add(nameLabel);
		productItemPanel.add(moreInfoButton);
		productItemPanel.add(addToCartButton);

		return productItemPanel;
	}

	private JPanel initCartPanel() {
		JPanel cartPanel = new JPanel();
		cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.PAGE_AXIS));

		JLabel cartLabel = new JLabel("Cart");
		cartLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		cartLabel.setAlignmentX(CENTER_ALIGNMENT);

		JPanel cartPanels = new JPanel();
		cartPanels.setMaximumSize(new Dimension(250, 250));

		cartPanels.setLayout(new BoxLayout(cartPanels, BoxLayout.PAGE_AXIS));

		JScrollPane cartScroller = new JScrollPane(cartPanels);
		cartScroller.setPreferredSize(new Dimension(250, 250));
		for (String label : store.getCurrentAccount().getCartLabels()) {
			String[] labelParts = label.split(" ");
			int productID = Integer.parseInt(labelParts[0]);
			int quantity = Integer.parseInt(labelParts[1]);
			JPanel itemPanel = initCartItemPanel(productID, quantity);
			itemPanel.setMaximumSize(itemPanel.getPreferredSize());
			itemPanel.setAlignmentX(RIGHT_ALIGNMENT);
			cartPanels.add(itemPanel);
		}

		JButton checkoutButton = new JButton("Checkout");
		checkoutButton.setAlignmentX(CENTER_ALIGNMENT);
		checkoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				store.checkOut();
				revalidateMenuPanel();
			}
		});

		double cartTotal = store.getCartTotal();
		JLabel totalLabel = new JLabel("$" + String.format("%.2f", cartTotal));
		checkoutButton.setEnabled(true);
		if (cartTotal == 0 || cartTotal > store.getCurrentAccount().getMoney()) {
			checkoutButton.setEnabled(false);
		}

		JPanel infoPanel = new JPanel();
		infoPanel.add(totalLabel);
		infoPanel.add(checkoutButton);

		cartPanel.add(cartLabel);
		cartPanel.add(cartScroller);
		cartPanel.add(infoPanel);

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
				revalidateMenuPanel();
			}
		});

		JSpinner quantitySpinner = new JSpinner(
				new SpinnerNumberModel(quantity, 1, store.getInventory().get(productID).getQuantity(), 1));
		quantitySpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int newQuantity = (int) quantitySpinner.getModel().getValue();
				store.getCurrentAccount().updateCartWith(productID, newQuantity);
				revalidateMenuPanel();
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
