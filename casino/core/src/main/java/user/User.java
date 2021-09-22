package user;

public class User {

    private double balance;
    private String username;

    public User(String username, double balance) {
        this.balance = balance;
        this.username = username;
    }

    public void addMoney(double amount){
        this.balance += amount;
    }

    public double getBalance() {
        return balance;
    }
    
	public void withdraw(double delta) {
		if (delta > balance) {
			throw new IllegalStateException("You dont have enough money");
		}
		if (delta <= 0) {
			throw new IllegalArgumentException("You can't withdraw negative amount of money");			
		}
		this.balance -= delta;
	}


    public void setMoney(double money) {
        if (money < 0)
            throw new IllegalArgumentException("Cannot have a negative value of money");
        this.balance = money;
    }

    public String getUsername() {
        return username;
    }
    
}
