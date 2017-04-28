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

	public Product get(int productID) {

		for (Product p : this.products) {
			if (p.getItemID() == productID) {
				return p;
			}
		}
		return null;
	}

	public Product get(String productName) {

		for (Product p : this.products) {
			if (p.getName().equals(productName)) {
				return p;
			}
		}
		return null;
	}

	public void add(Product product) {
		this.products.add(product);
	}

	public String[] getProductNames() {
		String[] names = new String[this.products.size()];

		for (int i = 0; i < this.products.size(); i++) {
			names[i] = this.products.get(i).getName();
		}
		return names;
	}

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

	public String[] getCategories() {
		Set<String> categories = new HashSet<String>();

		for (Product p : this.products) {
			categories.add(p.getCategory());
		}
		String[] names = new String[categories.size()];
		names = categories.toArray(names);

		return names;
	}
	
	
	public void loadProducts(String dir) {
		
		File folder = new File(dir);
		File[] files = folder.listFiles();
		
		for (File f : files) {
			add(Product.readFromFile(f));
		}
	}
	
	public void saveProducts(String dir) {
		for (Product p : this.products) {
			p.writeToFile(dir);
		}
	}
}