package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {


    private List<Card> deck = new ArrayList<>();
    private final char[] validSuits = new char[] {'S', 'H', 'C', 'D'};
    private int sumOfDeck = 0;

    public Deck() {
        this.deck = new ArrayList<>();
    }

    public Deck(int i) {
        generateDeck(i);
        Collections.shuffle(deck);
    }

    public void generateDeck(int i) {
        if (i != 13) {
            throw new IllegalStateException("Must be 13 of each suit");
        }

        for (char suit : validSuits) {
            for (int j = 1; j <= i; j++) {
                deck.add(new Card(j, suit));
            }
        }
    }

    public List<Card> getDeck() {
        return deck;
    }

    public Card popTopCard(){
        if (this.getDeck().size() == 0) {
            throw new IllegalStateException("Deck is empty");
        }
        return this.deck.remove(0);
    }

    public int getSumOfDeck() {
        for (Card card : this.getDeck()) {
            sumOfDeck+= card.getCardValue();
        }
        return sumOfDeck;
    }

    public Card getLastCard(){
        return this.getDeck().get(this.getDeck().size() - 1);
    }


}
