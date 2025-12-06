/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author santi
 */
public class BlackjackCard extends Card {

    private final Suit suit;
    private final Rank rank;

    public BlackjackCard(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    /**
     * Blackjack value for this card.
     * Ace is treated as 11 here; we adjust to 1 later in hand logic if we bust.
     */
    public int getValue() {
        switch (rank) {
            case TWO:   return 2;
            case THREE: return 3;
            case FOUR:  return 4;
            case FIVE:  return 5;
            case SIX:   return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE:  return 9;
            case TEN:
            case JACK:
            case QUEEN:
            case KING:  return 10;
            case ACE:   return 11;
            default:    return 0;
        }
    }

    private String rankToString() {
        switch (rank) {
            case TWO:   return "Two";
            case THREE: return "Three";
            case FOUR:  return "Four";
            case FIVE:  return "Five";
            case SIX:   return "Six";
            case SEVEN: return "Seven";
            case EIGHT: return "Eight";
            case NINE:  return "Nine";
            case TEN:   return "Ten";
            case JACK:  return "Jack";
            case QUEEN: return "Queen";
            case KING:  return "King";
            case ACE:   return "Ace";
            default:    return "";
        }
    }

    private String suitToString() {
        switch (suit) {
            case CLUBS:    return "Clubs";
            case DIAMONDS: return "Diamonds";
            case HEARTS:   return "Hearts";
            case SPADES:   return "Spades";
            default:       return "";
        }
    }

    @Override
    public String toString() {
        return rankToString() + " of " + suitToString();
    }
}
