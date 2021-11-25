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

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Class for setting standards in the UI.
 * Creates standard circles and chips in the UI.
 */

public class CasinoElements {

  private static final int[] values = { 1, 5, 25, 50, 100, 500, 1000, 5000, 10000, 50000 };
  public static final double chipRadius = 20;
  public static final double fontSize = 15;
  public static final double largeFontSize = 30;
  public static final Font textFont = Font.font("Arial", FontWeight.BOLD, fontSize);
  public static final Font largeTextFont = Font.font("Arial", FontWeight.BOLD, largeFontSize);
  public static final Color textColor = Color.WHITE;

  public static int getValue(int index) {
    return values[index];
  }

  public static int getValuesSize() {
    return values.length;
  }


  public static String getColorName(int value){
    return Arrays.stream(ChipColor.values())
            .collect(Collectors.toList())
            .get(value).getColorName();
  }

  public static String getTextColor(int value){
    return Arrays.stream(TextColor.values())
            .collect(Collectors.toList())
            .get(value).getTextColor();
  }
  /**
   * //TODO.
   */

  public static Pane getChip(int valueIndex) {

    Circle circle = new Circle();
    circle.setRadius(chipRadius);
    circle.setFill(Color.valueOf(getColorName(valueIndex)));
    circle.setStroke(Paint.valueOf("black"));
    circle.setStrokeWidth(6);
    circle.setStrokeLineCap(StrokeLineCap.BUTT); // The poker chip border design
    circle.setStrokeType(StrokeType.INSIDE);
    circle.setStyle("-fx-stroke-dash-array:8;");

    Circle circle2 = new Circle(); // To get a "second border on the chip
    circle2.setRadius(chipRadius);
    circle2.setStroke(Paint.valueOf("black"));

    Pane chipContainer = new Pane();

    chipContainer.getChildren().add(circle2);
    chipContainer.getChildren().add(circle);

    Label chipLabel = new Label(getTextColor(valueIndex));
    chipLabel.setFont(textFont);
    chipLabel.setTranslateX(-fontSize / 3 * 2 - 3);
    chipLabel.setTranslateY(-fontSize / 2 - 2);
    chipLabel.setTextFill(Paint.valueOf("black"));

    chipContainer.getChildren().add(chipLabel);

    return chipContainer;

  }

}
