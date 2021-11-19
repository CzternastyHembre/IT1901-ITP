package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import savehandler.UserSaveHandler;
import slots.Slots;
import ui.MenuItem.CasinoMenu;
import user.User;


/**
 * Abstract class for the slotsController which manipulates the slotsController view.
 *
 *
 */

public abstract class SlotsDisplay extends CasinoMenu implements Initializable {

  protected Slots slotMachine;

  @FXML
  private MenuItem mainMenu;
  @FXML
  private MenuItem lobby;
  @FXML
  private Button spinButton;

  @FXML
  private TextField betField;

  @FXML
  private HBox slotHbox1;
  @FXML
  private HBox slotHbox2;
  @FXML
  private HBox slotHbox3;

  @FXML
  private Label balanceNum;
  @FXML
  private Label netGainNum;
  @FXML
  private Label currentBetNum;
  @FXML
  private Label payoutNum;
  @FXML
  private Label comboSlot;

  @FXML
  private Label avgPayout;
  @FXML
  private Label spinsCounter;

  @FXML
  private ToggleButton keepBetButton;

  private final List<HBox> hboxesList = new ArrayList<>();
  private final RestModel restModel = new RestModel();

  protected void initializeHboxes() {
    hboxesList.add(slotHbox1);
    hboxesList.add(slotHbox2);
    hboxesList.add(slotHbox3);
  }

  protected void viewAtStart() {
    for (HBox box : hboxesList) {
      box.getChildren().add(createImageView("backOfCard"));
    }
  }


  /**
   * This is run when the spinButton is clicked. This "spins" the cards,
   * and play's the bet.
   *
   *
   */

  public void spin(ActionEvent actionEvent) throws InterruptedException {
    int bet = Integer.parseInt(betField.getText());
    slotMachine.play(bet);
    for (HBox box : hboxesList) {
      rotateCard(box, 0);
    }
  }


  private void updateUserState() throws InterruptedException {
    RestModel.updateUser(user);
  }

  /**
   * Switches out the previous cards with the new cards that should be displayed.
   *
   *
   */
  public void updateCardsDisplay() {
    for (HBox box : hboxesList) {
      box.getChildren().set(0, createImageView(
              slotMachine.getSymbols().get(hboxesList.indexOf(box))));
    }
  }

  /**
   * Makes all 3 cards display the backOfCard image.
   *
   *
   */
  public void displayBackOfCard() {
    for (HBox box : hboxesList) {
      box.getChildren().set(0, createImageView("backOfCard"));
    }
  }

  /**
   * Updates the on-screen stats.
   *
   *
   */
  protected void updateStats() {
    balanceNum.setText("" + slotMachine.getUserBalance());
    netGainNum.setText("" + slotMachine.getNetGain());
    currentBetNum.setText("" + slotMachine.getBet());
    payoutNum.setText("" + slotMachine.getCurrentWinnings());
    if (slotMachine.getCombo() == null) {
      comboSlot.setText("Bet and Spin to start!");
    } else {
      comboSlot.setText("" + slotMachine.getCombo());
    }
    avgPayout.setText("" + (Math.round(slotMachine.getAveragePayout() * 100.0) / 100.0));
    spinsCounter.setText("" + slotMachine.getSpins());
    if (!keepBetButton.isSelected()) {
      betField.setText("");
    }
  }

  /**
   * Creates an image view based on the given string.
   *
   * @param imageName The name of the card (e.g. 4H)
   */

  private ImageView createImageView(String imageName) {
    ImageView imageView = new ImageView(new Image(
        Objects.requireNonNull(SlotsDisplay.class.getResourceAsStream(
                "/images/cards/" + imageName + ".jpg"))));
    imageView.setFitWidth(148);
    imageView.setFitHeight(210);
    return imageView;
  }


  /**
   * Continues the rotation of a given card based on where in the rotation it is.
   *
   * @param card the node which will be rotated.
   * @param stage which stage of the rotation is the card in.
   *
   */

  private void rotateCard(Node card, int stage) throws InterruptedException {
    switch (stage) {
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
      default -> {
      }
    }
  }

  /**
   * Actually rotates the card based on the angle and which stage it is in.
   *
   * @param card the node being rotated.
   *
   * @param angle the angle of which the card should be rotated.
   *
   * @param stage which stage the rotation is in.
   */

  private void animateCard(Node card, double angle, int stage) {
    RotateTransition rotator = new RotateTransition(Duration.millis(500), card);
    double currentAngle = card.getRotate();
    rotator.setAxis(new Point3D(0, 5, 0));
    rotator.setFromAngle(currentAngle);
    rotator.setToAngle(currentAngle + angle);
    rotator.setInterpolator(Interpolator.LINEAR);
    rotator.setCycleCount(1);
    rotator.play();
    rotator.setOnFinished(event -> {
      try {
        rotateCard(card, stage + 1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  public TextField getBetField() {
    return betField;
  }

  public Label getBalanceNum() {
    return balanceNum;
  }

  public Label getNetGainNum() {
    return netGainNum;
  }

  public Label getCurrentBetNum() {
    return currentBetNum;
  }

  public Label getPayoutNum() {
    return payoutNum;
  }

  public Label getComboSlot() {
    return comboSlot;
  }

  public Label getAvgPayout() {
    return avgPayout;
  }

  public Label getSpinsCounter() {
    return spinsCounter;
  }

  public List<HBox> getHboxesList() {
    return hboxesList;
  }
}
