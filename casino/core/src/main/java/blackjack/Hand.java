package blackjack;

public class Hand extends Deck {
    private boolean isActive = true;
    private boolean isValid = true;


    public Hand() {

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
