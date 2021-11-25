package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import user.User;
import static org.mockito.Mockito.*;


public class RouletteUITest extends ApplicationTest {

    private RouletteController controller;
    private final User user = new User("TestUser", 1000);

    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Roulette.fxml"));
        controller = new RouletteController();
        controller.setUser(user);
        controller.setRestModel(mock(RestModel.class));
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
        Assertions.assertTrue(controller.isRouletteWheelContainerShown());
        sleep(4000);
        Assertions.assertEquals(controller.getRouletteGame().getRolledNumber(), Integer.parseInt(controller.getRolledNumberLabelText().strip()));
    }

    @Test
    void undoGuess(){
        clickOn("#selectChip1");
        clickOn("#3");
        clickOn("#5");
        var guesses = controller.getChipListSize();
        clickOn("#undoButton");
        Assertions.assertEquals(guesses - 1, controller.getChipListSize());

    }
}
