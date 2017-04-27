package main;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Account {

	private int id;
	private String name;
	private int passwordHash;
	private String type;
	private double money;
	// Point is used as such (Product ID, Quantity) to lower coupling
	private ArrayList<Point> cart;

	public Account(int id, String name, char[] password, String type) {
		this.id = id;
		this.name = name;
		this.passwordHash = Arrays.hashCode(password);
		this.type = type;
		this.money = 0.0;
		this.cart = new ArrayList<Point>();
	}

	public Account(int id, String name, int passwordHash, String type, double money, ArrayList<Point> cart) {
		this.id = id;
		this.name = name;
		this.passwordHash = passwordHash;
		this.type = type;
		this.money = money;
		this.cart = cart;
	}

	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) {;
		this.passwordHash = Arrays.hashCode(password.toCharArray());
	}

	public String getType() {
		return this.type;
	}

	public double getMoney() {
		return this.money;
	}

	public void addMoney(double value) {
		this.money += value;
	}

	public void removeMoney(double value) {
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
			cartArray[i] = this.cart.get(i).x + " " + this.cart.get(i).y;
		}
		return cartArray;
	}

	public void addToCart(int id, int quantity) {
		for (Point item: this.cart) {
			if (item.x == id) {
				item.y++;
				return;
			}
		}
		this.cart.add(new Point(id, quantity));
	}
	
	public void updateCartWith(int id, int quantity) {
		for (Point item: this.cart) {
			if (item.x == id) {
				item.y = quantity;
				return;
			}
		}
	}

	public void removeFromCart(int id) {
		for (int i = 0; i < this.cart.size(); i++) {
			if (this.cart.get(i).x == id) {
				this.cart.remove(i);
			}
		}
	}

	public void checkOut() {
		this.cart.clear();
	}

	public boolean isPassword(char[] letters) {
		return this.passwordHash == Arrays.hashCode(letters);
	}

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
