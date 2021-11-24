package blackjack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;
import blackjack.Hand;
import blackjack.Deck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackTest {

    private User user;
    private Blackjack blackjack;
    // Tests use cards from this deck instead of creating new card objects for every test
    private Deck cardDeck = new Deck(13);
    private Deck testDeck = new Deck();


    void naturalBlackjackWinDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("10D"),
                selectCard("2C"),
                selectCard("1C"),
                selectCard("3C")));
    }

    void naturalBlackjackTieDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("10D"),
                selectCard("10C"),
                selectCard("1C"),
                selectCard("1H")));
    }

    void setSplittableDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("5D"),
                selectCard("6C"),
                selectCard("5C"),
                selectCard("7H")));
    }

    void setInsplittableDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("5D"),
                selectCard("6C"),
                selectCard("4C"),
                selectCard("7H")));
    }

    void hitOverLimitDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("9D"),
                selectCard("6C"),
                selectCard("9C"),
                selectCard("5H"),
                selectCard("8S")));
    }

    void hitUnderLimitDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("2D"),
                selectCard("6C"),
                selectCard("4C"),
                selectCard("5H"),
                selectCard("3S")));
    }

    void standWinDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("10D"),
                selectCard("10C"),
                selectCard("9C"),
                selectCard("8H"),
                selectCard("2S")));
    }

    void standLoseDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("2D"),
                selectCard("10C"),
                selectCard("3C"),
                selectCard("8H"),
                selectCard("2S")));
    }

    void splitWinOneDeck(){
        splittableDeck();
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("8D"),
                selectCard("8C"),
                selectCard("10C"),
                selectCard("10H"),
                selectCard("10D")));
    }

    void splitLose(){
        splittableDeck();
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("10D"),
                selectCard("10S"),
                selectCard("10C"),
                selectCard("10H"),
                selectCard("8D")));
    }

    void splitWinBothDeck(){
        splittableDeck();
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("8D"),
                selectCard("8C"),
                selectCard("8S"),
                selectCard("8H"),
                selectCard("10D")));
    }

    @BeforeEach
    void setUp() {
        user = new User("BlackjackTestUser", 1000);
        blackjack = new Blackjack(user);
    }


    @Test
    void startGame() {
        assertThrows(IllegalArgumentException.class, () ->{
            blackjack.startGame(-1);
        });
        blackjack.startGame(10);
        Assertions.assertEquals(blackjack.getBet(), 10);
        Assertions.assertEquals(blackjack.getDealingDeck().getDeck().size(),48);
        Assertions.assertTrue(blackjack.getPlayersHand1().isActive());
        Assertions.assertFalse(blackjack.getPlayersHand2().isActive());


//        if (blackjack.isInstantBlackjack()){
//            if (blackjack.getDealersHand().getSumOfDeck() == 21){
//                assertEquals(blackjack.getPayout(), blackjack.getBet());
//            }
//            else{
//                assertEquals(blackjack.getPayout(), blackjack.getBet()*1.5);
//            }
//        }
//        else {
//            Assertions.assertEquals(blackjack.getPayout(), 0);
//        }
//        Assertions.assertEquals(blackjack.getDealersHand().getDeck().size(),2);
//        Assertions.assertEquals(blackjack.getTargetHand(),blackjack.getPlayersHand1());
    }

    @Test
    void naturalBlackjackWin(){
        naturalBlackjackWinDeck();
        blackjack.setDealingDeck(testDeck);
        blackjack.startGame(10);
        assertTrue(blackjack.isInstantBlackjack());
        assertFalse(blackjack.getDealersHand().isActive());
        assertEquals(blackjack.getPayout(), blackjack.getBet()*1.5);
        assertEquals(blackjack.getUser().getBalance(), 1005);
    }

    @Test
    void naturalBlackjackTie(){
        naturalBlackjackTieDeck();
        blackjack.setDealingDeck(testDeck);
        blackjack.startGame(10);
        assertTrue(blackjack.isInstantBlackjack());
        assertFalse(blackjack.getDealersHand().isActive());
        assertEquals(blackjack.getPayout(), blackjack.getBet());
        assertEquals(blackjack.getUser().getBalance(),1000);
    }

    @Test
    void splittableDeck(){
        setSplittableDeck();
        blackjack.setDealingDeck(testDeck);
        blackjack.startGame(10);
        assertTrue(blackjack.canSplit());
        assertEquals(blackjack.getTargetHand(), blackjack.getPlayersHand1());
    }

    @Test
    void hitOverLimit(){
        hitOverLimitDeck();
        blackjack.setDealingDeck(testDeck);
        blackjack.startGame(10);
        blackjack.hit();
        assertFalse(blackjack.getTargetHand().isActive());
        assertFalse(blackjack.canSplit());
        assertEquals(3, blackjack.getTargetHand().getDeck().size());
    }

//    @Test
//    void hitUnderLimit(){
//        hitUnderLimitDeck();
//        blackjack.setDealingDeck(testDeck);
//
//    }








    //todo; test hit on inactive deck




//    @Test
//    void hit() {
//        blackjack.startGame(10);
//        int handSize = blackjack.getTargetHand().getDeck().size();
//        Card cardToAdd = blackjack.getDealingDeck().getDeck().get(0);
//        blackjack.hit();
//        assertFalse(blackjack.isCanSplit());
//        Assertions.assertEquals(blackjack.getTargetHand().getDeck().size(),handSize+1);
//        Assertions.assertEquals(blackjack.getTargetHand().getLastCard(), cardToAdd);
//        if (blackjack.getTargetHand().getSumOfDeck() > 21){
//            Assertions.assertFalse(blackjack.getTargetHand().isActive());
//            blackjack.stand();
//            Assertions.assertThrows(IllegalArgumentException.class, () -> {
//                blackjack.hit();
//            });
//        }
//        else {
//            Assertions.assertTrue(blackjack.getTargetHand().isActive());
//        }
//
//    }
//
//    @Test
//    void stand() {
//        blackjack.startGame(10);
//        blackjack.stand();
//        Assertions.assertFalse(blackjack.getTargetHand().isActive());
//    }
//
//    @Test
//    void split() {
//        blackjack.startGame(10);
//
//        Hand splittableHand = new Hand();
//        splittableHand.getDeck().add(new Card(5,'C'));
//        splittableHand.getDeck().add(new Card(5,'H'));
//        Hand insplittableHand = new Hand();
//        insplittableHand.getDeck().add(new Card(5,'C'));
//        insplittableHand.getDeck().add(new Card(6,'H'));
//        double userBalance = blackjack.getUser().getBalance();
//
//        blackjack.setPlayersHand1(insplittableHand);
//        blackjack.split();
//        Assertions.assertEquals(userBalance, user.getBalance()); // If this is true, the rest of the method hasn't been called
//        blackjack.setPlayersHand1(splittableHand);
//        blackjack.split();
//        assertEquals(blackjack.getUser().getBalance(), userBalance - blackjack.getBet());
//        assertEquals(blackjack.getPlayersHand1().getDeck().size(), 1);
//        assertEquals(blackjack.getPlayersHand2().getDeck().size(),1);
//        assertTrue(blackjack.getPlayersHand2().isActive());
//        assertEquals(blackjack.getTargetHand(), blackjack.getTargetHand());
//        assertTrue(blackjack.isHasSplit());
//
//    }
//
//    @Test
//    void toggleTargetHand() {
//        blackjack.startGame(10);
//        blackjack.toggleTargetHand();
//        assertEquals(blackjack.getTargetHand(), blackjack.getPlayersHand2());
//        blackjack.toggleTargetHand();
//        assertEquals(blackjack.getTargetHand(), blackjack.getPlayersHand1());
//    }
//
//    @Test
//    void dealerPlay() {
//        blackjack.startGame(10);
//        blackjack.dealerPlay();
//        Assertions.assertTrue(blackjack.getDealersHand().getSumOfDeck() >= 17);
//        Assertions.assertFalse(blackjack.getDealersHand().isActive());
//        if (blackjack.getDealersHand().getSumOfDeck() == 21){
//            assertEquals(0, blackjack.getPayout());
//        }
//        if (blackjack.getPlayersHand2().getSumOfDeck()!=0){
//            assertTrue(blackjack.getPayout()-blackjack.getBet() != blackjack.getPayout());
//        }
//    }
//
//
//    void gameSetup(){
//        blackjack.setBet(10);
//        blackjack.fieldSetup();
//    }
//
//
//    void dealerCardSetup(Card card1, Card card2){
//        Hand hand = new Hand();
//        hand.getDeck().addAll(Arrays.asList(card1,card2));
//        blackjack.setDealersHand(hand);
//    }
//
//    void playerCardSetup(Card card1, Card card2){
//        Hand hand = new Hand();
//        hand.getDeck().addAll(Arrays.asList(card1,card2));
//        blackjack.setPlayersHand1(hand);
//    }

    private Card selectCard(String name){
        return cardDeck.getDeck().stream().filter(
                card -> card.getCardName().equals(name))
                .collect(Collectors.toList()).get(0);
    }


}