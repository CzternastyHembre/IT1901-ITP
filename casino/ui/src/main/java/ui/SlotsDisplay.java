package ui;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import saveHandler.UserSaveHandler;
import slots.Slots;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public abstract class SlotsDisplay implements Initializable {

    protected Slots slotMachine;

    @FXML public MenuItem mainMenu;
    @FXML public MenuItem lobby;
    @FXML FXMLLoader loader = new FXMLLoader();
    @FXML AnchorPane anchorPane;

    @FXML Button spinButton;

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

    public void spin(ActionEvent actionEvent) throws IOException {
        int bet = Integer.parseInt(betField.getText());
        slotMachine.play(bet);
        for (HBox box : hboxesList){
            rotateCard(box, 0);
        }
    }

    private void updateUserState() throws IOException {
        UserSaveHandler.updateUser(slotMachine.getUser());
    }


    public void updateCardsDisplay(){
        for (HBox box : hboxesList) {
            box.getChildren().set(0, createImageView(slotMachine.getSymbols().get(hboxesList.indexOf(box))));
        }
    }

    public void displayBackOfCard(){
        for (HBox box : hboxesList) {
            box.getChildren().set(0, createImageView("backOfCard"));
        }
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




    private void rotateCard(Node card, int stage) {
        switch (stage){
            case 0 -> {
                animateCard(card, 90, stage);
                spinButton.setDisable(true);
            }
            case 1 -> {
                animateCard(card, 90, stage);
                displayBackOfCard();
            }
            case 2 -> animateCard(card, -90, stage);
            case 3 -> {
                animateCard(card, -90, stage);
                updateCardsDisplay();
            }
            case 4 -> {
                updateStats();
                updateUserState();
                spinButton.setDisable(false);
            }
        }
    }

    private void animateCard(Node card, double angle, int stage) {
        RotateTransition rotator = new RotateTransition(Duration.millis(500), card);
        double currentAngle = card.getRotate();
        rotator.setAxis(new Point3D(0,5,0));
        rotator.setFromAngle(currentAngle);
        rotator.setToAngle(currentAngle + angle);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(1);
        rotator.play();
        rotator.setOnFinished(event ->{
            rotateCard(card, stage + 1);
        });
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

