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
public class Deck extends GroupOfCards {

    public Deck() {
        super(52);
        ArrayList<Card> cards = getCards();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new BlackjackCard(suit, rank));
            }
        }
    }

    /**
     * Deals (removes) one card from the top of the deck.
     */
    public BlackjackCard dealCard() {
        if (getCards().isEmpty()) {
            return null; // deck empty; in this simple game we recreate deck each round
        }
        return (BlackjackCard) getCards().remove(0);
    }
}
