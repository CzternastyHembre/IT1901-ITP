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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import saveHandler.UserSaveHandler;
import slots.Slots;

import java.io.File;
import java.net.URL;
import java.util.*;

public abstract class SlotsDisplay implements Initializable {

    protected Slots slotMachine;

    private final Image backImage = new Image((getClass().getResourceAsStream("/images/cards/backOfCard.jpg")));

    @FXML FXMLLoader loader = new FXMLLoader();
    @FXML private Button betButton;
    @FXML private TextField betField;
    @FXML public ImageView slot1;
    @FXML public ImageView slot2;
    @FXML public ImageView slot3;

    @FXML public HBox slotHBox1;
    @FXML public HBox slotHBox2;
    @FXML public HBox slotHBox3;

    @FXML public Label balanceNum;
    @FXML public Label netGainNum;
    @FXML public Label currentBetNum;
    @FXML public Label payoutNum;
    @FXML public Label comboSlot;

    @FXML public Label avgPayout;
    @FXML public Label spinsCounter;

    @FXML public ToggleButton keepBetButton;

    private final List<HBox> hboxesList = new ArrayList<>();


    public void viewAtStart(){
        for (HBox box : hboxesList){
            box.getChildren().add(createImageView("backOfCard"));
        }
    }

    public void initializeHBoxes(){
        hboxesList.add(slotHBox1);
        hboxesList.add(slotHBox2);
        hboxesList.add(slotHBox3);
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
        removeCards();
        for (HBox box : hboxesList){
            assignCard(box, slotMachine.getSymbols().get(hboxesList.indexOf(box)));
        }
    }

    private void removeCards() {
        for (HBox box : hboxesList){
            box.getChildren().clear();
        }
    }

    public void assignCard(HBox slotHBox, String slotCard){
        slotHBox.getChildren().add(createImageView(slotCard));
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


    private ImageView createImageView(String imageName){
        System.out.println("/images/cards/" + imageName + ".jpg");
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/cards/" + imageName + ".jpg")));
        imageView.setFitWidth(148);
        imageView.setFitHeight(210);
        return imageView;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

