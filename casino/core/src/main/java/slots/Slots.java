package slots;

import user.User;
import validators.SlotsValidator;

import java.io.IOException;
import java.util.*;

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

  // Constructor

  // MAIN CONSTRUCTOR FOR FINAL PRODUCT
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

  public void play(int bet) {
    setBet(bet);
    withdrawMoney();
    spin();
    updateWinnings();
  }

  public void spin() {
    for (int i = 0; i < symbols.size(); i++) {
      symbols.set(i, generateSymbol());
    }
    spins++;
  }

  private void withdrawMoney() {
    this.netGain -= getBet();
    this.user.setBalance(this.user.getBalance() - getBet());
  }

  private String generateSymbol() {
    return random.nextInt(10) + 1 + validSuits[random.nextInt(validSuits.length)]; // Generate a random number between
                                                                                   // 1-9, and add a random suit
  }

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

  // Getters and Setters

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

  public void setBet(int bet) {
    if (bet <= 0 || bet > this.getUserBalance())
      throw new IllegalArgumentException("Invalid bet");
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
