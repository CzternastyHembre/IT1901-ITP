package blackjack;

import java.util.Arrays;


/**
 * This is a blueprint of how a card object should act.
 */
public class Card {

  private int cardValue;
  private final String cardImage;
  private final String cardName;


  /**
   * This constructor creates a card object and sets the cardImage and the cardValue.
   *
   * @param faceValue the face value of the card being created.
   * @param suit the suit of the card being created.
   * @throws IllegalArgumentException if the card has an illegal face value or suit.
   */
  public Card(int faceValue, char suit) throws IllegalArgumentException {
    if (faceValue > 13 || faceValue < 1) {
      throw new IllegalStateException("Must be an number between 1 and 13");
    }

    String suitString = Character.toString(suit);

    if (Arrays.stream(CardColor.values()).noneMatch(
            element -> element.toString().equals(suitString))) {
      throw new IllegalStateException("Suit must be either S, H, C, D");
    }
    this.cardName = "" + faceValue + suit;
    this.cardImage = cardName + ".jpg";
    setCardValue(faceValue);
  }

  public int getCardValue() {
    return cardValue;
  }

  /**
   * Set the card value of the card. Jack, queen and king have values of 10,
   * aces have a value of 11.
   *
   * @param faceValue the face value of the card
   */
  private void setCardValue(int faceValue) {
    if (faceValue < 10 && faceValue != 1) {
      this.cardValue = faceValue;
    }
    if (faceValue > 9) {
      this.cardValue = 10;
    }
    if (faceValue == 1) {
      this.cardValue = 11;
    }
  }

  public String getCardImage() {
    return cardImage;
  }

  public String getCardName() {
    return cardName;
  }
}

