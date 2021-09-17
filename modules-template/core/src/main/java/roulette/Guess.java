package roulette;

import java.util.ArrayList;
import java.util.List;

public abstract class Guess {

	protected double amount;
	protected List<Integer> numbers = new ArrayList<>();

	public Guess() {
	}

	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
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
		return "" + numbers;
	}

}
