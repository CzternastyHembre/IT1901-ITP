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
  private final UserSaveHandler userSaveHandler = new UserSaveHandler();

  public SlotsController(User user) {
    super(user);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    super.slotMachine = new Slots(user);
    super.updateStats();
    super.initializeHboxes();
    super.viewAtStart();

  }
}