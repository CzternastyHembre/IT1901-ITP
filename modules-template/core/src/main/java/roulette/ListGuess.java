package roulette;

public class ListGuess extends Guess {

	public ListGuess(int amount, int start, int end) {
		super(amount);

		for (int i = start; i <= end; i++) {
			numbers.add(i);
		}
	}

}
