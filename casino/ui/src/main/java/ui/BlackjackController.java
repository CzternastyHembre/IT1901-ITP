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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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

    public void disableGameButtons() {
        hit.setDisable(true);
        stand.setDisable(true);
        split.setDisable(true);
        radioHand1.setDisable(true);
        radioHand2.setDisable(true);
    }


    public void bet(){
        blackjack.startGame(Integer.parseInt(this.betAmount.getText()));
        hit.setDisable(false);
        stand.setDisable(false);
        if (blackjack.canSplit()){
            split.setDisable(false);
        }
        turnLabel.setText("Player");
        hand2.setDisable(true);

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
        radioHand1.setDisable(false);
        radioHand2.setDisable(false);
        radioHand1.setSelected(true);
    }


    public void hit() {
        blackjack.hit();
        if (!blackjack.getTargetHand().isActive() && blackjack.hasSplit()){
            toggleHandPanes();
        }
        updatePlayerViews();
        if (blackjack.getTargetHand().getSumOfDeck() > 21){
            hit.setDisable(true);
        }
        playerTotal.setText(""+blackjack.getTargetHand().getSumOfDeck());
    }

    public void stand() {
        blackjack.stand(blackjack.getTargetHand());
        if (blackjack.isPlayerDone()){
            updateDealerViews();
            endOfGameView();
        }
        if (blackjack.hasSplit()) {
            if (!blackjack.isPlayerDone()){
                toggleHandPanes();
            }
            else {
                radioHand1.setDisable(true);
                radioHand2.setDisable(true);
            }
        }
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
    }

    private void toggleHandPanes() {
        var activePane = playerHandPanes.stream().filter(pane ->
                !pane.isDisabled()).collect(Collectors.toList()).get(0);
        var disabledPane = playerHandPanes.stream().filter(
                Node::isDisabled).collect(Collectors.toList()).get(0);
        activePane.setDisable(true);
        disabledPane.setDisable(false);
    }


    public ImageView createImageView(String name){
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(
                BlackjackController.class.getResourceAsStream("/images/cards/" + name))));
        imageView.setFitHeight(100);
        imageView.setFitWidth(65);
        return imageView;
    }

    public void showPlayerViewOnStart(){
        for (Card card : blackjack.getPlayerHands().get(0).getDeck()){
            playerHandPanes.get(0).getChildren().add(createImageView(card.getCardImage()));
        }
    }

    public void showDealerViewOnStart(){
        ImageView firstCard = createImageView("backOfCard.jpg");
        ImageView secondCard = createImageView(blackjack.getDealersHand().getDeck().get(1).getCardImage());
        dealerHandHBox.getChildren().add(firstCard);
        dealerHandHBox.getChildren().add(secondCard);
    }

    public void updatePlayerViews(){
        for (int i = 0; i<2; i++){
            playerHandPanes.get(i).getChildren().clear();
            for (Card card : blackjack.getPlayerHands().get(i).getDeck()){
                playerHandPanes.get(i).getChildren().add(createImageView(card.getCardImage()));
            }
        }
    }

    public void updateDealerViews(){
        dealerHandHBox.getChildren().clear();
        for (Card card : blackjack.getDealersHand().getDeck()){
            dealerHandHBox.getChildren().add(createImageView(card.getCardImage()));
        }
    }



    public void radioButtonClick(ActionEvent event){
        RadioButton buttonClicked = (RadioButton) event.getTarget();
        switch (buttonClicked.getText().toLowerCase()){
            case "hand 1" -> {
                playerHandPanes.get(0).setDisable(false);
                playerHandPanes.get(1).setDisable(true);
                blackjack.setTargetHand(blackjack.getPlayerHands().get(0));
            }
            case "hand 2" -> {
                playerHandPanes.get(0).setDisable(true);
                playerHandPanes.get(1).setDisable(false);
                blackjack.setTargetHand(blackjack.getPlayerHands().get(1));
            }
        }
    }


}

