import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import roulette.Roulette;
import saveHandler.UserSaveHandler;
import ui.RouletteController;
import ui.StartController;
import user.User;

import static org.junit.jupiter.api.Assertions.*;


public class CasinoUITest extends ApplicationTest {

    private StartController controller;
    private String userTest = "UserTest";
    private RouletteController rouletteController;

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
    public void logInUser() throws IOException {
        User user = new User("test", 100);
        UserSaveHandler.createUser(user);
        clickOn("#log_in_button");
        clickOn("#log_in_field").write("test");
        clickOn("#log_in");
    }

    @Test
    public void playRoulette() throws IOException {
        User user1 = new User("roulette_test", 100);
        UserSaveHandler.createUser(user1);
        clickOn("#log_in_button");
        clickOn("#log_in_field").write("roulette_test");
        clickOn("#log_in");
        clickOn("#roulette");
        clickOn("#selectChip0");
        clickOn("#1");
        clickOn("#play_button");
        sleep(5000);
    }

    @Test
    public void undoRouletteBet() throws IOException {
        User user5 = new User("undo_test", 10000);
        UserSaveHandler.createUser(user5);
        clickOn("#log_in_button");
        clickOn("#log_in_field").write("undo_test");
        clickOn("#log_in");
        clickOn("#roulette");
        clickOn("#selectChip3");
        clickOn("#1");
        clickOn("#undo_button");
        clickOn("#selectChip5");
        clickOn("#7");
        clickOn("#play_button");
        sleep(7000);
    }

}
