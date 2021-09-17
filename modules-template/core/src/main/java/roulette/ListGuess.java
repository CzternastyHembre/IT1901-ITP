package roulette;

public class ListGuess extends Guess {

	public ListGuess(int start, int end) {
		super();

		for (int i = start; i <= end; i++) {
			numbers.add(i);
		}
	}

}
