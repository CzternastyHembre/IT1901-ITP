package blackjack;



public class Card {

    private int faceValue;
    private char suit;
    private int cardValue;
    private final char[] validSuits = new char[] {'S', 'H', 'C', 'D'};
    private String cardString;
    private String backCard;
    private String cardImage;


    public Card(int faceValue, char suit) {
        if (faceValue > 13 || faceValue < 1) {
            throw new IllegalStateException("Must be an number between 1 and 13");
        }
        this.faceValue = faceValue;

        if (new String(validSuits).indexOf(suit) == -1) {
            throw new IllegalStateException("Suit must be either S, H, C, D");
        }

        this.cardString = "" + faceValue + suit;
        this.cardImage = "" + faceValue + suit + ".jpg";
        this.backCard = "backOfCard.jpg";
        setCardValue(faceValue);
    }

    public int getFaceValue() {
        return faceValue;
    }

    public char getSuit() {
        return suit;
    }

    public int getCardValue() {
        return cardValue;
    }

    public void setCardValue(int faceValue) {
        if (faceValue < 10 && faceValue != 1) {
            this.cardValue = faceValue;
        }
        if (faceValue > 9) {
            this.cardValue = 10;
        }
        if (faceValue == 1){
            this.cardValue = 11;
        }
    }

    public String getCardString() {
        return cardString;
    }

    public String getBackCard() {
        return backCard;
    }

    public String getCardImage() {
        return cardImage;
    }
}

