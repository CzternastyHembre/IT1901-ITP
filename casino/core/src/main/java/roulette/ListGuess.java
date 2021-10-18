package roulette;

import java.util.ArrayList;

/**
 * Exends the {@link Guess} class and creates the numbers based on a start and end (inclusive).
 *
 */

public class ListGuess extends Guess {

  /**
 * Creates a list of numbers from start to end (inclusive).
 * Supers to the guess.
 *
 * @param amount amount, the amount of money the guess contains.
 * @param start the start of the {@code list} of numbers (inclusive).
 * @param end PatternGuess
 *
 */

  public ListGuess(double amount, int start, int end) {
    super(amount);
    for (int i = start; i <= end; i++) {
      numbers.add(i);
    }
  }

}
