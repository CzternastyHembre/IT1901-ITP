package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import savehandler.UserSaveHandler;
import user.User;
import static org.junit.jupiter.api.Assertions.*;


public class CasinoUITest extends ApplicationTest {

    private StartController starController = new StartController();



    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml"));
        loader.setController(starController);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @Test
    public void checkNewUser() throws IOException {
        clickOn("#createUserButton");
        clickOn("#usernameField").write("UserTest");
        clickOn("#submit");
        assertEquals("UserTest", starController.getLoginController().getNextController().getUser().getUsername());
    }

    @Test
    public void logInUser() throws IOException, InterruptedException {
        User user = new User("LogTest", 500);
        RestModel.createUser(user);
        clickOn("#logInButton");
        clickOn("#usernameField").write("LogTest");
        clickOn("#submit");
        assertEquals("LogTest", starController.getLoginController().getNextController().getUser().getUsername());
    }

    @Test
    public void moveAround() throws IOException, InterruptedException {
        User user = new User("moveAroundTest", 500);
        RestModel.createUser(user);
        clickOn("#logInButton");
        clickOn("#usernameField").write("moveAroundTest");
        clickOn("#submit");
        clickOn("#roulette");
        clickOn("#menyButton");
        clickOn("#lobby");
        clickOn("#menyButton");
        clickOn("#mainMenu");
    }
    @Test
    public void addMoneyTest() throws IOException, InterruptedException {
        RestModel.createUser(new User("addMoneyTest", 1000));
        clickOn("#logInButton");
        clickOn("#usernameField").write("addMoneyTest");
        clickOn("#submit");
        clickOn("#addChips");
        clickOn("#amountField").write("1000");
        clickOn("#addButton");
        assertEquals(2000, RestModel.getUser("addMoneyTest").getBalance());
    }
}
