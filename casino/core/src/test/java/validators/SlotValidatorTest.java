package validators;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slots.Slots;
import user.User;

import java.util.ArrayList;
import java.util.List;

class SlotValidatorTest {

    User user = new User("Test",100000);
    Slots slots = new Slots(user);
    List<Integer> symbols = new ArrayList<>();

    @BeforeEach
    void setUp() {
        symbols.clear();
    }

    @Test
    void isJackpot() {
        symbols.add(1);
        symbols.add(1);
        symbols.add(1);
        Assertions.assertTrue(SlotsValidator.isJackpot(symbols));

        symbols.remove(symbols.get(2));
        symbols.add(2);
        Assertions.assertFalse(SlotsValidator.isJackpot(symbols));
    }

    @Test
    void isPerfectStraight() {
        symbols.add(1);
        symbols.add(2);
        symbols.add(3);
        Assertions.assertTrue(SlotsValidator.isPerfectStraight(symbols));

        symbols.clear();

        symbols.add(3);
        symbols.add(2);
        symbols.add(1);
        Assertions.assertTrue(SlotsValidator.isPerfectStraight(symbols));

        symbols.clear();

        symbols.add(1);
        symbols.add(3);
        symbols.add(2);
        Assertions.assertFalse(SlotsValidator.isPerfectStraight(symbols));

    }

    @Test
    void isStraight() {
        symbols.add(1);
        symbols.add(3);
        symbols.add(2);
        Assertions.assertTrue(SlotsValidator.isStraight(symbols));
    }

    @Test
    void isDevil() {
        symbols.add(6);
        symbols.add(6);
        symbols.add(6);
        Assertions.assertTrue(SlotsValidator.isDevil(symbols));
    }

    @Test
    void isPair() {
        symbols.add(1);
        symbols.add(0);
        symbols.add(1);
        Assertions.assertTrue(SlotsValidator.isPair(symbols));
    }
}

