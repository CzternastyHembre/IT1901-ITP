package ui;

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

import java.io.IOException;
import java.util.Objects;

public class SelectGameController {

  private Stage stage;
  private Scene scene;
  @FXML
  Button roulette, slots;
  @FXML
  MenuItem mainMenu, exit;
  @FXML
  FXMLLoader loader = new FXMLLoader();
  @FXML
  AnchorPane anchorPane;

  @FXML
  public void loadRoulette(ActionEvent actionEvent) throws IOException {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Roulette.fxml")));
    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void loadSlots(ActionEvent actionEvent) throws IOException {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Slots.fxml")));
    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void loadAddChips(ActionEvent actionEvent) throws IOException {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add_money.fxml")));
    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
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

}
