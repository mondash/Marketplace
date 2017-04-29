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

	// member variables
	private int itemNumber;
	private int sellerID;
	private String name;
	private String description;
	private String category;
	private double price;
	private int quantity;

	/**
	 * Product Constructor:
	 * 
	 * @param itemNumber
	 *            - integer ID of the Product
	 * @param sellerID
	 *            - ID of the Seller of the Product
	 * @param name
	 *            - name of the Product
	 * @param description
	 *            - short description of the Product
	 * @param category
	 *            - the category of the Product
	 * @param price
	 *            - the price of the Product
	 * @param quantity
	 *            - the quantity of the Product
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
	 * @return the ID of the Product
	 */
	public int getItemID() {
		return this.itemNumber;
	}

	/**
	 * @return the ID of the Seller
	 */
	public int getSellerID() {
		return this.sellerID;
	}

	/**
	 * @return the name of the Product
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            - the name to set the Product
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return a String conjuncting the name of the Product and its item number
	 */
	public String getIdentifier() {
		return this.name + ": " + this.itemNumber;
	}

	/**
	 * @return the description of the Product
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param desc
	 *            - the description to set the Product
	 */
	public void setDescription(String desc) {
		this.description = desc;
	}

	/**
	 * @return the category of the Product
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * @param category
	 *            - the category to set the Product
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the price of the Product
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 * @param cost
	 *            - the cost to set the Product
	 */
	public void setPrice(double cost) {
		this.price = cost;
	}

	/**
	 * @return the quantity of the Product
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * Subtracts the amount from the Product's quantity; includes a safety catch
	 * as to disallow negative quantities
	 * 
	 * @param amount
	 *            - the number of Products to purchase
	 */
	public void purchase(int amount) {
		if (amount > this.quantity)
			amount = quantity;
		this.quantity -= amount;
	}

	/**
	 * @param amount
	 *            - the amount to set the Product quantity
	 */
	public void setQuantity(int amount) {
		this.quantity = amount;
	}

	/**
	 * Reads a text file and interprets it as a Product
	 * 
	 * @param file
	 *            - the File to read
	 * @return a Product given the interpreted data
	 */
	public static Product readFromFile(File file) {

		try {
			Scanner in = new Scanner(file);
			int itemNumber = Integer.parseInt(in.nextLine());
			int sellerID = Integer.parseInt(in.nextLine());
			String name = in.nextLine();
			String[] descriptionArray = in.nextLine().split("\\+");
			String description = "";
			for (String s : descriptionArray) {
				description += s + "\n";
			}

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
	 * Saves Product by writing it to a text File
	 * 
	 * @param dir
	 *            - the directory of the File
	 */
	public void writeToFile(String dir) {

		try {
			File file = new File(dir + "Product_" + this.itemNumber + ".txt");
			PrintWriter out = new PrintWriter(file);

			out.println(this.itemNumber);
			out.println(this.sellerID);
			out.println(this.name);

			String[] descriptionLines = this.description.split("\n");
			for (int i = 0; i < descriptionLines.length; i++) {
				out.print(descriptionLines[i]);
				if (i < descriptionLines.length - 1) {
					out.print("+");
				}
			}
			out.println();
			out.println(this.category);
			out.println(this.price);
			out.println(this.quantity);

			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
