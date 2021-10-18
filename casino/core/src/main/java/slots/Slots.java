package slots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import user.User;
import validators.SlotsValidator;


/**
 * Slots class for the logic of the slots game.
 */

public class Slots {

  // Fields
  private List<String> symbols = new ArrayList<>();
  private int spins;
  private int netGain;
  private int bet;
  private final Random random;
  private double currentWinnings;
  private String combo;
  private double averagePayout;
  private User user;
  private final String[] validSuits = new String[] { "S", "D", "H", "C" };

  /**
   * Constructor that sets the spins, netgain and bet to 0.
   * Adds the symbols and creates a user with a random object.
   *
   * @param user takes int the user and sets the user.
   */

  public Slots(User user) {
    this.spins = 0;
    this.netGain = 0;
    this.bet = 0;
    for (int i = 0; i < 3; i++) {
      symbols.add("");
    }
    this.random = new Random();
    this.user = user;
  }

  /**
   * Runs the game with the seperate methods that builds the game.
   *
   * @param bet takes in the users bet.
   */

  public void play(int bet) {
    setBet(bet);
    withdrawMoney();
    spin();
    updateWinnings();
  }


  /**
   * Update the symbols array with the new symbols.
   *
   *
   */

  public void spin() {
    for (int i = 0; i < symbols.size(); i++) {
      symbols.set(i, generateSymbol());
    }
    spins++;
  }


  /**
   * Updates fields that deal with money. Netgain and the user's balance are updated
   *
   *
   */
  private void withdrawMoney() {
    this.netGain -= getBet();
    this.user.setBalance(this.user.getBalance() - getBet());
  }

  private String generateSymbol() {
    return random.nextInt(10) + 1 + validSuits[random.nextInt(validSuits.length)];
  }

  /**
   * Uses the class SlotsValidator to calculate the winnings of the user.
   *
   * @return 0 if the user did not win or the bet times the given combination.
   */

  public double calculateWinnings() {

    if (SlotsValidator.isDevil(symbols)) {
      this.combo = "DEVIL";
      return 0;
    }

    if (SlotsValidator.isSuperJackpot(symbols)) {
      this.combo = "SUPER JACKPOT";
      return getBet() * 500;
    }

    if (SlotsValidator.isSuperPerfectStraight(symbols)) {
      this.combo = "SUPER PERFECT STRAIGHT";
      return getBet() * 400;
    }

    if (SlotsValidator.isJackpot(symbols)) {
      this.combo = "JACKPOT";
      return getBet() * 16;
    }

    if (SlotsValidator.isPerfectStraight(symbols)) {
      this.combo = "PERFECT STRAIGHT";
      return getBet() * 2.25;
    }
    if (SlotsValidator.isStraight(symbols)) {
      this.combo = "STRAIGHT";
      return getBet() * 1.65;
    }

    if (SlotsValidator.isFlush(symbols)) {
      this.combo = "FLUSH";
      return getBet() * 1.35;
    }

    if (SlotsValidator.isPair(symbols)) {
      this.combo = "PAIR";
      return getBet() * 1.25;
    } else {
      this.combo = "LOSS";
      return 0;
    }
  }

  /**
   * Updates the winnings of the user during the session.
   */

  private void updateWinnings() {
    var winnings = calculateWinnings();
    this.currentWinnings = winnings;
    netGain += winnings;
    this.user.setBalance(this.user.getBalance() + winnings);
    calculateAveragePayout();
  }

  private void calculateAveragePayout() {
    this.averagePayout = (double) netGain / spins;
  }

  /**
   * Getters and setters for the variables.
   */

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }

  public double getAveragePayout() {
    return averagePayout;
  }

  public double getUserBalance() {
    return this.user.getBalance();
  }

  public int getSpins() {
    return spins;
  }

  public int getNetGain() {
    return netGain;
  }

  public int getBet() {
    return bet;
  }

  /**
   * Sets the bet of the user.
   *
   * @param bet is the amount that the user is betting.
   */

  public void setBet(int bet) {
    if (bet <= 0 || bet > this.getUserBalance()) {
      throw new IllegalArgumentException("Invalid bet");
    }
    this.bet = bet;
  }

  public double getCurrentWinnings() {
    return currentWinnings;
  }

  public String getCombo() {
    return combo;
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public User getUser() {
    return this.user;
  }
}
