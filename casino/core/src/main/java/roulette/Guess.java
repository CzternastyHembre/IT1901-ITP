package roulette;

import java.util.ArrayList;
import java.util.List;

/**
 *Abstract class containing a single guess.
 *{@code numbers} contains the numbers the bets gambled on.
 *{@code amount} contains the amount of money the bets contains.
 *
 */

public abstract class Guess {

  protected double amount;
  protected List<Integer> numbers = new ArrayList<>();

  /**
   * Constructur of the guess class.
   *
   * @param amount the amount of money the guess contains.
   */
  public Guess(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Guess amount must be a positive integer");
    }
    this.amount = amount;
  }

	public List<Integer> getNumbers(){
		return new ArrayList<>(numbers);
	}
  public double getAmount() {
    return amount;
  }

  public boolean isWin(int number) {
    return numbers.contains(number);
  }

  public List<Integer> getNumbers() {
    return numbers;
  }

  public int getPossibleWins() {
    return numbers.size();
  }

  @Override
  public String toString() {
    return "amount: " + amount + ", numbers: " + numbers;
  }

}
