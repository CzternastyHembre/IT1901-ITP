package roulette;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import user.User;

public class GuessTest {

    Roulette roulette;

    
    @BeforeEach
    public void berforeEach() {
    	roulette = new Roulette(new User("bob", 1000));

    }
    
    @Test
    public void negativeAmountTest() {
//    	assertThrows(IllegalArgumentException.class, new NumberGuess(0, 1));
    }
}