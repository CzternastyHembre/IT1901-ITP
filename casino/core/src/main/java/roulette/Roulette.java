package roulette;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import user.User;

/**
 * Roulette class for handling the rouletteGame and logic.
 */

public class Roulette {

  public static final int rouletteSize = 36;
  private Random rand = new Random();
  private User user;
  private List<Guess> guesses = new ArrayList<>();
  private int rolledNumber;

  public Roulette(User user) {
    this.user = user;
  }

  public void rollNumber() {
    rolledNumber = rand.nextInt(37);
  }

  public int getRolledNumber() {
    return rolledNumber;
  }

  protected void setRolledNumber(int rolledNumber) {
    this.rolledNumber = rolledNumber;
  }

  /**
   * Rolls the number and calculates the guess winnings based on the new rolled number.
   * Adds the winnings into the {@link User} balance field.
   *
   * @return The amount of money won.
   *
   */
  
  public double calculate() {

    double winnings = calculateGuessWinnings();
    user.addMoney(winnings);
    guesses.clear();

    return winnings;
  }

  /**
   * Calculates the winnings for all the {@link ArrayList}
   * <b> guesses </b> containing {@link Guess}.
   *
   *
   * @return the amount of money you win based on the {@code rolledNumber}.
   *
   */
  
  public double calculateGuessWinnings() {
    double winnings = 0;

    for (Guess guess : guesses) {
      if (guess.isWin(rolledNumber)) {
        //The odds in Roulette is calculated by {the amount of numbers guess on}
        //{the amount of possible outcomes the numbers can be (excluding 0)}.
        winnings += guess.amount * rouletteSize / guess.getPossibleWins();
      }
    }
    return winnings;

  }

  /**
   * Calculates the sum of all the guesses.
   *
   * @return The sum of all the {@link Guess} amounts.
   */
  
  public double getSumOfBets() {
    if (guesses.size() == 0) {
      return 0;
    }
    return guesses.stream().mapToDouble(Guess::getAmount).sum();
  }

  /**
   * Adds a guess in {@code guesses} and withdraws the amount of the guess in {@link User}.
   *
   * @param guess The Guess thats is take in.
   *
   */
  
  public void addGuess(Guess guess) {
    user.withdraw(guess.getAmount());
    guesses.add(guess);
  }

  /**
   * Removes the {@link Guess} at the end of the {@code guesses}.
   * 
   * @throws IllegalArgumentException if there are no {@link Guess} in {@code guesses}.
   *
   *
   * @throws if there are no {@link Guess} in {@code guesses}.
   *
   */

  public void undoGuess() throws IllegalArgumentException{
    if (guesses.size() == 0) {
      throw new IllegalArgumentException("No guesses to undo");
    }
    Guess lastGuess = guesses.get(guesses.size() - 1);
    user.addMoney(lastGuess.getAmount());
    guesses.remove(guesses.size() - 1);
  }

  public void clearGuesses() {
    guesses.clear();
  }


}
