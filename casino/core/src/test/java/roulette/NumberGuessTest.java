package roulette;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

public class NumberGuessTest {

    Roulette roulette;

    
    @BeforeEach
    public void berforeEach() {
    	roulette = new Roulette(new User("bob", 1000));

    }
    
    @Test
    public void numbersFalseTest(){
    	
    	for (int i = 0; i <= Roulette.RoulettSize; i++) {
    		berforeEach();
        	roulette.setRolledNumber(-1);
    		
            roulette.addGuess(new NumberGuess(10, i));
            assertEquals(roulette.calculateGuessWinnings(), 0);
    		}
    }
    
    @Test
    public void numbersCorrectTest(){
    	
    	for (int i = 0; i <= Roulette.RoulettSize; i++) {
    		berforeEach();
        	roulette.setRolledNumber(i);
	
            roulette.addGuess(new NumberGuess(10, i));
            assertEquals(roulette.calculateGuessWinnings(), 36 * 10);
    		}
    }
    
    @Test
    public void multipleGuessesFalseTest() {
    	
    	for (int i = 0; i <= Roulette.RoulettSize; i++) {
    		berforeEach();
        	roulette.setRolledNumber(i);
	
        	for (int j = 0; j <= Roulette.RoulettSize; j++) {
        		if (j != i) {
        			roulette.addGuess(new NumberGuess(10, j));
				}        		
    		}
        	assertEquals(roulette.calculateGuessWinnings(), 0);					
		}
    }
    
    @Test
    public void multipleGuessesOneCorrectTest() {
    	for (int i = 0; i <= Roulette.RoulettSize; i++) {
    		berforeEach();
        	roulette.setRolledNumber(i);
	
        	for (int j = 0; j <= Roulette.RoulettSize; j++) {
    			roulette.addGuess(new NumberGuess(10, j));
    		}
        	assertEquals(roulette.calculateGuessWinnings(), 36 * 10);					
		}

    }
    
    @Test
    public void multipleCorrectGuessesTest() {
    	for (int i = 1; i < 10; i++) {
    		berforeEach();
        	roulette.setRolledNumber(0);

        	int bettingSum = 0;
			for (int j = 1; j <= i; j++) {
				roulette.addGuess(new NumberGuess(j, 0));
				bettingSum += j;
				
			}
			assertEquals(roulette.calculateGuessWinnings(), 36 * bettingSum);
		}
    }

    @Test
    public void multipleNumbersGuess() {
    	for (int i = 0; i <= Roulette.RoulettSize; i++) {
    		berforeEach();
        	roulette.setRolledNumber(0);
        	List<Integer> numberSequence = new ArrayList<>();
        	
        	for (int j = 0; j <= i; j++) {
        		numberSequence.add(j);
			}

        	roulette.addGuess(new NumberGuess(36, numberSequence));
        	assertEquals(roulette.calculateGuessWinnings(), Roulette.RoulettSize * 36.0 / numberSequence.size());

		}
    }
}
