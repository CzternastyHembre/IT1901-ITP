package ui.MenuItem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user.User;

import java.io.IOException;

public class LobbyMenu extends LoginMenu {

    protected final User user;

    public LobbyMenu(User user) {
        this.user = user;
    }

}
