package roulette;

public class NumberGuess extends Guess {

	public NumberGuess(double amount, int... numbers) {
		super(amount);
		for (int nums : numbers) {
			this.numbers.add(nums);
		}
	}

}
