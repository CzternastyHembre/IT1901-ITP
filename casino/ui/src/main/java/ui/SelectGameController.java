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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.MenuItem.LobbyMenu;
import user.User;


/**
 * Controller for the selectGameView.fxml
 */

public class SelectGameController extends LobbyMenu {

  public SelectGameController(User user) {
    super(user);
  }

  private void loadView(ActionEvent actionEvent, FXMLLoader loader) throws IOException {
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(loader.load());
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Actionevent when pressing button to load roulette.
   * Switches scene to Roulette.fxml.
   *
   * @param actionEvent the actionevent when pressing the button.
   */

  @FXML
  private void loadRoulette(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Roulette.fxml")));
    loader.setController(new RouletteController(user));
    loadView(actionEvent, loader);
  }

  /**
   * Actionevent when pressing button to load Slots.
   * Switches scene to Slots.fxml.
   *
   * @param actionEvent the actionevent when pressing the button.
   */

  @FXML
  private void loadSlots(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Slots.fxml")));
    loader.setController(new SlotsController(user));
    loadView(actionEvent, loader);
  }

  /**
   * Actionevent when pressing button to load Add chips.
   * Switches scene to AddMoney.fxml.
   *
   * @param actionEvent the actionevent when pressing the button.
   */

  @FXML
  private void loadAddChips(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("AddMoney.fxml")));
    loader.setController(new AddMoneyController(user));
    loadView(actionEvent, loader);
  }
}
