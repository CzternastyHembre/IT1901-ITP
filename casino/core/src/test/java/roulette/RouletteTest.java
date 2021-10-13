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
    
    @Test
    public void CalulateTest() {
    	
    	int iterations = 10;
    	double amount1 = 10;
    	double amount2 = 15;
    	double amount3 = 20;
    	
    	for (int i = 0; i < iterations; i++) {//Roulette.calculate rolls a random number each time
    		
	    	Guess guess1 = new NumberGuess(amount1, 1);
	    	Guess guess2 = new ListGuess(amount2, 1, 12);
	    	Guess guess3 = new PatternGuess(amount3, 1, 2);
	    	
	    	roulette.addGuess(guess1);
	    	assertEquals(roulette.getSumOfBets(), amount1);
	    	
	    	roulette.addGuess(guess1);
	    	assertEquals(roulette.getSumOfBets(), amount1 * 2);
	    	
	    	roulette.addGuess(guess2);
	    	assertEquals(roulette.getSumOfBets(), amount1 * 2 + amount2);
	    	
	    	roulette.addGuess(guess3);
	    	assertEquals(roulette.getSumOfBets(), amount1 * 2 + amount2 + amount3);
	    	
	    	double winnings = roulette.calculate();
	    	double shouldWin = 0;
	    	shouldWin += roulette.getRolledNumber() == 1 ? Roulette.RoulettSize * amount1 * 2 : 0;
	    	shouldWin += roulette.getRolledNumber() > 0 && roulette.getRolledNumber() < 13 ? Roulette.RoulettSize * amount2 / guess2.getPossibleWins(): 0;
	    	shouldWin += roulette.getRolledNumber() % 2 == 1 ? Roulette.RoulettSize * amount3 / guess3.getPossibleWins() : 0;
	    	
	    	assertEquals(winnings, shouldWin);
	    	assertEquals(roulette.getSumOfBets(), 0);    	
    	}
    }

}
