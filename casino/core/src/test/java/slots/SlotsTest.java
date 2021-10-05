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
        this.slotMachine = new Slots(user.getBalance());
    }

    @Test
    void spin() {
        slotMachine.spin();
        Assertions.assertEquals(1, slotMachine.getSpins());
    }
}
