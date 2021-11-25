package ui;

import blackjack.Blackjack;
import blackjack.Card;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ui.menuItem.CasinoMenu;


/**
 * BlackjackController is the controller that manipulates the blackjack view.
 */
public class BlackjackController extends CasinoMenu implements Initializable {


  private Blackjack blackjack;
  @FXML
  private Button hit;
  @FXML
  private Button stand;
  @FXML
  private ToggleButton toggleButton;
  @FXML
  private Button playAgainButton;
  @FXML
  private Button bet;
  @FXML
  private Text playerTotal;
  @FXML
  private Text dealerTotal;
  @FXML
  private Text payout;
  @FXML
  private Text result;
  @FXML
  private Text balanceField;
  @FXML
  private Button split;
  @FXML
  private TextField betAmount;
  @FXML
  private Text turnLabel;
  @FXML
  private Pane hand1;
  @FXML
  private Pane hand2;
  @FXML
  private HBox dealerHandHbox;
  private ObservableList<Pane> playerHandPanes = FXCollections.observableArrayList();


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    createMenu();
    blackjack = new Blackjack(user);
    disableGameButtons();
    playerHandPanes.add(hand1);
    playerHandPanes.add(hand2);
    this.balanceField.setText("" + user.getBalance());
    turnLabel.setText("Start a game");
    payout.setVisible(false);
    result.setVisible(false);

  }

  private void disableGameButtons() {
    hit.setDisable(true);
    stand.setDisable(true);
    split.setDisable(true);
    toggleButton.setDisable(true);
    playAgainButton.setDisable(true);
  }

  /**
   * playAgain is run when the playAgainButton is clicked.
   * This resets the game view to what it looked like before a bet was played
   *
   */
  @FXML
  public void playAgain() {
    blackjack = new Blackjack(user);
    disableGameButtons();
    for (Pane pane : playerHandPanes) {
      pane.getChildren().clear();
    }
    dealerHandHbox.getChildren().clear();
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
    this.balanceField.setText("" + user.getBalance());
    turnLabel.setText("Start a game");
  }

  /**
   * bet is run when the bet button is clicked. This starts the blackjack game,
   * updates the user's balance, and sets the view for the start of the game.
   *
   * @throws InterruptedException from restModel (Sending Http request)
   */
  @FXML
  public void bet() throws InterruptedException {
    blackjack.startGame(Double.parseDouble(this.betAmount.getText()));
    restModel.updateUser(user);
    if (blackjack.isInstantBlackjack()) {
      instantWinView();
    } else {
      if (blackjack.getTargetHand().getSumOfDeck() < 21) {
        hit.setDisable(false);
      }
      if (blackjack.isCanSplit()) {
        split.setDisable(false);
      }
      stand.setDisable(false);
      showDealerViewOnStart();
      showTextOnStart();
      this.balanceField.setText("" + user.getBalance());
    }

    hand2.setOpacity(0.5);
    bet.setDisable(true);
    showPlayerViewOnStart();
  }

  private void instantWinView() throws InterruptedException {
    updateDealerViews();
    stand.setDisable(true);
    endGame();
  }

  private void showTextOnStart() {
    playerTotal.setText("" + blackjack.getTargetHand().getSumOfDeck());
    dealerTotal.setText(blackjack.getDealersHand().getDeck().get(1).getCardValue() + " + ?");
    turnLabel.setText("Player");
    payout.setVisible(false);
    result.setVisible(false);
  }

  /**
   * split is run when the split button is clicked. It runs the split method in blackjack,
   * and updates views allowing for a player to toggle between their two hands.
   */
  @FXML
  public void split() {
    blackjack.split();
    toggleButton.setDisable(false);
    updatePlayerViews();
    hand2.setOpacity(0.5);
    split.setDisable(true);
    playerTotal.setText("" + blackjack.getTargetHand().getSumOfDeck());
    turnLabel.setText("Player (Hand "
            + (blackjack.getPlayerHands().indexOf(blackjack.getTargetHand()) + 1) + ")");
  }

  /**
   * toggle is run when the toggleHandButton is clicked.
   * This toggles which hand is active (if a player has split)
   *
   */
  @FXML
  public void toggle() {
    blackjack.toggleTargetHand();
    toggleHandPanes();
    turnLabel.setText("Player (Hand "
            + (blackjack.getPlayerHands().indexOf(blackjack.getTargetHand()) + 1) + ")");
    playerTotal.setText("" + blackjack.getTargetHand().getSumOfDeck());
  }

  /**
   * hit is run when the hit button is clicked. This updates the user's display, and disables
   * buttons based on which state the game is in.
   */
  @FXML
  public void hit() {
    blackjack.hit();

    if (!split.isDisabled()) {
      split.setDisable(true);
    }
    if (blackjack.getTargetHand().getSumOfDeck() >= 21) {
      hit.setDisable(true);
    }

    updatePlayerViews();
    playerTotal.setText("" + blackjack.getTargetHand().getSumOfDeck());
  }

  /**
   * stand is called when stand button is clicked. If the user is done hitting on their hands,
   * the dealer's view is updated, and buttons are disabled (and play again button is enabled).
   * If not, then the view is toggled.
   *
   * @throws InterruptedException from RestModel sending Http Request.
   */
  @FXML
  public void stand() throws InterruptedException {
    blackjack.stand();
    if (blackjack.isPlayerDone()) {
      stand.setDisable(true);
      split.setDisable(true);
      hit.setDisable(true);
      endGame();
    } else {
      if (blackjack.isHasSplit()) {
        toggle();
      }
    }
  }

  private void endGame() throws InterruptedException {
    updateDealerViews();
    restModel.updateUser(user);
    endOfGameView();
  }

  private void endOfGameView() {
    if (blackjack.getPayout() > 0) {
      if (blackjack.getPayout() == blackjack.getBet()) {
        result.setText("NO GAIN!");
      } else {
        result.setText("WIN!");
      }
    } else {
      result.setText("LOSS!");
    }
    toggleButton.setDisable(true);
    playerTotal.setText("" + blackjack.getTargetHand().getSumOfDeck());
    dealerTotal.setText("" + blackjack.getDealersHand().getSumOfDeck());
    turnLabel.setText("Game Over");
    result.setVisible(true);
    payout.setVisible(true);
    payout.setText("" + blackjack.getPayout());
    this.balanceField.setText("" + user.getBalance());
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


  private ImageView createImageView(String name) {
    ImageView imageView = new ImageView(new Image(Objects.requireNonNull(
            BlackjackController.class.getResourceAsStream("/images/cards/" + name))));
    imageView.setFitHeight(100);
    imageView.setFitWidth(65);
    return imageView;
  }

  private void showPlayerViewOnStart() {
    for (Card card : blackjack.getPlayerHands().get(0).getDeck()) {
      playerHandPanes.get(0).getChildren().add(createImageView(card.getCardImage()));
    }
  }

  private void showDealerViewOnStart() {
    ImageView firstCard = createImageView("backOfCard.jpg");
    ImageView secondCard = createImageView(
            blackjack.getDealersHand().getDeck().get(1).getCardImage());
    dealerHandHbox.getChildren().add(firstCard);
    dealerHandHbox.getChildren().add(secondCard);
  }

  private void updatePlayerViews() {
    for (int i = 0; i < 2; i++) {
      playerHandPanes.get(i).getChildren().clear();
      for (Card card : blackjack.getPlayerHands().get(i).getDeck()) {
        playerHandPanes.get(i).getChildren().add(createImageView(card.getCardImage()));
      }
    }
  }

  private void updateDealerViews() {
    dealerHandHbox.getChildren().clear();
    for (Card card : blackjack.getDealersHand().getDeck()) {
      dealerHandHbox.getChildren().add(createImageView(card.getCardImage()));
    }
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

  public HBox getDealerHandHbox() {
    return dealerHandHbox;
  }

  public ObservableList<Pane> getPlayerHandPanes() {
    return playerHandPanes;
  }

}

