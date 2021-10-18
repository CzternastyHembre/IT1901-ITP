package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import savehandler.UserSaveHandler;
import user.User;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Objects;

public class addMoneyUITest extends ApplicationTest {

    private AddMoneyController controller;

    @FXML
    TextField amountField;
    private User user = new User("bob", 1000);


    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMoneyTest.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
