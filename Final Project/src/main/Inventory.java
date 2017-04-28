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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Inventory {

	// instance variables
	private ArrayList<Product> products;

	/**
	 * Inventory Constructor
	 */
	public Inventory() {
		products = new ArrayList<Product>();
	}

	/**
	 * getter method for Inventory
	 * @param productID
	 * @return a Product matching the Product ID
	 */
	public Product get(int productID) {

		for (Product p : this.products) {
			if (p.getItemID() == productID) {
				return p;
			}
		}
		return null;
	}

	/**
	 * getter method for Inventory
	 * @param productName
	 * @return a Product matching the Product name
	 */
	public Product get(String productName) {

		for (Product p : this.products) {
			if (p.getName().equals(productName)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * adds products to the Array List of Products
	 * @param product - adds a Product to the Array List of Products
	 */
	public void add(Product product) {
		this.products.add(product);
	}

	/**
	 * getter method for Inventory
	 * @return a String Array of the Product Names
	 */
	public String[] getProductNames() {
		String[] names = new String[this.products.size()];

		for (int i = 0; i < this.products.size(); i++) {
			names[i] = this.products.get(i).getName();
		}
		return names;
	}

	/**
	 * getter method for Inventory
	 * @param catNames - String Array of categories to limit "search"
	 * @return names of Products matching categories in the String Array
	 */
	public String[] getProductNames(String[] catNames) {
		ArrayList<String> names = new ArrayList<String>();

		for (Product p : this.products) {
			boolean hasCat = false;
			for (String catName : catNames) {
				if (p.getCategory().equals(catName)) {
					hasCat = true;
				}
			}
			if (hasCat) {
				names.add(p.getName());
			}
		}
		String[] nameArray = new String[names.size()];
		return names.toArray(nameArray);
	}

	/**
	 * getter method for Inventory
	 * @return a String Array of categories for Products
	 */
	public String[] getCategories() {
		Set<String> categories = new HashSet<String>();

		for (Product p : this.products) {
			categories.add(p.getCategory());
		}
		String[] names = new String[categories.size()];
		names = categories.toArray(names);

		return names;
	}
	
	/**
	 * PrintReader for Inventory
	 * @param dir - File Directory/location to read from the File
	 */
	public void loadProducts(String dir) {
		
		File folder = new File(dir);
		File[] files = folder.listFiles();
		
		for (File f : files) {
			add(Product.readFromFile(f));
		}
	}
	
	/**
	 * PrintWriter for Inventory
	 * @param dir - File Directory/location to write the File to
	 */
	public void saveProducts(String dir) {
		for (Product p : this.products) {
			p.writeToFile(dir);
		}
	}
}