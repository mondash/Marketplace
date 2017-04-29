package main;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Inventory {

	private ArrayList<Product> products;

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

	/*public Product get(String productName) {

		for (Product p : this.products) {
			if (p.getName().equals(productName)) {
				return p;
			}
		}
		return null;
	}*/

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

	/*public String[] getProductNames() {
		String[] names = new String[this.products.size()];

		for (int i = 0; i < this.products.size(); i++) {
			names[i] = this.products.get(i).getName();
		}
		return names;
	}*/
	
	public int[] getProductIDs() {
		int[] IDs = new int[this.products.size()];
		
		for (int i = 0; i < this.products.size(); i++) {
			IDs[i] = this.products.get(i).getItemID();
		}
		return IDs;
	}
	
	public int[] getProductIDs(String[] catNames) {
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		
		for (Product p: this.products) {
			boolean hasCat = false;
			for (String catName: catNames) {
				if (p.getCategory().equals(catName)) {
					hasCat = true;
				}
			}
			if (hasCat) {
				IDs.add(p.getItemID());
			}
		}
		
		return IDs.stream().mapToInt(i->i).toArray();
	}
	
	public String[] getProductIdentifiers() {
		String[] identifiers = new String[this.products.size()];
		
		for (int i = 0; i < this.products.size(); i++) {
			identifiers[i] = this.products.get(i).getIdentifier();
		}

		return identifiers;
	}
	
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
	
	public int[] getProductIDs(int sellerID) {
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		
		for (Product p: this.products) {
			if (p.getSellerID() == sellerID) {
				IDs.add(p.getItemID());
			}
		}
		
		return IDs.stream().mapToInt(i->i).toArray();
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
