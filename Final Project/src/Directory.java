import java.util.ArrayList;

public class Directory {

	private ArrayList<Account> accounts;
	
	public Directory() {
		accounts = new ArrayList<Account>();
	}
	
	public void add(Account account) {
		this.accounts.add(account);
	}
	
	public Account get(int id) {
		for (Account a : this.accounts) {
			if (a.getID() == id) {
				return a;
			}
		}
		return null;
	}
	
	public boolean inDir(String name) {
		if (get(name) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Account get(String name) {
		for (Account a : this.accounts) {
			if (a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}	

}
