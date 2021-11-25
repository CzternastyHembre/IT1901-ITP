
import java.io.IOException;
import it1901.rest.RestApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.BlackjackController;
import ui.RestModel;
import ui.StartController;
import user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest extends ApplicationTest {

    private final RestModel restModel = new RestModel(true);
    private final StartController startController = new StartController();


    @AfterEach
    public void removeUser() throws InterruptedException {
        restModel.deleteUser("testUser");
    }

    private void setTest() {
        startController.getLoginController().setRestModel(new RestModel(true));
    }

    private void setTestCasino() {
        startController.getLoginController().getNextController().getNextController().setRestModel(new RestModel(true));
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/Start.fxml"));
        loader.setController(startController);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @BeforeAll
    public static void setUp() {
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
        assertEquals("testUser", startController.getLoginController().getNextController().getUser().getUsername());
    }


    @Test
    public void logInUser() throws InterruptedException {
        logIn();
        assertEquals("testUser", startController.getLoginController().getNextController().getUser().getUsername());
    }

    @Test
    public void addMoneyTest() throws IOException, InterruptedException {
        logIn();
        setTestCasino();
        clickOn("#addChips");
        setTestCasino();
        clickOn("#amountField").write("1000");
        clickOn("#addButton");
        assertEquals(2000, restModel.getUser("testUser").getBalance());
    }

    @Test
    public void updateUserTest() throws InterruptedException, IOException {
        clickOn("#createUserButton");
        clickOn("#usernameField").write("testUser");
        setTest();
        clickOn("#submit");
        clickOn("#blackjack");
        setTestCasino();
        clickOn("#betAmount").write("50");
        clickOn("#bet");
        BlackjackController blackjackController = (BlackjackController) startController.getLoginController().getNextController().getNextController();
        while (blackjackController.getBlackjack().getPlayersHand1().getSumOfDeck() < 21) {
            clickOn("#hit");
        }
        clickOn("#stand");
        clickOn("#playAgainButton");
        assertEquals(950, restModel.getUser("testUser").getBalance());
    }
}
