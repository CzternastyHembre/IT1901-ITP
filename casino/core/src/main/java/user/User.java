package user;

/**
 * User class which contains a balance and a username.
 */

public class User {
  private double balance;
  private String username;

  /**
   * Empty constructor such that Gson can save.
   */

  public User() {

  }

  public User(String username, double balance) {
    this.balance = balance;
    this.username = username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Sets the balance of the user.
   *
   * @param balance the balance which the user will get.
   */

  public void setBalance(double balance) {
    if (balance < 0) {
      throw new IllegalArgumentException("Cant have negative balance");
    }
    this.balance = balance;
  }

  /**
   * Add more money to the balance of the user.
   *
   * @param amount the amount that is being added.
   */

  public void addMoney(double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Can not add negative amount");
    }
    this.balance += amount;
  }

  public double getBalance() {
    return balance;
  }

  /**
   * Withdraw money from the users balance.
   *
   * @param amount the amount that is being drawn from the balance.
   */

  public void withdraw(double amount) {
    if (amount > balance) {
      throw new IllegalStateException("You dont have enough money");
    }
    if (amount <= 0) {
      throw new IllegalArgumentException("You can't withdraw negative amount of money");
    }
    this.balance -= amount;
  }


  public String getUsername() {
    return username;
  }

  @Override
  public String toString() {
    return "User{" + "balance=" + balance + ", username='" + username + '\'' + '}';
  }
}
