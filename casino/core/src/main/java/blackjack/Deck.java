package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Deck is a blueprint for how a deck should act. This will be a deck of cards
 */
public class Deck {


  private List<Card> deck;
  private final char[] validSuits = new char[]{'S', 'H', 'C', 'D'};

  /**
   * Creates an empty deck.
   */
  public Deck() {
    this.deck = new ArrayList<>();
  }


  /**
   * This generates a playing deck of 52 cards, and shuffles the deck.
   *
   * @param i is the amount of cards that should be generated in a deck.
   */
  public Deck(int i) {
    generateDeck(i);
    Collections.shuffle(deck);
  }

  private void generateDeck(int i) {
    if (i != 13) {
      throw new IllegalStateException("Must be 13 of each suit");
    }
    List<Card> deck = new ArrayList<>();
    for (char suit : validSuits) {
      for (int j = 1; j <= i; j++) {
        deck.add(new Card(j, suit));
      }
    }
    this.deck = deck;
  }

  public List<Card> getDeck() {
    return deck;
  }

  /**
   * If there are cards in the deck, return the top card.
   *
   * @return the top card of the deck.
   */
  public Card popTopCard() throws IllegalStateException {
    if (this.getDeck().size() == 0) {
      throw new IllegalStateException("Deck is empty");
    }
    Card card = deck.get(0);
    deck.remove(card);
    return card;
  }

  /**
   * Returns the sum of the deck.
   *
   * @return the sum of the deck (based on the cardValue of each card in the deck).
   */
  public int getSumOfDeck() {
    int cardSum = 0;
    for (Card card : this.getDeck()) {
      cardSum += card.getCardValue();
    }
    return cardSum;
  }

  /**
   * Returns the last card in the deck.
   *
   * @return the last card in the deck.
   */
  public Card getLastCard() {
    return this.getDeck().get(this.getDeck().size() - 1);
  }


}
