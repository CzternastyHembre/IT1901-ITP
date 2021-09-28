package slots;

import user.User;
import validators.SlotsValidator;

import java.util.*;

public class Slots {

    // Fields
    private List<Integer> symbols = new ArrayList<>();
    private double userBalance;
    private double prizePool;
    private int spins;
    private int netGain;
    private int bet;
    private User user;
    private final Random random;
    private double currentWinnings;
    private String combo;
    private double averagePayout;

    // Constructor


    public Slots() {
        this.prizePool = 100000.0;
        this.spins = 0;
        this.netGain = 0;
        this.bet = 0;
        this.userBalance = 100000;
        for (int i = 0; i < 3; i++){
            symbols.add(0);
        }
        this.random = new Random();
    }

    public void play(int bet) {
        setBet(bet);
        spin();
    }

    public void spin(){
        placeBet();
        for (int i = 0; i < symbols.size();i++){
            symbols.set(i,generateSymbol());
        }
        spins++;
        updateWinnings();
    }

    private void placeBet() {
        this.netGain-=getBet();
        this.userBalance-=getBet();
        this.prizePool+=getBet();
    }


    private int generateSymbol(){
        return random.nextInt(9)+1;
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
        prizePool-=winnings;
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

    public double getPrizePool() {
        return prizePool;
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

    public List<Integer> getSymbols() {
        return symbols;
    }


}



