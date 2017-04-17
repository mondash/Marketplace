package main;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Marketplace {

	private Directory accounts;
	private Inventory inventory;
	private Account currentAccount;
	private static int id = 0;

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

	private int assignID() {
		id++;
		return id;
	}

	public void addAccount(String name, char[] password, String type) {

		accounts.add(new Account(assignID(), name, password, type));

	}

	public void addProduct(int sellerID, String name, String description, String catagory, double price, int quantity) {
		inventory.add(new Product(assignID(), sellerID, name, description, catagory, price, quantity));
	}

	public boolean tryLogin(String name, char[] password) {
		Account account = accounts.get(name);

		if (account != null && account.isPassword(password)) {
			System.out.println("Successful login");
			this.currentAccount = account;
			return true;
		}
		return false;
	}

	public void logout() {
		this.currentAccount = null;
	}

	public void addToCart(int id, int quantity) {
		this.currentAccount.addToCart(id, quantity);
	}

	public void removeFromCart(int id) {
		this.currentAccount.removeFromCart(id);
	}

	public void checkOut() {
		conductTransaction(this.currentAccount.getCart());
		updateInventory(this.currentAccount.getCart());
		this.currentAccount.checkOut();
	}

	public void conductTransaction(Point[] cart) {
		for (Point p : cart) {
			Product product = this.inventory.get(p.x);
			Account seller = this.accounts.get(product.getSellerID());
			seller.addMoney(product.getPrice() * p.y);
		}
	}

	public void updateInventory(Point[] cart) {

	}
	
	public Account getCurrentAccount() {
		return this.currentAccount;
	}

	public Directory getDir() {
		return this.accounts;
	}

	public Inventory getInventory() {
		return this.inventory;
	}

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

	public void loadResources(String constantsDir, String inventoryDir, String directoryDir) {
		loadConstants(constantsDir);
		this.inventory.loadProducts(inventoryDir);
		this.accounts.loadAccounts(directoryDir);
	}

	public void saveResources(String constantsDir, String inventoryDir, String directoryDir) {
		saveConstants(constantsDir);
		this.inventory.saveProducts(inventoryDir);
		this.accounts.saveAccounts(directoryDir);
	}

}
