package roulette;

public class PokerChip {
	public static final String[] colors = {"#a60000", "#cfc132", "#1b32a8", "#ffffff", "#4a4a4a", "ed3434", "12ff34"};
	public static final int[] values = {1,5,25,50,100,500,1000,5000,10000,50000};
	public static final String[] textValues = {"  1", "  5"," 25", " 50", "100", "500", " 1k", " 5k", "10k", "50k"};
	
	
	
	
	public static String getColor(int index) {
		return colors[index % colors.length];
	}
	
	public static int getValue(int index) {
		return values[index];
	}
	
	public static String getTextValue(int index) {
		return textValues[index];
	}
	
	


}
