package core;

import validators.SlotValidator;

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
    private Random random;

    // Constructor


    public Slots(User user) {
        this.prizePool = 100000.0;
        this.spins = 0;
        this.netGain = 0;
        this.bet = 0;
        this.user = user;
        this.userBalance = user.getMoney();
        for (int i = 0; i < 3; i++){
            symbols.add(0);
        }
        this.random = new Random();
    }


    public void spin(){
        if (getBet() <=0)
            throw new IllegalArgumentException("Invalid Bet");
        placeBet();
        for (int i = 0; i < symbols.size();i++){
            symbols.set(i,generateSymbol());
        }

        calculateWinnings();
    }

    private void placeBet() {
        this.netGain-=getBet();
        this.userBalance-=getBet();
        this.prizePool+=getBet();
    }


    private int generateSymbol(){
        return random.nextInt(7)+1;
    }


    private void calculateWinnings(){

        if (SlotValidator.isDevil(symbols))
            System.out.println("DEVIL");
        if (SlotValidator.isJackpot(symbols))
            System.out.println("JACKPOT");
        if (SlotValidator.isPerfectStraight(symbols))
            System.out.println("PERFECT STRAIGHT");
        if (SlotValidator.isStraight(symbols))
            System.out.println("STRAIGHT");
        if (SlotValidator.isPair(symbols))
            System.out.println("PAIR");
    }


    // Getters and Setters

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

    public User getUser() {
        return user;
    }

    private void setUserBalance(int balance) {
        this.userBalance = balance;
    }


    public List<Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Integer> symbols) {
        this.symbols = symbols;
    }

    public static void main(String[] args) {
        User user = new User("Sebastian", 100);
        Slots slot = new Slots(user);
        slot.setUserBalance(1000000);
        slot.setBet(10);
        slot.spin();
        for (int symbol : slot.getSymbols()){
            System.out.println(symbol);
        }
        System.out.println("Current balance: " + slot.getUserBalance());
        System.out.println("Net gain: " + slot.getNetGain());
    }

}
