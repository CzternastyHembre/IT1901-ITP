package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import saveHandler.UserSaveHandler;
import slots.Slots;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public abstract class SlotsDisplay implements Initializable {

    protected Slots slotMachine;

    @FXML public MenuItem mainMenu;
    @FXML public MenuItem lobby;
    @FXML FXMLLoader loader = new FXMLLoader();
    @FXML AnchorPane anchorPane;

    @FXML private TextField betField;

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
        else{
            comboSlot.setText(""+slotMachine.getCombo());
        }
        avgPayout.setText("" + (Math.round( slotMachine.getAveragePayout()*100.0)/100.0));
        spinsCounter.setText("" + slotMachine.getSpins());
        if (!keepBetButton.isSelected())
            betField.setText("");
    }


    private ImageView createImageView(String imageName){
        ImageView imageView = new ImageView(new Image(SlotsDisplay.class.getResourceAsStream("/images/cards/" + imageName + ".jpg")));
        imageView.setFitWidth(148);
        imageView.setFitHeight(210);
        return imageView;
    }


    @FXML
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void backToMainMenu(ActionEvent actionEvent) throws IOException {
        //Sets location on the loader by getting the class and then the view file from resources
        loader.setLocation(getClass().getResource("Start.fxml"));
        Parent newGame = loader.load(); // Create a parent class of the loader.load()
        Scene newGameScene = new Scene(newGame); //Create a new Scene from the parent object

        Stage window = (Stage) anchorPane.getScene().getWindow();   //Create new Stage to from the view-file
        window.setScene(newGameScene);  //Set the window to the previous chosen scene

        window.show();  //Opens the window
    }

    @FXML
    public void backToLobby(ActionEvent actionEvent) throws IOException {
        //Sets location on the loader by getting the class and then the view file from resources
        loader.setLocation(getClass().getResource("selectGameView.fxml"));
        Parent newGame = loader.load(); // Create a parent class of the loader.load()
        Scene newGameScene = new Scene(newGame); //Create a new Scene from the parent object

        Stage window = (Stage) anchorPane.getScene().getWindow();   //Create new Stage to from the view-file
        window.setScene(newGameScene);  //Set the window to the previous chosen scene

        window.show();  //Opens the window
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

