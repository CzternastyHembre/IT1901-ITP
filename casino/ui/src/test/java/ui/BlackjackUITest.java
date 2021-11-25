package ui;

import blackjack.Blackjack;
import blackjack.Card;
import blackjack.Deck;
import blackjack.Hand;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import user.User;
import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackUITest extends ApplicationTest {
    private BlackjackController blackjackController;
    private User user = new User("slotsTest", 100);
    private Blackjack blackjack;
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







    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Blackjack.fxml"));
        blackjackController = new BlackjackController();
        blackjackController.setUser(user);
        blackjackController.setRestModel(mock(RestModel.class));
        loader.setController(blackjackController);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @BeforeEach
    void setUp() {
        blackjack = blackjackController.getBlackjack();
    }



    @Test
    void naturalBlackjackWin() {
        naturalBlackjackWinDeck();
        blackjack.setDealingDeck(testDeck);
        clickOn("#betAmount").write("10");
        clickOn("#bet");
        assertTrue(blackjackController.getHit().isDisabled());
        assertTrue(blackjackController.getSplit().isDisabled());
        clickOn("#stand");
        assertFalse(blackjackController.getPlayAgainButton().isDisabled());
        assertEquals(Double.parseDouble(blackjackController.getPlayerTotal().getText()),21);
        assertEquals(blackjackController.getResult().getText(), "WIN!");
    }

    @Test
    void naturalBlackjackTie(){
        naturalBlackjackTieDeck();
        blackjack.setDealingDeck(testDeck);
        clickOn("#betAmount").write("10");
        clickOn("#bet");
        clickOn("#stand");
        assertEquals("NO GAIN!", blackjackController.getResult().getText());
    }

    @Test
    void hitOverLimit(){
        hitOverLimitDeck();
        blackjack.setDealingDeck(testDeck);
        clickOn("#betAmount").write("10");
        clickOn("#bet");
        clickOn("#hit");
        assertTrue(blackjackController.getHit().isDisabled());
        assertTrue(blackjackController.getSplit().isDisabled());
        assertEquals(Double.parseDouble(blackjackController.getPlayerTotal().getText())
                , blackjack.getTargetHand().getSumOfDeck());
    }

    @Test
    void hitUnderLimit(){
        hitUnderLimitDeck();
        blackjack.setDealingDeck(testDeck);
        clickOn("#betAmount").write("10");
        clickOn("#bet");
        clickOn("#hit");
        assertFalse(blackjackController.getHit().isDisabled());
        assertEquals(3, blackjackController.getPlayerHandPanes().get(0).getChildren().size());
    }

    @Test
    void hitOnSplittable(){
        setSplittableDeck();
        blackjack.setDealingDeck(testDeck);
        clickOn("#betAmount").write("10");
        clickOn("#bet");
        clickOn("#hit");
        assertTrue(blackjackController.getSplit().isDisabled());
    }
    

//    @Test
//    void bet() {
//        double prevBalance = user.getBalance();
//        clickOn("#betAmount").write("10");
//        clickOn("#bet");
//
//        if (blackjack.getTargetHand().getSumOfDeck() == 21){
//            assertTrue(blackjackController.isDealerIsFlipped());
//            assertEquals(blackjackController.getDealerHandHbox().getChildren().size(), 2);
//            assertEquals(user.getBalance(),prevBalance + blackjack.getPayout());
//            if (blackjack.getDealersHand().getSumOfDeck() == 21){
//              assertEquals("NO GAIN!", blackjackController.getResult().getText());
//            }
//            else {
//              assertEquals("WIN!", blackjackController.getResult().getText());
//            }
//            assertEquals(Double.parseDouble(blackjackController.getDealerTotal().getText()), blackjack.getDealersHand().getSumOfDeck());
//            assertTrue(blackjackController.getResult().isVisible());
//            assertTrue(blackjackController.getPayout().isVisible());
//            assertEquals(Double.parseDouble(blackjackController.getPayout().getText()), blackjack.getPayout());
//            assertEquals(Double.parseDouble(blackjackController.getBalanceField().getText()), user.getBalance());
//            assertFalse(blackjackController.getPlayAgainButton().isDisabled());
//
//        }
//
//        if (blackjack.getTargetHand().getSumOfDeck() < 21)
//            assertFalse(blackjackController.getHit().isDisabled());
//        assertFalse(blackjackController.getStand().isDisabled());
//        if (blackjack.isCanSplit()){
//            assertFalse(blackjackController.getSplit().isDisabled());
//        }
//        assertEquals(0.5, blackjackController.getHand2().getOpacity());
//        assertTrue(blackjackController.getBet().isDisabled());
//        assertEquals(Double.parseDouble(blackjackController.getBalanceField().getText()), blackjackController.getUser().getBalance());
//
//        assertEquals(2, blackjackController.getPlayerHandPanes().get(0).getChildren().size());
//        if (blackjack.isCanSplit()){
//            assertFalse(blackjackController.getSplit().isDisabled());
//        }
//        assertEquals(blackjackController.getPlayerTotal().getText(), ""+blackjack.getTargetHand().getSumOfDeck());
//        assertEquals(blackjackController.getDealerTotal().getText(), blackjack.getDealersHand().getDeck().get(1).getCardValue() + " + ?");
//        assertSame("Player", blackjackController.getTurnLabel().getText());
//        assertFalse(blackjackController.getPayout().isVisible());
//        assertFalse(blackjackController.getResult().isVisible());
//    }
//
//    @Test
//    void hit() {
//        boolean isSplitDisabled = blackjackController.getSplit().isDisabled();
//        Hand validHitHand = new Hand();
//        validHitHand.getDeck().add(new Card(2,'C'));
//        validHitHand.getDeck().add(new Card(5,'S'));
//        blackjack.setPlayersHand1(validHitHand);
//        blackjack.setTargetHand(blackjack.getPlayersHand1());
//
//        clickOn("#betAmount").write("10");
//        clickOn("#bet");
//        clickOn("#hit");
//
//        if (blackjack.getTargetHand().getSumOfDeck() >= 21){
//            assertTrue(blackjackController.getHit().isDisabled());
//        }
//        else {
//            assertEquals(3, blackjackController.getPlayerHandPanes().get(0).getChildren().size());
//        }
//        assertEquals(blackjackController.getPlayerTotal().getText(), "" + blackjack.getTargetHand().getSumOfDeck());
//    }
//
//    @Test
//    void split() {
//
//        clickOn("#betAmount").write("10");
//        clickOn("#bet");
//
//        splitSetUp();
//        blackjackController.getSplit().setDisable(false);
//        // Easiest way to test the split is by enabling the split button instead of changing the blackjack deck
//
//        clickOn("#split");
//
//        assertFalse(blackjackController.getToggleButton().isDisabled());
//
//        for (Pane pane : blackjackController.getPlayerHandPanes())
//            assertEquals(1, pane.getChildren().size());
//
//        assertEquals(blackjackController.getHand2().getOpacity(), 0.5);
//        assertTrue(blackjackController.getSplit().isDisabled());
//        assertEquals(blackjackController.getPlayerTotal().getText(), "5");
//        assertEquals(blackjackController.getTurnLabel().getText(), "Player (Hand 1)");
//
//    }
//
//    @Test
//    void toggle() {
//
//        clickOn("#betAmount").write("10");
//        clickOn("#bet");
//
//
//        splitSetUp();
//        blackjackController.getSplit().setDisable(false);
//        // Easiest way to test the toggle is by enabling the split button instead of changing the blackjack deck
//
//        clickOn("#split");
//        clickOn("#toggleButton");
//        assertEquals(0.5, blackjackController.getPlayerHandPanes().get(0).getOpacity());
//        assertEquals(1, blackjackController.getPlayerHandPanes().get(1).getOpacity());
//        if (!blackjack.getTargetHand().isActive())
//            assertTrue(blackjackController.getHit().isDisabled());
//
//        assertEquals(blackjackController.getTurnLabel().getText(), "Player (Hand 2)");
//        assertEquals(blackjackController.getPlayerTotal().getText(), "5");
//    }
//
//
//
//    @Test
//    void stand() {
//        clickOn("#betAmount").write("10");
//        clickOn("#bet");
//        double prevBalance = user.getBalance();
//        clickOn("#stand");
//
//
//        assertTrue(blackjackController.isDealerIsFlipped());
//        assertEquals(blackjack.getDealersHand().getDeck().size(), blackjackController.getDealerHandHbox().getChildren().size());
//        assertEquals(user.getBalance(),prevBalance + blackjack.getPayout());
//        assertTrue(blackjackController.getResult().getText().equals("WIN!")
//                || blackjackController.getResult().getText().equals("LOSS!"));
//        assertEquals(Double.parseDouble(blackjackController.getDealerTotal().getText()), blackjack.getDealersHand().getSumOfDeck());
//        assertTrue(blackjackController.getResult().isVisible());
//        assertTrue(blackjackController.getPayout().isVisible());
//        assertEquals(Double.parseDouble(blackjackController.getPayout().getText()), blackjack.getPayout());
//        assertEquals(Double.parseDouble(blackjackController.getBalanceField().getText()), user.getBalance());
//        assertFalse(blackjackController.getPlayAgainButton().isDisabled());
//
//    }
//
//    @Test
//    void standHasSplit(){
//        clickOn("#betAmount").write("10");
//        clickOn("#bet");
//        splitSetUp();
//        blackjackController.getSplit().setDisable(false);
//        // Easiest way to test the toggle is by enabling the split button instead of changing the blackjack deck
//        clickOn("#split");
//        clickOn("#stand");
//
//        assertEquals(0.5, blackjackController.getPlayerHandPanes().get(0).getOpacity());
//        assertEquals(1, blackjackController.getPlayerHandPanes().get(1).getOpacity());
//
//        if (!blackjack.getTargetHand().isActive())
//            assertTrue(blackjackController.getHit().isDisabled());
//
//        assertEquals(blackjackController.getTurnLabel().getText(), "Player (Hand 2)");
//        assertEquals(blackjackController.getPlayerTotal().getText(), "5");
//
//    }
//
//
//    @Test
//    void playAgain() {
//        clickOn("#betAmount").write("10");
//        clickOn("#bet");
//        clickOn("#stand");
//        clickOn("#playAgainButton");
//
//        assertNotSame(blackjackController.getBlackjack(), blackjack);
//        assertTrue(blackjackController.getHit().isDisabled());
//        assertTrue(blackjackController.getStand().isDisabled());
//        assertTrue(blackjackController.getSplit().isDisabled());
//        assertTrue(blackjackController.getToggleButton().isDisabled());
//        assertTrue(blackjackController.getPlayAgainButton().isDisabled());
//        assertTrue(blackjackController.getPlayerHandPanes().get(0).getChildren().isEmpty());
//        assertTrue(blackjackController.getPlayerHandPanes().get(1).getChildren().isEmpty());
//        assertTrue(blackjackController.getDealerHandHbox().getChildren().isEmpty());
//
//        assertSame("0", blackjackController.getPlayerTotal().getText());
//        assertSame("0", blackjackController.getDealerTotal().getText());
//        assertFalse(blackjackController.getPayout().isVisible());
//        assertFalse(blackjackController.getResult().isVisible());
//        assertEquals(Double.parseDouble(blackjackController.getBalanceField().getText()), user.getBalance());
//        assertEquals(blackjackController.getTurnLabel().getText(), "Start a game");
//
//    }
//
//    private void splitSetUp() {
//        Hand validSplitHand = new Hand();
//        validSplitHand.getDeck().add(new Card(5,'C'));
//        validSplitHand.getDeck().add(new Card(5,'S'));
//
//        blackjack.getPlayersHand1().getDeck().clear();
//        blackjack.getPlayersHand1().getDeck().addAll(Arrays.asList(
//                new Card(5,'C'),
//                new Card(5,'S')));
//
//        blackjack.setTargetHand(blackjack.getPlayersHand1());
//    }

    private Card selectCard(String name){
        return cardDeck.getDeck().stream().filter(
                        card -> card.getCardName().equals(name))
                .collect(Collectors.toList()).get(0);
    }

}