package user;

public class User {

    private double money;
    private String username;

    public User(String username, double money) {
        this.money = money;
        this.username = username;
    }

    public void addMoney(int amount){
        this.money += amount;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(int money) {
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
