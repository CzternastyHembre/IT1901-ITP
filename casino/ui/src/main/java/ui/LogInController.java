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

public class LogInController {
    @FXML
    private TextField log_in_field;
    private final static double StartingBalance = 1000;
    private Stage stage;
    private Scene scene;


    public void run(ActionEvent actionEvent) throws IOException {
        if(UserSaveHandler.getUser(getUsername()) == null){
            throw new IllegalArgumentException("This user does not exist");
        }
        User userr = UserSaveHandler.getUser(getUsername());
        UserSaveHandler.setActive(userr);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("selectGameView.fxml")));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public String getUsername(){
        return log_in_field.getText();
    }
}
