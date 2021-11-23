package blackjack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;
import blackjack.Hand;
import blackjack.Deck;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackTest {

    private User user;
    private Blackjack blackjack;

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
        Assertions.assertEquals(blackjack.getPayout(), 0);
        Assertions.assertEquals(blackjack.getDealersHand().getDeck().size(),2);
        Assertions.assertEquals(blackjack.getTargetHand(),blackjack.getPlayersHand1());
    }

    @Test
    void stand() {
        blackjack.startGame(10);
        blackjack.stand();
        Assertions.assertFalse(blackjack.getTargetHand().isActive());
    }

    @Test
    void split() {
        blackjack.startGame(10);

        Hand splittableHand = new Hand();
        splittableHand.getDeck().add(new Card(5,'C'));
        splittableHand.getDeck().add(new Card(5,'H'));
        Hand insplittableHand = new Hand();
        insplittableHand.getDeck().add(new Card(5,'C'));
        insplittableHand.getDeck().add(new Card(6,'H'));
        double userBalance = blackjack.getUser().getBalance();

        blackjack.setPlayersHand1(insplittableHand);
        blackjack.split();
        Assertions.assertEquals(userBalance, user.getBalance()); // If this is true, the rest of the method hasn't been called

        blackjack.setPlayersHand1(splittableHand);
        blackjack.split();
        assertEquals(blackjack.getUser().getBalance(), userBalance - blackjack.getBet());
        assertEquals(blackjack.getPlayersHand1().getDeck().size(), 1);
        assertEquals(blackjack.getPlayersHand2().getDeck().size(),1);
        assertTrue(blackjack.getPlayersHand2().isActive());
        assertEquals(blackjack.getTargetHand(), blackjack.getTargetHand());
        assertTrue(blackjack.isHasSplit());

    }

    @Test
    void toggleTargetHand() {
        blackjack.startGame(10);
        blackjack.toggleTargetHand();
        assertEquals(blackjack.getTargetHand(), blackjack.getPlayersHand2());
        blackjack.toggleTargetHand();
        assertEquals(blackjack.getTargetHand(), blackjack.getPlayersHand1());
    }

    @Test
    void dealerPlay() {
        blackjack.startGame(10);
//        Hand dealerHand = new Hand();
//        dealerHand.getDeck().add(new Card(2,'C'));
//        dealerHand.getDeck().add(new Card(3,'C'));
        blackjack.dealerPlay();
        Assertions.assertTrue(blackjack.getDealersHand().getSumOfDeck() >= 17);
        Assertions.assertFalse(blackjack.getDealersHand().isActive());
        if (blackjack.getDealersHand().getSumOfDeck() == 21){
            assertEquals(0, blackjack.getPayout());
        }
        if (blackjack.getPlayersHand2().getSumOfDeck()!=0){
            assertTrue(blackjack.getPayout()-blackjack.getBet() != blackjack.getPayout());
        }
    }
}