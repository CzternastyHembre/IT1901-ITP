package roulette;

public class PokerChip {
	public static final String[] colors = {"#a60000", "#cfc132", "#1b32a8", "#ffffff", "#4a4a4a", "ed3434", "12ff34"};
	public static final int[] values = {1,5,25,50,100,500,1000,2500};
	public static final String[] textValues = {"  1", "  5"," 25", " 50", "100", "500", " 1k", " 5k"};
	
	
	int index;
		
	public PokerChip(int index) {
		this.index = index;
	}
	
	public String getColor() {
		return colors[index % colors.length];
	}
	
	public int getValue() {
		return values[index];
	}
	
	public String getTextValue() {
		return textValues[index];
	}
	
	


}
