package ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App.
 */
public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Start.fxml"));
    loader.setController(new StartController());
    stage.setScene(new Scene(loader.load()));
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}