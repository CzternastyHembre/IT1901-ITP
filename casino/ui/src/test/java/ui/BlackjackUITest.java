package ui;

import blackjack.Blackjack;
import blackjack.Card;
import blackjack.Hand;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import user.User;

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
    void playAgain() {

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
//        assertEquals(2, blackjackController.getDealerHandHBox().getChildren().size());

        assertEquals(blackjackController.getPlayerTotal().getText(), ""+blackjack.getTargetHand().getSumOfDeck());
        assertEquals(blackjackController.getDealerTotal().getText(), blackjack.getDealersHand().getDeck().get(1).getCardValue() + " + ?");
        assertSame("Player", blackjackController.getTurnLabel().getText());
        assertFalse(blackjackController.getPayout().isVisible());
        assertFalse(blackjackController.getResult().isVisible());
    }

    @Test
    void split() {

    }

    @Test
    void toggle() {
    }

    @Test
    void hit() {
        Hand validHitHand = new Hand();
        validHitHand.getDeck().add(new Card(2,'C'));
        validHitHand.getDeck().add(new Card(5,'S'));
        blackjack.setPlayersHand1(validHitHand);

        clickOn("#betAmount").write("10");
        clickOn("#bet");
        clickOn("#hit");

        assertTrue(blackjackController.getSplit().isDisabled());
        if (blackjack.getTargetHand().getSumOfDeck() >= 21){
            assertTrue(blackjackController.getHit().isDisabled());
        }
        assertEquals(3, blackjackController.getPlayerHandPanes().get(0).getChildren().size());
        assertEquals(blackjackController.getPlayerTotal().getText(), "" + blackjack.getTargetHand().getSumOfDeck());

    }

    @Test
    void stand() {
    }
}