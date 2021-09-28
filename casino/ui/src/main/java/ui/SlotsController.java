package ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import slots.Slots;

import java.net.URL;
import java.util.ResourceBundle;

public class SlotsController extends SlotsDisplay {



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.slotMachine = new Slots();
        super.updateStats();
    }


}