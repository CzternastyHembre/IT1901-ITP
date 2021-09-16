package ui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import roulette.*;

public class RouletteController {
	
	
	int size = 36;
	
	@FXML
	Pane board;
	@FXML
	Pane chipFolder;
	@FXML
	Label moneyLabel;
	
	private Roulette r;
	private TemporaryUser user;
	
	
	double width = 600;
	double height = 290;
	
	double paneWidth = width / (size/3);
	double paneHeight= height / (size/12);

	private int valueChip= 0;
	
	
	@FXML
	public void initialize() {
		board.getChildren().clear();
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
			
				p.setOnMouseClicked(e -> {addNumberGuess(number);addChip(number-1);});
				
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
			c.setTranslateX(i * 30 + 20);
			int a = i;
			chipFolder.getChildren().add(c);
			
			c.setOnMouseClicked(e -> {
				((Circle) chipFolder.getChildren().get(valueChip)).setStroke(null);
				valueChip = a;
				c.setStyle("-fx-border-style:dashed;");
				c.setStroke(Paint.valueOf("black"));
				c.setStrokeWidth(5);
			});
			if (i == valueChip) {
				c.setStyle("-fx-border-style:dashed;");
				c.setStroke(Paint.valueOf("black"));
				c.setStrokeWidth(5);				
			}
		}
		for (int i = 0; i < PokerChip.values.length; i++) {
			Label l = new Label("" + PokerChip.values[i]);
			l.setFont(new Font(14));
			l.setTextFill(Paint.valueOf("black"));
			l.setTranslateX(i * 30 + 10);
			l.setTranslateY(20);
			chipFolder.getChildren().add(l);

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
	
	@FXML
	private void run() {
		double winnings = r.calculate();
		updateMoneyLabel();
		initialize();
	}
	
	private void updateMoneyLabel() {
		this.moneyLabel.setText("" + user.getBalance());
	}
	

}
