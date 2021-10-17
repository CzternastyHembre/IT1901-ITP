package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import savehandler.UserSaveHandler;
import user.User;
import static org.junit.jupiter.api.Assertions.*;


public class CasinoUITest extends ApplicationTest {

    private StartController controller;
    private String userTest = "UserTest";
    private RouletteController rouletteController;
    private User user = new User("test", 100);

    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("StartTest.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @Test
    public void checkNewUser() throws IOException {
        clickOn("#create_user_button");
        clickOn("#username_field").write(userTest);
        clickOn("#create");
        assertEquals(userTest, UserSaveHandler.getActiveUser().getUsername());
    }

    @Test
    public void logInUser() {
        clickOn("#log_in_button");
        clickOn("#log_in_field").write("test");
        clickOn("#log_in");
        assertEquals("test", user.getUsername());
    }

    @Test
    public void moveAround() throws IOException {
        UserSaveHandler.createUser(user);
        clickOn("#log_in_button");
        clickOn("#log_in_field").write("test");
        clickOn("#log_in");
        clickOn("#roulette");
        clickOn("#meny_button");
        clickOn("#lobby");
        clickOn("#meny_button");
        clickOn("#mainMenu");
    }
}
