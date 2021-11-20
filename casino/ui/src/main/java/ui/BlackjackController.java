package ui;

import blackjack.Blackjack;
import blackjack.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import savehandler.UserSaveHandler;
import ui.MenuItem.CasinoMenu;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BlackjackController extends CasinoMenu implements Initializable {


    private Blackjack blackjack;
    @FXML private Button hit;
    @FXML private Button stand;
    @FXML private ToggleButton toggleButton;
    @FXML private Button playAgainButton;
    @FXML private Button bet;
    @FXML private Text playerTotal;
    @FXML private Text dealerTotal;
    @FXML private Text payout;
    @FXML private Text result;
    @FXML private Text balanceField;
    @FXML private Button split;
    @FXML private TextField betAmount;
    @FXML private Text turnLabel;
    @FXML private Pane hand1;
    @FXML private Pane hand2;
    @FXML private HBox dealerHandHBox;
    private ObservableList<Pane> playerHandPanes = FXCollections.observableArrayList();
    private final UserSaveHandler userSaveHandler = new UserSaveHandler();
    private boolean dealerIsFlipped;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        blackjack = new Blackjack(user);
        disableGameButtons();
        playerHandPanes.add(hand1);
        playerHandPanes.add(hand2);
        this.balanceField.setText(""+user.getBalance());
        turnLabel.setText("Start a game");
    }

    private void disableGameButtons() {
        hit.setDisable(true);
        stand.setDisable(true);
        split.setDisable(true);
        toggleButton.setDisable(true);
        playAgainButton.setDisable(true);
    }

    @FXML
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
        this.balanceField.setText(""+user.getBalance());
        turnLabel.setText("Start a game");
    }

    @FXML
    public void bet() throws IOException {
        blackjack.startGame(Double.parseDouble(this.betAmount.getText()));
        userSaveHandler.updateUser(user);
        if (blackjack.getTargetHand().getSumOfDeck() < 21)
            hit.setDisable(false);
        stand.setDisable(false);
        if (blackjack.canSplit()){
            split.setDisable(false);
        }
        hand2.setOpacity(0.5);
        bet.setDisable(true);
        this.balanceField.setText(""+user.getBalance());

        showPlayerViewOnStart();
        showDealerViewOnStart();
        showTextOnStart();
    }

    private void showTextOnStart() {
        playerTotal.setText(""+blackjack.getTargetHand().getSumOfDeck());
        dealerTotal.setText(blackjack.getDealersHand().getDeck().get(1).getCardValue() + " + ?");
        turnLabel.setText("Player");
        payout.setVisible(false);
        result.setVisible(false);
    }

    @FXML
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

    @FXML
    public void toggle(){
        blackjack.toggleTargetHand();
        toggleHandPanes();
        turnLabel.setText("Player (Hand "
                + (blackjack.getPlayerHands().indexOf(blackjack.getTargetHand()) + 1) + ")");
        playerTotal.setText(""+blackjack.getTargetHand().getSumOfDeck());
    }

    @FXML
    public void hit() {
        blackjack.hit();

        if (!split.isDisabled()){
            split.setDisable(true);
        }
        if (blackjack.getTargetHand().getSumOfDeck() >= 21){
            hit.setDisable(true);
        }

        updatePlayerViews();
        playerTotal.setText(""+blackjack.getTargetHand().getSumOfDeck());
    }

    @FXML
    public void stand() throws IOException {
        blackjack.stand();
        if (blackjack.isPlayerDone()){
            stand.setDisable(true);
            split.setDisable(true);
            hit.setDisable(true);
            endGame();
        }
        else {
            if (blackjack.hasSplit()) {
                toggle();
            }
        }
    }

    private void endGame() throws IOException {
        updateDealerViews();
        userSaveHandler.updateUser(user);
        endOfGameView();
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
        this.balanceField.setText(""+user.getBalance());
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
        dealerIsFlipped = true;
    }

    public Blackjack getBlackjack() {
        return blackjack;
    }

    public Button getHit() {
        return hit;
    }

    public Button getStand() {
        return stand;
    }

    public ToggleButton getToggleButton() {
        return toggleButton;
    }

    public Button getPlayAgainButton() {
        return playAgainButton;
    }

    public Button getBet() {
        return bet;
    }

    public Text getPlayerTotal() {
        return playerTotal;
    }

    public Text getDealerTotal() {
        return dealerTotal;
    }

    public Text getPayout() {
        return payout;
    }

    public Text getResult() {
        return result;
    }

    public Text getBalanceField() {
        return balanceField;
    }

    public Button getSplit() {
        return split;
    }

    public Text getTurnLabel() {
        return turnLabel;
    }

    public Pane getHand2() {
        return hand2;
    }

    public HBox getDealerHandHBox() {
        return dealerHandHBox;
    }

    public ObservableList<Pane> getPlayerHandPanes() {
        return playerHandPanes;
    }


    public boolean isDealerIsFlipped() {
        return dealerIsFlipped;
    }
}

