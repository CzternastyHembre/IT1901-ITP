package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import savehandler.UserSaveHandler;
import slots.Slots;
import user.User;


/**
 * Controller for the slots view.
 *
 */
public class SlotsController extends SlotsDisplay {

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    createMenu();
    super.slotMachine = new Slots(user);
    super.updateStats();
    super.initializeHboxes();
    super.viewAtStart();

  }
}