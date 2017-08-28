// Author: Philip Smith
package assignment1;

import java.util.ArrayList;

public class Customer {
	
	private String name;
	private static short id_counter = 9000;
	private short id;
	private ArrayList<BankAccount> accountList = new ArrayList<BankAccount>();
		
	public Customer(String name) {
		this.name = name;
		id = id_counter;
		id_counter += 1;
	}

	public short getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void addAccount(BankAccount b1) {
		accountList.add(b1);
	}

	public double getAssetTotal() {
		double total = 0;
		for (BankAccount acc: accountList) {
			total += acc.getBalance();
		}
		return total;
	}
	
	
	
}
