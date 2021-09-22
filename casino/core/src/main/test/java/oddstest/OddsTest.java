package oddstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import roulette.*;

import org.junit.jupiter.api.Test;
import user.User;


public class OddsTest {

    Roulette rouletteGame = new Roulette(new User("userrr", 1000));

    @Test
    public void checkOddSingleNumberTest(){
        Guess guess = new NumberGuess(5, 1);
        rouletteGame.addGuess(guess);

        rouletteGame.setRolledNumber(5);
        assertEquals(0, rouletteGame.calcuteGuessWinnings());

    }
}
