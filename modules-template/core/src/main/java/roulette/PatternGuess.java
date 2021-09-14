package roulette;

import java.util.function.UnaryOperator;

public class PatternGuess extends Guess{
	
	
	public PatternGuess(int amount, int start, int increment) {
		super(amount);
		
		for (int i = start; i < Roulette.RoulettSize ; i += increment) {
			numbers.add(i);
		}
	}
}
