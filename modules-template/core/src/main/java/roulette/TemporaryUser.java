package roulette;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author matti Only a user for testing and temporary moneyhandling
 */
public class TemporaryUser {

	private double balance;
	private List<Guess> guesses = new ArrayList<>();

	public TemporaryUser(int balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void addBalance(double delta) {
		this.balance += delta;
	}

	public void withdraw(double delta) {
		if (delta > balance) {
			throw new IllegalStateException("You dont have enough money");
		}
		this.balance -= delta;
	}

	public double getSumOfBets() {
		if (guesses.size() == 0) {
			return 0;
		}
		double betts = 0;
		for (Guess guess : guesses) {
			betts += guess.getAmount();
			System.out.println(guess.getAmount());
		}
		System.out.println(betts + " " + guesses.stream().mapToDouble(guess -> guess.getAmount()).sum());
		return betts;
	}

	
	public void addGuess(Guess guess) {
		this.withdraw(guess.getAmount());
		guesses.add(guess);
	}

	public List<Guess> getGuesses() {
		return guesses;
	}

}
