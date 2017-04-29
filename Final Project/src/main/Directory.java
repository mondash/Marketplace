package main;

import java.io.File;
import java.util.ArrayList;

/**
 * 
 * @author MattOndash
 * @author NatePellant
 *
 *<p>
 * Directory stores an ArrayList of Accounts as well as includes useful methods to interpret and mutate this data
 */
public class Directory {

	// member variables
	private ArrayList<Account> accounts;
	
	/**
	 * Directory Constructor: initializes the ArrayList of Accounts
	 */
	public Directory() {
		accounts = new ArrayList<Account>();
	}
	
	/**
	 * Adds an Account to the ArrayList
	 * @param account - the Account to add
	 */
	public void add(Account account) {
		this.accounts.add(account);
	}
	
	/**
	 * Gets the Account with given ID
	 * @param id - the Account ID
	 * @return the Account if it exists otherwise returns null
	 */
	public Account get(int id) {
		for (Account a : this.accounts) {
			if (a.getID() == id) {
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Searches for the Account of given name
	 * @param name - the specified name
	 * @return whether such Account was found
	 */
	public boolean inDir(String name) {
		if (get(name) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Gets the Account with given name
	 * @param name - the specified name
	 * @return the Account if it exists, otherwise null
	 */
	public Account get(String name) {
		for (Account a : this.accounts) {
			if (a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}
	
	/**
	 * @return an Array of all Accounts that are of type Admin
	 */
	public Account[] getAdmins() {
		ArrayList<Account> admins = new ArrayList<Account>();
		
		for (Account a : this.accounts) {
			if (a.getType().equals("Admin")) {
				admins.add(a);
			}
		}
		Account[] adminArray = new Account[admins.size()];
		adminArray = (Account[]) admins.toArray(adminArray);
		
		return adminArray;
	}
	
	/**
	 * loads each Account in the given Directory and adds it to the ArrayList
	 * @param dir - the specified Directory
	 */
	public void loadAccounts(String dir) {
		
		File folder = new File(dir);
		File[] files = folder.listFiles();
		
		for (File f : files) {
			add(Account.readFromFile(f));
		}
	}
	
	/**
	 * saves all Accounts by calling each one's write method 
	 * @param dir - the Directory passed to the Account's write method
	 */
	public void saveAccounts(String dir) {
		for (Account a : this.accounts) {
			a.writeToFile(dir);
		}
	}
}