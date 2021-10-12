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

public class RouletteUITest extends ApplicationTest {

    private RouletteController controller;


    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("RouletteTest.fxml"));
        final Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
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


}
