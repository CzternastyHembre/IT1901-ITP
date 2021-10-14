package slots;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlotsTest {

    User user;
    Slots slotMachine;

    @BeforeEach
    void setUp(){
        this.user = new User("Test User",100);
        this.slotMachine = new Slots(user);
    }


    @Test
    void spin() {
        slotMachine.spin();
        Assertions.assertEquals(1, slotMachine.getSpins());
        for (String string : this.slotMachine.getSymbols()){
            Assertions.assertNotEquals("", string);
        }
    }

    @Test
    void play(){
        var prevBalance = this.slotMachine.getUserBalance();
        this.slotMachine.play(10);
        Assertions.assertEquals(slotMachine.getBet(), 10);
        Assertions.assertTrue(prevBalance-slotMachine.getUserBalance()!=0); // Indirectly tests updateWinnings()
        Assertions.assertEquals(slotMachine.getSpins(),1);
    }

    @Test
    void isSuperJackpot(){
        List<String> symbols = Arrays.asList("1H", "1H", "1H");
        slotMachine.setSymbols(symbols);
        slotMachine.calculateWinnings();
        Assertions.assertEquals("SUPER JACKPOT", slotMachine.getCombo());
    }

    @Test
    void isSuperPerfectStraight(){
        List<String> symbols = Arrays.asList("1H", "2H", "3H");
        slotMachine.setSymbols(symbols);
        slotMachine.calculateWinnings();
        Assertions.assertEquals("SUPER PERFECT STRAIGHT", slotMachine.getCombo());
    }

    @Test
    void isJackPot(){
        List<String> symbols = Arrays.asList("1H", "1D", "1C");
        slotMachine.setSymbols(symbols);
        slotMachine.calculateWinnings();
        Assertions.assertEquals("JACKPOT", slotMachine.getCombo());
    }

    @Test
    void isDevil(){
        List<String> symbols = Arrays.asList("6H", "6D", "6C");
        slotMachine.setSymbols(symbols);
        slotMachine.calculateWinnings();
        Assertions.assertEquals("DEVIL", slotMachine.getCombo());
    }

    @Test
    void isPerfectStraight(){
        List<String> symbols = Arrays.asList("1H", "2D", "3C");
        slotMachine.setSymbols(symbols);
        slotMachine.calculateWinnings();
        Assertions.assertEquals("PERFECT STRAIGHT", slotMachine.getCombo());
    }

    @Test
    void isStraight(){
        List<String> symbols = Arrays.asList("1H", "3D", "2C");
        slotMachine.setSymbols(symbols);
        slotMachine.calculateWinnings();
        Assertions.assertEquals("STRAIGHT", slotMachine.getCombo());
    }

    @Test
    void isFlush(){
        List<String> symbols = Arrays.asList("1H", "6H", "8H");
        slotMachine.setSymbols(symbols);
        slotMachine.calculateWinnings();
        Assertions.assertEquals("FLUSH", slotMachine.getCombo());
    }

    @Test
    void isPair(){
        List<String> symbols = Arrays.asList("1H", "1D", "5C");
        slotMachine.setSymbols(symbols);
        slotMachine.calculateWinnings();
        Assertions.assertEquals("PAIR", slotMachine.getCombo());
    }

    @Test
    void isLoss(){
        List<String> symbols = Arrays.asList("1H", "2D", "7C");
        slotMachine.setSymbols(symbols);
        slotMachine.calculateWinnings();
        Assertions.assertEquals("LOSS", slotMachine.getCombo());
    }





}
