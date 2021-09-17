package roulette;

import java.util.function.UnaryOperator;

public class PatternGuess extends Guess {

	public PatternGuess(int start, int increment) {
		super();
		for (int i = start; i <= Roulette.RoulettSize; i += increment) {
			numbers.add(i);
		}
	}
}
