package slots;

import org.junit.jupiter.api.Test;
import user.User;

import static org.junit.jupiter.api.Assertions.*;

class SlotsTest {

    User user = new User("Test User",100);
    Slots slotMachine = new Slots();

    @Test
    void spin() {
        slotMachine.setBet(10);
        slotMachine.spin();
        assertEquals(10, slotMachine.getBet());
        assertEquals(90,slotMachine.getUserBalance());
        assertEquals(-10,slotMachine.getNetGain());
        assertEquals(1,slotMachine.getSpins());
    }


}
