package slots;

import user.User;
import validators.SlotsValidator;

import java.util.*;
import javafx.fxml.FXML;

public class Slots {

    // Fields
    private List<String> symbols = new ArrayList<>();
    private double userBalance;
    private int spins;
    private int netGain;
    private int bet;
    private final Random random;
    private double currentWinnings;
    private String combo;
    private double averagePayout;
    private User user;
    private final String[] validSuits = new String[] {"S","D","H","C"};



    // Constructor

    // MAIN CONSTRUCTOR FOR FINAL PRODUCT
    public Slots(User user) {
        this.spins = 0;
        this.netGain = 0;
        this.bet = 0;
        for (int i = 0; i < 3; i++){
            symbols.add("");
        }
        this.random = new Random();
        this.user = user;
        this.userBalance = user.getBalance();
    }

    // CONSTRUCTOR FOR SLOTAPP
    public Slots() {
        this.spins = 0;
        this.netGain = 0;
        this.bet = 0;
        for (int i = 0; i < 3; i++){
            symbols.add("");
        }
        this.random = new Random();
        this.user = null;
        this.userBalance = 100000;
    }

    // CONSTRUCTOR FOR TESTS
    public Slots(double balance){
        this.spins = 0;
        this.netGain = 0;
        this.bet = 0;
        this.userBalance = balance;
        for (int i = 0; i < 3; i++){
            symbols.add("");
        }
        this.random = new Random();
    }

    public void play(int bet) {
        setBet(bet);
        placeBet();
        spin();
        updateWinnings();
    }


    public void spin(){
        for (int i = 0; i < symbols.size();i++){
            symbols.set(i,generateSymbol());
        }
        spins++;
        for (String symbol : symbols){
            System.out.println(symbol);
        }
    }

    private void placeBet() {
        this.netGain-=getBet();
        this.userBalance-=getBet();
    }


    private String generateSymbol(){
        return random.nextInt(9)+1
                + validSuits[random.nextInt(validSuits.length)]; // Generate a random number between 1-9, and add a random suit
    }


    private double calculateWinnings(){
        if (SlotsValidator.isDevil(symbols)){
            this.combo = "DEVIL";
            return 0;
        }
        if (SlotsValidator.isJackpot(symbols)) {
            this.combo = "JACKPOT";
            return getBet()*40;
        }
        if (SlotsValidator.isPerfectStraight(symbols)) {
            this.combo = "PERFECT STRAIGHT";
            return getBet()*4.5;
        }
        if (SlotsValidator.isStraight(symbols)) {
            this.combo = "STRAIGHT";
            return getBet()*2.7;
        }

        if (SlotsValidator.isPair(symbols)) {
            this.combo = "PAIR";
            return getBet()*1.75;
        }
        else {
            this.combo = "LOSS";
            return 0;
        }
    }

    public void updateWinnings(){
        var winnings = calculateWinnings();
        this.currentWinnings = winnings;
        netGain+=winnings;
        userBalance+=winnings;
        calculateAveragePayout();
    }

    private void calculateAveragePayout(){
        this.averagePayout = (double) netGain / spins;
    }

    // Getters and Setters


    public double getAveragePayout() {
        return averagePayout;
    }

    public double getUserBalance() {
        return userBalance;
    }

    public int getSpins() {
        return spins;
    }

    public int getNetGain() {
        return netGain;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        if (bet <= 0 || bet > this.getUserBalance())
            throw new IllegalArgumentException("Invalid bet");
        this.bet = bet;
    }

    public double getCurrentWinnings() {
        return currentWinnings;
    }

    public String getCombo() {
        return combo;
    }

    public List<String> getSymbols() {
        return symbols;
    }


    public User getUser() {
        return this.user;
    }
}



