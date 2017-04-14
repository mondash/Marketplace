import java.awt.Point;
import java.util.ArrayList;

public class Account {

	private int id;
	private String name;
	private char[] password;
	private String type;
	private double money;
	// Point is used as such (Product ID, Quantity) to lower coupling
	protected ArrayList<Point> cart;

	public Account(int id, String name, char[] password, String type, double money, ArrayList<Point> cart) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.type = type;
		this.money = money;
		this.cart = cart;
	}
	
	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public double getMoney() {
		return this.money;
	}
	
	public void addMoney(double value) {
		this.money += value;
	}
	
	public void payMoney(double value) {
		this.money -= value;
	}
	
	public Point[] getCart() {
		return (Point[]) cart.toArray();
	}
	
	public void addToCart(int id, int quantity) {
		this.cart.add(new Point(id, quantity));
	}
	
	public void removeFromCart(int id) {
		for (int i = 0; i < this.cart.size(); i++) {
			if (this.cart.get(i).getX() == id) {
				this.cart.remove(i);
			}
		}
	}
	
	public void checkOut() {
		for (Point p : this.cart) {
			
		}
	}

	public boolean isPassword(char[] letters) {
		if (this.password.length == letters.length) {
			for (int i = 0; i < this.password.length; i++) {
				if (this.password[i] != letters[i]) {
					return false;
				}
			}
		}
		return false;
	}

}
