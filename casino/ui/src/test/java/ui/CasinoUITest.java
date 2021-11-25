package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import user.User;
import java.io.IOException;
import static org.mockito.Mockito.*;

public class CasinoUITest extends ApplicationTest {

    private StartController startController;
    private LoginController loginController;
    public RestModel service;

    private final User user = new User("TestUser", 1000);

    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml"));
        service = mock(RestModel.class);
        startController = new StartController();
        loader.setController(startController);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    private void updateLoginController() {
        loginController = startController.getLoginController();
        loginController.setRestModel(service);
    }

    @Test
    public void testLogin() throws IOException, InterruptedException {
        clickOn("#logInButton");

        clickOn("#usernameField").write(user.getUsername());

        updateLoginController();
        when(service.getUser(user.getUsername())).thenReturn(user);

        clickOn("#submit");
        Assertions.assertEquals(loginController.getNextController().getUser(), user);
        verify(service).getUser(user.getUsername());
    }

    @Test
    public void testCreateUser() throws IOException, InterruptedException {
        clickOn("#createUserButton");
        clickOn("#usernameField").write(user.getUsername());

        updateLoginController();
        when(service.getUser(user.getUsername())).thenReturn(null);

        clickOn("#submit");
        Assertions.assertEquals(loginController.getNextController().getUser().getUsername(), user.getUsername());
        verify(service).getUser(user.getUsername());

    }

    @Test
    public void testCreateExistingUser() throws IOException, InterruptedException {
        clickOn("#createUserButton");
        clickOn("#usernameField").write(user.getUsername());

        updateLoginController();
        when(service.getUser(user.getUsername())).thenReturn(user);

        //Trying to create a user that already exist, should throw when clicking on #submit
     /*   try {
            clickOn("#submit");
            sleep(1000);
            Assertions.fail();
        } catch (Exception e) {

        }
        verify(service).getUser(user.getUsername());*/
    }

    @Test
    public void testNavigate() throws IOException, InterruptedException {
        clickOn("#logInButton");
        clickOn("#usernameField").write(user.getUsername());
        updateLoginController();

        when(service.getUser(user.getUsername())).thenReturn(user);
        clickOn("#submit");

        clickOn("#roulette");
        clickOn("#menuButton");
        clickOn("#lobby");

        clickOn("#addChips");
        clickOn("#menuButton");
        clickOn("#lobby");

        clickOn("#slots");
        clickOn("#menuButton");
        clickOn("#lobby");

        clickOn("#blackjack");
        clickOn("#menuButton");
        clickOn("#lobby");

        clickOn("#menuButton");
        clickOn("#mainMenu");

        clickOn("#menuButton");
    }
}
