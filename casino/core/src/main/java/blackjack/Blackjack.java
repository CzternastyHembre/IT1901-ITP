package blackjack;

import user.User;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Blackjack {
// todo; Bets with decimals.
    private double bet;
    private double splitBet;
    private User user;

    private Deck dealingDeck;
    private Hand dealersHand;
    private ArrayList<Hand> playerHands = new ArrayList<>();
    private Hand playersHand1;
    private Hand playersHand2;
    private boolean canSplit;
    private Hand targetHand;
    private boolean hasSplit;



    public Blackjack(User user) {
        this.user = user;
    }

    public void startGame(int bet){
        setBet(bet);
        this.dealingDeck = new Deck(13);

        this.playersHand1 = new Hand();
        this.playersHand2 = new Hand();
        this.playerHands.add(playersHand1);
        this.playerHands.add(playersHand2);

        this.dealersHand = new Hand();


        deal();
        setCanSplit();
    }

    /**
     * Sets the bet of the user.
     *
     * @param bet is the amount that the user is betting.
     */

    public void setBet(int bet) {
        if (bet <= 0 || bet > this.user.getBalance()) {
            throw new IllegalArgumentException("Invalid bet");
        }
        this.bet = bet;
        this.user.setBalance(this.user.getBalance() - this.bet);
    }

    private void setCanSplit() {
        this.canSplit = playersHand1.getDeck().get(0).getCardValue() == playersHand1.getDeck().get(1).getCardValue()
                && (this.bet * 2) <= user.getBalance();
    }

    public void deal(){
        for (int i = 0; i<2; i++){
            playersHand1.getDeck().add(dealingDeck.popTopCard());
            dealersHand.getDeck().add(dealingDeck.popTopCard());
        }
        //todo; find way to calculate at beginning
    }





    public void hit() throws IllegalArgumentException {
        if (!targetHand.isActive()) {
            throw new IllegalArgumentException("Deck is inactive, cannot hit");
        }
        Card cardToAdd = dealingDeck.popTopCard();
        targetHand.getDeck().add(cardToAdd);
        int sumOfDeck = deckSum(targetHand);
        if (sumOfDeck >= 21){
            targetHand.setActive(false);
        }
    }

    private int deckSum(Deck targetDeck) {
        return targetDeck.getSumOfDeck();
    }

    public void stand(Hand targetHand){
        targetHand.setActive(false);
    }

    public void split() {
        this.splitBet = bet;
        user.setBalance(user.getBalance()-splitBet);
        Card card = playersHand1.getLastCard();
        this.playersHand1.getDeck().remove(card);
        this.playersHand2.getDeck().add(card);
        playersHand2.setActive(true);
        hasSplit = true;
    }

    public double calculateWinnings(){
        double payout = 0;
        int hand1Sum = playersHand1.getSumOfDeck();
        int hand2Sum = playersHand1.getSumOfDeck();
        int dealerSum = dealersHand.getSumOfDeck();
        if (!playersHand2.isActive() && !playersHand1.isActive() && !dealersHand.isActive()){
            if (dealerSum == 21){
                return payout;
            }
            if (hand1Sum<=21 && hand1Sum > dealerSum){
                payout += bet*2;
            }
            if (hand2Sum<21 && hand2Sum > dealerSum){
                payout += splitBet*2;
            }
        }
        return payout;
    }



    public void addCardToDeck(Deck deck, Deck activeDeck) {
        activeDeck.getDeck().add(deck.popTopCard());
    }


    public Deck getDealingDeck() {
        return dealingDeck;
    }

    public Deck getDealersHand() {
        return dealersHand;
    }

    public Deck getPlayersHand1() {
        return playersHand1;
    }

    public Deck getPlayersHand2() {return playersHand2;}


    public double getBet() {
        return bet;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Hand> getPlayerHands() {
        return playerHands;
    }

    public boolean canSplit() {
        return canSplit;
    }

    public void toggleTargetHand(){
        setTargetHand(playerHands.stream().filter(
                hand -> !hand.equals(targetHand)).collect(Collectors.toList()).get(0));
    }

    public void setTargetHand(Hand hand){
        this.targetHand = hand;
    }

    public Hand getTargetHand() {
        return targetHand;
    }

    public boolean hasSplit() {
        return hasSplit;
    }

    public boolean isPlayerDone(){
        return !playersHand1.isActive() && !playersHand2.isActive();
    }

    public void dealerPlay() {
        while(dealersHand.getSumOfDeck() < 17){
            dealersHand.getDeck().add(dealingDeck.popTopCard());
        }
        dealersHand.setActive(false);
        double payout = calculateWinnings();
        user.addMoney(payout);
    }
}
