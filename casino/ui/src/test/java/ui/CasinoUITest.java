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

    private final StartController starController = new StartController();
    private final RestModel restModel = new RestModel();



    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml"));
        loader.setController(starController);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @AfterEach
    void deleteUser() throws InterruptedException {
        restModel.deleteUser("testUser");
    }

    @Test
    public void checkNewUser() {
        clickOn("#createUserButton");
        clickOn("#usernameField").write("testUser");
        clickOn("#submit");
        assertEquals("testUser", starController.getLoginController().getNextController().getUser().getUsername());
    }

    @Test
    public void logInUser() throws IOException, InterruptedException {
        User user = new User("testUser", 500);
        restModel.createUser(user);
        clickOn("#logInButton");
        clickOn("#usernameField").write("testUser");
        clickOn("#submit");
        assertEquals("testUser", starController.getLoginController().getNextController().getUser().getUsername());
    }

    @Test
    public void moveAround() throws IOException, InterruptedException {
        User user = new User("testUser", 500);
        restModel.createUser(user);
        clickOn("#logInButton");
        clickOn("#usernameField").write("testUser");
        clickOn("#submit");
        clickOn("#roulette");
        clickOn("#menyButton");
        clickOn("#lobby");
        clickOn("#menyButton");
        clickOn("#mainMenu");
    }
    @Test
    public void addMoneyTest() throws IOException, InterruptedException {
        restModel.createUser(new User("testUser", 1000));
        clickOn("#logInButton");
        clickOn("#usernameField").write("testUser");
        clickOn("#submit");
        clickOn("#addChips");
        clickOn("#amountField").write("1000");
        clickOn("#addButton");
        assertEquals(2000, restModel.getUser("testUser").getBalance());
    }
}
