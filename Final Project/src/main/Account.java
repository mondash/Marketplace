package main;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Account {

	// instance variables
	private int id;
	private String name;
	private char[] password;
	private String type;
	private double money;
	// Point is used as such (Product ID, Quantity) to lower coupling
	private ArrayList<Point> cart;

	/**
	 * Account Constructor - initializes Account with new cart
	 * @param id - ID of Account
	 * @param name - String name of Account
	 * @param password - password of Account
	 * @param type - type of Account: Seller, Admin, Buyer
	 */
	public Account(int id, String name, char[] password, String type) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.type = type;
		this.money = 0.0;
		this.cart = new ArrayList<Point>();
	}

	/**
	 * Account Constructor - initializes Account with pre-made cart
	  @param id - ID of Account
	 * @param name - String name of Account
	 * @param password - password of Account
	 * @param type - type of Account: Seller, Admin, Buyer
	 * @param money - amount of funds to initialize Account with 
	 * @param cart - premade cart to initialize Account with 
	 */
	public Account(int id, String name, char[] password, String type, double money, ArrayList<Point> cart) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.type = type;
		this.money = money;
		this.cart = cart;
	}

	/**
	 * getter method for Account
	 * @return ID of Account
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * getter method for Account
	 * @return name of Account
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * getter method for Account
	 * @return Type of Account
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * getter method for Account
	 * @return funds of Account
	 */
	public double getMoney() {
		return this.money;
	}

	/**
	 * setter method for Account
	 * @return ID of account
	 */
	public void addMoney(double value) {
		this.money += value;
	}

	public void payMoney(double value) {
		this.money -= value;
	}

	public Point[] getCart() {
		Point[] cartArray = new Point[this.cart.size()];
		cartArray = this.cart.toArray(cartArray);
		return cartArray;
	}
	
	public String[] getCartLabels() {
		String[] cartArray = new String[this.cart.size()];
		for (int i = 0; i < cartArray.length; i++) {
			cartArray[i] = "ID: " + this.cart.get(i).x + " Quantity: " + this.cart.get(i).y;
		}
		return cartArray;
	}
	
	public String[] getCartIDs() {
		String[] IDs = new String[this.cart.size()];
		for (int i = 0; i < IDs.length; i++) {
			IDs[i] = "" + this.cart.get(i).x;
		}
		return IDs;
	}
	
	public String[] getCartQuantities() {
		String[] quantities = new String[this.cart.size()];
		for (int i = 0; i < quantities.length; i++) {
			quantities[i] = "" + this.cart.get(i).y;
		}
		return quantities;
	}

	public void addToCart(int id, int quantity) {
		this.cart.add(new Point(id, quantity));
	}

	public void removeFromCart(int id) {
		for (int i = 0; i < this.cart.size(); i++) {
			if (this.cart.get(i).getX() == id) {
				this.cart.remove(i);
			}
		}
	}

	public void checkOut() {

	}

	public boolean isPassword(char[] letters) {
		if (this.password.length == letters.length) {
			for (int i = 0; i < this.password.length; i++) {
				if (this.password[i] != letters[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static Account readFromFile(File file) {

		try {
			Scanner in = new Scanner(file);

			int id = Integer.parseInt(in.nextLine());
			String name = in.nextLine();
			char[] password = in.nextLine().toCharArray();
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
			return new Account(id, name, password, type, money, cart);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void writeToFile(String dir) {
		try {
			File file = new File(dir + this.name + "_" + this.id + ".txt");
			PrintWriter out = new PrintWriter(file);

			out.println(this.id);
			out.println(this.name);
			out.println(String.copyValueOf(this.password));
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
