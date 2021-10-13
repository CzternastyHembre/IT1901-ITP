package roulette;

import java.util.List;

public class NumberGuess extends Guess {

	public NumberGuess(double amount, int... numbers) {
		super(amount);
		for (int nums : numbers) {
			this.numbers.add(nums);
		}
	}
	public NumberGuess(double amount, List<Integer> numbers) {
		super(amount);
		this.numbers = numbers;
	}

}
