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

/**
 * Class for setting standards in the UI.
 * Creates standard circles and chips in the UI.
 */

public class CASINO_ELEMENTS {

  private static final String[] COLORS = {
    "#a60000", "#cfc132", "#1b32a8", "#ffffff", "#4a4a4a", "ed3434", "12ff34" };
  private static final int[] VALUES = { 1, 5, 25, 50, 100, 500, 1000, 5000, 10000, 50000 };
  private static final String[] TEXT_VALUES = {
    "  1", "  5", " 25", " 50", "100", "500", " 1k", " 5k", "10k", "50k" };
  public static final double CHIP_RADIUS = 20;
  public static final double FONTSIZE = 15;
  public static final double LARGE_FONTSIZE = 30;
  public static final Font TEXT_FONT = Font.font("Arial", FontWeight.BOLD, FONTSIZE);
  public static final Font LARGE_TEXT_FONT = Font.font("Arial", FontWeight.BOLD, LARGE_FONTSIZE);
  public static final Color TEXT_COLOR = Color.WHITE;

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
    return TEXT_VALUES[index];
  }

  /**
   * //TODO.
   */

  protected static Pane getChip(int valueIndex) {

    Circle circle = new Circle();
    circle.setRadius(CHIP_RADIUS);
    circle.setFill(Color.valueOf(CASINO_ELEMENTS.getColor(valueIndex)));
    circle.setStroke(Paint.valueOf("black"));
    circle.setStrokeWidth(6);
    circle.setStrokeLineCap(StrokeLineCap.BUTT); // The poker chip border design
    circle.setStrokeType(StrokeType.INSIDE);
    circle.setStyle("-fx-stroke-dash-array:8;");

    Circle circle2 = new Circle(); // To get a "second border on the chip
    circle2.setRadius(CHIP_RADIUS);
    circle2.setStroke(Paint.valueOf("black"));

    Pane chipContainer = new Pane();

    chipContainer.getChildren().add(circle2);
    chipContainer.getChildren().add(circle);

    Label chipLabel = new Label(CASINO_ELEMENTS.getTextValue(valueIndex));
    chipLabel.setFont(TEXT_FONT);
    chipLabel.setTranslateX(-FONTSIZE / 3 * 2 - 3);
    chipLabel.setTranslateY(-FONTSIZE / 2 - 2);
    chipLabel.setTextFill(Paint.valueOf("black"));

    chipContainer.getChildren().add(chipLabel);

    return chipContainer;

  }

}
