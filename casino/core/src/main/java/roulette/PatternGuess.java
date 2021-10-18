package roulette;

/**
 * Exends the {@link Guess} class and creates the numbers based on a start and an increment.
 * Which creates all numbers from numbers = [start + increment * k ∈ {N}].
 * Until the number exceeds the RouletteSize.
 *
 */

public class PatternGuess extends Guess {

	/**
	 * Creates a list of a pattern depending on the start and the increment.
	 * Supers to Guess.
	 *
	 * @param amount
	 *, the amount of money the guess contains.
	 * @param start
	 * , the start of the pattern.
	 * @param increment
	 * the increment for each number.
	 * 
	 */
	public PatternGuess(double amount, int start, int increment) {
		super(amount);
	    for (int i = start; i <= Roulette.rouletteSize; i += increment) {
	      numbers.add(i);
	    }
	}
}
