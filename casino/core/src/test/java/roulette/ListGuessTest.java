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
				
    			roulette.addGuess(new ListGuess(amount, i * 12 + 1, (i + 1) * 12));
    			assertEquals(roulette.calculateGuessWinnings(), Roulette.RoulettSize * amount / 12);
			}	
		}
    }
    
    @Test
    public void testFalse(){
    	double amount = 10;
    	for (int i = 0; i < 3; i++) {
    		
    		for (int j = i * 12 + 1; j <= (i + 2) * 12; j++) {
    			int guessNumber = (j + 12) % (Roulette.RoulettSize);
    			berforeEach();
    			roulette.setRolledNumber((j + 12) % (Roulette.RoulettSize)); 
    			
    			System.out.println((j + 12) % (Roulette.RoulettSize));
				
    			roulette.addGuess(new ListGuess(amount, i * 12 + 1, (i + 1) * 12));
    			assertEquals(roulette.calculateGuessWinnings(), 0);
			}	
		}
    }

}
