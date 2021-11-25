package ui.menuitem;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import ui.App;
import ui.SelectGameController;

/**
 * This class extends LoginMenu and the menuItem Lobby is added so
 * that the user go back to the lobby to either
 * switch game or add more money to their balance.
 */
public abstract class CasinoMenu extends LobbyMenu {

  /**
   * Method that sends user back to lobby view.
   */

  private void backToLobby() {
    // Sets location on the loader by getting the class and then the view file from
    // resources
    try {
      loader.setLocation(App.class.getResource("selectGameView.fxml"));
      SelectGameController selectGameController = new SelectGameController();
      selectGameController.setUser(user);
      loader.setController(selectGameController);
      Parent newGame = loader.load(); // Create a parent class of the loader.load()
      Scene newGameScene = new Scene(newGame); // Create a new Scene from the parent object

      Stage window = (Stage) anchorPane.getScene().getWindow();
      window.setScene(newGameScene); // Set the window to the previous chosen scene

      window.show(); // Opens the window
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates a menuItem and adds it in the menu and supers the class to create next item.
   */

  protected void createMenu() {
    MenuItem menuItem = new MenuItem("Lobby");
    menuItem.setId("lobby");
    menuItem.setOnAction(event -> backToLobby());
    menu.getItems().add(menuItem);
    super.createMenu();
  }

}
