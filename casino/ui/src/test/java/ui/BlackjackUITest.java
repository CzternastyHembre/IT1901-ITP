package ui;

import blackjack.Blackjack;
import card.Card;
import blackjack.Deck;
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

public class BlackjackUITest extends ApplicationTest {
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
        blackjack.setDealingDeck(testDeck);
    }

    void naturalBlackjackTieDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("10D"),
                selectCard("10C"),
                selectCard("1C"),
                selectCard("1H")));
        blackjack.setDealingDeck(testDeck);
    }

    void setSplittableDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("5D"),
                selectCard("6C"),
                selectCard("5C"),
                selectCard("7H"),
                selectCard("5H")));
        blackjack.setDealingDeck(testDeck);
    }

    void hitOverLimitDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("9D"),
                selectCard("6C"),
                selectCard("9C"),
                selectCard("5H"),
                selectCard("8S")));
        blackjack.setDealingDeck(testDeck);
    }

    void hitUnderLimitDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("2D"),
                selectCard("6C"),
                selectCard("4C"),
                selectCard("5H"),
                selectCard("3S")));
        blackjack.setDealingDeck(testDeck);
    }

    void standWinDeck(){
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("10D"),
                selectCard("10C"),
                selectCard("9C"),
                selectCard("8H"),
                selectCard("2S")));
        blackjack.setDealingDeck(testDeck);
    }

    void splitWinBothDeck(){
        setSplittableDeck();
        testDeck.getDeck().addAll(Arrays.asList(
                selectCard("7D"),
                selectCard("8C"),
                selectCard("7S"),
                selectCard("8H"),
                selectCard("10D")));
        blackjack.setDealingDeck(testDeck);
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
        betClicks();
        assertTrue(blackjackController.getHit().isDisabled());
        assertTrue(blackjackController.getSplit().isDisabled());
        assertFalse(blackjackController.getPlayAgainButton().isDisabled());
        assertEquals(Double.parseDouble(blackjackController.getPlayerTotal().getText()),21);
        assertEquals(blackjackController.getResult().getText(), "WIN!");
    }

    @Test
    void naturalBlackjackTie(){
        naturalBlackjackTieDeck();
        betClicks();
        assertEquals("NO GAIN!", blackjackController.getResult().getText());
    }

    @Test
    void hitOverLimit(){
        hitOverLimitDeck();
        betAndHit();
        assertTrue(blackjackController.getHit().isDisabled());
        assertTrue(blackjackController.getSplit().isDisabled());
        assertEquals(Double.parseDouble(blackjackController.getPlayerTotal().getText())
                , blackjack.getTargetHand().getSumOfDeck());
    }

    @Test
    void hitUnderLimit(){
        hitUnderLimitDeck();
        betAndHit();
        assertFalse(blackjackController.getHit().isDisabled());
        assertEquals(3, blackjackController.getPlayerHandPanes().get(0).getChildren().size());
    }

    @Test
    void hitOnSplittable(){
        setSplittableDeck();
        betAndHit();
        assertTrue(blackjackController.getSplit().isDisabled());
    }

    @Test
    void standWithoutSplit(){
        standWinDeck();
        betClicks();
        double prevBalance = user.getBalance();
        clickOn("#stand");
        //Buttons
        assertTrue(blackjackController.getStand().isDisabled());
        assertTrue(blackjackController.getSplit().isDisabled());
        assertTrue(blackjackController.getHit().isDisabled());
        assertFalse(blackjackController.getPlayAgainButton().isDisabled());
        assertTrue(blackjackController.getResult().isVisible());
        assertTrue(blackjackController.getPayout().isVisible());

        // Text
        assertEquals(blackjack.getUser().getBalance(),
                Double.parseDouble(blackjackController.getBalanceField().getText()));

        assertEquals("WIN!", blackjackController.getResult().getText());

        assertEquals(blackjack.getDealersHand().getDeck().size(),
                blackjackController.getDealerHandHbox().getChildren().size());

        assertEquals(user.getBalance(),prevBalance + blackjack.getPayout());

        assertEquals(Double.parseDouble(blackjackController.getDealerTotal().getText()),
                blackjack.getDealersHand().getSumOfDeck());
        assertEquals(Double.parseDouble(blackjackController.getPayout().getText()),
                blackjack.getPayout());
        assertEquals(Double.parseDouble(blackjackController.getBalanceField().getText()),
                user.getBalance());
    }

    @Test
    void playSplitGame(){
        splitWinBothDeck();
        betClicks();
        clickOn("#split");

        // Test for split
        assertFalse(blackjackController.getToggleButton().isDisabled());
        for (Pane pane : blackjackController.getPlayerHandPanes())
            assertEquals(1, pane.getChildren().size());

        assertEquals(blackjackController.getHand2().getOpacity(), 0.5);
        assertTrue(blackjackController.getSplit().isDisabled());
        assertEquals(blackjackController.getPlayerTotal().getText(), "5");
        assertEquals(blackjackController.getTurnLabel().getText(), "Player (Hand 1)");

        // Hit
        doubleHit();

        // Toggle
        clickOn("#toggleButton");

        assertEquals(0.5, blackjackController.getPlayerHandPanes().get(0).getOpacity());
        assertEquals(1, blackjackController.getPlayerHandPanes().get(1).getOpacity());
        assertEquals(blackjackController.getTurnLabel().getText(), "Player (Hand 2)");
        assertEquals(blackjackController.getPlayerTotal().getText(), "5");

        // Hit
        doubleHit();

        // Stand
        clickOn("#stand");
        // Test that it toggled
        assertEquals(1, blackjackController.getPlayerHandPanes().get(0).getOpacity());
        assertEquals(0.5, blackjackController.getPlayerHandPanes().get(1).getOpacity());

        // Toggle again
        clickOn("#toggleButton");
        assertTrue(blackjackController.getHit().isDisabled());

        // Toggle back to stand final hand
        clickOn("#toggleButton");
        assertFalse(blackjackController.getHit().isDisabled());
        assertFalse(blackjackController.getStand().isDisabled());

        // Stand
        clickOn("#stand");
        assertTrue(blackjackController.getHit().isDisabled());
        assertTrue(blackjackController.getStand().isDisabled());
        assertTrue(blackjackController.getToggleButton().isDisabled());
        assertFalse(blackjackController.getPlayAgainButton().isDisabled());

    }

    @Test
    void playAgain() {
        betClicks();
        clickOn("#stand");
        clickOn("#playAgainButton");

        assertNotSame(blackjackController.getBlackjack(), blackjack);
        assertTrue(blackjackController.getHit().isDisabled());
        assertTrue(blackjackController.getStand().isDisabled());
        assertTrue(blackjackController.getSplit().isDisabled());
        assertTrue(blackjackController.getToggleButton().isDisabled());
        assertTrue(blackjackController.getPlayAgainButton().isDisabled());

        assertTrue(blackjackController.getPlayerHandPanes().get(0).getChildren().isEmpty());
        assertTrue(blackjackController.getPlayerHandPanes().get(1).getChildren().isEmpty());
        assertTrue(blackjackController.getDealerHandHbox().getChildren().isEmpty());

        assertSame("0", blackjackController.getPlayerTotal().getText());
        assertSame("0", blackjackController.getDealerTotal().getText());

        assertFalse(blackjackController.getPayout().isVisible());
        assertFalse(blackjackController.getResult().isVisible());

        assertEquals(Double.parseDouble(blackjackController.getBalanceField().getText()), user.getBalance());
        assertEquals(blackjackController.getTurnLabel().getText(), "Start a game");

    }

    private void doubleHit() {
        clickOn("#hit");
        clickOn("#hit");
    }

    private void betClicks(){
        clickOn("#betAmount").write("10");
        clickOn("#bet");
    }

    private void betAndHit(){
        clickOn("#betAmount").write("10");
        clickOn("#bet");
        clickOn("#hit");
    }

    private Card selectCard(String name){
        return cardDeck.getDeck().stream().filter(
                        card -> card.getCardName().equals(name))
                .collect(Collectors.toList()).get(0);
    }

}