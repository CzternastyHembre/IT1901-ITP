package validators;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slots.Slots;
import user.User;

import java.util.ArrayList;
import java.util.List;

class SlotValidatorTest {

    List<String> symbols = new ArrayList<>();

    @BeforeEach
    void setUp() {
        symbols.clear();
    }

    @Test
    void isSuperJackpot(){
        symbols.add("1H");
        symbols.add("1H");
        symbols.add("1H");
        Assertions.assertTrue(SlotsValidator.isSuperJackpot(symbols));

        symbols.remove(symbols.get(2));
        symbols.add("1D");
        Assertions.assertFalse(SlotsValidator.isSuperJackpot(symbols));
    }

    @Test
    void isSuperPerfectStraight(){
        symbols.add("1H");
        symbols.add("2H");
        symbols.add("3H");
        Assertions.assertTrue(SlotsValidator.isSuperPerfectStraight(symbols));
        symbols.remove(2);
        symbols.add("3D");
        Assertions.assertFalse(SlotsValidator.isSuperPerfectStraight(symbols));
    }

    @Test
    void isJackpot() {
        symbols.add("1H");
        symbols.add("1S");
        symbols.add("1D");
        Assertions.assertTrue(SlotsValidator.isJackpot(symbols));

        symbols.remove(symbols.get(2));
        symbols.add("2H");
        Assertions.assertFalse(SlotsValidator.isJackpot(symbols));
    }

    @Test
    void isPerfectStraight() {
        symbols.add("1H");
        symbols.add("2D");
        symbols.add("3D");
        Assertions.assertTrue(SlotsValidator.isPerfectStraight(symbols));

        symbols.clear();

        symbols.add("3H");
        symbols.add("2D");
        symbols.add("1H");
        Assertions.assertTrue(SlotsValidator.isPerfectStraight(symbols));

        symbols.clear();

        symbols.add("1D");
        symbols.add("3S");
        symbols.add("2C");
        Assertions.assertFalse(SlotsValidator.isPerfectStraight(symbols));

    }

    @Test
    void isStraight() {
        symbols.add("1H");
        symbols.add("3D");
        symbols.add("2S");
        Assertions.assertTrue(SlotsValidator.isStraight(symbols));
    }

    @Test
    void isDevil() {
        symbols.add("6H");
        symbols.add("6D");
        symbols.add("6S");
        Assertions.assertTrue(SlotsValidator.isDevil(symbols));
    }

    @Test
    void isPair() {
        symbols.add("1S");
        symbols.add("3D");
        symbols.add("1C");
        Assertions.assertTrue(SlotsValidator.isPair(symbols));
    }
}

