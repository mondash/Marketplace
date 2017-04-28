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
	private static int ID = 0;
	private static double SELLER_REVENUE_PERCENTAGE = .9;

	public Marketplace() {

		accounts = new Directory();
		inventory = new Inventory();
		currentAccount = null;
	}

	private static int assignID() {
		ID++;
		return ID;
	}
	
	public static void setSellerRevenuePercentage(double percentage) {
		SELLER_REVENUE_PERCENTAGE = percentage;
	}
	
	public static double getSellerRevenuePercentage() {
		return SELLER_REVENUE_PERCENTAGE;
	}

	public void addAccount(String name, char[] password, String type) {

		accounts.add(new Account(assignID(), name, password, type));

	}

	public void addProduct(int sellerID, String name, String description, String category, double price, int quantity) {
		inventory.add(new Product(assignID(), sellerID, name, description, category, price, quantity));
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

	public void addToCart(int id) {
		this.currentAccount.addToCart(id);
	}

	public void removeFromCart(int id) {
		this.currentAccount.removeFromCart(id);
	}
	
	public double getCartTotal() {
		double total = 0.0;
		
		for (Point p : this.currentAccount.getCart()) {
			Product product = this.inventory.get(p.x);
			total += product.getPrice() * p.y;
		}
		return total;
	}

	public void checkOut() {
		conductTransaction(this.currentAccount.getCart());
		updateInventory(this.currentAccount.getCart());
		this.currentAccount.checkOut();
	}

	private void conductTransaction(Point[] cart) {
		for (Point p : cart) {
			Product product = this.inventory.get(p.x);
			Account seller = this.accounts.get(product.getSellerID());
			this.currentAccount.removeMoney(product.getPrice() * p.y);
			seller.addMoney(product.getPrice() * SELLER_REVENUE_PERCENTAGE * p.y);
			payAdmins(product.getPrice() * (1 - SELLER_REVENUE_PERCENTAGE) * p.y);
		}
	}

	private void updateInventory(Point[] cart) {
		for (Point p : cart) {
			Product product = this.inventory.get(p.x);
			product.purchase(p.y);
		}
	}
	
	private void payAdmins(double adminRevenue) {
		Account[] admins = this.accounts.getAdmins();
		
		double revenuePerAdmin = adminRevenue / admins.length;
		for (Account a : admins) {
			a.addMoney(revenuePerAdmin);
		}
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

			ID = Integer.parseInt(in.nextLine());
			SELLER_REVENUE_PERCENTAGE = Double.parseDouble(in.nextLine());

			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
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
