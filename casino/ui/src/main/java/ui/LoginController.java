package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.menuItem.LoginMenu;
import user.User;

/**
 * Controller for LogIn.fxml.
 */

public class LoginController extends LoginMenu implements Initializable {

  @FXML
  protected TextField usernameField;
  @FXML
  protected Label errorLabel;
  @FXML
  public Button submit;

  /**
   * Button to log in with the user written in the textfield.
   * Throws an exception if the user does not exist.
   *
   * @param actionEvent the event when pressing the button.
   */

  @FXML
  public void run(ActionEvent actionEvent) throws IOException, InterruptedException {
    User user = restModel.getUser(getUsername());
    if (user == null) {
      errorLabel.setText("Could not find user, please try again");
      throw new IllegalArgumentException("This user does not exist");
    }
    openView(actionEvent, user);
  }

  protected void openView(ActionEvent actionEvent, User user) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(App.class.getResource("selectGameView.fxml"));
    nextController = new SelectGameController();
    nextController.setUser(user);
    loader.setController(nextController);

    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(loader.load());
    stage.setScene(scene);
    stage.show();
  }

  protected void setButtonText(String text) {
    submit.setText(text);
  }

  @FXML
  public String getUsername() {
    return usernameField.getText();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (this instanceof CreateUserController) {
      setButtonText("Create user");
    }
  }
}
