package ui.menuItem;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import ui.App;
import ui.RestModel;
import ui.StartController;

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
     * Method that sends user back to main menu view.
     */

    private void backToMainMenu() {
        // Sets location on the loader by getting the class and then the view file from
        // resources
        try {
        loader.setLocation(App.class.getResource("Start.fxml"));
        loader.setController(new StartController());
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
     * Creates a menuItem and adds it in the menu and supers the class to create next item
     */

    protected void createMenu() {
        MenuItem menuItem = new MenuItem("Main Menu");
        menuItem.setId("mainMenu");
        menuItem.setOnAction(event -> backToMainMenu());
        menu.getItems().add(menuItem);
        super.createMenu();
    }
}