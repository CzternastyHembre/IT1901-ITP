package ui;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import saveHandler.UserSaveHandler;
import slots.Slots;

import java.net.URL;
import java.util.ResourceBundle;

public class SlotsController extends SlotsDisplay {




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        super.slotMachine = new Slots(UserSaveHandler.getActiveUser());
        super.slotMachine = new Slots(UserSaveHandler.getActiveUser());
        super.updateStats();
        super.initializeHBoxes();
        super.viewAtStart();
    }



}