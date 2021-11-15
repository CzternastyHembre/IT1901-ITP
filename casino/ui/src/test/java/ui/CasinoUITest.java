package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import savehandler.UserSaveHandler;
import user.User;
import static org.junit.jupiter.api.Assertions.*;


public class CasinoUITest extends ApplicationTest {

    private User user = new User("test", 100);
    private final UserSaveHandler userSaveHandler = new UserSaveHandler(true);



    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("StartTest.fxml"));
        final Parent root = loader.load();
        StartController controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void checkNewUser() throws IOException {
        clickOn("#createUserButton");
        clickOn("#usernameField").write("UserTest");
        clickOn("#create");
        assertEquals("UserTest", userSaveHandler.getActiveUser().getUsername());
    }

    @Test
    public void logInUser() throws IOException {
        User user = new User("LogTest", 500);
        userSaveHandler.createUser(user);
        clickOn("#logInButton");
        clickOn("#logInField").write("LogTest");
        clickOn("#logIn");
        assertEquals("LogTest", user.getUsername());
    }

    @Test
    public void moveAround() throws IOException {
        userSaveHandler.createUser(user);
        clickOn("#logInButton");
        clickOn("#logInField").write("test");
        clickOn("#logIn");
        clickOn("#roulette");
        clickOn("#menyButton");
        clickOn("#lobby");
        clickOn("#menyButton");
        clickOn("#mainMenu");
    }
    @Test
    public void addMoneyTest() throws IOException {
        userSaveHandler.createUser(new User("addMoneyTest", 1000));
        clickOn("#logInButton");
        clickOn("#logInField").write("addMoneyTest");
        clickOn("#logIn");
        clickOn("#addChips");
        clickOn("#amountField").write("1000");
        clickOn("#addButton");
        assertEquals(2000, Objects.requireNonNull(userSaveHandler.getUser("addMoneyTest")).getBalance());
    }
}
