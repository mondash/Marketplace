//////////////////////////////////////////////////////////////////////////////////
//
// C212 Spring 17
// Final Project Part 2
//
// Due:       4/28/17 11:59 PM
//              
// Group Members: Matt Ondash, Nate Pellant, Joshua Isaacson
//
//////////////////////////////////////////////////////////////////////////////////

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
 */
public class Marketplace {

	// instance variables
	private Directory accounts;
	private Inventory inventory;
	private Account currentAccount;
	private static int id = 0;

	/**
	 * Marketplace Constructor
	 */
	public Marketplace() {

		accounts = new Directory();
		inventory = new Inventory();
		/*
		addProduct(12, "P1", "desc", "cat1", 12, 200);
		addProduct(13, "P2", "desc12", "cat2", 12.4, 400);
		addProduct(14, "P3", "desc12", "cat1", 12.4, 400);
		addProduct(15, "P4", "desc12", "cat3", 12.4, 400);
		addProduct(16, "P5", "desc12", "cat3", 12.4, 400);
		addProduct(17, "P6", "desc12", "cat4", 12.4, 400);
		addProduct(18, "P7", "desc12", "cat5", 12.4, 400);
		addProduct(19, "P8", "desc12", "cat6", 12.4, 400);
		addProduct(10, "P9", "desc12", "cat7", 12.4, 400);
		addProduct(11, "P10", "desc12", "cat8", 12.4, 400);
		addProduct(12, "P11", "desc12", "cat1", 12.4, 400);
		addProduct(12, "P12", "desc12", "cat3", 12.4, 400);
		addProduct(12, "P13", "desc12", "cat2", 12.4, 400);
		addProduct(12, "P14", "desc12", "cat2", 12.4, 400);
		addProduct(12, "P15", "desc12", "cat2", 12.4, 400);
		addProduct(12, "P16", "desc12", "cat2", 12.4, 400);
		addProduct(12, "P17", "desc12", "cat2", 12.4, 400);
		addProduct(12, "P18", "desc12", "cat2", 12.4, 400);
		
		addAccount("Matt", "pass".toCharArray(), "Buyer");
		*/
	}

	/**
	 * setter method for Marketplace
	 * @return an incremented ID to assign to the next Product
	 */
	private int assignID() {
		id++;
		return id;
	}

	/**
	 * adds an Account to the Marketplace
	 * @param name - name of the Account to be added
	 * @param password - password of the Account to be added
	 * @param type - type of the Account to be added
	 */
	public void addAccount(String name, char[] password, String type) {

		accounts.add(new Account(assignID(), name, password, type));
	}

	/**
	 * adds a Product to the Marketplace
	 * @param sellerID - SellerID of the Product to be added
	 * @param name - name of the Product to be added
	 * @param description - description of the Product to be added
	 * @param category - category of the Product to be added
	 * @param price - price of the Product to be added
	 * @param quantity - amount of the Product to be added
	 */
	public void addProduct(int sellerID, String name, String description, String category, double price, int quantity) {
		inventory.add(new Product(assignID(), sellerID, name, description, category, price, quantity));
	}

	/**
	 * @param name - name of the Account to try
	 * @param password - password of the Account to try
	 * @return checks if password matches that of the correct User, returns true if so and subsequently logs in, if not, it returns false
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
	 * logs a User out from the Marketplace
	 */
	public void logout() {
		this.currentAccount = null;
	}

	/**
	 * adds an Item to the cart
	 * @param id - ID of the Item to add to the cart
	 * @param quantity - amount of Items to add to the cart
	 */
	public void addToCart(int id, int quantity) {
		this.currentAccount.addToCart(id, quantity);
	}

	/**
	 * removes an Item from the cart one at a time
	 * @param id - ID of the Item to remove from the cart
	 */
	public void removeFromCart(int id) {
		this.currentAccount.removeFromCart(id);
	}

	/**
	 * checks out current User and pays the Sellers
	 */
	public void checkOut() {
		conductTransaction(this.currentAccount.getCart());
		updateInventory(this.currentAccount.getCart());
		this.currentAccount.checkOut();
	}

	/**
	 * pays the Sellers
	 * @param cart - Point Array of Items in the cart
	 */
	public void conductTransaction(Point[] cart) {
		for (Point p : cart) {
			Product product = this.inventory.get(p.x);
			Account seller = this.accounts.get(product.getSellerID());
			seller.addMoney(product.getPrice() * p.y);
		}
	}

	/**
	 * 
	 * @param cart
	 */
	public void updateInventory(Point[] cart) {

	}
	
	/**
	 * getter method for Marketplace
	 * @return the Account that is current
	 */
	public Account getCurrentAccount() {
		return this.currentAccount;
	}

	/**
	 * getter method for Marketplace
	 * @return the Directory of Accounts
	 */
	public Directory getDir() {
		return this.accounts;
	}

	/**
	 * getter method for Marketplace
	 * @return the Inventory
	 */
	public Inventory getInventory() {
		return this.inventory;
	}

	/**
	 * loads constants from a text File
	 * @param constantsDir - File to be read from
	 */
	private void loadConstants(String constantsDir) {
		try {
			File file = new File(constantsDir);
			Scanner in = new Scanner(file);

			id = Integer.parseInt(in.nextLine());

			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * saves constants to a text File
	 * @param constantsDir - File to be written to
	 */
	private void saveConstants(String constantsDir) {
		try {
			File file = new File(constantsDir);
			PrintWriter out = new PrintWriter(file);
			
			out.println(id);
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * loads various values from text files
	 * @param constantsDir - Directory of constants (product ID iteration and % of gains to the Seller)
	 * @param inventoryDir - Inventory Directory
	 * @param directoryDir - Directory Directory
	 */
	public void loadResources(String constantsDir, String inventoryDir, String directoryDir) {
		loadConstants(constantsDir);
		this.inventory.loadProducts(inventoryDir);
		this.accounts.loadAccounts(directoryDir);
	}

	/**
	 * saves various values to text files
	 * @param constantsDir - Directory of constants (product ID iteration and % of gains to the Seller)
	 * @param inventoryDir - Inventory Directory
	 * @param directoryDir - Directory Directory
	 */
	public void saveResources(String constantsDir, String inventoryDir, String directoryDir) {
		saveConstants(constantsDir);
		this.inventory.saveProducts(inventoryDir);
		this.accounts.saveAccounts(directoryDir);
	}
}