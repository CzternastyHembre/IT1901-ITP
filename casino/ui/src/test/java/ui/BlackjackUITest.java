package ui;

import blackjack.Blackjack;
import blackjack.Card;
import blackjack.Hand;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import user.User;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackUITest extends ApplicationTest {
    private BlackjackController blackjackController;
    private User user = new User("slotsTest", 100);
    private Blackjack blackjack;

    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Blackjack.fxml"));
        blackjackController = new BlackjackController();
        blackjackController.setUser(user);
        loader.setController(blackjackController);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @BeforeEach
    void setUp() {
        blackjack = blackjackController.getBlackjack();
    }



    @Test
    void bet() {

        clickOn("#betAmount").write("10");
        clickOn("#bet");

        if (blackjack.getTargetHand().getSumOfDeck() < 21)
            assertFalse(blackjackController.getHit().isDisabled());
        assertFalse(blackjackController.getStand().isDisabled());
        if (blackjack.canSplit()){
            assertFalse(blackjackController.getSplit().isDisabled());
        }
        assertEquals(0.5, blackjackController.getHand2().getOpacity());
        assertTrue(blackjackController.getBet().isDisabled());
        assertEquals(Double.parseDouble(blackjackController.getBalanceField().getText()), blackjackController.getUser().getBalance());

//        String playerFirstCard = blackjack.getPlayerHands().get(0).getDeck().get(0).getCardImage();
//        ImageView playerCardImageView = new ImageView(new Image(Objects.requireNonNull(
//                BlackjackUITest.class.getResourceAsStream("/images/cards/" + playerFirstCard))));
//        ImageView dealerCardImageView = new ImageView(new Image(Objects.requireNonNull(
//                BlackjackUITest.class.getResourceAsStream("/images/cards/backOfCard.jpg"))));
//
//        ImageView playerInGameImage = (ImageView) blackjackController.getHand1().getChildren().get(0);
//        ImageView dealerInGameImage = (ImageView) blackjackController.getDealerHandHBox().getChildren().get(0);
//
//        assertEquals(playerInGameImage.getImage(), playerCardImageView.getImage());
//        assertEquals(dealerInGameImage.getImage(), dealerCardImageView.getImage());

        assertEquals(2, blackjackController.getPlayerHandPanes().get(0).getChildren().size());
        if (blackjack.canSplit()){
            assertFalse(blackjackController.getSplit().isDisabled());
        }
//        assertEquals(2, blackjackController.getDealerHandHBox().getChildren().size());

        assertEquals(blackjackController.getPlayerTotal().getText(), ""+blackjack.getTargetHand().getSumOfDeck());
        assertEquals(blackjackController.getDealerTotal().getText(), blackjack.getDealersHand().getDeck().get(1).getCardValue() + " + ?");
        assertSame("Player", blackjackController.getTurnLabel().getText());
        assertFalse(blackjackController.getPayout().isVisible());
        assertFalse(blackjackController.getResult().isVisible());
    }

    @Test
    void hit() {

        Hand validHitHand = new Hand();
        validHitHand.getDeck().add(new Card(2,'C'));
        validHitHand.getDeck().add(new Card(5,'S'));
        blackjack.setPlayersHand1(validHitHand);
        blackjack.setTargetHand(blackjack.getPlayersHand1());

        clickOn("#betAmount").write("10");
        clickOn("#bet");
        clickOn("#hit");

        assertTrue(blackjackController.getSplit().isDisabled());
        if (blackjack.getTargetHand().getSumOfDeck() >= 21){
            assertTrue(blackjackController.getHit().isDisabled());
        }
        else {
            assertEquals(3, blackjackController.getPlayerHandPanes().get(0).getChildren().size());
        }
        assertEquals(blackjackController.getPlayerTotal().getText(), "" + blackjack.getTargetHand().getSumOfDeck());
//        blackjackController.getSplit().setDisable(false);
//        blackjackController.getHit().setDisable(false);
//        clickOn("#hit");
//        assertTrue(blackjackController.getSplit().isDisabled());
    }

    @Test
    void split() {

        clickOn("#betAmount").write("10");
        clickOn("#bet");

        splitSetUp();
        blackjackController.getSplit().setDisable(false);
        // Easiest way to test the split is by enabling the split button instead of changing the blackjack deck

        clickOn("#split");

        assertFalse(blackjackController.getToggleButton().isDisabled());

        for (Pane pane : blackjackController.getPlayerHandPanes())
            assertEquals(1, pane.getChildren().size());

        assertEquals(blackjackController.getHand2().getOpacity(), 0.5);
        assertTrue(blackjackController.getSplit().isDisabled());
        assertEquals(blackjackController.getPlayerTotal().getText(), "5");
        assertEquals(blackjackController.getTurnLabel().getText(), "Player (Hand 1)");

    }

    @Test
    void toggle() {

        clickOn("#betAmount").write("10");
        clickOn("#bet");


        splitSetUp();
        blackjackController.getSplit().setDisable(false);
        // Easiest way to test the toggle is by enabling the split button instead of changing the blackjack deck

        clickOn("#split");
        clickOn("#toggleButton");
        assertEquals(0.5, blackjackController.getPlayerHandPanes().get(0).getOpacity());
        assertEquals(1, blackjackController.getPlayerHandPanes().get(1).getOpacity());
        if (!blackjack.getTargetHand().isActive())
            assertTrue(blackjackController.getHit().isDisabled());

        assertEquals(blackjackController.getTurnLabel().getText(), "Player (Hand 2)");
        assertEquals(blackjackController.getPlayerTotal().getText(), "5");
    }



    @Test
    void stand() {
        clickOn("#betAmount").write("10");
        clickOn("#bet");
        double prevBalance = user.getBalance();
        clickOn("#stand");

        if (blackjack.isPlayerDone()){
            assertTrue(blackjackController.isDealerIsFlipped());
        }
        assertEquals(blackjack.getDealersHand().getDeck().size(), blackjackController.getDealerHandHBox().getChildren().size());
        assertEquals(user.getBalance(),prevBalance + blackjack.getPayout());
        assertTrue(blackjackController.getResult().getText().equals("WIN!")
                || blackjackController.getResult().getText().equals("LOSS!"));
        assertEquals(Double.parseDouble(blackjackController.getDealerTotal().getText()), blackjack.getDealersHand().getSumOfDeck());
        assertTrue(blackjackController.getResult().isVisible());
        assertTrue(blackjackController.getPayout().isVisible());
        assertEquals(Double.parseDouble(blackjackController.getPayout().getText()), blackjack.getPayout());
        assertEquals(Double.parseDouble(blackjackController.getBalanceField().getText()), user.getBalance());
        assertFalse(blackjackController.getPlayAgainButton().isDisabled());

    }

    @Test
    void standHasSplit(){
        clickOn("#betAmount").write("10");
        clickOn("#bet");
        splitSetUp();
        blackjackController.getSplit().setDisable(false);
        // Easiest way to test the toggle is by enabling the split button instead of changing the blackjack deck
        clickOn("#split");
        clickOn("#stand");

        assertEquals(0.5, blackjackController.getPlayerHandPanes().get(0).getOpacity());
        assertEquals(1, blackjackController.getPlayerHandPanes().get(1).getOpacity());
        if (!blackjack.getTargetHand().isActive())
            assertTrue(blackjackController.getHit().isDisabled());

        assertEquals(blackjackController.getTurnLabel().getText(), "Player (Hand 2)");
        assertEquals(blackjackController.getPlayerTotal().getText(), "5");

    }


    @Test
    void playAgain() {
        clickOn("#betAmount").write("10");
        clickOn("#bet");
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
        assertTrue(blackjackController.getDealerHandHBox().getChildren().isEmpty());

        assertSame("0", blackjackController.getPlayerTotal().getText());
        assertSame("0", blackjackController.getDealerTotal().getText());
        assertFalse(blackjackController.getPayout().isVisible());
        assertFalse(blackjackController.getResult().isVisible());
        assertEquals(Double.parseDouble(blackjackController.getBalanceField().getText()), user.getBalance());
        assertEquals(blackjackController.getTurnLabel().getText(), "Start a game");

    }

    private void splitSetUp() {
        Hand validSplitHand = new Hand();
        validSplitHand.getDeck().add(new Card(5,'C'));
        validSplitHand.getDeck().add(new Card(5,'S'));

        blackjack.getPlayersHand1().getDeck().clear();
        blackjack.getPlayersHand1().getDeck().addAll(Arrays.asList(
                new Card(5,'C'),
                new Card(5,'S')));

        blackjack.setTargetHand(blackjack.getPlayersHand1());
    }



}