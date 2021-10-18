package roulette;

import java.util.List;

/**
 * Exends the {@link Guess} class and creates a guess based on numbers betted on.
 *
 */
public class NumberGuess extends Guess {
 
/**
 * 
 * @param amount, the amount of money the guess contains.
 * @param numbers, the numbers containing the guess
 */
  public NumberGuess(double amount, int... numbers) {
    super(amount);
    for (int nums : numbers) {
      this.numbers.add(nums);
    }
  }
}
