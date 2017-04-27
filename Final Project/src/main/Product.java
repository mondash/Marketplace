package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Product {

	private int itemNumber;
	private int sellerID;
	private String name;
	private String description;
	private String category;
	private double price;
	private int quantity;

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

	public void purchase(int amount) {
		if (amount > this.quantity)
			amount = quantity;
		this.quantity -= amount;
	}
	
	public void setQuantity(int amount) {
		this.quantity = amount;
	}
	
	public int getItemID() {
		return this.itemNumber;
	}
	
	public int getSellerID() {
		return this.sellerID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double cost) {
		this.price = cost;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public static Product readFromFile(File file) {

		try {
			Scanner in = new Scanner(file);
			int itemNumber = Integer.parseInt(in.nextLine());
			int sellerID = Integer.parseInt(in.nextLine());
			String name = in.nextLine();
			String[] descriptionArray = in.nextLine().split("\\+");
			String description = "";
			for (String s: descriptionArray) {
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
