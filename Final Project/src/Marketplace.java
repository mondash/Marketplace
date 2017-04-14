import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Marketplace {

	private Directory accounts;
	private Inventory inventory;
	private Account currentAccount;

	public Marketplace() {

		accounts = new Directory();
		inventory = new Inventory();
	}

	// TODO
	private int assignID() {
		return 1;
	}

	public void addAccount(String name, char[] password, String type) {

		accounts.add(new Account(assignID(), name, password, type, 0, null));

	}

	public void login(String name, char[] password) {
		Account account = accounts.get(name);
		if (account != null && account.isPassword(password)) {
			this.currentAccount = account;
		}
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

	public Directory getDir() {
		return this.accounts;
	}

	public void readAccountsFromFile(String dir) {

	}

	public void writeAccountsToFile(String dir) {
		File folder = new File(dir);
		File[] files = folder.listFiles();

		for (int i = 0; i < files.length; i++) {
			try {
				Scanner in = new Scanner(files[i]);
				String name = in.nextLine();
				String password = in.nextLine();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void loadData() {

	}
}
