package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import savehandler.UserSaveHandler;
import slots.Slots;


/**
 * Controller for the slots view.
 *
 */
public class SlotsController extends SlotsDisplay {

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      super.slotMachine = new Slots(UserSaveHandler.getActiveUser());
    } catch (IOException e) {
      e.printStackTrace();
    }
    super.updateStats();
    super.initializeHboxes();
    super.viewAtStart();

  }
}