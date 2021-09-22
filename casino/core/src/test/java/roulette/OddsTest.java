package roulette;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import user.User;

public class OddsTest {

    Roulette roulette = new Roulette(new User("bob", 1000));

    @Test
    public void testSingleValueTest(){
        Guess numberGuess = new NumberGuess(5, 1);
        roulette.addGuess(numberGuess);

        roulette.setRolledNumber(6);
        assertEquals(0, roulette.calcuteGuessWinnings());
    }

}
