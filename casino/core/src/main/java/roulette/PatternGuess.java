package roulette;

public class PatternGuess extends Guess {

  public PatternGuess(double amount, int start, int increment) {
    super(amount);
    for (int i = start; i <= Roulette.rouletteSize; i += increment) {
      numbers.add(i);
    }
  }
}
