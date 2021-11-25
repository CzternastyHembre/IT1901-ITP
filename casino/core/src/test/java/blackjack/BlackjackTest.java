package blackjack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

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
                selectCard("7H"),
                selectCard("5H")));
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
        setSplittableDeck();
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("8D"),
                selectCard("8C"),
                selectCard("10C"),
                selectCard("10H"),
                selectCard("10D")));
    }

    void splitLose(){
        setSplittableDeck();
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("10D"),
                selectCard("10S"),
                selectCard("10C"),
                selectCard("10H"),
                selectCard("8D")));
    }

    void splitWinBothDeck(){
        setSplittableDeck();
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("8D"),
                selectCard("8C"),
                selectCard("8S"),
                selectCard("8H"),
                selectCard("10D")));
    }

    void unHittableDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("10D"),
                selectCard("10S"),
                selectCard("10C"),
                selectCard("10H"),
                selectCard("8D"),
                selectCard("4H")));
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
        assertTrue(blackjack.isCanSplit());
        assertEquals(blackjack.getTargetHand(), blackjack.getPlayersHand1());
    }

    @Test
    void hitOverLimit(){
        hitOverLimitDeck();
        blackjack.setDealingDeck(testDeck);
        blackjack.startGame(10);
        blackjack.hit();
        assertFalse(blackjack.getTargetHand().isActive());
        assertFalse(blackjack.isCanSplit());
        assertEquals(3, blackjack.getTargetHand().getDeck().size());
    }

    @Test
    void hitUnderLimit(){
        hitUnderLimitDeck();
        blackjack.setDealingDeck(testDeck);
        blackjack.startGame(10);
        blackjack.hit();
        assertTrue(blackjack.getTargetHand().isActive());
        assertFalse(blackjack.isCanSplit());
        assertEquals(3, blackjack.getTargetHand().getDeck().size());
    }

    @Test
    void splitTest(){
        setSplittableDeck();
        blackjack.setDealingDeck(testDeck);
        blackjack.startGame(10);
        double prevBalance = blackjack.getUser().getBalance();
        blackjack.split();
        assertEquals(blackjack.getUser().getBalance(), prevBalance - blackjack.getBet());
        assertTrue(blackjack.getPlayersHand2().isActive());
        assertTrue(blackjack.isHasSplit());
        assertFalse(blackjack.isCanSplit());
        assertEquals(1, blackjack.getPlayersHand1().getDeck().size());
        assertEquals(1, blackjack.getPlayersHand2().getDeck().size());
    }

    @Test
    void toggleTest(){
        setSplittableDeck();
        blackjack.setDealingDeck(testDeck);
        blackjack.startGame(10);
        blackjack.split();
        blackjack.toggleTargetHand();
        assertEquals(blackjack.getTargetHand(), blackjack.getPlayersHand2());
        assertNotEquals(blackjack.getTargetHand(), blackjack.getPlayersHand1());
    }

    @Test
    void standAfterSplit(){
        setSplittableDeck();
        blackjack.setDealingDeck(testDeck);
        blackjack.startGame(10);
        blackjack.split();
        blackjack.stand();
        assertFalse(blackjack.getPlayersHand1().isActive());
        blackjack.toggleTargetHand();
        blackjack.stand();
        assertFalse(blackjack.getPlayersHand2().isActive());
    }

    @Test
    void stand(){
        standWinDeck();
        blackjack.setDealingDeck(testDeck);
        blackjack.startGame(10);
        double prevBalance = blackjack.getUser().getBalance();
        blackjack.stand();
        assertEquals(blackjack.getUser().getBalance(), prevBalance + blackjack.getPayout());
    }

    @Test
    void unHittableDeckTest(){
        unHittableDeck();
        blackjack.setDealingDeck(testDeck);
        blackjack.startGame(10);
        blackjack.hit();
        assertThrows(IllegalArgumentException.class, () -> {
            blackjack.hit();
        });
    }

    @Test
    void unSplittableSplit(){
        setInsplittableDeck();
        blackjack.setDealingDeck(testDeck);
        blackjack.startGame(10);
        assertThrows(IllegalStateException.class, () ->{
            blackjack.split();
        });
    }

    private Card selectCard(String name){
        return cardDeck.getDeck().stream().filter(
                card -> card.getCardName().equals(name))
                .collect(Collectors.toList()).get(0);
    }


}