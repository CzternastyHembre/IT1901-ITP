package ui;

import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Controller for the selectGameView.fxml
 */

public class SelectGameController {

  private Stage stage;
  private Scene scene;
  @FXML
  Button roulette;
  @FXML
  Button slots;
  @FXML
  MenuItem mainMenu;
  @FXML
  MenuItem exit;
  @FXML
  FXMLLoader loader = new FXMLLoader();
  @FXML
  AnchorPane anchorPane;


  /**
   * Actionevent when pressing button to load roulette.
   * Switches scene to Roulette.fxml.
   *
   * @param actionEvent the actionevent when pressing the button.
   */

  @FXML
  public void loadRoulette(ActionEvent actionEvent) throws IOException {
    Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("Roulette.fxml")));
    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Actionevent when pressing button to load Slots.
   * Switches scene to Slots.fxml.
   *
   * @param actionEvent the actionevent when pressing the button.
   */

  @FXML
  public void loadSlots(ActionEvent actionEvent) throws IOException {
    Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("Slots.fxml")));
    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Actionevent when pressing button to load Add chips.
   * Switches scene to add_money.fxml.
   *
   * @param actionEvent the actionevent when pressing the button.
   */

  @FXML
  public void loadAddChips(ActionEvent actionEvent) throws IOException {
    Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("add_money.fxml")));
    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void exit(ActionEvent actionEvent) {
    System.exit(0);
  }

  /**
   * Actionevent when pressing button to load Select game view.
   * Switches scene to selectGameView.fxml.
   */

  @FXML
  public void backToMainMenu(ActionEvent actionEvent) throws IOException {
    // Sets location on the loader by getting the class and then the view file from
    // resources
    loader.setLocation(getClass().getResource("Start.fxml"));
    Parent newGame = loader.load(); // Create a parent class of the loader.load()
    Scene newGameScene = new Scene(newGame); // Create a new Scene from the parent object
    // Create new Stage to from the view-file
    Stage window = (Stage) anchorPane.getScene().getWindow();
    // Set the window to the previous chosen scene
    window.setScene(newGameScene);
    window.show(); // Opens the window
  }

}
