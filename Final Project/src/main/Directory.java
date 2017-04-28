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

/**
 * 
 * @author MattOndash
 * @author NatePellant
 *
 */
public class Directory {

	// instance variables
	private ArrayList<Account> accounts;
	
	/**
	 * Directory Constructor
	 */
	public Directory() {
		accounts = new ArrayList<Account>();
	}
	
	/**
	 * setter method for Directory
	 * @param account - Account to add to the Array List of Accounts
	 */
	public void add(Account account) {
		this.accounts.add(account);
	}
	
	/**
	 * getter method for Directory
	 * @param id - ID to find the Account in the Array List
	 * @return - the Account matching the ID
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
	 * 
	 * @param name - name of user to check whether or not it's in the Directory
	 * @return boolean indicating the result of the aforementioned prompt
	 */
	public boolean inDir(String name) {
		if (get(name) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * getter method for Directory
	 * @param name - name of the Account to search for in the Directory
	 * @return the Account corresponding to the given name
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
	 * reads each Account from its own text File
	 * @param dir - name of the Account to be called from the account
	 */
	public void loadAccounts(String dir) {
		File folder = new File(dir);
		File[] files = folder.listFiles();
		
		for (File f : files) {
			add(Account.readFromFile(f));
		}
	}
	
	/**
	 * writes each Account to its own text File
	 * @param dir - name of the Account to be saved to the file
	 */
	public void saveAccounts(String dir) {
		for (Account a : this.accounts) {
			a.writeToFile(dir);
		}
	}
}