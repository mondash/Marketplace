package main;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 * @author MattOndash
 * @author NatePellant
 * 
 * <p> 
 * Account manages all facets - data and methods - of a single Marketplace user
 */
public class Account {

	// member variables
	private int id;
	private String name;
	private int passwordHash;
	private String type;
	private double money;
	// Point is used as such (Product ID, Quantity) to lower coupling
	private ArrayList<Point> cart;

	/**
	 * Account Constructor: for initializing new Accounts
	 * @param id - ID of the Account
	 * @param name - name of the Account
	 * @param password - password of the Account
	 * @param type - type of the Account
	 */
	public Account(int id, String name, char[] password, String type) {
		this.id = id;
		this.name = name;
		this.passwordHash = Arrays.hashCode(password);
		this.type = type;
		this.money = 0.0;
		this.cart = new ArrayList<Point>();
	}

	/**
	 * Account Constructor: for reinitializing previous Accounts
	 * @param id - ID of the Account
	 * @param name - name of the Account
	 * @param passwordHash - hash code of the password of the Account
	 * @param type - type of the Account
	 * @param money - funds of the Account
	 * @param cart - Array List of Points representing an Account's cart
	 */
	public Account(int id, String name, int passwordHash, String type, double money, ArrayList<Point> cart) {
		this.id = id;
		this.name = name;
		this.passwordHash = passwordHash;
		this.type = type;
		this.money = money;
		this.cart = cart;
	}

	/**
	 * @return the ID of the Account
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * @return the name of the Account
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @param name - the name to set the Account
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param password - the password to set for the Account
	 */
	public void setPassword(String password) {;
		this.passwordHash = Arrays.hashCode(password.toCharArray());
	}

	/**
	 * @return the type to set the Account
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @return the balance of the Account
	 */
	public double getMoney() {
		return this.money;
	}

	/**
	 * @param value - the amount to add to the Account's balance
	 */
	public void addMoney(double value) {
		this.money += value;
	}

	/**
	 * @param value - the amount to remove from the Account's balance
	 */
	public void removeMoney(double value) {
		this.money -= value;
	}

	/**
	 * @return the Account's cart
	 */
	public Point[] getCart() {
		Point[] cartArray = new Point[this.cart.size()];
		cartArray = this.cart.toArray(cartArray);
		return cartArray;
	}
	
	/**
	 * Converts cart to a more readable format (String Array)
	 * @return this array
	 */
	public String[] getCartLabels() {
		String[] cartArray = new String[this.cart.size()];
		for (int i = 0; i < cartArray.length; i++) {
			cartArray[i] = this.cart.get(i).x + " " + this.cart.get(i).y;
		}
		return cartArray;
	}

	/**
	 * Adds a new Point to cart with the given ID and quantity 1
	 * @param id - the Product ID
	 */
	public void addToCart(int id) {
		for (Point item: this.cart) {
			if (item.x == id) {
				return;
			}
		}
		this.cart.add(new Point(id, 1));
	}
	
	/**
	 * Sets the quantity of element in cart given by ID
	 * @param id - the Product ID
	 * @param quantity - the quantity of the Product
	 */
	public void updateCartWith(int id, int quantity) {
		for (Point item: this.cart) {
			if (item.x == id) {
				item.y = quantity;
				return;
			}
		}
	}

	/**
	 * Removes the Point from the cart with given ID
	 * @param id - the Product ID
	 */
	public void removeFromCart(int id) {
		for (int i = 0; i < this.cart.size(); i++) {
			if (this.cart.get(i).x == id) {
				this.cart.remove(i);
				return;
			}
		}
	}

	/**
	 * Clears the cart of all elements
	 */
	public void checkOut() {
		this.cart.clear();
	}

	/**
	 * @param letters - the given password
	 * @return whether the given password's hash code matches that of the Account's password
	 */
	public boolean isPassword(char[] letters) {
		return this.passwordHash == Arrays.hashCode(letters);
	}

	/**
	 * Reads a text file and interprets it as an Account
	 * @param file - the File to read
	 * @return a new Account given the interpreted data
	 */
	public static Account readFromFile(File file) {

		try {
			Scanner in = new Scanner(file);

			int id = Integer.parseInt(in.nextLine());
			String name = in.nextLine();
			//char[] password = in.nextLine().toCharArray();
			int passwordHash = Integer.parseInt(in.nextLine());
			String type = in.nextLine();
			Double money = Double.parseDouble(in.nextLine());
			String[] cartItems = in.nextLine().split("\\+"); 
			ArrayList<Point> cart = new ArrayList<Point>();
			if (!cartItems[0].equals("")) {
				for (String item : cartItems) {
					int prodID = Integer.parseInt(item.substring(0, item.indexOf(",")));
					int quantity = Integer.parseInt(item.substring(item.indexOf(",") + 1));
					cart.add(new Point(prodID, quantity));
				}
			}

			in.close();
			return new Account(id, name, passwordHash, type, money, cart);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Saves the Account in a text file at the given address
	 * @param dir - the address to save the text file
	 */
	public void writeToFile(String dir) {
		try {
			File file = new File(dir + "User" + "_" + this.id + ".txt");
			//File file = new File(dir + this.name + "_" + this.id + ".txt");
			PrintWriter out = new PrintWriter(file);

			out.println(this.id);
			out.println(this.name);
			//out.println(String.copyValueOf(this.password));
			out.println(this.passwordHash);
			out.println(this.type);
			out.println(this.money);

			for (int i = 0; i < this.cart.size(); i++) {
				out.print(this.cart.get(i).x + "," + this.cart.get(i).y);
				if (i < this.cart.size() - 1) {
					out.print("+");
				}
			}
			out.println();

			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
