package roulette;

public class NumberGuess extends Guess {

	public NumberGuess(int... numbers) {
		super();
		for (int nums : numbers) {
			this.numbers.add(nums);
		}
	}

}
