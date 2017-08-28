// Author: Philip Smith
package assignment1;

public class BankAccount {
	
	private double balance;
	private short id;
	private static short id_counter = 9000;
	private BankAccount overdraftAcc = null;
	
	public BankAccount(double amount) {
		balance = amount;
		id = id_counter;
		id_counter += 1;
	}
	
	public void deposit(double amount) {
		balance += amount;
	}
	
	public void withdraw(double amount) {
		if (balance < amount && overdraftAcc != null) {
			overdraftAcc.withdraw(50);
			this.deposit(50);
			this.withdraw(amount);
		} else {
			balance -= amount;
		}
	}
	
	public void transfer(double amount, BankAccount acc) {
		this.withdraw(amount);
		acc.deposit(amount);
	}
	
	public void setOverdraftAccount(BankAccount acc) {
		this.overdraftAcc = acc;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public short getAccountNumber() {
		return id;
	}
	
}
