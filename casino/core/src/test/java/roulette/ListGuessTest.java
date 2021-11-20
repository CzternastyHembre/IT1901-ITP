package roulette;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import user.User;

public class ListGuessTest {
    Roulette roulette;

    
    @BeforeEach
    public void berforeEach() {
    	roulette = new Roulette(new User("bob", 1000));

    }
    
    @Test
    public void testCorrect(){
    	double amount = 10;
    	for (int i = 0; i < 3; i++) {
    		
    		for (int j = i * 12 + 1; j <= (i + 1) * 12; j++) {
    			berforeEach();
    			roulette.setRolledNumber(j);    			
				
    			roulette.addGuess(Guess.listGuess(amount, i * 12 + 1, (i + 1) * 12));
    			assertEquals(roulette.calculateGuessWinnings(), Roulette.rouletteSize * amount / 12);
			}	
		}
    }
    
    @Test
    public void testFalse(){
    	double amount = 10;
    	for (int i = 0; i < 3; i++) {
    		
    		for (int j = i * 12 + 1; j <= (i + 1) * 12; j++) {
    			berforeEach();
    			roulette.setRolledNumber(-1);    			
				
    			roulette.addGuess(Guess.listGuess(amount, i * 12 + 1, (i + 1) * 12));
    			assertEquals(roulette.calculateGuessWinnings(), 0);
			}	
		}
    }

}
