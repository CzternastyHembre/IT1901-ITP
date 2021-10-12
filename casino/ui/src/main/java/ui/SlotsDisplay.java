package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import saveHandler.UserSaveHandler;
import slots.Slots;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class SlotsDisplay implements Initializable {

    protected Slots slotMachine;


    @FXML FXMLLoader loader = new FXMLLoader();
    @FXML private Button betButton;
    @FXML private TextField betField;
    @FXML public Pane slot1;
    @FXML public Pane slot2;
    @FXML public Pane slot3;
    @FXML public Label balanceNum;
    @FXML public Label netGainNum;
    @FXML public Label currentBetNum;
    @FXML public Label payoutNum;
    @FXML public Label comboSlot;

    @FXML public Label slot1Label;
    @FXML public Label slot2Label;
    @FXML public Label slot3Label;
    @FXML public Label avgPayout;
    @FXML public Label spinsCounter;

    @FXML public ToggleButton keepBetButton;


    public void spin(ActionEvent actionEvent) throws IOException {
        int bet = Integer.parseInt(betField.getText());
        slotMachine.play(bet);
        setColors();
        updateStats();
        updateUserState();
    }

    private void updateUserState() throws IOException {
        UserSaveHandler.updateUser(slotMachine.getUser());
    }

    public void setColors(){
        assignColor(slot1, slotMachine.getSymbols().get(0), slot1Label);
        assignColor(slot2, slotMachine.getSymbols().get(1), slot2Label);
        assignColor(slot3, slotMachine.getSymbols().get(2), slot3Label);
    }

    public void assignColor(Pane slot, int slotNum, Label slotLabel){
        switch (slotNum){
            case 1 -> slot.setStyle("-fx-background-color: red");
            case 2 -> slot.setStyle("-fx-background-color: orange");
            case 3 -> slot.setStyle("-fx-background-color: yellow");
            case 4 -> slot.setStyle("-fx-background-color: green");
            case 5 -> slot.setStyle("-fx-background-color: #00ff22");
            case 6 -> slot.setStyle("-fx-background-color: turquoise");
            case 7 -> slot.setStyle("-fx-background-color: #293bff");
            case 8 -> slot.setStyle("-fx-background-color: purple");
            case 9 -> slot.setStyle("-fx-background-color: violet");
            default -> System.out.println("default");
        }
        slotLabel.setText(""+slotNum);
    }


    protected void updateStats() {
        balanceNum.setText(""+slotMachine.getUserBalance());
        netGainNum.setText(""+slotMachine.getNetGain());
        currentBetNum.setText(""+slotMachine.getBet());
        payoutNum.setText(""+slotMachine.getCurrentWinnings());
        if (slotMachine.getCombo() == null)
            comboSlot.setText("Bet and Spin to start!");
        else
            comboSlot.setText(""+slotMachine.getCombo());
        avgPayout.setText("" + slotMachine.getAveragePayout());
        spinsCounter.setText("" + slotMachine.getSpins());
        if (!keepBetButton.isSelected())
            betField.setText("");
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

