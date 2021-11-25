package ui;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.menuitem.LobbyMenu;


/**
 * Controller for the selectGameView.fxml
 */

public class SelectGameController extends LobbyMenu {

  private void loadView(ActionEvent actionEvent, FXMLLoader loader) throws IOException {
    nextController.setUser(user);
    loader.setController(nextController);
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(loader.load());
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Actionevent when pressing button to load blackjack.
   * Switches scene to Blackjack.fxml.
   *
   * @param actionEvent the actionevent when pressing the button.
   */

  @FXML
  private void loadBlackjack(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass()
            .getResource("Blackjack.fxml")));
    nextController = new BlackjackController();
    loadView(actionEvent, loader);
  }


  /**
   * Actionevent when pressing button to load roulette.
   * Switches scene to Roulette.fxml.
   *
   * @param actionEvent the actionevent when pressing the button.
   */

  @FXML
  private void loadRoulette(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass()
            .getResource("Roulette.fxml")));
    nextController = new RouletteController();
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
    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass()
            .getResource("Slots.fxml")));
    nextController = new SlotsController();
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
    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass()
            .getResource("AddMoney.fxml")));
    nextController = new AddMoneyController();
    loadView(actionEvent, loader);
  }
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    createMenu();
  }

}
