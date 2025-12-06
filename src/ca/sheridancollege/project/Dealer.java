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
public class Dealer extends Player {

    private ArrayList<Card> hand;

    public Dealer() {
        super("Dealer");
        hand = new ArrayList<>();
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

        // Adjust Aces from 11 to 1 if we bust
        while (total > 21 && aces > 0) {
            total -= 10; // one Ace becomes 1 instead of 11
            aces--;
        }

        return total;
    }

    public String getFullHandString() {
        StringBuilder sb = new StringBuilder();
        for (Card c : hand) {
            sb.append(c.toString()).append(", ");
        }
        if (!hand.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    /**
     * Dealer's first card is shown; the rest are hidden.
     */
    public String getVisibleHandString() {
        if (hand.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(hand.get(0).toString());
        if (hand.size() > 1) {
            sb.append(", [Hidden]");
        }
        return sb.toString();
    }

    /**
     * House rule: Dealer hits while total < 17.
     */
    public boolean shouldHit() {
        return getHandValue() < 17;
    }

    @Override
    public void play() {
        // Game logic (BlackjackGame) controls the dealer; nothing needed here.
    }
}
