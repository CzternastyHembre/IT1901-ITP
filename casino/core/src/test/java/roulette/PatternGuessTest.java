package roulette;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import user.User;

public class PatternGuessTest {
	
    Roulette roulette;

    
    @BeforeEach
    public void berforeEach() {
    	roulette = new Roulette(new User("bob", 1000));

    }

    @Test
    public void patternFalseTest(){
    	double amount = 10;
    	Guess patternGuessOdd = new PatternGuess(amount, 1, 2);
    	for (int i = 0; i <= Roulette.RoulettSize; i+= 2) {
    		berforeEach();
        	roulette.setRolledNumber(i);
    		
            roulette.addGuess(patternGuessOdd);
            assertEquals(roulette.calculateGuessWinnings(), 0);
    		}

    	Guess patternGuessEven = new PatternGuess(amount, 2, 2);
    	for (int i = 1; i <= Roulette.RoulettSize; i+= 2) {
    		berforeEach();
        	roulette.setRolledNumber(i);
    		
            roulette.addGuess(patternGuessEven);
            assertEquals(roulette.calculateGuessWinnings(), 0);
    		}
	}
    
    @Test
    public void patternCorrectTest(){
    	double amount = 10;
    	Guess patternGuessOdd = new PatternGuess(amount, 1, 2);
    	for (int i = 1; i <= Roulette.RoulettSize; i+= 2) {
    		berforeEach();
        	roulette.setRolledNumber(i);
    		
            roulette.addGuess(patternGuessOdd);
            assertEquals(roulette.calculateGuessWinnings(), Roulette.RoulettSize * amount / patternGuessOdd.getPossibleWins());
    		}

    	Guess patternGuessEven = new PatternGuess(amount, 2, 2);
    	for (int i = 2; i <= Roulette.RoulettSize; i+= 2) {
    		berforeEach();
        	roulette.setRolledNumber(i);
    		
            roulette.addGuess(patternGuessEven);
            assertEquals(roulette.calculateGuessWinnings(), Roulette.RoulettSize * amount / patternGuessOdd.getPossibleWins());
    		}
	}
}

