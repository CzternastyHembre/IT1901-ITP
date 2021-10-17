package ui;

import java.io.IOException;
import java.util.*;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import roulette.Guess;
import roulette.ListGuess;
import roulette.NumberGuess;
import roulette.PatternGuess;
import roulette.Roulette;
import savehandler.UserSaveHandler;
import user.User;

public class RouletteController {

  @FXML
  Pane chipFolder, anchorPane, rouletteBoardPane, controllsFolder, gridPane;
  @FXML
  Label moneyLabel, moneyBettedLabel, feedBackLabel, nameLabel, textLabel1, textLabel2;
  @FXML
  MenuItem mainMenu, lobby, exit;
  @FXML
  FXMLLoader loader = new FXMLLoader();

  Label rolledNumberLabel = new Label();
  private Roulette rouletteGame;
  private User user;
  private Map<Integer, Pane> numbersTilesMap = new HashMap<>();
  private List<Pane> chipList = new ArrayList<>();

  private int chipValueIndex = 0;
  private final List<Integer> rouletteWheelNumberSequence = Arrays.asList(0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27,
      13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26);
  private Pane rouletteWheelContainer;

  @FXML
  public void initialize() throws IOException {
    List<Label> labelList = new ArrayList<>(Arrays.asList(moneyLabel, moneyBettedLabel, feedBackLabel, nameLabel,
        textLabel1, textLabel2, rolledNumberLabel));
    user = UserSaveHandler.getActiveUser();
    rouletteGame = new Roulette(user);
    updateUserLables();
    nameLabel.setText(user.getUsername());

    int rouletteRows = 5;
    int rouletteColums = 14;
    double tileWidth = rouletteBoardPane.getPrefWidth() / rouletteColums;
    double tileHeight = rouletteBoardPane.getPrefHeight() / rouletteRows;

    // Adding all the "numberTiles" the tiles that have a single number to bet on
    for (int y = 0; y < rouletteRows - 2; y++) {
      for (int x = 0; x < rouletteColums - 1; x++) {
        Pane tile = new Pane();
        Label tileLabel = new Label();
        int number = (x - 1) * 3 + (3 - y);

        if (x == 0) {// The leftmost column (#0)
          if (y != 0) {
            continue;
          }
          tile.setPrefSize(tileWidth, tileHeight * (rouletteRows - 2));
          // tile.setStyle("-fx-background-color:green;");

        } else {// all the columns in the middle (the numbers)
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
    for (int y = 0; y < 3; y++) {// The rightmost Column
      Pane tile = new Pane();
      Label tileLabel = new Label();

      tile.setPrefSize(tileWidth, tileHeight);
      tile.setTranslateX(tileWidth * (rouletteColums - 1));
      tile.setTranslateY(tileHeight * y);
      // tile.setStyle("-fx-background-color:green;");

      tileLabel.setText("Row " + (y + 1));
      tile.getChildren().add(tileLabel);
      labelList.add(tileLabel);
      rouletteBoardPane.getChildren().add(tile);

      int start = 3 - y;
      int increment = 3;

      setPatternGuess(tile, start, increment);
    }

    for (int x = 0; x < 3; x++) {// The 4. row
      Pane tile = new Pane();
      Label tileLabel = new Label();

      tile.setPrefSize(tileWidth * 4, tileHeight);
      tile.setTranslateX(tileWidth * x * 4 + tileWidth);
      tile.setTranslateY(tileHeight * 3);
      // tile.setStyle("-fx-background-color:green;");
      rouletteBoardPane.getChildren().add(tile);

      int startNumber = (x * 12) + 1;
      int endNumber = (x + 1) * 12;
      String tileString = startNumber + "-" + endNumber;
      tileLabel.setText("" + tileString);
      tile.getChildren().add(tileLabel);
      setListGuess(tile, startNumber, endNumber);

      labelList.add(tileLabel);
    }
    for (int i = 0; i < 4; i++) {// The 5. row
      Pane tile = new Pane();
      Label tileLabel = new Label();

      tile.setPrefSize(tileWidth * 3, tileHeight);
      tile.setTranslateX(tileWidth * i * 3 + tileWidth);
      tile.setTranslateY(tileHeight * 4);
      // tile.setStyle("-fx-background-color:green;");
      rouletteBoardPane.getChildren().add(tile);

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
    for (int i = 0; i < CasinoElements.getValuesSize(); i++) {
      Pane chipContainer = CasinoElements.getChip(i);
      String style = chipContainer.getStyle();
      chipContainer.setId("selectChip" + i);

      chipContainer.setTranslateX(i * (CasinoElements.CHIPRADIUS * 2 + 4) + 20);
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
      label.setFont(CasinoElements.TEXTFONT);
      label.setTextFill(CasinoElements.TEXTCOLOR);
    });
    // gridPane.setStyle(CasinoElements.BACKGROUNDSTYLE);

    rouletteWheelContainer = createRouletteWheel();
    gridPane.getChildren().add(rouletteWheelContainer);
    rouletteWheelContainer.setVisible(false);

    Circle circleWheelPointer = new Circle();
    circleWheelPointer.setRadius(CasinoElements.FONTSIZE - 2);
    circleWheelPointer.setStroke(Color.WHITE);
    circleWheelPointer.setStrokeWidth(2);
    circleWheelPointer.setTranslateX(rouletteWheelContainer.getPrefWidth() / 2);
    circleWheelPointer.setFill(null);
    circleWheelPointer.setTranslateY(15);
    rouletteWheelContainer.getChildren().add(circleWheelPointer);

    rolledNumberLabel.setFont(CasinoElements.LARGETEXTFONT);
    rolledNumberLabel.setTranslateX(rouletteBoardPane.getPrefWidth() / 2 - CasinoElements.LARGEFONTSIZE / 2);
    rolledNumberLabel.setTranslateY(rouletteBoardPane.getPrefHeight() / 2 - CasinoElements.LARGEFONTSIZE / 2);
    rouletteWheelContainer.getChildren().add(rolledNumberLabel);
  }

  @FXML
  public void run() throws IOException {

    double winnings = rouletteGame.calculate();
    UserSaveHandler.updateUser(user);

    int number = rouletteGame.getRolledNumber();

    setShowRouletteWheel(true);
    Pane rouletteWheelPivotPane = (Pane) rouletteWheelContainer.getChildren().get(0);
    rouletteWheelPivotPane.setRotate(0);
    double extraAngle = getAngle() * rouletteWheelNumberSequence.indexOf(number);

    RotateTransition rt = new RotateTransition(Duration.seconds(4), rouletteWheelPivotPane);
    rt.setByAngle(360 * 4 + 360 - extraAngle);
    rt.play();

    RotateTransition rt2 = new RotateTransition(Duration.seconds(2), rouletteWheelPivotPane);
    rt2.setOnFinished(e -> {
      setShowRouletteWheel(false);
      feedBackLabel.setText("you won: " + winnings);
      updateUserLables();
      clearChips();
      rolledNumberLabel.setText(null);
    });
    rt.setOnFinished(e -> {
      rolledNumberLabel.setText(rouletteGame.getRolledNumber() < 10 ? " " + rouletteGame.getRolledNumber()
          : "" + rouletteGame.getRolledNumber());
      rt2.play();
    });
  }

  private void setShowRouletteWheel(boolean b) {
    if (b) {
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

  private void clearChips() {
    rouletteBoardPane.getChildren().forEach((n) -> {
      Pane tile = (Pane) n;
      if (tile.getChildren().size() > 1) {
        tile.getChildren().subList(1, tile.getChildren().size()).clear();
      }
    });
  }

  private void addChip(Pane tile) {

    double tileWidth = tile.getPrefWidth();
    double tileHeight = tile.getPrefHeight();

    Pane chipContainer = CasinoElements.getChip(chipValueIndex);
    chipContainer.setTranslateX(tileWidth / 2);
    chipContainer.setTranslateY(tileHeight / 2);

    double yTranslated = chipContainer.getTranslateY();
    int chipSize = tile.getChildren().size() - 1; // - 1 because first child is a label
    if (chipSize < 7) {
      chipContainer.setTranslateY(yTranslated - chipSize * 3);
    } else {
      chipContainer.setTranslateY(yTranslated + -7 * 3);

    }
    tile.getChildren().add(chipContainer);
    chipList.add(chipContainer);
  }

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

  private void setNumberGuess(Pane tile, int number) {
    tile.setOnMouseClicked(e -> {
      NumberGuess guess = new NumberGuess(CasinoElements.getValue(chipValueIndex), number);
      addGuess(guess, tile);
    });
    setGuessAnimation(tile, new NumberGuess(CasinoElements.getValue(chipValueIndex), number).getNumbers());
  }

  private void setListGuess(Pane tile, int start, int end) {
    tile.setOnMouseClicked(e -> {
      ListGuess guess = new ListGuess(CasinoElements.getValue(chipValueIndex), start, end);
      addGuess(guess, tile);
    });
    setGuessAnimation(tile, new ListGuess(CasinoElements.getValue(chipValueIndex), start, end).getNumbers());
  }

  private void setPatternGuess(Pane tile, int start, int increment) {
    tile.setOnMouseClicked(e -> {
      PatternGuess guess = new PatternGuess(CasinoElements.getValue(chipValueIndex), start, increment);
      addGuess(guess, tile);
    });
    setGuessAnimation(tile, new PatternGuess(CasinoElements.getValue(chipValueIndex), start, increment).getNumbers());
  }

  private void setGuessAnimation(Pane tile, List<Integer> numbers) {
    tile.setOnMousePressed(e -> numbers.forEach(num -> numbersTilesMap.get(num).setOpacity(0.5)));
    tile.setOnMouseReleased(e -> numbers.forEach(num -> numbersTilesMap.get(num).setOpacity(1)));
  }

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

  private Pane createRouletteWheel() {
    double radius = rouletteBoardPane.getPrefHeight() / 2 - 5;

    Pane rouletteWheelContainer = new Pane();
    rouletteWheelContainer.setPrefHeight(gridPane.getPrefHeight());
    rouletteWheelContainer.setPrefWidth(gridPane.getPrefWidth());

    Pane rouletteWheelPivotPane = new Pane();
    rouletteWheelPivotPane.setTranslateX(rouletteBoardPane.getPrefWidth() / 2); // Senters the pane
    rouletteWheelPivotPane.setTranslateY(rouletteBoardPane.getPrefHeight() / 2); // Senters the pane

    Circle backgroundCircle = new Circle();
    backgroundCircle.setFill(Paint.valueOf("black"));
    backgroundCircle.setRadius(radius);
    rouletteWheelPivotPane.getChildren().add(backgroundCircle);

    for (int i = 0; i <= Roulette.RoulettSize; i++) {
      Paint style = (i % 2 == 0) ? Paint.valueOf("red") : Paint.valueOf("black");
      style = (i == 0) ? Paint.valueOf("green") : style;

      Polygon tri = createTriangle(0, 0, radius, Math.PI / (Roulette.RoulettSize + 1));
      tri.setFill(style);

      Rotate triangleRotation = new Rotate();
      triangleRotation.setAngle(getAngle() * i);

      tri.getTransforms().add(triangleRotation);

      int number = rouletteWheelNumberSequence.get(i);
      String text = number < 10 ? " " + number : "" + number;
      Label l = new Label(text);
      l.setFont(CasinoElements.TEXTFONT);
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
    middleCircle.setStrokeLineCap(StrokeLineCap.BUTT);// The poker chip border design
    middleCircle.setStrokeType(StrokeType.INSIDE);
    middleCircle.setStyle("-fx-stroke-dash-array:8;");

    rouletteWheelContainer.getChildren().add(rouletteWheelPivotPane);
    return rouletteWheelContainer;
  }

  /*
   * Creates a triangle from a point with an angle
   */
  private Polygon createTriangle(double x, double y, double length, double angle) {
    Polygon fovTriangle = new Polygon(0d, 0d, (length * Math.tan(angle)), -length, -(length * Math.tan(angle)),
        -length);
    fovTriangle.setLayoutX(x);
    fovTriangle.setLayoutY(y);
    return fovTriangle;
  }

  private double getAngle() {
    return 360.0 / (Roulette.RoulettSize + 1);
  }

  private void updateUserLables() {
    moneyBettedLabel.setText("" + rouletteGame.getSumOfBets());
    moneyLabel.setText("" + user.getBalance());
  }

  @FXML
  public void exit(ActionEvent actionEvent) {
    System.exit(0);
  }

  @FXML
  public void backToMainMenu(ActionEvent actionEvent) throws IOException {
    // Sets location on the loader by getting the class and then the view file from
    // resources
    loader.setLocation(getClass().getResource("Start.fxml"));
    Parent newGame = loader.load(); // Create a parent class of the loader.load()
    Scene newGameScene = new Scene(newGame); // Create a new Scene from the parent object

    Stage window = (Stage) anchorPane.getScene().getWindow(); // Create new Stage to from the view-file
    window.setScene(newGameScene); // Set the window to the previous chosen scene

    window.show(); // Opens the window
  }

  @FXML
  public void backToLobby(ActionEvent actionEvent) throws IOException {
    // Sets location on the loader by getting the class and then the view file from
    // resources
    loader.setLocation(getClass().getResource("selectGameView.fxml"));
    Parent newGame = loader.load(); // Create a parent class of the loader.load()
    Scene newGameScene = new Scene(newGame); // Create a new Scene from the parent object

    Stage window = (Stage) anchorPane.getScene().getWindow(); // Create new Stage to from the view-file
    window.setScene(newGameScene); // Set the window to the previous chosen scene

    window.show(); // Opens the window
  }

  public Roulette getRouletteGame() {
    return rouletteGame;
  }
}
