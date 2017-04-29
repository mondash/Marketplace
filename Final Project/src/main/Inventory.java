package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author MattOndash
 * @author NatePellant
 *
 *         <p>
 *         Inventory contains an ArrayList of Products and also includes useful
 *         methods to interpret and mutate this data
 */
public class Inventory {

	// member variables
	private ArrayList<Product> products;

	/**
	 * Inventory Constructor: initializes a new ArrayList of Products
	 */
	public Inventory() {
		products = new ArrayList<Product>();
	}

	/**
	 * Gets the Product with given ID
	 * 
	 * @param productID
	 *            - the specified ID
	 * @return the selected Product if it exists, otherwise null
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
	 * Adds the Product to the ArrayList and sorts the ArrayList for aesthetic
	 * viewing
	 * 
	 * @param product
	 *            - the Product to add to the ArrayList
	 */
	public void add(Product product) {
		this.products.add(product);
		// sorts products by name for convenient viewing in GUI
		products.sort(new Comparator<Product>() {
			@Override
			public int compare(Product p1, Product p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});
	}

	/**
	 * @return an Array of all Product IDs
	 */
	public int[] getProductIDs() {
		int[] IDs = new int[this.products.size()];

		for (int i = 0; i < this.products.size(); i++) {
			IDs[i] = this.products.get(i).getItemID();
		}
		return IDs;
	}

	/**
	 * Gets all Product IDs which correspond to any of the categories
	 * 
	 * @param catNames
	 *            - the Array of category names
	 * @return an integer Array of all Product IDs fulfilling the aforementioned
	 *         condition
	 */
	public int[] getProductIDs(String[] catNames) {
		ArrayList<Integer> IDs = new ArrayList<Integer>();

		for (Product p : this.products) {
			boolean hasCat = false;
			for (String catName : catNames) {
				if (p.getCategory().equals(catName)) {
					hasCat = true;
				}
			}
			if (hasCat) {
				IDs.add(p.getItemID());
			}
		}

		return IDs.stream().mapToInt(i -> i).toArray();
	}

	/**
	 * @return an Array of every Product's identifier
	 */
	public String[] getProductIdentifiers() {
		String[] identifiers = new String[this.products.size()];

		for (int i = 0; i < this.products.size(); i++) {
			identifiers[i] = this.products.get(i).getIdentifier();
		}

		return identifiers;
	}

	/**
	 * 
	 * @param sellerID
	 *            - the ID of the Seller
	 * @return an Array of the identifiers of each Product with given Seller ID
	 */
	public String[] getProductIdentifiers(int sellerID) {
		ArrayList<String> identifiers = new ArrayList<String>();

		for (Product p : this.products) {
			if (p.getSellerID() == sellerID) {
				identifiers.add(p.getIdentifier());
			}
		}
		String[] identifierArray = new String[identifiers.size()];
		return identifiers.toArray(identifierArray);
	}

	/**
	 * Gets all Product IDs which correspond to the Seller ID
	 * 
	 * @param sellerID
	 *            - the ID of the Seller
	 * @return an integer Array of all Product IDs fulfilling the aforementioned
	 *         condition
	 */
	public int[] getProductIDs(int sellerID) {
		ArrayList<Integer> IDs = new ArrayList<Integer>();

		for (Product p : this.products) {
			if (p.getSellerID() == sellerID) {
				IDs.add(p.getItemID());
			}
		}

		return IDs.stream().mapToInt(i -> i).toArray();
	}

	/**
	 * @return an Array of all categories in the Inventory without duplication
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
	 * loads each Product in the Inventory by calling its read method
	 * 
	 * @param dir
	 *            - the Directory to look for files
	 */
	public void loadProducts(String dir) {

		File folder = new File(dir);
		File[] files = folder.listFiles();

		for (File f : files) {
			add(Product.readFromFile(f));
		}
	}

	/**
	 * saves each Product in the Inventory by calling its write method
	 * 
	 * @param dir
	 *            - the Directory to save the files
	 */
	public void saveProducts(String dir) {
		for (Product p : this.products) {
			p.writeToFile(dir);
		}
	}
}