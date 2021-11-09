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
 * Controller for the CreateUser.fxml.
 */

public class CreateUserController {
  @FXML
  private TextField usernameField;
  private static final double StartingBalance = 1000;
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

  /**
   * Button for creating the user.
   * If the username is taken, an exception is thrown.
   * Creates a user and switches scene to selectGameView.fxml.
   */

  @FXML
  private void run(ActionEvent actionEvent) throws IOException, InterruptedException {

    if (UserSaveHandler.getUser(getUsername()) != null) {
      errorLabel.setText("This username is taken, try again");
      throw new IllegalArgumentException("Username already exist");
    }
    User newUser = new User(getUsername(), StartingBalance);

    RestModel.createUser(newUser);
    RestModel.getUserList();
    Parent root = FXMLLoader.load((
            Objects.requireNonNull(getClass().getResource("selectGameView.fxml"))));
    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }




  @FXML
  public String getUsername() {
    return usernameField.getText();
  }

  @FXML
  public void exit(ActionEvent actionEvent) {
    System.exit(0);
  }

  /**
   * Button to go back to the main menu.
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
    window.setScene(newGameScene); // Set the window to the previous chosen scene

    window.show(); // Opens the window
  }
}
