package ui.MenuItem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public abstract class MainMenu {

    @FXML
    protected Pane anchorPane;
    @FXML
    protected FXMLLoader loader = new FXMLLoader();

    @FXML
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

}
