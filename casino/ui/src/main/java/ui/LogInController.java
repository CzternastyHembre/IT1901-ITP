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
import ui.MenuItem.LobbyMenu;
import ui.MenuItem.LoginMenu;
import ui.MenuItem.MainMenu;
import user.User;

/**
 * Controller for LogIn.fxml.
 */

public class LogInController extends LoginMenu {

  @FXML
  protected TextField usernameField;
  @FXML
  protected AnchorPane anchorPane;
  @FXML
  protected Label errorLabel;


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
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(loader.load());
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public String getUsername() {
    return usernameField.getText();
  }

}
