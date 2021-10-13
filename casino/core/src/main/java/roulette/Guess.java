package roulette;

import java.util.ArrayList;
import java.util.List;

public abstract class Guess {

	protected double amount;
	protected List<Integer> numbers = new ArrayList<>();

	public Guess(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Guess amount must be a positive integer");
		}
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}
	
	public boolean isWin(int number) {
		return numbers.contains(number);
	}
	
	public List<Integer> getNumbers(){
		return numbers;
	}

	public int getPossibleWins() {
		return numbers.size();
	}
	
	@Override
	public String toString() {
		return "amount: " + amount + ", numbers: " + numbers;
	}

}
