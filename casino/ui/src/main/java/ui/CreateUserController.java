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
 * Controller for when you create user
 */

public class CreateUserController extends LogInController {

  private static final double StartingBalance = 1000;


  /**
   * Button for creating the user.
   * If the username is taken, an exception is thrown.
   * Creates a user and switches scene to selectGameView.fxml.
   */

  @FXML
  public void run(ActionEvent actionEvent) throws IOException, InterruptedException {

    if (RestModel.getUser(getUsername()) != null) {
      errorLabel.setText("This username is taken, try again");
      throw new IllegalArgumentException("Username already exist");
    }
    User newUser = new User(getUsername(), StartingBalance);
    RestModel.createUser(newUser);
    super.openView(actionEvent, newUser);
  }
}
