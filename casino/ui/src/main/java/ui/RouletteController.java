package ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import roulette.Guess;
import roulette.ListGuess;
import roulette.NumberGuess;
import roulette.PatternGuess;
import roulette.PokerChip;
import roulette.Roulette;
import saveHandler.UserSaveHandler;
import user.User;

public class RouletteController {
	
		
	@FXML
	Pane anchorPane;
	@FXML
	Pane chipFolder;
	@FXML
	Label moneyLabel;
	@FXML
	Label moneyBettedLabel;
	@FXML
	Label feedBackLabel;
	@FXML
	Label nameLabel;
	@FXML
	Label rolledNumberLabel;
	
	private Roulette rouletteGame;
	private User user;
	private Map<Integer, Pane> numbersTilesMap = new HashMap<>();
	
	public final int FontSize = 15;
	private final Font textFont = Font.font("Arial", FontWeight.BOLD, FontSize);
	private int chipRadius = 20;
		
	private int valueIndex = 0;
	
	@FXML
	public void initialize() {
		/*
		 * Takes in an temporary user for now
		 */
		user = UserSaveHandler.getActiveUser();
		rouletteGame = new Roulette(user);
		
		int rouletteRows = 3 ;
		int rouletteColums = 14;
		
		double width = 600;
		double height = 290 * 3 / 5;
		
		double tileWidth = width / rouletteColums;
		double tileHeight= height / rouletteRows;
		
		updateLables();
		nameLabel.setText(user.getUsername());

		anchorPane.setStyle("-fx-background-color:#075600");
		chipFolder.setStyle("-fx-background-color:#075600");
		
		//Adding all the "numberTiles" the tiles that have a single number to bet on
		for (int y = 0; y < rouletteRows; y++) 
		{
			for (int x = 0; x < rouletteColums - 1; x++) 
			{
				Pane tile = new Pane();
				Label tileLabel = new Label();
				int number = (x - 1)*3 + (3- y);
				
				if (x == 0) {//The leftmost column (0)
					if (y != 0) {
						continue;
					}
					tile.setPrefSize(tileWidth, tileHeight * rouletteRows);
					tile.setStyle("-fx-background-color:green;");
				} else {//all the columns in the middle (the numbers)
					tile.setPrefSize(tileWidth, tileHeight);
					tile.setTranslateX(tileWidth * x); 
					tile.setTranslateY(tileHeight * y);
					String backgroundColor = "-fx-background-color:" + ((x + y) % 2 == 0 ? "red;" : "black;" );
					tile.setStyle(backgroundColor);
				}
				
				tile.getChildren().add(tileLabel);
				setNumberGuess(tile, number);
//				tile.setOnMouseClicked(e -> {addNumberGuess(number, tile);});
				
				tileLabel.setText("" + number);
				numbersTilesMap.put(number, tile);
				anchorPane.getChildren().add(tile);
			}
		}
		for (int y = 0; y < 3; y++) {//The rightmost Column
			Pane tile = new Pane();
			Label tileLabel = new Label();
			
			tile.setPrefSize(tileWidth, tileHeight);
			tile.setTranslateX(tileWidth * (rouletteColums - 1)); 
			tile.setTranslateY(tileHeight * y); 
			tile.setStyle("-fx-background-color:green;");
			
			tileLabel.setText("Row " + (y + 1));
			tile.getChildren().add(tileLabel);
			anchorPane.getChildren().add(tile);
	
			int start = 3 - y;
			int increment = 3;
			
			setPatternGuess(tile, start, increment);
		}
		
		for (int x = 0; x < 3; x++) {//The 4. row
			Pane tile = new Pane();
			Label tileLabel = new Label();

			tile.setPrefSize(tileWidth * 4, tileHeight);
			tile.setTranslateX(tileWidth * x * 4 + tileWidth); 
			tile.setTranslateY(tileHeight * 3);
			tile.setStyle("-fx-background-color:green;");
			anchorPane.getChildren().add(tile);

			int startNumber = (x * 12) + 1;
			int endNumber = (x + 1) * 12;
			String tileString = startNumber + "-" + endNumber;
			tileLabel.setText("" + tileString);
			tile.getChildren().add(tileLabel);
			
			setListGuess(tile, startNumber, endNumber);
		}
		
		for (int i = 0; i < 4; i++) {//The 5. row
			Pane tile = new Pane();
			Label tileLabel = new Label();
			
			tile.setPrefSize(tileWidth * 3, tileHeight);
			tile.setTranslateX(tileWidth * i * 3 + tileWidth); 
			tile.setTranslateY(tileHeight * 4);
			tile.setStyle("-fx-background-color:green;");
			anchorPane.getChildren().add(tile);

			int startNumber = i;
			String tileString = startNumber + "";
			tileLabel.setText("" + tileString);
			
			tile.getChildren().add(tileLabel);	
			
			switch (i) {
				case 0 -> {setListGuess(tile, 1, 18);tileLabel.setText("1-18");}
				case 1 -> {setPatternGuess(tile, 2, 2); tileLabel.setText("EVEN");}
				case 2 -> {setPatternGuess(tile, 1, 2); tileLabel.setText("ODD");}
				case 3 -> {setListGuess(tile, 19, 36); tileLabel.setText("1-18");}
				default -> throw new IllegalArgumentException();
			}
		}
		
		for (Node node : anchorPane.getChildren()) {
			Pane tile = (Pane) node;
			String oldStyle = tile.getStyle();
			tile.setStyle(oldStyle + "-fx-border-color:white;");
			
			Label tileLabel = (Label) tile.getChildren().get(0);
			tileLabel.setFont(textFont);
			tileLabel.setTextFill(Paint.valueOf("white"));
		}
		
		//Adding the select poker chips
		for (int i = 0; i < PokerChip.values.length; i++) {
			Pane chipContainer = getChip(i);
			String style = chipContainer.getStyle();
			
			chipContainer.setTranslateX(i * (chipRadius*2 + 4) + 20);
			chipContainer.setTranslateY(chipFolder.getPrefHeight() / 2);
			int currentIndex = i;
			chipFolder.getChildren().add(chipContainer);
			
			chipContainer.setOnMouseClicked(e -> {
				Pane oldChipContainer = (Pane) chipFolder.getChildren().get(valueIndex);
				oldChipContainer.setOpacity(1);
					
				chipContainer.setOpacity(0.5);
				this.valueIndex = currentIndex;
			});
			if (this.valueIndex == currentIndex) {
				chipContainer.setStyle(style + "-fx-opacity:0.5;");
			}
		}
	}
	
	@FXML
	public void run() {
		double winnings = rouletteGame.calculate();
		feedBackLabel.setText("you won: " + winnings);
		UserSaveHandler.UpdateUser(user);
		updateLables();
		clearChips();
		rolledNumberLabel.setText( "" + rouletteGame.getRolledNumber());
//		System.out.println("" + rouletteGame.getRolledNumber());
	}
	
	private void clearChips() {
		anchorPane.getChildren().forEach((n) -> {
			Pane tile = (Pane) n;
			for (int i = tile.getChildren().size() - 1; i > 0; i--) {
				tile.getChildren().remove(i);
			}
		});;
	}
	
	private void addChip(Pane tile) {
		
		double tileWidth = tile.getPrefWidth();
		double tileHeight = tile.getPrefHeight();
		
		Pane chipContainer = getChip(valueIndex);
		chipContainer.setTranslateX(tileWidth / 2);
		chipContainer.setTranslateY(tileHeight / 2);

		double yTranslated = chipContainer.getTranslateY();
		int chipSize = tile.getChildren().size() - 1; 	// - 1 because first child is a label
		if (chipSize < 7) {
			chipContainer.setTranslateY(yTranslated - chipSize*3);			
		} else {
			chipContainer.setTranslateY(yTranslated + -7*3);			

		}
		tile.getChildren().add(chipContainer);
	}
	
	
	private Pane getChip(int valueIndex) {
		
	Pane chipContainer = new Pane();
		
	Circle circle = new Circle();
	circle.setRadius(chipRadius);
	circle.setFill(Color.valueOf(PokerChip.getColor(valueIndex)));
	circle.setStroke(Paint.valueOf("black"));
	circle.setStrokeWidth(6);
	circle.setStrokeLineCap(StrokeLineCap.BUTT);//The poker chip border design
	circle.setStrokeType(StrokeType.INSIDE);
	circle.setStyle("-fx-stroke-dash-array:8;");
	

	
	Circle circle2 = new Circle();//To get a "seconde border on the chip
	circle2.setRadius(chipRadius);
	circle2.setStroke(Paint.valueOf("black"));
	
	chipContainer.getChildren().add(circle2);
	chipContainer.getChildren().add(circle);
	
	
	Label chipLabel = new Label(PokerChip.getTextValue(valueIndex));
	chipLabel.setFont(textFont);
	chipLabel.setTranslateX(-FontSize/3*2 - 3);
	chipLabel.setTranslateY(-FontSize/2 - 2);
	chipLabel.setTextFill(Paint.valueOf("black"));
	
	chipContainer.getChildren().add(chipLabel);
	
	return chipContainer;
	
}
	private void setNumberGuess(Pane tile, int number) {
		tile.setOnMouseClicked(e -> {
			NumberGuess guess = new NumberGuess(PokerChip.getValue(valueIndex), number);
			addGuess(guess, tile);
		});
		setGuessAnimation(tile, new NumberGuess(PokerChip.getValue(valueIndex), number).getNumbers());
	}
	
	private void setListGuess(Pane tile, int start, int end) {
		tile.setOnMouseClicked(e -> {
			ListGuess guess = new ListGuess(PokerChip.getValue(valueIndex), start, end);
			addGuess(guess, tile);
		});
		setGuessAnimation(tile, new ListGuess(PokerChip.getValue(valueIndex), start, end).getNumbers());
	}
	
	private void setPatternGuess(Pane tile, int start, int increment) {
		tile.setOnMouseClicked(e -> {
			PatternGuess guess = new PatternGuess(PokerChip.getValue(valueIndex), start, increment);
			addGuess(guess, tile);
		});
		setGuessAnimation(tile, new PatternGuess(PokerChip.getValue(valueIndex), start, increment).getNumbers());
	}
	
	private void setGuessAnimation(Pane tile, List<Integer> numbers) {
		tile.setOnMousePressed(e -> numbers.forEach(num -> numbersTilesMap.get(num).setOpacity(0.5)));
		tile.setOnMouseReleased(e -> numbers.forEach(num -> numbersTilesMap.get(num).setOpacity(1)));
		}
	
	private void addGuess(Guess guess, Pane tile) {
		try {
			rouletteGame.addGuess(guess);
			updateLables();
			addChip(tile);
			feedBackLabel.setText(null);
			
		} catch (Exception e) {
			feedBackLabel.setText(e.getMessage());
		}
	}
	
	private void updateLables() {
		moneyBettedLabel.setText("" + rouletteGame.getSumOfBets());
		moneyLabel.setText("" + user.getBalance());
	}

}

	
	