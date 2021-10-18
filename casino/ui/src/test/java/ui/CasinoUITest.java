package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

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
        clickOn("#createUserButton");
        clickOn("#usernameField").write(userTest);
        clickOn("#create");
        assertEquals(userTest, UserSaveHandler.getActiveUser().getUsername());
    }

    @Test
    public void logInUser() {
        clickOn("#logInButton");
        clickOn("#logInField").write("test");
        clickOn("#logIn");
        assertEquals("test", user.getUsername());
    }

    @Test
    public void moveAround() throws IOException {
        UserSaveHandler.createUser(user);
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
        UserSaveHandler.createUser(new User("addMoneyTest", 1000));
        clickOn("#logInButton");
        clickOn("#logInField").write("addMoneyTest");
        clickOn("#logIn");
        clickOn("#addChips");
        clickOn("#amountField").write("1000");
        clickOn("#addButton");
        assertEquals(2000, Objects.requireNonNull(UserSaveHandler.getUser("addMoneyTest")).getBalance());
    }
}
