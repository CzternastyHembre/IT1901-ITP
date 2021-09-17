package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.FadeTransition;
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
import javafx.util.Duration;
import roulette.Guess;
import roulette.ListGuess;
import roulette.NumberGuess;
import roulette.PatternGuess;
import roulette.PokerChip;
import roulette.Roulette;
import roulette.TemporaryUser;

//import calc.core.main.src.java.core.roulette.Roulette;

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
	
	private Roulette rouletteGame;
	private TemporaryUser user;
	private List<Circle> chipList = new ArrayList<>();
	private Map<Integer, Pane> numbersTilesMap = new HashMap<>();
	
	public final int FontSize = 15;
	private final Font textFont = Font.font("Arial", FontWeight.BOLD, FontSize);
	private int chipRadius = 20;
		
	private PokerChip pokerChip = new PokerChip(0);
	
	private int rouletteRows = 3 ;
	private int rouletteColums = 14;
	
	private double width = 600;
	private double height = 290 * 3 / 5;
	
	double tileWidth = width / rouletteColums;
	double tileHeight= height / rouletteRows;
	@FXML
	public void initialize() {
		/*
		 * Takes in an temporary user for now
		 */
		user = new TemporaryUser(1000);
		rouletteGame = new Roulette(user);
		
		moneyBettedLabel.setText("" + user.getSumOfBets());
		moneyLabel.setText("" + user.getBalance());

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
				Guess guess = new NumberGuess(number);
				setGuess(guess, tile);
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
			int increment = 3;;
			
			Guess guess = new PatternGuess(start, increment);
			setGuess(guess, tile);
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
			
			Guess guess = new ListGuess(startNumber, endNumber);
			setGuess(guess, tile);
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
			
			Guess guess;
			
			switch (i) {
				case 0 -> {guess = new ListGuess(1, 18); tileLabel.setText("1-18");}
				case 1 -> {guess = new PatternGuess(2, 2); tileLabel.setText("EVEN");}
				case 2 -> {guess = new PatternGuess(1, 2); tileLabel.setText("ODD");}
				case 3 -> {guess = new ListGuess(19, 36); tileLabel.setText("1-18");}
				default -> throw new IllegalArgumentException();
			}
			setGuess(guess, tile);

		}
		
		for (Node node : anchorPane.getChildren()) {
			Pane tile = (Pane) node;
			String oldStyle = tile.getStyle();
			tile.setStyle(oldStyle + "-fx-border-color:white;");
			
			Label tileLabel = (Label) tile.getChildren().get(0);
			tileLabel.setFont(textFont);
			tileLabel.setTextFill(Paint.valueOf("white"));
		}
		
		//Adding the select poker chip
		for (int i = 0; i < PokerChip.values.length; i++) {
			Pane chipContainer = getChip(i);
			String style = chipContainer.getStyle();
			
			chipContainer.setTranslateX(i * (chipRadius*2 + 4) + 20);
			chipContainer.setTranslateY(chipFolder.getPrefHeight() / 2);
			int valueIndex = i;
			chipFolder.getChildren().add(chipContainer);
			chipContainer.setOnMouseClicked(e -> {
				Pane oldChipContainer = (Pane) chipFolder.getChildren().get(pokerChip.getIndex());
				
				oldChipContainer.setStyle(style);
				chipContainer.setStyle(style +  "-fx-opacity:0.5;");
				pokerChip.setIndex(valueIndex);
			});
			if (valueIndex == pokerChip.getIndex()) {
				chipContainer.setStyle(style + "-fx-opacity:0.5;");
			}
		}
	}
	
	private void setGuess(Guess guess, Pane tile) {
		List<Integer> numbers = guess.getNumbers();
		tile.setOnMouseClicked(e -> addGuess(guess, tile));
		
		tile.setOnMousePressed(e -> numbers.forEach(num -> numbersTilesMap.get(num).setOpacity(0.5)));
		tile.setOnMouseReleased(e -> numbers.forEach(num -> numbersTilesMap.get(num).setOpacity(1)));
		
		}
	
	private void addGuess(Guess guess, Pane tile) {
		try {
			guess.setAmount(pokerChip.getValue());
			user.addGuess(guess);
			moneyBettedLabel.setText("" + user.getSumOfBets());
			moneyLabel.setText("" + user.getBalance());	
			addChip(tile);
			feedBackLabel.setText(null);
			
		} catch (Exception e) {
			feedBackLabel.setText(e.getMessage());
		}
	}
	
	
	private void addChip(Pane tile) {
		
		double tileWidth = tile.getPrefWidth();
		double tileHeight = tile.getPrefHeight();
		
		Pane chipContainer = getChip(pokerChip.getIndex());
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
	PokerChip pokerChip = new PokerChip(valueIndex);
	circle.setRadius(chipRadius);
	circle.setFill(Color.valueOf(pokerChip.getColor()));
	circle.setStroke(Paint.valueOf("black"));
	circle.setStrokeWidth(6);
	circle.setStrokeLineCap(StrokeLineCap.BUTT);
	circle.setStrokeType(StrokeType.INSIDE);
	circle.setStyle("-fx-stroke-dash-array:8;");
	

	
	Circle circle2 = new Circle();
	circle2.setRadius(chipRadius);
	circle2.setStroke(Paint.valueOf("black"));
	
	chipContainer.getChildren().add(circle2);
	chipContainer.getChildren().add(circle);
	
	
	Label chipLabel = new Label(pokerChip.getTextValue());
	chipLabel.setFont(textFont);
	chipLabel.setTranslateX(-FontSize/3*2 - 3);
	chipLabel.setTranslateY(-FontSize/2 - 2);
	chipLabel.setTextFill(Paint.valueOf("black"));
	
	chipContainer.getChildren().add(chipLabel);
	
	return chipContainer;
	
}

	
	@FXML
	public void run() {
		
	}
}
	
	
//	@FXML
//	public void initialize() {
//		user = new TemporaryUser(1000);
//		r = new Roulette(user);
//		
//		for (int i = 0; i < size/3; i++) {
//			for (int j = 0; j < 3; j++) {
//				Pane p = new Pane();
//				
//				p.setTranslateX(paneWidth * i);
//				p.setTranslateY(paneHeight * (2 - j));
//								
//				p.setPrefWidth(paneWidth);
//				p.setPrefHeight(paneHeight);
//				
//				if ((i + j) % 2 == 0) {
//					p.setStyle("-fx-background-color:red;");
//				} else {
//					p.setStyle("-fx-background-color:black;");
//				}
//				
//				int number = j + i*3 + 1;
//				
//				board.getChildren().add(p);				
//			
//				p.setOnMouseClicked(e -> {addNumberGuess(number);addChip(number-1);run();});
//				
//			}
//		}
//		for (int i = 1; i <= Roulette.RoulettSize; i++) {
//			Label l = new Label("" + i);
//			l.setStyle("-fx-text-fill:white;");
//			l.setFont(new Font(16));

//			Pane tile = (Pane) board.getChildren().get(i-1);
//			tile.getChildren().add(l);
//		}
//		
//		for (int i = 0; i < PokerChip.values.length; i++) {
//			Circle c = getChip(i);
//			c.setTranslateX(i * 30 + 50);
//			int a = i;
//			chipFolder.getChildren().add(c);
//			c.setOnMouseClicked(e -> {
//				((Circle) chipFolder.getChildren().get(valueChip)).setStroke(null);
//				valueChip = a;
//				c.setStyle("-fx-border");
//				c.setStroke(Paint.valueOf("black"));
//				c.setStrokeWidth(5);
//			});
//			if (i == valueChip) {
//				c.setStyle("-fx-border");
//				c.setStroke(Paint.valueOf("black"));
//				c.setStrokeWidth(5);				
//			}
//		}
//	}
//	
//	private Circle getChip(int n) {
//		Circle circle = new Circle();
//		PokerChip p = new PokerChip(n);
//		int r = 15;
//		circle.setRadius(r);
//		circle.setFill(Color.valueOf(p.getColor()));
//		circle.setTranslateX(paneWidth / 2);
//		circle.setTranslateY(paneHeight / 2);
//		return circle;
//		
//	}
//	
//	private void addChip(int n) {
//		Pane tile = (Pane) board.getChildren().get(n);
//		tile.getChildren().add(getChip(valueChip));
//	}
//	
//	
//	private void addNumberGuess(int n) {
//		user.addGuess(new NumberGuess(PokerChip.values[valueChip], n));
//		System.out.println(n);
//	}
//	
//	private void run() {
//		System.out.println(r.calculate());
//	}
//	
//	
//	public static void main(String[] args) {
//		System.out.println("y");
////		Roulette r = new Roulette(new TemporaryUser(1000));
//	}
//
//}
