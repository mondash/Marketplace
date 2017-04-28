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
public class Product {

	// instance variables
	private int itemNumber;
	private int sellerID;
	private String name;
	private String description;
	private String category;
	private double price;
	private int quantity;

	/**
	 * Product Constructor
	 * @param itemNumber - ID of the Product
	 * @param sellerID - ID of the Seller of the Product
	 * @param name - name of the Product
	 * @param description - description of the Product
	 * @param category - category of the Product
	 * @param price - price of the Product
	 * @param quantity - number amount of the Product
	 */
	public Product(int itemNumber, int sellerID, String name, String description, String category, double price,
			int quantity) {
		this.itemNumber = itemNumber;
		this.sellerID = sellerID;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * buys a Product
	 * @param amount - cost of the item
	 */
	public void purchase(int amount) {
		if (amount > this.quantity)
			amount = quantity;
		this.quantity -= amount;
	}
	
	/**
	 * setter method for Product
	 * @param amount - the amount to re-stock the Product with
	 */
	public void restock(int amount) {
		this.quantity += amount;
	}
	
	/**
	 * getter method for Product
	 * @return the ItemID of the Product
	 */
	public int getItemID() {
		return this.itemNumber;
	}
	
	/**
	 * getter method for Product
	 * @return the SellerID of the Product
	 */
	public int getSellerID() {
		return this.sellerID;
	}
	
	/**
	 * getter method for Product
	 * @return the name of the Product
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * setter method for Product
	 * @param name - name to re-set the Product as
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * getter method for Product
	 * @return the description of the Product
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * setter method for Product
	 * @param desc - new description used to describe the Product
	 */
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	/**
	 * getter method for Product
	 * @return the category of the Product
	 */
	public String getCategory() {
		return this.category;
	}
	
	/**
	 * setter method for Product
	 * @param category - category to re-set the Product as
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * getter method for Product
	 * @return the price of the Product
	 */
	public double getPrice() {
		return this.price;
	}
	
	/**
	 * setter method for Product
	 * @param cost - double value to set the price for the Product to
	 */
	public void setPrice(double cost) {
		this.price = cost;
	}
	
	/**
	 * getter method for Product
	 * @return updated quantity
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * PrintReader for Product
	 * @param file - File to be read from by Scanner
	 * @return a Product from the File
	 */
	public static Product readFromFile(File file) {

		try {
			Scanner in = new Scanner(file);
			int itemNumber = Integer.parseInt(in.nextLine());
			int sellerID = Integer.parseInt(in.nextLine());
			String name = in.nextLine();
			String description = in.nextLine();
			String category = in.nextLine();
			double price = Double.parseDouble(in.nextLine());
			int quantity = Integer.parseInt(in.nextLine());

			in.close();
			return new Product(itemNumber, sellerID, name, description, category, price, quantity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * PrintWriter for Product
	 * @param dir - name of the File to be written to
	 */
	public void writeToFile(String dir) {

		try {
			File file = new File(dir + this.name + "_" + this.itemNumber + ".txt");
			PrintWriter out = new PrintWriter(file);

			out.println(this.itemNumber);
			out.println(this.sellerID);
			out.println(this.name);
			out.println(this.description);
			out.println(this.category);
			out.println(this.price);
			out.println(this.quantity);

			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}