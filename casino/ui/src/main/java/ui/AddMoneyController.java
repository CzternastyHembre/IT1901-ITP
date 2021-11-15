package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import savehandler.UserSaveHandler;
import ui.MenuItem.CasinoMenu;
import user.User;

/**
 * Controller for adding money AddMoney.fxml.
 */

public class AddMoneyController extends CasinoMenu {

  @FXML
  private TextField amountField;
  @FXML
  private Label responseLabel;
  @FXML
  private Label errorLabel;
  @FXML
  private Label nameLabel;
  private final UserSaveHandler userSaveHandler = new UserSaveHandler();

  public AddMoneyController(User user) {
    super(user);
  }

  @FXML
  public void initialize() {
    nameLabel.setText("Welcome " + user.getUsername());
  }

  @FXML
  public void addMoney() throws NumberFormatException {
    try {
      int amount = Integer.parseInt(amountField.getText());
      if (amount < 0) {
        errorLabel.setText("You can only add positive numbers");
      }
      user.addMoney(amount);
      userSaveHandler.updateUser(user);
      errorLabel.setText("");
      responseLabel.setText(amount + " has been added to your account!");

    } catch (NumberFormatException | IOException e) {
      responseLabel.setText("");
      errorLabel.setText("We only accept integers, try again");
    }
  }
}
