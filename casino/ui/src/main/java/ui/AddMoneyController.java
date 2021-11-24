package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import savehandler.UserSaveHandler;
import ui.menuItem.CasinoMenu;

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

  @FXML
  public void initialize() {
    nameLabel.setText("Welcome " + user.getUsername());
  }


  /**
   * Method for adding money to a user.
   * Tries to add money and checks if the input is a positive integer.
   *
   * @throws NumberFormatException if the input is not an integer.
   */

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

    } catch (NumberFormatException e) {
      responseLabel.setText("");
      errorLabel.setText("We only accept integers, try again");
    }
  }
}
