package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import saveHandler.UserSaveHandler;
import slots.Slots;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class SlotsDisplay implements Initializable {

    protected Slots slotMachine;

    private final Image backImage = new Image((getClass().getResourceAsStream("/images/cards/backOfCard.jpg")));

    @FXML FXMLLoader loader = new FXMLLoader();
    @FXML private Button betButton;
    @FXML private TextField betField;
    @FXML public ImageView slot1;
    @FXML public ImageView slot2;
    @FXML public ImageView slot3;

    @FXML public Label balanceNum;
    @FXML public Label netGainNum;
    @FXML public Label currentBetNum;
    @FXML public Label payoutNum;
    @FXML public Label comboSlot;

    @FXML public Label avgPayout;
    @FXML public Label spinsCounter;

    @FXML public ToggleButton keepBetButton;



    public void viewAtStart(){
        System.out.println(this.backImage.getUrl());
        slot1 = new ImageView(this.backImage);
        System.out.println(this.backImage.getWidth());
        System.out.println(this.backImage.getHeight());
        slot2 = new ImageView(this.backImage);
        slot3 = new ImageView(this.backImage);
    }


    public void spin(ActionEvent actionEvent){
        int bet = Integer.parseInt(betField.getText());
        slotMachine.play(bet);
        updateCardsDisplay();
        updateStats();
//        updateUserState();
    }

    private void updateUserState() {
        UserSaveHandler.updateUser(slotMachine.getUser());
    }


    public void updateCardsDisplay(){
        System.out.println(slotMachine.getSymbols().get(0));
        assignCard(this.slot1,slotMachine.getSymbols().get(0));
        assignCard(this.slot2,slotMachine.getSymbols().get(1));
        assignCard(this.slot3,slotMachine.getSymbols().get(2));
    }

    public void assignCard(ImageView slot, String slotCard){
        File file = new File("../images/cards/" + slotCard + ".jpg");
        Image image = new Image(file.toURI().toString());
        slot.setImage(image);
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

