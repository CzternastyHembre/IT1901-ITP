package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import saveHandler.UserSaveHandler;
import user.User;

import java.io.IOException;
import java.util.Objects;

public class CreateUserController {
    @FXML
    private TextField username_field;
    private static final double StartingBalance = 1000;
    private Stage stage;
    private Scene scene;
    @FXML
    private void run(ActionEvent actionEvent) throws IOException {

        if (UserSaveHandler.getUser(getUsername()) != null) {
            throw new IllegalArgumentException("Username already exist");
        }
        User newUser = new User(getUsername(), StartingBalance);
        UserSaveHandler.createUser(newUser);
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("Roulette.fxml"))));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public String getUsername(){
        return username_field.getText();
    }
}
