package ui;

import saveHandler.UserSaveHandler;
import slots.Slots;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SlotsController extends SlotsDisplay {



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            super.slotMachine = new Slots(UserSaveHandler.getActiveUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.updateStats();
    }
}