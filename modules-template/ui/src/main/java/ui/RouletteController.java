package ui;

import java.util.ArrayList;
import java.util.List;

//
//import roulette.Roulette;
//import roulette.TemporaryUser;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import roulette.NumberGuess;
import roulette.PokerChip;
import roulette.Roulette;
import roulette.TemporaryUser;

//import calc.core.main.src.java.core.roulette.Roulette;

public class RouletteController {
	
		
	@FXML
	Pane anchorPane;
	@FXML
	Pane chipFolder;
	
	private Roulette rouletteGame;
	private TemporaryUser user;
	private List<Circle> chipList = new ArrayList<>();
	
	public final int FontSize = 20;
	

	private int valueChip= 1;
	
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
		
		
		for (int y = 0; y < rouletteRows; y++) {
			for (int x = 0; x < rouletteColums; x++) {
				Pane tile = new Pane();
				Label tileLabel = new Label();
				if (x == 0) {//The leftmost column
					if (y == 0) {
						tile.setPrefSize(tileWidth, tileHeight * rouletteRows);
						tile.setStyle("-fx-background-color:green;");
						anchorPane.getChildren().add(tile);
						
						tileLabel.setText("" + x);
						tileLabel.setFont(new Font(FontSize));
						tile.getChildren().add(tileLabel);
						tileLabel.setTranslateX(tileWidth / 2 - FontSize / 2);
						tileLabel.setTranslateY(tileHeight * rouletteRows / 2 - FontSize / 2);
						tile.setOnMouseClicked(e -> {addNumberGuess(0);addChip(0);});
					} 	
				} else if (x == rouletteColums - 1) { // The rightmost column
					tile.setPrefSize(tileWidth, tileHeight);
					tile.setTranslateX(tileWidth * x); 
					tile.setTranslateY(tileHeight * y); 
					tile.setStyle("-fx-background-color:green;");
					
					tileLabel.setText("Row " + y);
					tile.getChildren().add(tileLabel);
					
					anchorPane.getChildren().add(tile);
				} else {//all the columns in the middle (the numbers)
					Pane numberTile = new Pane();
					numberTile.setPrefSize(tileWidth, tileHeight);
					numberTile.setTranslateX(tileWidth * x); 
					numberTile.setTranslateY(tileHeight * y); 
					
					int number = (x - 1)*3 + (3- y);
					tileLabel.setText("" + number);
					numberTile.getChildren().add(tileLabel);
					anchorPane.getChildren().add(numberTile);
					int index = anchorPane.getChildren().indexOf(numberTile);
					numberTile.setOnMouseClicked(e -> {addNumberGuess(number);addChip(index);});
					
					

				}
				
//				String style = tile.getStyle();
//				tile.setStyle(style + "-fx-cursor:pointer;");
			}
		}
		for (int i = 0; i < PokerChip.values.length; i++) {
			Circle c = getChip(i);
			c.setTranslateX(i * 30 + 50);
			c.setStroke(null);
			int a = i;
			chipFolder.getChildren().add(c);
			c.setOnMouseClicked(e -> {
				((Circle) chipFolder.getChildren().get(valueChip)).setStroke(null);
				valueChip = a;
				c.setStyle("-fx-border");
				c.setStroke(Paint.valueOf("black"));
			});
			if (i == valueChip) {
				c.setStyle("-fx-border");
				c.setStroke(Paint.valueOf("black"));
			}
		}

	}
	
	private void addNumberGuess(int number) {
		NumberGuess g = new NumberGuess(PokerChip.values[valueChip], number);
		user.addGuess(g);
	}
	
	private void addChip(int number) {
		Pane tile = (Pane) anchorPane.getChildren().get(number);
		Circle chip = getChip(valueChip);
		double yTranslated = chip.getTranslateY();
		int chipSize = tile.getChildren().size() - 1; 	// - 1 because first child is a label
		if (chipSize < 10) {
			chip.setTranslateY(yTranslated + -chipSize*2);			
		} else {
			chip.setTranslateY(yTranslated + -10*2);			

		}
		tile.getChildren().add(chip);
	}
	
	private Circle getChip(int n) {
	Circle circle = new Circle();
	PokerChip p = new PokerChip(n);
	int r = 15;
	circle.setRadius(r);
	circle.setFill(Color.valueOf(p.getColor()));
	circle.setTranslateX(tileWidth / 2);
	circle.setTranslateY(tileHeight / 2);
	circle.setStroke(Paint.valueOf("black"));
	return circle;
	
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
