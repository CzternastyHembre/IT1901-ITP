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
    public void undoBet(){

    }


}
