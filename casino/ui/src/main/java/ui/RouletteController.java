package ui;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import roulette.Guess;
import roulette.Roulette;
import savehandler.UserSaveHandler;
import ui.menuItem.CasinoMenu;

/**
 * Controller for the roulette game.
 */

public class RouletteController extends CasinoMenu {

  @FXML Pane chipFolder;
  @FXML Pane rouletteBoardPane;
  @FXML Pane controllsFolder;
  @FXML Pane gridPane;
  @FXML Label moneyLabel;
  @FXML Label moneyBettedLabel;
  @FXML Label feedBackLabel;
  @FXML Label nameLabel;
  @FXML Label textLabel1;
  @FXML Label textLabel2;
  @FXML MenuItem mainMenu;
  @FXML MenuItem lobby;
  @FXML MenuItem exit;

  private Roulette rouletteGame;
  private Label rolledNumberLabel = new Label();
  private Map<Integer, Pane> numbersTilesMap = new HashMap<>();
  private List<Pane> chipList = new ArrayList<>();

  private int chipValueIndex = 0;

  /**
   * The number sequense on an European rouletteWheel.
   **/
  
  private final List<Integer> rouletteWheelNumberSequence = Arrays.asList(
          0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27,
      13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20,
          14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26);
  private Pane rouletteWheelContainer;


  /**
   * Creating the rouletteBoard.
   *
   */

  public RouletteController() {
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    createMenu();
    rouletteGame = new Roulette(user);
    List<Label> labelList = new ArrayList<>(Arrays.asList(
            moneyLabel, moneyBettedLabel, feedBackLabel, nameLabel,
            textLabel1, textLabel2, rolledNumberLabel));
    updateUserLables();
    nameLabel.setText(user.getUsername());

    int rouletteRows = 5;
    int rouletteColumns = 14;
    double tileWidth = rouletteBoardPane.getPrefWidth() / rouletteColumns;
    double tileHeight = rouletteBoardPane.getPrefHeight() / rouletteRows;

    // Adding all the "numberTiles" the tiles that have a single number to bet on
    for (int y = 0; y < rouletteRows - 2; y++) {
      for (int x = 0; x < rouletteColumns - 1; x++) {
        Pane tile = new Pane();
        Label tileLabel = new Label();
        int number = (x - 1) * 3 + (3 - y);

        if (x == 0) { // The leftmost column (#0)
          if (y != 0) {
            continue;
          }
          tile.setPrefSize(tileWidth, tileHeight * (rouletteRows - 2));

        } else { // all the columns in the middle (the numbers)
          tile.setPrefSize(tileWidth, tileHeight);
          tile.setTranslateX(tileWidth * x);
          tile.setTranslateY(tileHeight * y);
          String backgroundColor = "-fx-background-color:" + ((x + y) % 2 == 0 ? "red;" : "black;");
          tile.setStyle(backgroundColor);
        }
        tile.setId("" + number);
        tile.getChildren().add(tileLabel);
        setNumberGuess(tile, number);

        labelList.add(tileLabel);
        tileLabel.setText("" + number);
        numbersTilesMap.put(number, tile);
        rouletteBoardPane.getChildren().add(tile);
      }
    }
    for (int y = 0; y < 3; y++) { // The rightmost Column
      Pane tile = new Pane();
      tile.setPrefSize(tileWidth, tileHeight);
      tile.setTranslateX(tileWidth * (rouletteColumns - 1));
      tile.setTranslateY(tileHeight * y);
      Label tileLabel = new Label();
      tileLabel.setText("Row " + (y + 1));
      tile.getChildren().add(tileLabel);
      labelList.add(tileLabel);
      rouletteBoardPane.getChildren().add(tile);

      int start = 3 - y;
      int increment = 3;

      setPatternGuess(tile, start, increment);
    }

    for (int x = 0; x < 3; x++) { // The 4. row
      Pane tile = new Pane();
      tile.setPrefSize(tileWidth * 4, tileHeight);
      tile.setTranslateX(tileWidth * x * 4 + tileWidth);
      tile.setTranslateY(tileHeight * 3);

      rouletteBoardPane.getChildren().add(tile);

      Label tileLabel = new Label();
      int startNumber = (x * 12) + 1;
      int endNumber = (x + 1) * 12;
      String tileString = startNumber + "-" + endNumber;
      tileLabel.setText("" + tileString);
      tile.getChildren().add(tileLabel);
      setListGuess(tile, startNumber, endNumber);

      labelList.add(tileLabel);
    }
    for (int i = 0; i < 4; i++) { // The 5. row
      Pane tile = new Pane();

      tile.setPrefSize(tileWidth * 3, tileHeight);
      tile.setTranslateX(tileWidth * i * 3 + tileWidth);
      tile.setTranslateY(tileHeight * 4);
      rouletteBoardPane.getChildren().add(tile);

      Label tileLabel = new Label();
      String tileString = i + "";
      tileLabel.setText("" + tileString);

      labelList.add(tileLabel);
      tile.getChildren().add(tileLabel);

      switch (i) {
        case 0 -> {
          setListGuess(tile, 1, 18);
          tileLabel.setText("1-18");
        }
        case 1 -> {
          setPatternGuess(tile, 2, 2);
          tileLabel.setText("EVEN");
        }
        case 2 -> {
          setPatternGuess(tile, 1, 2);
          tileLabel.setText("ODD");
        }
        case 3 -> {
          setListGuess(tile, 19, 36);
          tileLabel.setText("19-36");
        }
        default -> throw new IllegalArgumentException();
      }
    }
    for (Node node : rouletteBoardPane.getChildren()) {
      if (node instanceof Pane tile) {
        String oldStyle = tile.getStyle();
        tile.setStyle(oldStyle + "-fx-border-color:white;");
      }
    }

    // Adding the select poker chips
    for (int i = 0; i < CASINO_ELEMENTS.getValuesSize(); i++) {
      Pane chipContainer = CASINO_ELEMENTS.getChip(i);
      String style = chipContainer.getStyle();
      chipContainer.setId("selectChip" + i);

      chipContainer.setTranslateX(i * (CASINO_ELEMENTS.CHIP_RADIUS * 2 + 4) + 20);
      chipContainer.setTranslateY(chipFolder.getPrefHeight() / 2);
      int currentIndex = i;
      chipFolder.getChildren().add(chipContainer);

      if (this.chipValueIndex == currentIndex) {
        chipContainer.setStyle(style + "-fx-opacity:0.5;");
      }

      chipContainer.setOnMouseClicked(e -> {
        Pane oldChipContainer = (Pane) chipFolder.getChildren().get(chipValueIndex);
        oldChipContainer.setOpacity(1);
        chipContainer.setOpacity(0.5);
        this.chipValueIndex = currentIndex;
      });

    }
    labelList.forEach(label -> {
      label.setFont(CASINO_ELEMENTS.TEXT_FONT);
      label.setTextFill(CASINO_ELEMENTS.TEXT_COLOR);
    });

    //Creating the rouletteWheel.
    rouletteWheelContainer = createRouletteWheel();
    gridPane.getChildren().add(rouletteWheelContainer);
    rouletteWheelContainer.setVisible(false);

    Circle circleWheelPointer = new Circle();
    circleWheelPointer.setRadius(CASINO_ELEMENTS.FONTSIZE - 2);
    circleWheelPointer.setStroke(Color.WHITE);
    circleWheelPointer.setStrokeWidth(2);
    circleWheelPointer.setTranslateX(rouletteWheelContainer.getPrefWidth() / 2);
    circleWheelPointer.setFill(null);
    circleWheelPointer.setTranslateY(15);
    rouletteWheelContainer.getChildren().add(circleWheelPointer);

    rolledNumberLabel.setFont(CASINO_ELEMENTS.LARGE_TEXT_FONT);
    rolledNumberLabel.setTranslateX(rouletteBoardPane.getPrefWidth()
            / 2 - CASINO_ELEMENTS.LARGE_FONTSIZE / 2);
    rolledNumberLabel.setTranslateY(rouletteBoardPane.getPrefHeight()
            / 2 - CASINO_ELEMENTS.LARGE_FONTSIZE / 2);
    rouletteWheelContainer.getChildren().add(rolledNumberLabel);
  }

  /**
   * Plays the bet, spins the wheel and generates a winning number. Updates winnings
   * and user's balance
   *
   * @throws IOException uses a scanner object which requires throws
   */

  @FXML
  public void run() throws IOException, InterruptedException {
    restModel.updateUser(user);
    rouletteGame.rollNumber();

    int number = rouletteGame.getRolledNumber();

    setShowRouletteWheel(true);
    Pane rouletteWheelPivotPane = (Pane) rouletteWheelContainer.getChildren().get(0);
    rouletteWheelPivotPane.setRotate(0);
    double extraAngle = getAngle() * rouletteWheelNumberSequence.indexOf(number);

    RotateTransition rt = new RotateTransition(Duration.seconds(4), rouletteWheelPivotPane);
    rt.setByAngle(360 * 4 + 360 - extraAngle);
    rt.play();

    var winnings = rouletteGame.calculate();

    RotateTransition rt2 = new RotateTransition(Duration.seconds(2), rouletteWheelPivotPane);
    rt2.setOnFinished(e -> {
      setShowRouletteWheel(false);
      feedBackLabel.setText("you won: " + winnings);
      updateUserLables();
      clearChips();
      rolledNumberLabel.setText(null);
    });
    rt.setOnFinished(e -> {
      rolledNumberLabel.setText(rouletteGame.getRolledNumber()
              < 10 ? " " + rouletteGame.getRolledNumber()
          : "" + rouletteGame.getRolledNumber());
      rt2.play();
    });
  }

  /**
   * If {@code bool} is true, the rouletteWheel is shown, and the rest of the view is set to blurry.
   * If {@code bool} is false, the rouletteWheel is hidden.
   * The rest of the view removes the blur effect.
   *
   * @param bool a boolean that is either true or false.
   */
  private void setShowRouletteWheel(boolean bool) {
    if (bool) {
      rouletteWheelContainer.setVisible(true);

      rouletteBoardPane.setEffect(new BoxBlur());
      controllsFolder.setEffect(new BoxBlur());
      chipFolder.setEffect(new BoxBlur());
    } else {
      rouletteWheelContainer.setVisible(false);

      rouletteBoardPane.setEffect(null);
      controllsFolder.setEffect(null);
      chipFolder.setEffect(null);
    }
  }

  /**
   * Methos thats removes all the chips places on the {@code rouletteBoardPane}.
   */
  private void clearChips() {
    rouletteBoardPane.getChildren().forEach((n) -> {
      Pane tile = (Pane) n;
      if (tile.getChildren().size() > 1) {
        //The first element in the panes is a Label.
        // the remaining is panes representing pokerChips.
        tile.getChildren().subList(1, tile.getChildren().size()).clear();
      }
    });
  }

  /**
   * Adds a chip in the center of the {@link Pane} {@code tile}.
   *
   * @param tile the the chip is added on.
   */

  private void addChip(Pane tile) {

    double tileWidth = tile.getPrefWidth();
    double tileHeight = tile.getPrefHeight();

    Pane chipContainer = CASINO_ELEMENTS.getChip(chipValueIndex);
    chipContainer.setTranslateX(tileWidth / 2);
    chipContainer.setTranslateY(tileHeight / 2);

    double translateY = chipContainer.getTranslateY();
    int chipSize = tile.getChildren().size() - 1; // - 1 because first child is a label
    if (chipSize < 7) {
      chipContainer.setTranslateY(translateY - chipSize * 3);
    } else {
      chipContainer.setTranslateY(translateY + - 7 * 3);

    }
    tile.getChildren().add(chipContainer);
    chipList.add(chipContainer);
  }

  /**
   * Method that undoes the last guess in the {@link Roulette} game
   * Removes the last {@link Pane} chip added in {@code chipList}.
   */
  
  @FXML
  private void undoGuess() {
    if (chipList.isEmpty()) {
      return;
    }
    rouletteGame.undoGuess();
    Pane chip = chipList.get(chipList.size() - 1);
    chipList.remove(chip);
    Pane tile = (Pane) chip.getParent();
    tile.getChildren().remove(tile.getChildren().size() - 1);
    updateLables();
  }

  private void updateLables() {
    moneyBettedLabel.setText("" + rouletteGame.getSumOfBets());
    moneyLabel.setText("" + user.getBalance());
  }

  /**
   * Creates a {@link Guess} add calls the method {@code addGuess}.
   *
   * @param tile creates the {@link Guess} when clicked on.
   * @param number creates the {@link Guess} based on the number.
   */
  
  private void setNumberGuess(Pane tile, int number) {
    tile.setOnMouseClicked(e -> {
      Guess guess = Guess.numberGuess(CASINO_ELEMENTS.getValue(chipValueIndex), number);
      addGuess(guess, tile);
    });
    setGuessAnimation(tile, Guess.numberGuess(
            CASINO_ELEMENTS.getValue(chipValueIndex),
            number).getNumbers());
  }

  /**
   * Creates a {@link Guess} add calls the method {@code addGuess}.
   *
   * @param tile creates the {@link Guess} when clicked on.
   * @param start the start parameter for {@link Guess}.
   * @param end the end parameter for {@link Guess}.
   */

  private void setListGuess(Pane tile, int start, int end) {
    tile.setOnMouseClicked(e -> {
      Guess guess = Guess.listGuess(CASINO_ELEMENTS.getValue(chipValueIndex), start, end);
      addGuess(guess, tile);
    });
    setGuessAnimation(tile, Guess.listGuess(
            CASINO_ELEMENTS.getValue(chipValueIndex),
            start, end).getNumbers());
  }

  /**
   * Creates a {@link Guess} add calls the method {@code addGuess}.
   *
   * @param tile creates the {@link Guess} when clicked on.
   * @param start the start parameter for {@link Guess}.
   * @param increment the end increment for {@link Guess}.
   */

  private void setPatternGuess(Pane tile, int start, int increment) {
    tile.setOnMouseClicked(e -> {
      Guess guess = Guess.patternGuess(
              CASINO_ELEMENTS.getValue(chipValueIndex), start, increment);
      addGuess(guess, tile);
    });
    setGuessAnimation(tile, Guess.patternGuess(
            CASINO_ELEMENTS.getValue(chipValueIndex), start, increment).getNumbers());
  }

  /**
   * Sets the animation.
   *
   * @param tile Creates a clickanimation when clicked on.
   * @param numbers Animates the corresponing {@code numberTiles} on the numbers.
   */
  
  private void setGuessAnimation(Pane tile, List<Integer> numbers) {
    tile.setOnMousePressed(e -> numbers.forEach(num -> numbersTilesMap.get(num).setOpacity(0.5)));
    tile.setOnMouseReleased(e -> numbers.forEach(num -> numbersTilesMap.get(num).setOpacity(1)));
  }

  /**
   * Adds the guess in the {@link Roulette} instance and draws the guess visually.
   *
   * @param guess the guess created.
   * @param tile the tile the chip will be added on.
   */
  private void addGuess(Guess guess, Pane tile) {
    try {
      rouletteGame.addGuess(guess);
      updateUserLables();
      addChip(tile);
      feedBackLabel.setText(null);

    } catch (Exception e) {
      feedBackLabel.setText(e.getMessage());
    }
  }

  public boolean isRouletteWheelContainerShown() {
    return rouletteWheelContainer.isVisible();
  }

  public String getRolledNumberLabelText() {
    return rolledNumberLabel.getText();
  }

  public int getChipListSize() {
    return chipList.size();
  }

  /**
   * Creates the roulette wheel.
   *
   * @return A {@link Pane} containing 37 triangles from the center of the pane.
   */

  private Pane createRouletteWheel() {

    Pane rouletteWheelContainer = new Pane();
    rouletteWheelContainer.setPrefHeight(gridPane.getPrefHeight());
    rouletteWheelContainer.setPrefWidth(gridPane.getPrefWidth());

    Pane rouletteWheelPivotPane = new Pane();
    rouletteWheelPivotPane.setTranslateX(rouletteBoardPane.getPrefWidth() / 2); // Senters the pane
    rouletteWheelPivotPane.setTranslateY(rouletteBoardPane.getPrefHeight() / 2); // Senters the pane

    double radius = rouletteBoardPane.getPrefHeight() / 2 - 5;

    Circle backgroundCircle = new Circle();
    backgroundCircle.setFill(Paint.valueOf("black"));
    backgroundCircle.setRadius(radius);
    rouletteWheelPivotPane.getChildren().add(backgroundCircle);

    for (int i = 0; i <= Roulette.rouletteSize; i++) {
      Paint style = (i % 2 == 0) ? Paint.valueOf("red") : Paint.valueOf("black");
      style = (i == 0) ? Paint.valueOf("green") : style;

      Polygon tri = createTriangle(radius, Math.PI / (Roulette.rouletteSize + 1));
      tri.setFill(style);

      Rotate triangleRotation = new Rotate();
      triangleRotation.setAngle(getAngle() * i);

      tri.getTransforms().add(triangleRotation);

      int number = rouletteWheelNumberSequence.get(i);
      String text = number < 10 ? " " + number : "" + number;
      Label l = new Label(text);
      l.setFont(CASINO_ELEMENTS.TEXT_FONT);
      l.setTextFill(Paint.valueOf("white"));
      l.setTranslateY(-radius);

      Rotate labelRotation = new Rotate();
      labelRotation.setAngle((i - 0.4) * getAngle());

      labelRotation.setPivotX(0);
      labelRotation.setPivotY(radius);
      l.getTransforms().add(labelRotation);

      rouletteWheelPivotPane.getChildren().add(tri);
      rouletteWheelPivotPane.getChildren().add(l);

    }
    rouletteWheelPivotPane.setPrefSize(0, 0);

    Circle middleCircle = new Circle();
    middleCircle.setRadius(radius * 2 / 3);
    middleCircle.setFill(Paint.valueOf("#075600"));
    rouletteWheelPivotPane.getChildren().add(middleCircle);
    middleCircle.setStroke(Paint.valueOf("black"));
    middleCircle.setStrokeWidth(6);
    middleCircle.setStrokeLineCap(StrokeLineCap.BUTT); // The poker chip border design.
    middleCircle.setStrokeType(StrokeType.INSIDE);
    middleCircle.setStyle("-fx-stroke-dash-array:8;");

    rouletteWheelContainer.getChildren().add(rouletteWheelPivotPane);
    return rouletteWheelContainer;
  }

  /**
   * Creates a {@link Polygon} triangle out with an angle and length on the "legs".
   *
   * @param length The length of each "leg" out from the triangle.
   * @param angle The angle out for the corner of the triangle.
   * @return A {@link Polygon} which is a triangle based on the {@code angle} and {@code length}.
   */
  
  private Polygon createTriangle(double length, double angle) {
    Polygon fovTriangle = new Polygon(
            0d, 0d, (length * Math.tan(angle)), -length, -(length * Math.tan(angle)),
        -length);
    fovTriangle.setLayoutX(0);
    fovTriangle.setLayoutY(0);
    return fovTriangle;
  }

  /**
   * Method for getting the angle of the triangle.
   *
   * @return The angle of the triangle out from the "first" corner.
   */
  
  private double getAngle() {
    return 360.0 / (Roulette.rouletteSize + 1);
  }

  private void updateUserLables() {
    moneyBettedLabel.setText("" + rouletteGame.getSumOfBets());
    moneyLabel.setText("" + user.getBalance());
  }


  public Roulette getRouletteGame() {
    return rouletteGame;
  }

}
