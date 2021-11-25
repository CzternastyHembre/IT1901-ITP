package user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

  User user;

  @BeforeEach
  void setUp(){
    this.user = new User("TestUser", 1000);
  }


  @Test
  void setBalance() {
    user.setBalance(100);
    Assertions.assertEquals(100, user.getBalance());
    Assertions.assertThrows(IllegalArgumentException.class,()->user.setBalance(-1));
  }

  @Test
  void addMoney() {
    user.addMoney(100);
    Assertions.assertEquals(user.getBalance(), 1100);
    Assertions.assertThrows(IllegalArgumentException.class, ()-> user.addMoney(-10));
  }


  @Test
  void withdraw() {
    user.withdraw(999);
    Assertions.assertEquals(1, user.getBalance());
    Assertions.assertThrows(IllegalStateException.class, ()-> user.withdraw(1000));
    Assertions.assertThrows(IllegalArgumentException.class, ()-> user.withdraw(-10));
  }

  @Test
  void getUsername(){
    Assertions.assertEquals("TestUser", user.getUsername());
  }



}