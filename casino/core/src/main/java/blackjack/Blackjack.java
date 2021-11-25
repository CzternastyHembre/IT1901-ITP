package blackjack;

import java.util.ArrayList;
import java.util.stream.Collectors;
import user.User;


/**
 * Blueprint for how a blackjack game is played.
 */
public class Blackjack {
  private double bet;
  private final User user;

  private Deck dealingDeck;
  private Hand dealersHand;
  private final ArrayList<Hand> playerHands = new ArrayList<>();
  private Hand playersHand1;
  private Hand playersHand2;
  private boolean canSplit;
  private Hand targetHand;
  private boolean hasSplit;
  private double payout;
  private boolean instantBlackjack;

  /**
   * Creates a new blackjack game.
   *
   * @param user sets the user.
   */
  public Blackjack(User user) {
    this.user = user;
  }


  /**
   * Starts the blackjack game by placing the bet and dealing the cards.
   *
   * @param bet the amount being bet by the user.
   */
  public void startGame(double bet) {
    setBet(bet);
    this.dealingDeck = new Deck(13);

    this.playersHand1 = new Hand();
    this.playersHand2 = new Hand();
    this.playersHand2.setActive(false);
    this.playerHands.add(playersHand1);
    this.playerHands.add(playersHand2);
    this.payout = 0;
    this.dealersHand = new Hand();


    deal();
    setCanSplit();
    this.targetHand = playersHand1;
  }

  private void setBet(double bet) {
    if (bet <= 0 || bet > this.user.getBalance()) {
      throw new IllegalArgumentException("Invalid bet");
    }
    this.bet = bet;
    this.user.setBalance(this.user.getBalance() - this.bet);
  }

  private void setCanSplit() {
    this.canSplit = playersHand1.getDeck().get(0).getCardValue()
            == playersHand1.getDeck().get(1).getCardValue()
            && (this.bet * 2) <= user.getBalance();
  }

  private void deal() {
    for (int i = 0; i < 2; i++) {
      playersHand1.getDeck().add(dealingDeck.popTopCard());
      dealersHand.getDeck().add(dealingDeck.popTopCard());
    }
    if (playersHand1.getSumOfDeck() == 21) {
      instantBlackjack = true;
      endGame();
    }
  }

  /**
   * Adds a card to the targeted hand from the dealing deck,
   * and disables splitting ability.
   *
   * @throws IllegalArgumentException if the hand that is being targeted
   *                                  for the hit is inactive (21 or over)
   */
  public void hit() throws IllegalArgumentException {
    if (!targetHand.isActive()) {
      throw new IllegalArgumentException("Deck is inactive, cannot hit");
    }
    canSplit = false;
    Card cardToAdd = dealingDeck.popTopCard();
    targetHand.getDeck().add(cardToAdd);
    int sumOfDeck = targetHand.getSumOfDeck();
    if (sumOfDeck >= 21) {
      this.targetHand.setActive(false);
    }

  }

  /**
   * Makes the target hand inactive. If all player hands are inactive,
   * then play the dealers hand.
   */
  public void stand() {
    this.targetHand.setActive(false);
    if (isPlayerDone()) {
      dealerPlay();
    }
  }

  /**
   * Split the hand, withdraw another bet from the player's balance
   * and take a card from the first hand and add it onto the second
   * hand.
   */
  public void split() {
    setCanSplit();
    if (!canSplit) {
      return;
    }
    user.setBalance(user.getBalance() - bet);
    Card card = playersHand1.getLastCard();
    this.playersHand1.getDeck().remove(card);
    this.playersHand2.getDeck().add(card);
    playersHand2.setActive(true);
    setTargetHand(playersHand1);
    hasSplit = true;
    canSplit = false;
  }

  /**
   * Toggle which player hand is active and controlled.
   */
  public void toggleTargetHand() {
    setTargetHand(playerHands.stream().filter(
            hand -> !hand.equals(targetHand)).collect(Collectors.toList()).get(0));
  }

  /**
   * Play the dealer hand. Continue hitting until the
   * dealer has reached at least 17,
   * Pay the player their winnings.
   */
  public void dealerPlay() {
    if (!(dealersHand.getSumOfDeck() == 21)) {
      while (dealersHand.getSumOfDeck() < 17) {
        dealersHand.getDeck().add(dealingDeck.popTopCard());
      }
    }
    endGame();
  }

  private void endGame() {
    dealersHand.setActive(false);
    calculateWinnings();
    user.addMoney(this.payout);
  }

  private void calculateWinnings() {
    int dealerSum = dealersHand.getSumOfDeck();
    if (instantBlackjack) {
      if (dealerSum == 21) {
        this.payout = bet;
      } else {
        this.payout = bet * 1.5;
      }
      return;
    }
    if (dealerSum == 21) {
      return;
    } else if (dealerSum > 21) {
      dealerSum = 0;
    }
    double hand1Score = calculateHand(playersHand1, dealerSum);
    double hand2Score = 0;
    if (playersHand2.getSumOfDeck() != 0) {
      hand2Score = calculateHand(playersHand2, dealerSum);
    }
    this.payout = hand1Score + hand2Score;
  }

  private double calculateHand(Hand hand, double dealerSum) {

    if (hand.getSumOfDeck() == 0 || hand.getSumOfDeck() > 21) {
      return 0;
    }
    if (hand.getSumOfDeck() <= 21 && hand.getSumOfDeck() > dealerSum) {
      return bet * 2;
    }

    return 0;
  }

  public Hand getDealersHand() {
    return dealersHand;
  }

  public User getUser() {
    return user;
  }

  public ArrayList<Hand> getPlayerHands() {
    return playerHands;
  }

  public boolean canSplit() {
    return canSplit;
  }

  public void setTargetHand(Hand hand) {
    this.targetHand = hand;
  }

  public Hand getTargetHand() {
    return targetHand;
  }

  public boolean hasSplit() {
    return hasSplit;
  }

  public boolean isPlayerDone() {
    return !playersHand1.isActive() && !playersHand2.isActive();
  }

  public double getPayout() {
    return payout;
  }

  public double getBet() {
    return bet;
  }

  public Deck getDealingDeck() {
    return dealingDeck;
  }

  public Hand getPlayersHand1() {
    return playersHand1;
  }

  public Hand getPlayersHand2() {
    return playersHand2;
  }

  public boolean isCanSplit() {
    return canSplit;
  }

  public boolean isHasSplit() {
    return hasSplit;
  }

  public void setPlayersHand1(Hand playersHand1) {
    this.playersHand1 = playersHand1;
  }

  public boolean isInstantBlackjack() {
    return instantBlackjack;
  }
}
