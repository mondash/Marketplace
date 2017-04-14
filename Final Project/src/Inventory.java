import java.util.ArrayList;

public class Inventory {

	private ArrayList<Product> products;
	
	public Inventory() {
		products = new ArrayList<Product>();
	}
	
	public Product get(int productID) {
		
		for (Product p: this.products) {
			if (p.getItemID() == productID) {
				return p;
			}
		}
		
		return null;
	}
}
