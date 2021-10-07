package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    @FXML MenuItem mainMenu;
    @FXML MenuItem exit;
    @FXML FXMLLoader loader = new FXMLLoader();
    @FXML AnchorPane anchorPane;


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

    @FXML
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void backToMainMenu(ActionEvent actionEvent) throws IOException {
        //Sets location on the loader by getting the class and then the view file from resources
        loader.setLocation(getClass().getResource("Start.fxml"));
        Parent newGame = loader.load(); // Create a parent class of the loader.load()
        Scene newGameScene = new Scene(newGame); //Create a new Scene from the parent object

        Stage window = (Stage) anchorPane.getScene().getWindow();   //Create new Stage to from the view-file
        window.setScene(newGameScene);  //Set the window to the previous chosen scene

        window.show();  //Opens the window
    }
}
