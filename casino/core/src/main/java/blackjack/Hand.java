package blackjack;

/**
 * A hand is a deck that can be active or inactive.
 * If active, it can be played on, if not, it can't
 */
public class Hand extends Deck {
  private boolean isActive = true;

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
