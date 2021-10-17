package ui;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CasinoElements {

  private static final String[] COLORS = { "#a60000", "#cfc132", "#1b32a8", "#ffffff", "#4a4a4a", "ed3434", "12ff34" };
  private static final int[] VALUES = { 1, 5, 25, 50, 100, 500, 1000, 5000, 10000, 50000 };
  private static final String[] TEXTVALUES = { "  1", "  5", " 25", " 50", "100", "500", " 1k", " 5k", "10k", "50k" };
  public static final double CHIPRADIUS = 20;
  public static final double FONTSIZE = 15;
  public static final double LARGEFONTSIZE = 30;
  public static final Font TEXTFONT = Font.font("Arial", FontWeight.BOLD, FONTSIZE);
  public static final Font LARGETEXTFONT = Font.font("Arial", FontWeight.BOLD, LARGEFONTSIZE);
  public static final Color TEXTCOLOR = Color.WHITE;
  public static final String BACKGROUNDSTYLE = "-fx-background-color:#075600";

  public static String getColor(int index) {
    return COLORS[index % COLORS.length];
  }

  public static int getValue(int index) {
    return VALUES[index];
  }

  public static int getValuesSize() {
    return VALUES.length;
  }

  public static String getTextValue(int index) {
    return TEXTVALUES[index];
  }

  public static Pane getChip(int valueIndex) {

    Pane chipContainer = new Pane();

    Circle circle = new Circle();
    circle.setRadius(CHIPRADIUS);
    circle.setFill(Color.valueOf(CasinoElements.getColor(valueIndex)));
    circle.setStroke(Paint.valueOf("black"));
    circle.setStrokeWidth(6);
    circle.setStrokeLineCap(StrokeLineCap.BUTT);// The poker chip border design
    circle.setStrokeType(StrokeType.INSIDE);
    circle.setStyle("-fx-stroke-dash-array:8;");

    Circle circle2 = new Circle();// To get a "second border on the chip
    circle2.setRadius(CHIPRADIUS);
    circle2.setStroke(Paint.valueOf("black"));

    chipContainer.getChildren().add(circle2);
    chipContainer.getChildren().add(circle);

    Label chipLabel = new Label(CasinoElements.getTextValue(valueIndex));
    chipLabel.setFont(TEXTFONT);
    chipLabel.setTranslateX(-FONTSIZE / 3 * 2 - 3);
    chipLabel.setTranslateY(-FONTSIZE / 2 - 2);
    chipLabel.setTextFill(Paint.valueOf("black"));

    chipContainer.getChildren().add(chipLabel);

    return chipContainer;

  }

}
