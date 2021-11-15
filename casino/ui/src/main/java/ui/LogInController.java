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
import user.User;

/**
 * Controller for LogIn.fxml.
 */

public class LogInController {
  @FXML
  protected TextField usernameField;
  protected Stage stage;
  protected Scene scene;
  @FXML
  protected MenuItem mainMenu;
  @FXML
  protected MenuItem exit;
  @FXML
  protected FXMLLoader loader = new FXMLLoader();
  @FXML
  protected AnchorPane anchorPane;
  @FXML
  protected Label errorLabel;

  private final UserSaveHandler userSaveHandler = new UserSaveHandler();

  /**
   * Button to log in with the user written in the textfield.
   * Throws an exception if the user does not exist.
   *
   * @param actionEvent the event when pressing the button.
   */

  @FXML
  public void run(ActionEvent actionEvent) throws IOException, InterruptedException {
    if (RestModel.getUser(getUsername()) == null) {
      errorLabel.setText("Could not find user, please try again");
      throw new IllegalArgumentException("This user does not exist");
    }
    User user = RestModel.getUser(getUsername());
    openView(actionEvent, user);
  }

  protected void openView(ActionEvent actionEvent, User user) throws IOException {
    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("selectGameView.fxml")));
    loader.setController(new SelectGameController(user));
    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    scene = new Scene(loader.load());
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
