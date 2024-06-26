package am.aua.monopoly.core;

import java.util.ArrayList;

/**
 * Represents the cards used in the Monopoly game.
 * Each card has an ID, content, fee, and optional position.
 */
public class Card {
    private String content;
    private int id;
    private int position;
    private int fee;

    /**
     * Constructs a Card object with specified ID, content, fee, and position.
     *
     * @param id       The ID of the card.
     * @param content  The content of the card.
     * @param fee      The fee associated with the card.
     * @param position The position to move to, if applicable.
     */
    public Card(int id, String content, int fee, int position) {
        this.id = id;
        this.content = content;
        this.fee = fee;
        this.position = position;
    }

    /**
     * Constructs a Card object with specified ID, content, and fee.
     *
     * @param id      The ID of the card.
     * @param content The content of the card.
     * @param fee     The fee associated with the card.
     */
    public Card(int id, String content, int fee) {
        this.id = id;
        this.content = content;
        this.fee = fee;
    }

    /**
     * Retrieves the fee associated with the card.
     *
     * @return The fee associated with the card.
     */
    public int getFee() {
        return fee;
    }

    /**
     * Retrieves the position to move to, if applicable.
     *
     * @return The position to move to, or 0 if not applicable.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Retrieves the content of the card.
     *
     * @return The content of the card.
     */
    public String getContent() {
        return content;
    }


    /**
     * Retrieves the ID of the card.
     *
     * @return The ID of the card.
     */
    public int getId() {
        return id;
    }

    /**
     * Initializes and returns a list of cards with predefined values.
     *
     * @return A list of Card objects.
     */
    public static ArrayList<Card> initializeCards() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(0, "Advance to Entrance. Collect $200.", 200, 0));
        cards.add(new Card(1, "Bank error in your favor. Collect $200.", 200));
        cards.add(new Card(1, "Doctor's fees. Pay $50.", -50));
        cards.add(new Card(1, "From sale of stock you get $50.", 50));
        cards.add(new Card(5, "Get Out of Probation. This card may be kept until needed.", 0));
        cards.add(new Card(2, "Low GPA. Go to probation.", 0, 10)); // Jail position
        cards.add(new Card(3, "Spring Ball. Collect $50 from every player for ball night tickets.", 50));
        cards.add(new Card(1, "Holiday Fund matures. Receive $100.", 100));
        cards.add(new Card(1, "Income tax refund. Collect $20.", 20));
        cards.add(new Card(3, "It's your birthday. Collect $10 from every player.", 10));
        cards.add(new Card(1, "Life insurance matures – Collect $100.", 100));
        cards.add(new Card(1, "Hospital Fees. Pay $100.", -100));
        cards.add(new Card(1, "University fees. Pay $50.", -50));
        cards.add(new Card(1, "Receive $25 consultancy fee.", 25));
        cards.add(new Card(1, "Pay poor tax of $15.", -15));
        cards.add(new Card(1, "You have won second prize in a programming contest. Collect $10.", 10));
        cards.add(new Card(1, "You inherit $100.", 100));

        cards.add(new Card(0, "Advance to Library. If you pass Go, don't collect $200.", 0, 24));
        cards.add(new Card(0, "Advance to Coffee House. If you pass Go, don't collect $200.", 0, 11));
        cards.add(new Card(1, "You have won first prize in a Math olimpiad. Collect $50.", 50));
        cards.add(new Card(5, "Get out of Probation. This card may be kept until needed.", 0));// Move back 3 spaces
        cards.add(new Card(1, "Pay poor tax of $15.", -15));
        cards.add(new Card(0, "Take a trip to Elevator 1. If you pass Go, don't collect $200.", 0, 5)); // Reading Railroad position
        cards.add(new Card(0, "Take a walk on the The Green Bean Cafe. Advance token to The Green Bean Cafe.", 0, 39)); // Boardwalk position
        cards.add(new Card(3, "You have been elected Chairman of the Student Union. Pay each player $50.", -50));
        cards.add(new Card(1, "Your building and loan matures. Receive $150.", 150));
        cards.add(new Card(1, "You have won a crossword competition. Collect $100.", 100));
        cards.add(new Card(1, "You inherit $100.", 100));

        return cards;

    }

}
