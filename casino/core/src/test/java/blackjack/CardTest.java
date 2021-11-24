package blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

  @Test
  void constructor(){
    assertThrows(IllegalStateException.class,()->{
      new Card(-1,'D');
    });
    assertThrows(IllegalStateException.class,()->{
      new Card(10,'F');
    });
  }

}