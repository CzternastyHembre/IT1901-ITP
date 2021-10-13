package roulette;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import user.User;

public class RouletteTest {
	
    Roulette roulette;
    User user;
    double balance;

    @BeforeEach
    public void berforeEach() {
    	user = new User("bob", 1000);
    	this.balance = 1000;
    	roulette = new Roulette(user);

    }

    @Test
    public void GuessBalanceTest(){
    	double amount = 10;
    	roulette.setRolledNumber(0);

    	NumberGuess guess = new NumberGuess(amount, 0);
    	roulette.addGuess(guess);
    	balance -= guess.getAmount();
    	
    	
    	double amount2 = 100;
    	NumberGuess guess2 = new NumberGuess(amount2, 0);
    	roulette.addGuess(guess2);

    	balance -= amount2;
    	assertEquals(user.getBalance(), balance);

    	
    	assertEquals(roulette.calculateGuessWinnings(), Roulette.RoulettSize * (amount2 + amount));
	}

}
