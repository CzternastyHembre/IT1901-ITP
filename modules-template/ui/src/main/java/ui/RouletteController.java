package ui;

//
//import roulette.Roulette;
//import roulette.TemporaryUser;
import core.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import tempRoulette.PokerChip;
import tempRoulette.NumberGuess;
import tempRoulette.Roulette;
//import roulette.Roulette;
import tempRoulette.TemporaryUser;

//import calc.core.main.src.java.core.roulette.Roulette;

public class RouletteController {
	
	
	int size = 36;
	
	@FXML
	Pane board;
	@FXML
	Pane chipFolder;
	
	private Roulette r;
	private TemporaryUser user;
	
	
	double width = 600;
	double height = 290;
	
	double paneWidth = width / (size/3);
	double paneHeight= height / (size/12);

	private int valueChip= 1;
	
	
	@FXML
	public void initialize() {
		user = new TemporaryUser(1000);
		r = new Roulette(user);
		
		for (int i = 0; i < size/3; i++) {
			for (int j = 0; j < 3; j++) {
				Pane p = new Pane();
				
				p.setTranslateX(paneWidth * i);
				p.setTranslateY(paneHeight * (2 - j));
								
				p.setPrefWidth(paneWidth);
				p.setPrefHeight(paneHeight);
				
				if ((i + j) % 2 == 0) {
					p.setStyle("-fx-background-color:red;");
				} else {
					p.setStyle("-fx-background-color:black;");
				}
				
				int number = j + i*3 + 1;
				
				board.getChildren().add(p);				
			
				p.setOnMouseClicked(e -> {addNumberGuess(number);addChip(number-1);run();});
				
			}
		}
		for (int i = 1; i <= Roulette.RoulettSize; i++) {
			Label l = new Label("" + i);
			l.setStyle("-fx-text-fill:white;");
			l.setFont(new Font(16));
			Pane tile = (Pane) board.getChildren().get(i-1);
			tile.getChildren().add(l);
		}
		
		for (int i = 0; i < PokerChip.values.length; i++) {
			Circle c = getChip(i);
			c.setTranslateX(i * 30 + 50);
			int a = i;
			chipFolder.getChildren().add(c);
			c.setOnMouseClicked(e -> {
				((Circle) chipFolder.getChildren().get(valueChip)).setStroke(null);
				valueChip = a;
				c.setStyle("-fx-border");
				c.setStroke(Paint.valueOf("black"));
				c.setStrokeWidth(5);
			});
			if (i == valueChip) {
				c.setStyle("-fx-border");
				c.setStroke(Paint.valueOf("black"));
				c.setStrokeWidth(5);				
			}
		}
	}
	
	private Circle getChip(int n) {
		Circle circle = new Circle();
		PokerChip p = new PokerChip(n);
		int r = 15;
		circle.setRadius(r);
		circle.setFill(Color.valueOf(p.getColor()));
		circle.setTranslateX(paneWidth / 2);
		circle.setTranslateY(paneHeight / 2);
		return circle;
		
	}
	
	private void addChip(int n) {
		Pane tile = (Pane) board.getChildren().get(n);
		tile.getChildren().add(getChip(valueChip));
	}
	
	
	private void addNumberGuess(int n) {
		user.addGuess(new NumberGuess(PokerChip.values[valueChip], n));
		System.out.println(n);
	}
	
	private void run() {
		System.out.println(r.calculate());
	}
	
	
	public static void main(String[] args) {
		System.out.println("y");
//		Roulette r = new Roulette(new TemporaryUser(1000));
	}

}
