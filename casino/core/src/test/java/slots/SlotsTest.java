package slots;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

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


}
