package blackjack;

import user.User;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Blackjack {
//todo; add field on view for balance
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
    private double payout;

    public Blackjack(User user) {
        this.user = user;
    }


    public void startGame(double bet){
        setBet(bet);
        this.dealingDeck = new Deck(13);

        this.playersHand1 = new Hand();
        this.playersHand2 = new Hand();
        this.playersHand2.setActive(false);
        this.playerHands.add(playersHand1);
        this.playerHands.add(playersHand2);
        this.payout = 0;
        this.dealersHand = new Hand();


        deal();
        setCanSplit();
        this.targetHand = playersHand1;
    }

    /**
     * Sets the bet of the user.
     *
     * @param bet is the amount that the user is betting.
     */

    public void setBet(double bet) {
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
        canSplit=false;
        Card cardToAdd = dealingDeck.popTopCard();
        targetHand.getDeck().add(cardToAdd);
        int sumOfDeck = sumOfDeck(targetHand);
        if (sumOfDeck > 21){
            this.targetHand.setActive(false);
        }

    }


    public void stand(){
        this.targetHand.setActive(false);
        if (isPlayerDone()){
            dealerPlay();
        }
    }

    public void split() {
        this.splitBet = bet;
        user.setBalance(user.getBalance()-splitBet);
        Card card = playersHand1.getLastCard();
        this.playersHand1.getDeck().remove(card);
        this.playersHand2.getDeck().add(card);
        playersHand2.setActive(true);
        setTargetHand(playersHand1);
        hasSplit = true;
    }

    public double calculateWinnings(){
        double payout = 0;
        int dealerSum = sumOfDeck(dealersHand);
        if (dealerSum == 21){
            return 0;
        }
        else if (dealerSum > 21){
            dealerSum = 0;
        }
        double hand1Score = calculateHand(playersHand1, dealerSum);
        double hand2Score = 0;
        if (playersHand2.getSumOfDeck()!=0){
            hand2Score = calculateHand(playersHand2, dealerSum);
        }
        payout = hand1Score + hand2Score;
        this.payout = payout;
        return payout;
    }

    private double calculateHand(Hand hand, double dealerSum) {

        if (hand.getSumOfDeck() == 0 || hand.getSumOfDeck() > 21){
            return 0;
        }
        if (hand.getSumOfDeck() <= 21 && hand.getSumOfDeck() > dealerSum)
            return bet*2;

        return 0;
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
        if (!(dealersHand.getSumOfDeck() == 21)){
            while(dealersHand.getSumOfDeck() < 17){
                dealersHand.getDeck().add(dealingDeck.popTopCard());
            }
        }
        dealersHand.setActive(false);
        double payout = calculateWinnings();
        user.addMoney(payout);
    }

    public double getPayout() {
        return payout;
    }

    public int sumOfDeck(Hand hand){
        int result = 0;
        for (Card card : hand.getDeck()){
            result += card.getCardValue();
        }
        return result;
    }


//    public static void main(String[] args) {
//        User user = new User("Seb", 10000);
//        Blackjack blackjack = new Blackjack(user);
//        blackjack.startGame(10);
//        System.out.println("---Player Hand---");
//        for (Card card : blackjack.getPlayersHand1().getDeck()){
//            System.out.println(card.getCardImage());
//        }
//        System.out.println(blackjack.getPlayersHand1().getSumOfDeck());
//        System.out.println("---------");
//        System.out.println("---Dealers Hand---");
//        for (Card card : blackjack.getDealersHand().getDeck()){
//            System.out.println(card.getCardImage());
//        }
//        System.out.println(blackjack.getDealersHand().getSumOfDeck());
//        System.out.println("---------");
//        System.out.println("---NEXT STAGE: HIT---");
//        System.out.println("---------");
//        blackjack.hit();
//        System.out.println("---Players Hand---");
//        for (Card card : blackjack.getPlayersHand1().getDeck()){
//            System.out.println(card.getCardImage());
//        }
//        System.out.println(blackjack.getPlayersHand1().getSumOfDeck());
//        System.out.println("---------");
//        System.out.println("---Dealers Hand---");
//        for (Card card : blackjack.getDealersHand().getDeck()){
//            System.out.println(card.getCardImage());
//        }
//
//    }

}
