package ui;

import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import savehandler.UserSaveHandler;
import user.User;

/**
 * Controller for LogIn.fxml.
 */

public class LogInController {
  @FXML
  private TextField logInField;
  private Stage stage;
  private Scene scene;
  @FXML
  MenuItem mainMenu;
  @FXML
  MenuItem exit;
  @FXML
  FXMLLoader loader = new FXMLLoader();
  @FXML
  AnchorPane anchorPane;
  @FXML
  Label errorLabel;

  private RestModel restService;

  /**
   * Button to log in with the user written in the textfield.
   * Throws an exception if the user does not exist.
   *
   * @param actionEvent the event when pressing the button.
   */

  public void run(ActionEvent actionEvent) throws IOException, InterruptedException {
    if (UserSaveHandler.getUser(getUsername()) == null) {
      errorLabel.setText("Could not find user, please try again");
      throw new IllegalArgumentException("This user does not exist");
    }
    User user = RestModel.getUser(getUsername());
    RestModel.setActive(user);
    Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("selectGameView.fxml")));
    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public String getUsername() {
    return logInField.getText();
  }

  @FXML
  public void exit(ActionEvent actionEvent) {
    System.exit(0);
  }

  /**
   * Button to go back to the main menu.
   * Loads in the Start.fxml.
   */

  @FXML
  public void backToMainMenu(ActionEvent actionEvent) throws IOException {
    // Sets location on the loader by getting the class and then the view file from
    // resources
    loader.setLocation(getClass().getResource("Start.fxml"));
    Parent newGame = loader.load(); // Create a parent class of the loader.load()
    // Create a new Scene from the parent object
    Scene newGameScene = new Scene(newGame);
    // Create new Stage to from the view-file
    Stage window = (Stage) anchorPane.getScene().getWindow();
    window.setScene(newGameScene); // Set the window to the previous chosen scene

    window.show(); // Opens the window
  }
}
