package ui;

import blackjack.Blackjack;
import blackjack.Card;
import blackjack.Hand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import savehandler.UserSaveHandler;
import ui.MenuItem.CasinoMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BlackjackController extends CasinoMenu implements Initializable {


    protected Blackjack blackjack;
    @FXML MenuItem mainMenu;
    @FXML MenuItem lobby;
    @FXML MenuItem exit;
    @FXML FXMLLoader loader = new FXMLLoader();
    @FXML AnchorPane anchorPane;
    @FXML Button hit;
    @FXML Button stand;
    @FXML ToggleButton toggleButton;
    @FXML Button playAgainButton;
    @FXML Button bet;

    @FXML Text playerTotal;
    @FXML Text dealerTotal;
    @FXML Text payout;
    @FXML Text result;

    @FXML RadioButton radioHand1;
    @FXML RadioButton radioHand2;

    @FXML Button split;
    @FXML TextField betAmount;
    @FXML Text turnLabel;
    private ObservableList<Pane> playerHandPanes = FXCollections.observableArrayList();
    @FXML private Pane hand1;
    @FXML private Pane hand2;
    @FXML private HBox dealerHandHBox;


    private final UserSaveHandler userSaveHandler = new UserSaveHandler();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        blackjack = new Blackjack(user);
        disableGameButtons();
        playerHandPanes.add(hand1);
        playerHandPanes.add(hand2);
    }

    private void disableGameButtons() {
        hit.setDisable(true);
        stand.setDisable(true);
        split.setDisable(true);
        toggleButton.setDisable(true);
        playAgainButton.setDisable(true);
    }

    public void playAgain(){
        blackjack = new Blackjack(user);
        disableGameButtons();
        for (Pane pane : playerHandPanes){
            pane.getChildren().clear();
        }
        dealerHandHBox.getChildren().clear();
        resetText();
        bet.setDisable(false);
        hand1.setOpacity(1);
        hand2.setOpacity(1);
    }

    private void resetText() {
        playerTotal.setText("0");
        dealerTotal.setText("0");
        payout.setVisible(false);
        result.setVisible(false);
    }

    public void bet(){
        blackjack.startGame(Integer.parseInt(this.betAmount.getText()));
        hit.setDisable(false);
        stand.setDisable(false);
        if (blackjack.canSplit()){
            split.setDisable(false);
        }
        turnLabel.setText("Player");
        hand2.setOpacity(0.5);
        bet.setDisable(true);

        showPlayerViewOnStart();
        showDealerViewOnStart();
        showTextOnStart();
    }

    private void showTextOnStart() {
        playerTotal.setText(""+blackjack.getTargetHand().getSumOfDeck());
        dealerTotal.setText(blackjack.getDealersHand().getDeck().get(1).getCardValue() + " + ?");
        payout.setVisible(false);
        result.setVisible(false);
    }


    public void split(){
        blackjack.split();
        toggleButton.setDisable(false);
        updatePlayerViews();
        hand2.setOpacity(0.5);
        split.setDisable(true);
        playerTotal.setText(""+blackjack.getTargetHand().getSumOfDeck());
        turnLabel.setText("Player (Hand "
                + (blackjack.getPlayerHands().indexOf(blackjack.getTargetHand()) + 1) + ")");
    }

    public void toggle(){
        blackjack.toggleTargetHand();
        toggleHandPanes();
        turnLabel.setText("Player (Hand "
                + (blackjack.getPlayerHands().indexOf(blackjack.getTargetHand()) + 1) + ")");
        playerTotal.setText(""+blackjack.getTargetHand().getSumOfDeck());
    }

    public void hit() {
        blackjack.hit();

        if (!split.isDisabled()){ // works
            split.setDisable(true);
        }
        if (blackjack.getTargetHand().getSumOfDeck() > 21){
            hit.setDisable(true);
        }

        updatePlayerViews();
        playerTotal.setText(""+blackjack.getTargetHand().getSumOfDeck());
    }

    public void stand() {
        blackjack.stand();
        if (blackjack.isPlayerDone()){
            updateDealerViews();
            endOfGameView();
        }
        else {
            if (blackjack.hasSplit()) {
                toggle();
            }
        }
        //todo; disable split on stand even if hasn't split
        //todo; hitting stand on split makes the hit button disable even if a player can still bet on one of the hands
    }

    private void endOfGameView() {
        if (blackjack.getPayout() > 0){
            result.setText("WIN!");
        }
        else {
            result.setText("LOSS!");
        }
        dealerTotal.setText(""+blackjack.getDealersHand().getSumOfDeck());
        result.setVisible(true);
        payout.setVisible(true);
        payout.setText(""+blackjack.getPayout());
        playAgainButton.setDisable(false);
    }

    private void toggleHandPanes() {
        var activePane = playerHandPanes.stream().filter(pane ->
                pane.getOpacity() == 1).collect(Collectors.toList()).get(0);
        var disabledPane = playerHandPanes.stream().filter(pane ->
                pane.getOpacity() == 0.5).collect(Collectors.toList()).get(0);
        activePane.setOpacity(0.5);
        disabledPane.setOpacity(1);

        hit.setDisable(!blackjack.getTargetHand().isActive());

    }


    private ImageView createImageView(String name){
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(
                BlackjackController.class.getResourceAsStream("/images/cards/" + name))));
        imageView.setFitHeight(100);
        imageView.setFitWidth(65);
        return imageView;
    }

    private void showPlayerViewOnStart(){
        for (Card card : blackjack.getPlayerHands().get(0).getDeck()){
            playerHandPanes.get(0).getChildren().add(createImageView(card.getCardImage()));
        }
    }

    private void showDealerViewOnStart(){
        ImageView firstCard = createImageView("backOfCard.jpg");
        ImageView secondCard = createImageView(blackjack.getDealersHand().getDeck().get(1).getCardImage());
        dealerHandHBox.getChildren().add(firstCard);
        dealerHandHBox.getChildren().add(secondCard);
    }

    private void updatePlayerViews(){
        for (int i = 0; i<2; i++){
            playerHandPanes.get(i).getChildren().clear();
            for (Card card : blackjack.getPlayerHands().get(i).getDeck()){
                playerHandPanes.get(i).getChildren().add(createImageView(card.getCardImage()));
            }
        }
    }

    private void updateDealerViews(){
        dealerHandHBox.getChildren().clear();
        for (Card card : blackjack.getDealersHand().getDeck()){
            dealerHandHBox.getChildren().add(createImageView(card.getCardImage()));
        }
    }



//    private void radioButtonClick(ActionEvent event){
//        RadioButton buttonClicked = (RadioButton) event.getTarget();
//        switch (buttonClicked.getText().toLowerCase()){
//            case "hand 1" -> {
//                playerHandPanes.get(0).setDisable(false);
//                playerHandPanes.get(1).setDisable(true);
//                blackjack.setTargetHand(blackjack.getPlayerHands().get(0));
//            }
//            case "hand 2" -> {
//                playerHandPanes.get(0).setDisable(true);
//                playerHandPanes.get(1).setDisable(false);
//                blackjack.setTargetHand(blackjack.getPlayerHands().get(1));
//            }
//        }
//    }


}

