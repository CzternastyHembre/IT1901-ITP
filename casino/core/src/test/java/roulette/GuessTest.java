package roulette;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import user.User;

public class GuessTest {

    Roulette roulette;

    
    @BeforeEach
    public void berforeEach() {
    	roulette = new Roulette(new User("bob", 1000));

    }
    
    @Test
    public void negativeAmountTest() {
    	assertThrows(IllegalArgumentException.class, () -> new NumberGuess(0, 1));
    	assertThrows(IllegalArgumentException.class, () -> new ListGuess(-1, 1, 12));
    	assertThrows(IllegalArgumentException.class, () -> new PatternGuess(-1, 1, 2));
    }
    
    @Test
    public void getNumbersTest() {
    	double amount = 10;
    	Guess numberGuess = new NumberGuess(amount, 1);
    	List<Integer> numbers = new ArrayList<>();
    	numbers.add(1);
    	
    	assertEquals(numberGuess.getNumbers(), numbers);
    	
    	numbers.clear();
    	Guess listGuess  = new ListGuess(amount, 1, 12);
    	for (int i = 1; i <= 12; i++) {
			numbers.add(i);
		}
    	assertEquals(listGuess.getNumbers(), numbers);
    	
    	numbers.clear();
    	Guess patternGuess  = new PatternGuess(amount, 1, 2);
    	for (int i = 1; i <= Roulette.rouletteSize; i += 2) {
			numbers.add(i);
		}
    	assertEquals(patternGuess.getNumbers(), numbers);
    }
}
