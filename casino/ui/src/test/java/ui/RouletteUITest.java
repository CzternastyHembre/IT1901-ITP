package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

public class RouletteUITest extends ApplicationTest {

    private RouletteController controller;


    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("RouletteTest.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void run() throws IOException {
        clickOn("#selectChip1");
        clickOn("#1");
        clickOn("#1");
        clickOn("#play_button");
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
        clickOn("#undo_button");
        Assertions.assertEquals(guesses-controller.getChipList().size(), 1);

    }
}
