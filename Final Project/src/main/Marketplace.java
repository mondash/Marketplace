package main;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 
 * @author MattOndash
 * @author NatePellant
 *
 *         <p>
 *         Marketplace is the logical representation of the online store - it
 *         handles most logic events for the project
 */
public class Marketplace {

	// member variables
	private Directory accounts;
	private Inventory inventory;
	private Account currentAccount;
	private static int ID = 0;
	private static double SELLER_REVENUE_PERCENTAGE = .9;

	/**
	 * Marketplace Constructor: initializes new Directory and Inventory, sets
	 * current Account to be null
	 */
	public Marketplace() {

		accounts = new Directory();
		inventory = new Inventory();
		currentAccount = null;
	}

	/**
	 * increments the ID counter
	 * 
	 * @return the value of the counter after being incremented
	 */
	private static int assignID() {
		ID++;
		return ID;
	}

	/**
	 * Sets the percentage of profits a Seller receives for sales
	 * 
	 * @param percentage
	 *            - the percentage to set (in decimal form)
	 */
	public static void setSellerRevenuePercentage(double percentage) {
		SELLER_REVENUE_PERCENTAGE = percentage;
	}

	/**
	 * @return the percentage of profits a Seller receives for sales (in decimal
	 *         form)
	 */
	public static double getSellerRevenuePercentage() {
		return SELLER_REVENUE_PERCENTAGE;
	}

	/**
	 * Adds Account to the Marketplace's Directory with new generated ID
	 * 
	 * @param name
	 *            - the name of the Account to add
	 * @param password
	 *            - the password of the Account to add
	 * @param type
	 *            - the type of the Account to add
	 */
	public void addAccount(String name, char[] password, String type) {

		accounts.add(new Account(assignID(), name, password, type));

	}

	/**
	 * Adds a Product to the Marketplace's Inventory with new generated ID
	 * 
	 * @param sellerID
	 *            - the ID of the Seller of the Product
	 * @param name
	 *            - the name of the Product
	 * @param description
	 *            - short description of the Product
	 * @param category
	 *            - the category of the Product
	 * @param price
	 *            - the price of the Product
	 * @param quantity
	 *            - the amount of the Product
	 */
	public void addProduct(int sellerID, String name, String description, String category, double price, int quantity) {
		inventory.add(new Product(assignID(), sellerID, name, description, category, price, quantity));
	}

	/**
	 * Attempts to log in to the Marketplace with given credentials
	 * 
	 * @param name
	 *            - the name of the Account to attempt access
	 * @param password
	 *            - the password of the Account to attempt access
	 * @return whether or not login attempt is successful
	 */
	public boolean tryLogin(String name, char[] password) {
		Account account = accounts.get(name);

		if (account != null && account.isPassword(password)) {
			System.out.println("Successful login");
			this.currentAccount = account;
			return true;
		}
		return false;
	}

	/**
	 * logs out the current Account by setting its value to null
	 */
	public void logout() {
		this.currentAccount = null;
	}

	/**
	 * Adds a Product of given ID to the current Account's cart
	 * 
	 * @param id
	 *            - the ID of the Product to add
	 */
	public void addToCart(int id) {
		this.currentAccount.addToCart(id);
	}

	/**
	 * Removes a Product of given ID from the current Account's cart
	 * 
	 * @param id
	 *            - the ID of the Product to remove
	 */
	public void removeFromCart(int id) {
		this.currentAccount.removeFromCart(id);
	}

	/**
	 * Gets the total cost of the current Account's cart
	 * 
	 * @return the cost value
	 */
	public double getCartTotal() {
		double total = 0.0;

		for (Point p : this.currentAccount.getCart()) {
			Product product = this.inventory.get(p.x);
			total += product.getPrice() * p.y;
		}
		return total;
	}

	/**
	 * Calls the three steps for user checkout in the Marketplace
	 */
	public void checkOut() {
		conductTransaction(this.currentAccount.getCart());
		updateInventory(this.currentAccount.getCart());
		this.currentAccount.checkOut();
	}

	/**
	 * Compensates the Seller and Admin while charging the Buyer for the
	 * transaction of the passed cart
	 * 
	 * @param cart
	 *            - the cart to fulfill the transaction
	 */
	private void conductTransaction(Point[] cart) {
		for (Point p : cart) {
			Product product = this.inventory.get(p.x);
			Account seller = this.accounts.get(product.getSellerID());
			this.currentAccount.removeMoney(product.getPrice() * p.y);
			seller.addMoney(product.getPrice() * SELLER_REVENUE_PERCENTAGE * p.y);
			payAdmins(product.getPrice() * (1 - SELLER_REVENUE_PERCENTAGE) * p.y);
		}
	}

	/**
	 * Updates quantities in the Marketplace's Inventory corresponding to the
	 * cart
	 * 
	 * @param cart
	 *            - the cart whose items to sell
	 */
	private void updateInventory(Point[] cart) {
		for (Point p : cart) {
			Product product = this.inventory.get(p.x);
			product.purchase(p.y);
		}
	}

	/**
	 * Divides the revenue for Admins evenly between each Admin
	 * 
	 * @param adminRevenue
	 *            - the amount of revenue the Admins have received
	 */
	private void payAdmins(double adminRevenue) {
		Account[] admins = this.accounts.getAdmins();

		double revenuePerAdmin = adminRevenue / admins.length;
		for (Account a : admins) {
			a.addMoney(revenuePerAdmin);
		}
	}

	/**
	 * @return the current Account
	 */
	public Account getCurrentAccount() {
		return this.currentAccount;
	}

	/**
	 * @return the Marketplace's Directory of Accounts
	 */
	public Directory getDir() {
		return this.accounts;
	}

	/**
	 * @return the Marketplace's Inventory of Products
	 */
	public Inventory getInventory() {
		return this.inventory;
	}

	/**
	 * Reads the text File at the given filepath to determine global constants
	 * 
	 * @param constantsDir
	 *            - the filepath to read
	 */
	private void loadConstants(String constantsDir) {
		try {
			File file = new File(constantsDir);
			Scanner in = new Scanner(file);

			ID = Integer.parseInt(in.nextLine());
			SELLER_REVENUE_PERCENTAGE = Double.parseDouble(in.nextLine());

			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the global constants in a text file at the specified filepath
	 * 
	 * @param constantsDir
	 *            - the filepath to save to
	 */
	private void saveConstants(String constantsDir) {
		try {
			File file = new File(constantsDir);
			PrintWriter out = new PrintWriter(file);

			out.println(ID);
			out.println(SELLER_REVENUE_PERCENTAGE);

			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads all needed resources for the Marketplace to function at the
	 * specified filepaths
	 * 
	 * @param constantsDir
	 *            - filepath to save the constants
	 * @param inventoryDir
	 *            - filepath to save the Inventory
	 * @param directoryDir
	 *            - filepath to save the Directory
	 */
	public void loadResources(String constantsDir, String inventoryDir, String directoryDir) {
		loadConstants(constantsDir);
		this.inventory.loadProducts(inventoryDir);
		this.accounts.loadAccounts(directoryDir);
	}

	/**
	 * Saves all resources from the Marketplace at the specified filepaths
	 * 
	 * @param constantsDir
	 *            - filepath to save the constants
	 * @param inventoryDir
	 *            - filepath to save the Inventory
	 * @param directoryDir
	 *            - filepath to save the Directory
	 */
	public void saveResources(String constantsDir, String inventoryDir, String directoryDir) {
		saveConstants(constantsDir);
		this.inventory.saveProducts(inventoryDir);
		this.accounts.saveAccounts(directoryDir);
	}
}