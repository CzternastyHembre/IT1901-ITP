package tempRoulette;

public class PokerChip {
	public static final String[] colors = {"#a60000", "#cfc132", "#1b32a8", "#ffffff", "#4a4a4a"};
	public static final int[] values = {1,5,25,50,100,500,1000};
	
	
	int index;
		
	public PokerChip(int index) {
		this.index = index;
		
		// TODO Auto-generated constructor stub
	}
	
	public String getColor() {
		return colors[index % colors.length];
	}
	
	


}
