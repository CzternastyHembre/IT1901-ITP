package ui;

import saveHandler.UserSaveHandler;
import slots.Slots;

import java.net.URL;
import java.util.ResourceBundle;

public class SlotsController extends SlotsDisplay {



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        super.slotMachine = new Slots(UserSaveHandler.getActiveUser());
        super.slotMachine = new Slots();
        super.updateStats();
        super.viewAtStart();
    }


}