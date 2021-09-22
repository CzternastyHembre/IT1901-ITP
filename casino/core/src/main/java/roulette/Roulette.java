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

	/**
	 * Rolls the board and calculates the amount of money won
	 * 
	 * @return The amount of money won
	 */
	public double calculate() {
		rollNumber();
//		this.rolledNumber = 5;
		System.out.println("RolledNumber = " + this.rolledNumber + "\n");

		double winnings = 0;

		for (Guess guess : guesses) {
			if (guess.isWin(rolledNumber)) {
				System.out.println("" + guess.getClass().getSimpleName() + " = " + guess + " "
						+ (guess.amount * RoulettSize / guess.getPossibleWins()));

				winnings += guess.amount * RoulettSize / guess.getPossibleWins();
			} else {
				System.err.println("" + guess.getClass().getSimpleName() + " = " + guess + " 0 ");
			}
		}

		user.addMoney(winnings);
		guesses.clear();
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
	
	public void cleacGuess() {
		guesses.remove(guesses.size());
	}


	public static void main(String[] args) {


//		u.addGuess(new PatternGuess(100, 1, 2));
//		u.addGuess(new NumberGuess(100, 2, 5));
//		u.addGuess(new ListGuess(50, 2, 5));
	}
}
