package roulette;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberGuess extends Guess {

	public NumberGuess(int amount, int... numbers) {
		super(amount);
		for (int nums : numbers) {
			this.numbers.add(nums);
		}
	}

}
