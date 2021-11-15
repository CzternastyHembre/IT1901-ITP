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
  private final UserSaveHandler userSaveHandler = new UserSaveHandler();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      super.slotMachine = new Slots(userSaveHandler.getActiveUser());
    } catch (IOException e) {
      e.printStackTrace();
    }
    super.updateStats();
    super.initializeHboxes();
    super.viewAtStart();

  }
}