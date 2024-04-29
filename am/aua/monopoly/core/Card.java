package am.aua.monopoly.core;

import java.util.ArrayList;

public class Card {
    private String content;
    private int id;
    private int position;
    private int fee;


    public Card(int id, String content, int fee, int position){
        this.id = id;
        this.content = content;
        this.fee = fee;
        this.position = position;
    }
    public Card(int id, String content, int fee){
        this.id = id;
        this.content = content;
        this.fee = fee;
    }

    public int getFee() {
        return fee;
    }

    public int getPosition(){
        return position;
    }

    public String getContent(){
        return content;
    }
    public int getId(){
        return id;
    }

    public static ArrayList<Card> initializeCards() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(0, "Advance to Go. Collect $200.", 200, 0));
        cards.add(new Card(1, "Bank error in your favor. Collect $200.", 200));
        cards.add(new Card(1, "Doctor's fees. Pay $50.", -50));
        cards.add(new Card(1, "From sale of stock you get $50.", 50));
        cards.add(new Card(5, "Get Out of Jail Free. This card may be kept until needed or sold.", 0));
        cards.add(new Card(2, "Go to Jail. Go directly to jail. Do not pass Go, do not collect $200.", 0, 10)); // Jail position
        cards.add(new Card(3, "Grand Opera Night. Collect $50 from every player for opening night seats.", 50));
        cards.add(new Card(1, "Holiday Fund matures. Receive $100.", 100));
        cards.add(new Card(1, "Income tax refund. Collect $20.", 20));
        cards.add(new Card(3, "It's your birthday. Collect $10 from every player.", 10));
        cards.add(new Card(1, "Life insurance matures – Collect $100.", 100));
        cards.add(new Card(1, "Hospital Fees. Pay $100.", -100));
        cards.add(new Card(1, "School fees. Pay $50.", -50));
        cards.add(new Card(1, "Receive $25 consultancy fee.", 25));
        cards.add(new Card(1, "Pay poor tax of $15.", -15));
        cards.add(new Card(1, "You have won second prize in a beauty contest. Collect $10.", 10));
        cards.add(new Card(1, "You inherit $100.", 100));

        cards.add(new Card(0, "Advance to Illinois Ave. If you pass Go, don't collect $200.", 0, 24)); // Illinois Ave position
        cards.add(new Card(0, "Advance to St. Charles Place. If you pass Go, don't collect $200.", 0, 11)); // St. Charles Place position// Nearest Utility position// Nearest Railroad position
        cards.add(new Card(2, "Bank pays you dividend of $50.", 50));
        cards.add(new Card(5, "Get out of Jail Free. This card may be kept until needed, or sold.", 0));// Move back 3 spaces
        cards.add(new Card(3, "Pay poor tax of $15.", -15));
        cards.add(new Card(0, "Take a trip to Reading Railroad. If you pass Go, don't collect $200.", 0, 5)); // Reading Railroad position
        cards.add(new Card(0, "Take a walk on the Boardwalk. Advance token to Boardwalk.", 0, 39)); // Boardwalk position
        cards.add(new Card(3, "You have been elected Chairman of the Board. Pay each player $50.", -50));
        cards.add(new Card(1, "Your building and loan matures. Receive $150.", 150));
        cards.add(new Card(1, "You have won a crossword competition. Collect $100.", 100));
        cards.add(new Card(1, "You inherit $100.", 100));

        return cards;

    }

}
