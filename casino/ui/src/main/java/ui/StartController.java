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
import javafx.stage.Stage;

/**
 * Controller for the Start.fxml
 */

public class StartController {
  @FXML
  private Button createUserButton;
  @FXML
  private Button logInButton;
  @FXML
  private MenuItem exit;
  private Stage stage;
  private Scene scene;

  /**
   * Button for the log in scene. Switches scene to the log in.
   *
   * @param actionEvent is the event of pressing the buttton.
   */

  @FXML
  public void log_in_scene(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(
            Objects.requireNonNull(getClass().getResource("LogIn.fxml")));
    loader.setController(new LogInController());
    loadView(actionEvent, loader);
  }

  /**
   * Button for the create a user. Switches scene to create user.
   *
   * @param actionEvent is the event of pressing the button.
   */

  @FXML
  public void create_user_scene(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(
            Objects.requireNonNull(getClass().getResource("LogIn.fxml")));
    loader.setController(new CreateUserController());
    loadView(actionEvent, loader);
  }

  private void loadView(ActionEvent actionEvent, FXMLLoader loader) throws IOException {
    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    scene = new Scene(loader.load());
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void exit(ActionEvent actionEvent) {
    System.exit(0);
  }
}
