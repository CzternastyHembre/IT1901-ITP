package roulette;

/**
 * 
 * @author matti
 * Only a user for testing and temporary moneyhandling
 */
public class TemporaryUser {
	
	private int balance = 0;
	
	public TemporaryUser(int balance) {
		this.balance = balance;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void changeBalance(int delta) {
		this.balance += balance;
	}

}
