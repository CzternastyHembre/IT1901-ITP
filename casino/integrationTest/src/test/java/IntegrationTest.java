import it1901.rest.RestApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.RestModel;
import ui.StartController;
import user.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest extends ApplicationTest {

    private final RestModel restModel = new RestModel(true);
    private final StartController starController = new StartController();

    @BeforeEach
    public void removeUser() throws InterruptedException {
        restModel.deleteUser("testUser");
    }

    private void setTest() {
        this.starController.getLoginController().setTestMode(true);
    }

    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml"));
        loader.setController(starController);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @BeforeAll
    public static void setUp(){
        RestApplication.isTestMode();
    }

    private void logIn() throws InterruptedException {
        User user = new User("testUser", 1000);
        restModel.createUser(user);
        clickOn("#logInButton");
        setTest();
        clickOn("#usernameField").write("testUser");
        clickOn("#submit");
    }

    @Test
    public void checkNewUser() {
        clickOn("#createUserButton");
        clickOn("#usernameField").write("testUser");
        setTest();
        clickOn("#submit");
        assertEquals("testUser", starController.getLoginController().getNextController().getUser().getUsername());
    }


    @Test
    public void logInUser() throws InterruptedException {
        logIn();
        assertEquals("testUser", starController.getLoginController().getNextController().getUser().getUsername());
    }

    @Test
    public void moveAround() throws InterruptedException {
        logIn();
        clickOn("#roulette");
        clickOn("#menyButton");
        clickOn("#lobby");
        clickOn("#menyButton");
        clickOn("#mainMenu");
    }
    @Test
    public void addMoneyTest() throws IOException, InterruptedException {
        logIn();
        clickOn("#addChips");
        clickOn("#amountField").write("1000");
        clickOn("#addButton");
        assertEquals(2000, restModel.getUser("testUser").getBalance());
        assertEquals(2000, this.starController.getLoginController().getNextController().getUser().getBalance());
    }

}
