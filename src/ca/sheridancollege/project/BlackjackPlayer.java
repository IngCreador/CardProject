/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;
import java.util.ArrayList;
/**
 *
 * @author santi
 */
public class BlackjackPlayer extends Player {

    private ArrayList<Card> hand;
    private double wallet;

    private int roundsPlayed;
    private int wins;
    private int losses;
    private int pushes;
    private int blackjacks;
    private int points;

    public BlackjackPlayer(String name, double startingWallet) {
        super(name);
        this.wallet = startingWallet;
        this.hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void clearHand() {
        hand.clear();
    }

    public int getHandSize() {
        return hand.size();
    }

    public int getHandValue() {
        int total = 0;
        int aces = 0;

        for (Card c : hand) {
            BlackjackCard bc = (BlackjackCard) c;
            total += bc.getValue();
            if (bc.getRank() == Rank.ACE) {
                aces++;
            }
        }

        // Adjust Aces from 11 to 1 if needed
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    public String getHandString() {
        StringBuilder sb = new StringBuilder();
        for (Card c : hand) {
            sb.append(c.toString()).append(", ");
        }
        if (!hand.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    public double getWallet() {
        return wallet;
    }

    public int getPoints() {
        return points;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getPushes() {
        return pushes;
    }

    public int getBlackjacks() {
        return blackjacks;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    /**
     * 3:2 payout: +1.5 * bet when player wins.
     * (Based on your description: increase of 1.5× the bet.)
     */
    public void recordWin(double bet, boolean blackjack) {
        double payout = bet * 1.5;
        wallet += payout;
        wins++;
        if (blackjack) {
            blackjacks++;
            points += 15;
        } else {
            points += 10;
        }
        roundsPlayed++;
    }

    public void recordLoss(double bet) {
        wallet -= bet;
        losses++;
        roundsPlayed++;
    }

    public void recordPush() {
        pushes++;
        points += 5;
        roundsPlayed++;
    }

    public String getStatsString() {
        return "Rounds: " + roundsPlayed +
               " | Wins: " + wins +
               " | Losses: " + losses +
               " | Pushes: " + pushes +
               " | Blackjacks: " + blackjacks +
               " | Points: " + points +
               " | Wallet: $" + String.format("%.2f", wallet);
    }

    @Override
    public void play() {
        // Human decisions are handled in BlackjackGame via console input.
    }
}

