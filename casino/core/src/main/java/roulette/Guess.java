package roulette;

import java.util.ArrayList;
import java.util.List;

/**
 *Abstract class containing a single guess.
 *{@code numbers} contains the numbers the bets gambled on.
 *{@code amount} contains the amount of money the bets contains.
 *
 */

public class Guess {

  protected double amount;
  protected List<Integer> numbers = new ArrayList<>();

  /**
   * Constructur of the guess class.
   *
   * @param amount the amount of money the guess contains.
   */

  private Guess(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Guess amount must be a positive integer");
    }
    this.amount = amount;
  }

  /**
   *
   * Creates a list of numbers from start to end (inclusive).
   *
   * @param amount amount, the amount of money the guess contains.
   * @param start the start of the {@code list} of numbers (inclusive).
   * @param end PatternGuess
   * @return {@code Guess} containing the list of guesses
   */

    public static Guess listGuess(double amount, int start, int end) {
      Guess guess = new Guess(amount);
      List<Integer> numbers = new ArrayList<>();
      for (int i = start; i <= end; i++) {
        numbers.add(i);
      }
      guess.setNumbers(numbers);
      return guess;
  }

  /**
   *
   * Creates a list of numbers from start to end (inclusive).
   *
   * @param amount the amount of money the guess contains.
   * @param numberArray the numbers containing the guess.
   * @return {@code Guess} containing the list of guesses
   */

  public static Guess numberGuess(double amount, int... numberArray) {
    Guess guess = new Guess(amount);
    List<Integer> numbers = new ArrayList<>();
    for (int nums : numberArray) {
      numbers.add(nums);
    }
    guess.setNumbers(numbers);
    return guess;
  }

  /**
   * Creates a list of a pattern depending on the start and the increment.
   * Supers to Guess.
   *
   * @param amount the amount of money the guess contains.
   * @param start the start of the pattern.
   * @param increment the increment for each number.
   * @return {@code Guess} containing the list of guesses
   */
  public static Guess patternGuess(double amount, int start, int increment) {
    Guess guess = new Guess(amount);
    List<Integer> numbers = new ArrayList<>();
    for (int i = start; i <= Roulette.rouletteSize; i += increment) {
      numbers.add(i);
    }
    guess.setNumbers(numbers);
    return guess;
  }



  public void setNumbers(List<Integer> numbers) {
    this.numbers = numbers;
  }


  public List<Integer> getNumbers() {
    return new ArrayList<>(numbers);
  }

  public double getAmount() {
    return amount;
  }

  public boolean isWin(int number) {
    return numbers.contains(number);
  }

  public int getPossibleWins() {
    return numbers.size();
  }

  @Override
  public String toString() {
    return "amount: " + amount + ", numbers: " + numbers;
  }

}
