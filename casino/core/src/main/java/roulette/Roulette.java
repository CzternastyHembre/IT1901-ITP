package roulette;

import java.util.Random;

import user.User;

import java.util.ArrayList;
import java.util.List;

public class Roulette {

	private Random rand = new Random();
	public static final int RoulettSize = 36;
	private User user;
	private List<Guess> guesses = new ArrayList<>();

	public Roulette(User user) {
		this.user = user;
	}

	private int rolledNumber;

	private void rollNumber() {
		rolledNumber = rand.nextInt(37);
	}

	public int getRolledNumber() {
		return rolledNumber;
	}

	public void setRolledNumber(int rolledNumber) {
		this.rolledNumber = rolledNumber;
	}

	/**
	 * Rolls the board and calculates the amount of money won
	 * 
	 * @return The amount of money won
	 */
	public double calculate() {
		rollNumber();

		double winnings = calcuteGuessWinnings();
		user.addMoney(winnings);
		guesses.clear();
		return winnings;
	}

	public double calcuteGuessWinnings() {
		double winnings = 0;

		for (Guess guess : guesses) {
			if (guess.isWin(rolledNumber)) {
				winnings += guess.amount * RoulettSize / guess.getPossibleWins();
			}
		}
		return winnings;

	}
	
	public double getSumOfBets() {
		if (guesses.size() == 0) {
			return 0;
		}
		return guesses.stream().mapToDouble(guess -> guess.getAmount()).sum();
	}
	
	public void addGuess(Guess guess) {
		user.withdraw(guess.getAmount());
		guesses.add(guess);
	}
	
	public void undoGuess() {
		Guess lastGuess = guesses.get(guesses.size() - 1);
		user.addMoney(lastGuess.getAmount());
		guesses.remove(guesses.size() - 1);
	}
	
	public void cleacGuess() {
		guesses.remove(guesses.size());
	}

}
