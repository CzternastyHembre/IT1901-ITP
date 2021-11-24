package ui.menuItem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.App;
import ui.RestModel;
import ui.StartController;

import java.io.IOException;

public abstract class LoginMenu extends MainMenu {

    protected RestModel restModel = new RestModel(false);

    public void setRestModel(RestModel restModel) {
        this.restModel = restModel;
    }


    protected LobbyMenu nextController;

    public LobbyMenu getNextController() {
        return nextController;
    }

    /**
     * Sends user back to main menu view.
     *
     * @param actionEvent onClick, run this method
     *
     * @throws IOException loader.load() can throw an exception.
     */

    @FXML
    protected void backToMainMenu(ActionEvent actionEvent) throws IOException {
        // Sets location on the loader by getting the class and then the view file from
        // resources
        loader.setLocation(App.class.getResource("Start.fxml"));
        loader.setController(new StartController());
        Parent newGame = loader.load(); // Create a parent class of the loader.load()
        Scene newGameScene = new Scene(newGame); // Create a new Scene from the parent object

        Stage window = (Stage) anchorPane.getScene().getWindow();
        window.setScene(newGameScene); // Set the window to the previous chosen scene

        window.show(); // Opens the window
    }


}
