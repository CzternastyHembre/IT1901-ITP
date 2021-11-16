package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import savehandler.UserSaveHandler;
import user.User;


public class RouletteUITest extends ApplicationTest {

    private RouletteController controller;
    private final User user = new User("TestUser", 1000);


    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Roulette.fxml"));
        controller = new RouletteController();
        controller.setUser(user);
        loader.setController(controller);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @Test
    void run() {
        clickOn("#selectChip1");
        clickOn("#1");
        clickOn("#1");
        clickOn("#playButton");
        Assertions.assertTrue(controller.getRouletteWheelContainer().isVisible());
        sleep(4000);
        Assertions.assertEquals(controller.getRouletteGame().getRolledNumber(), Integer.parseInt(controller.getRolledNumberLabel().getText().strip()));
    }

    @Test
    void undoGuess(){
        clickOn("#selectChip1");
        clickOn("#3");
        clickOn("#5");
        var guesses = controller.getChipList().size();
        clickOn("#undoButton");
        Assertions.assertEquals(guesses-controller.getChipList().size(), 1);

    }
}
