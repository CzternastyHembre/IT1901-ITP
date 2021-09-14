package roulette;

import java.util.ArrayList;
import java.util.List;

public abstract class Guess {

	protected double amount;
	protected List<Integer> numbers = new ArrayList<>();

	public Guess(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public boolean isWin(int number) {
		return numbers.contains(number);
	}

	public int getPossibleWins() {
		return numbers.size();
	}

	@Override
	public String toString() {
		return "" + numbers;
	}

}
