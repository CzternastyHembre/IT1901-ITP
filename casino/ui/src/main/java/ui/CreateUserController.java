package ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import savehandler.UserSaveHandler;
import user.User;

/**
 * Controller for when you create user
 */

public class CreateUserController extends LoginController {

  private static final double StartingBalance = 1000;

  public void createRestUser(User user) throws InterruptedException {
    restModel.createUser(user);
  }

  /**
   * Button for creating the user.
   * If the username is taken, an exception is thrown.
   * Creates a user and switches scene to selectGameView.fxml.
   */

  @FXML
  public void run(ActionEvent actionEvent) throws IOException, InterruptedException {
    if (restModel.getUser(getUsername()) != null) {
      errorLabel.setText("This username is taken, try again");
      throw new IllegalArgumentException("Username already exist");
    }
    User newUser = new User(getUsername(), StartingBalance);
    createRestUser(newUser);
    openView(actionEvent, newUser);
  }
}
