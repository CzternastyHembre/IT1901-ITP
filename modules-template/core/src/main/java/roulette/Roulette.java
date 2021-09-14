package roulette;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class Roulette {
	
	
	public static final UnaryOperator<Integer> firstrow = a -> ((a+2) % 3);
	public static final UnaryOperator<Integer> secondRow = a -> ((a+1) % 3);
	public static final UnaryOperator<Integer> thirdRow = a -> (a == 0 ? -1 : a % 3);

	public static final UnaryOperator<Integer> oneToTwelve = a -> (a > 0 && a < 13 ? 0 : -1);
	public static final UnaryOperator<Integer> ThirteenToTventyfour= a -> (a > 12 && a < 25 ? 0 : -1);
	public static final UnaryOperator<Integer> TventyfourToThirtysix= a -> (a > 24 && a < 37 ? 0 : -1);
	
	private HashMap<Integer, UnaryOperator<Integer>> myGuesses = new HashMap<>();
	
	public void setGuess(UnaryOperator<Integer> op, int amount) {
		myGuesses.put(amount, op);
	}
	

	private static int rolledNumber;
	
	

	
	public void setNumber(int number) {
		Roulette.rolledNumber = number;
	}
	
	public void makeThings() {
		List l = new ArrayList<>();
		
		for (int i = 0; i < 37; i++) {
			if (i % 3 == 2) {
				l.add(i);
			}
		}
		System.out.println(l);
	}
	
	
	public boolean calculate(UnaryOperator<Integer> op) {
		
		return op.apply(rolledNumber) == 0;
	}
	
	
	
	public static void main(String[] args) {
		Roulette r = new Roulette();
		r.setNumber(13);
		
		System.out.println(r.calculate(oneToTwelve));
	}
}
