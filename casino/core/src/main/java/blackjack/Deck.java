package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {


    private List<Card> deck = new ArrayList<>();
    private final char[] validSuits = new char[] {'S', 'H', 'C', 'D'};

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
        Card card = deck.get(0);
        deck.remove(card);
        return card;
    }

    public int getSumOfDeck() {
        int sum = 0;
        for (Card card : this.getDeck()) {
            sum += card.getCardValue();
        }
        return sum;
    }

    public Card getLastCard(){
        return this.getDeck().get(this.getDeck().size() - 1);
    }


}
