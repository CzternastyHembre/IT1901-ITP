package ui.MenuItem;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.SelectGameController;
import user.User;

import java.io.IOException;

public abstract class CasinoMenu extends LobbyMenu {


    public CasinoMenu(User user) {
        super(user);
    }
    /**
     * Sends user back to lobby view.
     *
     * @throws IOException loader.load() can throw exception.
     */

    @FXML
    protected void backToLobby() throws IOException {
        // Sets location on the loader by getting the class and then the view file from
        // resources
        loader.setLocation(getClass().getResource("selectGameView.fxml"));
        loader.setController(new SelectGameController(user));
        Parent newGame = loader.load(); // Create a parent class of the loader.load()
        Scene newGameScene = new Scene(newGame); // Create a new Scene from the parent object

        Stage window = (Stage) anchorPane.getScene().getWindow();
        window.setScene(newGameScene); // Set the window to the previous chosen scene

        window.show(); // Opens the window
    }

}
