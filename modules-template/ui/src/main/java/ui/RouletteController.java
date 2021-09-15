package ui;
//
//import roulette.Roulette;
//import roulette.TemporaryUser;
import core.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
//import roulette.Roulette;

//import calc.core.main.src.java.core.roulette.Roulette;

public class RouletteController {
	
//	Roulette r = new Roulette(new TemporaryUser(100));
	
	int size = 36;
	
	@FXML
	Pane board;
	
	@FXML
	public void initialize() {
		System.out.println("ye");
		
		double width = 600;
		double height = 290;
		
		System.out.println(width + " " + height);	
		
		double paneWidth = width / (size/3);
		double paneHeight= height / (size/12);
		
		for (int i = 0; i < 1 + size/3; i++) {
			for (int j = 0; j < 3; j++) {
				Pane p = new Pane();
				
				p.setTranslateX(paneWidth * i);
				p.setTranslateY(paneHeight * j);
								
				p.setPrefWidth(paneWidth);
				p.setPrefHeight(paneHeight);
				
				if ((i + j) % 2 == 0) {
					p.setStyle("-fx-background-color:red;");
				} else {
					p.setStyle("-fx-background-color:black;");
				}
				
				Label l = new Label("" + (j + i*3 + 1));
				l.setStyle("-fx-text-fill:white;");
				l.setFont(new Font(16));
				l.setPrefSize(0, 0);
				
				
				p.getChildren().add(l);
				board.getChildren().add(p);
				
			
				
				p.setOnMouseClicked(e -> System.out.println(((Label)((Pane) e.getTarget()).getChildren().get(0)).getText()));
			}
		}
		
		
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("y");
//		Roulette r = new Roulette(new TemporaryUser(1000));
	}

}
