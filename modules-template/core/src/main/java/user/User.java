package user;

public class User {

    private double money;
    private String username;

    public User(String username, double money) {
        this.money = money;
        this.username = username;
    }

    public void addMoney(double amount){
        this.money += amount;
    }

    public double getMoney() {
        return money;
    }
    
	public void withdraw(double delta) {
		if (delta > money) {
			throw new IllegalStateException("You dont have enough money");
		}
		this.money -= delta;
	}


    public void setMoney(double money) {
        if (money < 0)
            throw new IllegalArgumentException("Cannot have a negative value of money");
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
