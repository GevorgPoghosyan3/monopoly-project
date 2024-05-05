package am.aua.monopoly.core;

import javax.management.loading.PrivateClassLoader;
import java.util.ArrayList;
import java.util.Scanner;

public class Monopoly {
    private int numberOfPLayers;

    private static ArrayList<Player> players;

    Player.Type[] types = Player.Type.values();

    private static ArrayList<Card> cards;

    private int turn;
    private boolean hasRolled;
    private boolean hasRolledDouble;
    private boolean hasRolledTriple;


    public Monopoly(int numberOfPlayers) throws InvalidNumberOfPlayersException {
        players = new ArrayList<>();
        if (numberOfPlayers < 2 || numberOfPlayers > 8) {
            throw new InvalidNumberOfPlayersException();
        } else {
            this.numberOfPLayers = numberOfPlayers;
            this.turn = -1;
            Board board = new Board();
            board.initializeBoard();
            cards = Card.initializeCards();
            hasRolled = false;
            hasRolledDouble = false;
            hasRolledTriple = false;
        }

    }


    public void setPlayers(String[] playerNames) {
        for (int i = 0; i < this.numberOfPLayers; i++) {
            players.add(new Player(playerNames[i], types[i]));
        }
    }


    public ArrayList<Player> getPlayers() {
        return players;
    }


    public boolean getHasRolled() {
        return hasRolled;
    }

    public boolean getHasRolledDouble() {
        return hasRolledDouble;
    }

    public boolean getHasRolledTriple() {
        return hasRolledTriple;
    }


    public static boolean canBuildOn(Property prop) {
        Property.PropertyType type = prop.getPropertyType();
        Player owner = prop.getOwner();

        for (int i = 0; i < Board.tiles.size(); i++) {
            if (Board.tiles.get(i).getClass() == Property.class) {
                Property property = (Property) Board.tiles.get(i);
                int houses = property.getNumberOfHouses();
                if (property.getPropertyType() == type && property.getOwner() != owner) {
                    return false;
                }


            }
        }
        return true;
    }

    public static void build(Player player, Property prop) throws InvalidNumberOfHousesException {
        if (canBuildOn(prop)) {
            int fee = 0;
            switch (prop.getNumberOfHouses()) {
                case 0:
                    fee = prop.getLevel1Fee();
                    prop.setRent((int) (prop.getRent() * 1.1));
                    System.out.println("1 house");
                    prop.setPrice(prop.getPrice() + prop.getLevel1Fee());
                    break;
                case 1:
                    fee = prop.getLevel2Fee();
                    System.out.println("2 house");
                    prop.setRent((int) (prop.getRent() * 1.2));
                    prop.setPrice(prop.getPrice() + prop.getLevel2Fee());
                    break;
                case 2:
                    fee = prop.getLevel3Fee();
                    System.out.println("3 house");
                    prop.setRent((int) (prop.getRent() * 1.3));
                    prop.setPrice(prop.getPrice() + prop.getLevel3Fee());
                    break;
                default:
                    throw new InvalidNumberOfHousesException();
            }

            player.setMoney(player.getMoney() - fee);
            prop.setNumberOfHouses(prop.getNumberOfHouses() + 1);

        }
    }

    public void move(Player player, int diceRoll) {
        player.setPosition(player.getPosition() + diceRoll);
        if (player.getPosition() > Board.BOARD_SIZE - 1) {
            int difference = player.getPosition() - Board.BOARD_SIZE;
            player.setPosition(difference);
            player.setMoney(player.getMoney() + 200);
        }

        if (player.getPosition() == 2 || player.getPosition() == 7 || player.getPosition() == 17 || player.getPosition() == 33) {
            System.out.println(getCard(player));
        } else payRent(player);

        hasRolled = true;
        hasRolledDouble = false;

    }

    public static void payRent(Player player) {
        Property property = Board.propertyAt(player.getPosition());
        if (property != null) {
            Player owner = property.getOwner();

            int propertyTax = 0;
            switch (property.getNumberOfHouses()) {
                case 0:
                    propertyTax = property.getRent();
                    break;
                case 1:
                    propertyTax = property.getLevel1Fee();
                    break;
                case 2:
                    propertyTax = property.getLevel2Fee();
                    break;
                case 3:
                    propertyTax = property.getLevel3Fee();
                    break;

            }

            if (owner != null && owner != player && !property.getIsUnderMortgage()) {
                owner.setMoney(owner.getMoney() + propertyTax);
                player.setMoney(player.getMoney() - propertyTax);
            }
        }

    }

    public static void buyProperty(Player player, int p) throws InvalidPurchaseException {

        if (Board.tileAt(p).getClass() == Property.class && Board.propertyAt(p).getOwner() == null) {
            player.setMoney(player.getMoney() - Board.propertyAt(p).getPrice());
            player.getPlayerProperties().add(Board.propertyAt(p));
            Board.propertyAt(p).setOwner(player);
        } else {
            throw new InvalidPurchaseException();
        }

    }

    public static void sellProperty(Player player, Property prop){
        for (int i = 0; i < player.getPlayerProperties().size(); i++) {
            if(prop == player.getPlayerProperties().get(i)) {
                player.removeFromPlayerProperties(prop);
            }
        }
    }

    public static String getCard(Player player) {
        int i = (int) (Math.random() * (cards.size())) + 1;

        if (cards.get(i).getId() == 1) {
            player.setMoney(player.getMoney() + cards.get(i).getFee());
        }
        if (cards.get(i).getId() == 2) {
            player.setPosition(cards.get(i).getPosition());
        }
        if (cards.get(i).getId() == 3) {
            for (Player playerOther : players) {
                if (playerOther != player)
                    playerOther.setMoney(player.getMoney() - (cards.get(i).getFee()));
            }
            player.setMoney(player.getMoney() + cards.get(i).getFee());
        }
        if (cards.get(i).getId() == 0) {
            player.setMoney(player.getMoney() + cards.get(i).getFee());
            player.setPosition(cards.get(i).getPosition());
        }
        return cards.get(i).getContent();
    }

    public void GoToJail(Player player) {
        if (player.getPosition() == 30) {
            player.setPosition(10);
            player.setIsInJail(true);
        }
    }

    public static void putUnderMortgage(Player player, Property prop) {
        if (prop.getOwner() == player) {
            prop.setIsUnderMortgage(true);
            player.setMoney(player.getMoney() + prop.getPrice());
        }
    }

    public static void deMortgage(Player player, Property prop) {
        if (prop.getIsUnderMortgage()) {
            if (prop.getOwner() == player) {
                prop.setIsUnderMortgage(false);
                player.setMoney(player.getMoney() - prop.getPrice());
            }
        }
    }


    public void leaveTheGame(Player player) {
        for (Property property : player.getPlayerProperties()) {
            property.setOwner(null);
        }
        players.remove(player);
    }

    public void bankrupt(Player player) {

        int playerBudget = player.getMoney();
        int properties = player.getPlayerProperties().size();
        int mortgageProperties = 0;
        for (Property prop : player.getPlayerProperties()) {
            if (prop.getIsUnderMortgage()) {
                mortgageProperties++;
            }
        }


        if (properties == 0 && playerBudget == 0) {
            leaveTheGame(player);
        } else if (mortgageProperties == properties && playerBudget == 0) {
            leaveTheGame(player);
        }

    }

    public boolean gameOver() {
        if (players.size() == 1) {
            return true;
        }
        return false;
    }


    public Player getTurn() {
        ++turn;
        if (turn > players.size() - 1) {
            turn = 0;
        }
        hasRolled = false;
        hasRolledDouble = false;
        return players.get(turn);

}
    public static ArrayList<Property> showProperties(Player player){
       return player.getPlayerProperties();
    }

    public void print(ArrayList<Property> arrayList){
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i).getName() + arrayList.get(i).getNumberOfHouses());
        }
    }

    public static ArrayList<Property> checkSameColorProps(Player player) {
        ArrayList<Property> sameColorProps = new ArrayList<>();
        for (Property prop : player.getPlayerProperties()) {
            if (canBuildOn(prop)) {
                sameColorProps.add(prop);
            }
        }
        return sameColorProps;
    }

    public static ArrayList<Property> checkNotUnderMortgage(Player player) {
        ArrayList<Property> notUnderMortgage = new ArrayList<>();
        for (Property prop : player.getPlayerProperties()) {
            if (!prop.getIsUnderMortgage()) {
                notUnderMortgage.add(prop);
            }
        }
        return notUnderMortgage;
    }

    public static ArrayList<Property> checkUnderMortgage(Player player) {
        ArrayList<Property> mortgageProps = new ArrayList<>();
        for (Property prop : player.getPlayerProperties()) {
            if (prop.getIsUnderMortgage()) {
                mortgageProps.add(prop);
            }

        }
        return mortgageProps;
    }
}