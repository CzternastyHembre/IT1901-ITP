package user;

import java.util.Objects;

public class User {
    private double balance;
    private String username;

    public User(){

    }

    public User(String username, double balance) {
        this.balance = balance;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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

    @Override
    public String toString() {
        return "User{" +
                "balance=" + balance +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return hashCode() == user.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, username);
    }
}
