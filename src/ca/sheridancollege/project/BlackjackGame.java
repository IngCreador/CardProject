/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author santi
 */
public class BlackjackGame extends Game {

    private Deck deck;
    private BlackjackPlayer player;
    private Dealer dealer;
    private Scanner scanner;

    private static final double STARTING_WALLET = 1000.0;

    public BlackjackGame(String name) {
        super(name);
        scanner = new Scanner(System.in);
    }

    @Override
    public void play() {
        System.out.println("=== Welcome to " + getName() + " ===");
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine().trim();
        if (playerName.isEmpty()) {
            playerName = "Player";
        }

        player = new BlackjackPlayer(playerName, STARTING_WALLET);
        dealer = new Dealer();

        ArrayList<Player> players = getPlayers();
        players.clear();
        players.add(player);
        players.add(dealer);

        boolean keepPlaying = true;

        while (keepPlaying && player.getWallet() > 0) {
            System.out.println("\n--------------------------------");
            System.out.println(player.getStatsString());
            double bet = askForBet();

            playSingleRound(bet);

            if (player.getWallet() <= 0) {
                System.out.println("\nYou have lost all your money. You are bankrupt. Game over.");
                break;
            }

            System.out.print("\nPlay another round? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            keepPlaying = choice.startsWith("y");
        }

        System.out.println("\nThanks for playing " + getName() + "!");
        System.out.println("Final stats:");
        System.out.println(player.getStatsString());
    }

    private double askForBet() {
        double bet = 0;
        while (true) {
            System.out.print("Enter your bet amount (wallet $" +
                    String.format("%.2f", player.getWallet()) + "): ");
            String line = scanner.nextLine().trim();
            try {
                bet = Double.parseDouble(line);
                if (bet <= 0) {
                    System.out.println("Bet must be greater than 0.");
                } else if (bet > player.getWallet()) {
                    System.out.println("You cannot bet more than your wallet.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return bet;
    }

    private void playSingleRound(double bet) {
        // New deck every round (simple, and guarantees no duplicate cards)
        deck = new Deck();
        deck.shuffle();

        player.clearHand();
        dealer.clearHand();

        // Deal initial cards: 2 to player, 2 to dealer
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        System.out.println("\n=== New Round ===");
        System.out.println("Your hand: " + player.getHandString()
                + " (Total: " + player.getHandValue() + ")");
        System.out.println("Dealer shows: " + dealer.getVisibleHandString());

        boolean playerBlackjack = (player.getHandValue() == 21 && player.getHandSize() == 2);
        boolean dealerBlackjack = (dealer.getHandValue() == 21 && dealer.getHandSize() == 2);

        // Check for blackjacks immediately
        if (playerBlackjack || dealerBlackjack) {
            System.out.println("\n--- Checking for Blackjack ---");
            System.out.println("Dealer's full hand: " + dealer.getFullHandString()
                    + " (Total: " + dealer.getHandValue() + ")");

            if (playerBlackjack && dealerBlackjack) {
                System.out.println("Both you and the dealer have Blackjack. Push (tie).");
                player.recordPush();
            } else if (playerBlackjack) {
                System.out.println("You have Blackjack! You win with 3:2 payout.");
                player.recordWin(bet, true);
            } else {
                System.out.println("Dealer has Blackjack. You lose.");
                player.recordLoss(bet);
            }
            System.out.println(player.getStatsString());
            return;
        }

        // Player's turn
        boolean playerBusted = playerTurn();

        if (playerBusted) {
            System.out.println("You busted. You lose this round.");
            player.recordLoss(bet);
            System.out.println(player.getStatsString());
            return;
        }

        // Dealer's turn
        System.out.println("\n=== Dealer's Turn ===");
        System.out.println("Dealer's hand: " + dealer.getFullHandString()
                + " (Total: " + dealer.getHandValue() + ")");

        while (dealer.shouldHit()) {
            System.out.println("Dealer hits...");
            dealer.addCard(deck.dealCard());
            System.out.println("Dealer's hand: " + dealer.getFullHandString()
                    + " (Total: " + dealer.getHandValue() + ")");
        }

        if (dealer.getHandValue() > 21) {
            System.out.println("Dealer busts! You win.");
            player.recordWin(bet, false);
        } else {
            // Compare totals
            int playerTotal = player.getHandValue();
            int dealerTotal = dealer.getHandValue();

            System.out.println("\n=== Final Hands ===");
            System.out.println("Your hand: " + player.getHandString()
                    + " (Total: " + playerTotal + ")");
            System.out.println("Dealer's hand: " + dealer.getFullHandString()
                    + " (Total: " + dealerTotal + ")");

            if (playerTotal > dealerTotal) {
                System.out.println("You win this round! 3:2 payout.");
                player.recordWin(bet, false);
            } else if (playerTotal < dealerTotal) {
                System.out.println("Dealer wins this round.");
                player.recordLoss(bet);
            } else {
                System.out.println("Push (tie).");
                player.recordPush();
            }
        }

        System.out.println(player.getStatsString());
    }

    private boolean playerTurn() {
        while (true) {
            int total = player.getHandValue();
            System.out.println("\nYour hand: " + player.getHandString()
                    + " (Total: " + total + ")");

            if (total > 21) {
                return true; // busted
            }

            System.out.print("Hit or stand? (h/s): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("h")) {
                player.addCard(deck.dealCard());
            } else if (choice.equals("s")) {
                return false; // stand
            } else {
                System.out.println("Please type 'h' for hit or 's' for stand.");
            }
        }
    }

    @Override
    public void declareWinner() {
        // In this implementation, we display outcome after each round,
        // and final stats when the game loop ends in play().
    }
}
