package am.aua.monopoly.core;

import java.util.ArrayList;
import java.util.Scanner;

public class Monopoly {
    private int numberOfPLayers;

    private static ArrayList<Player> players;

    Player.Type[] types = Player.Type.values();

    private static ArrayList<Card> cards;



    public Monopoly(int numberOfPlayers) throws InvalidNumberOfPlayersException {
        players = new ArrayList<>();
        if(numberOfPlayers < 2 || numberOfPlayers > 8){
            throw new InvalidNumberOfPlayersException();
        }else this.numberOfPLayers = numberOfPlayers;
        Board board = new Board();
        board.initializeBoard();
        cards = Card.initializeCards();

    }



    public void setPlayers() {
        for (int i = 0; i < this.numberOfPLayers; i++) {
            System.out.print("Enter player " + (i + 1) + " name: ");
            Scanner scanner = new Scanner(System.in);
            String playerName = scanner.nextLine();

            // Assign a color to the player using the ordinal of the enum, which is automatically the index
            players.add(new Player(playerName, types[i]));
        }
    }


    public ArrayList<Player> getPlayers() {
//        ArrayList<Player> players = new ArrayList<>();
//        for (Player player : this.players) {
//            players.add(new Player(player)); // Assuming Player class has a copy constructor
//        }
        return players;
    }


    public static boolean canBuildOn(Property prop) {
        Property.PropertyType type = prop.getPropertyType();
        Player owner = prop.getOwner();

        for (int i = 0; i < Board.tiles.size(); i++) {
            if(Board.tiles.get(i).getClass() == Property.class) {
                Property property = (Property) Board.tiles.get(i);
                int houses = property.getNumberOfHouses();
                if(property.getPropertyType() == type && property.getOwner() != owner) {
                    return false;
                }


            }
        }
        return true;
    }

    public static void build(Player player, Property prop) throws InvalidNumberOfHousesException{
        if(canBuildOn(prop)) {
            int fee = 0;
            switch (prop.getNumberOfHouses()) {
                case 0:
                    fee = prop.getLevel1Fee();
                    prop.setRent ((int)(prop.getRent() * 1.1));
                    System.out.println("1 house");
                    break;
                case 1:
                    fee = prop.getLevel2Fee();
                    System.out.println("2 house");
                    prop.setRent ((int)(prop.getRent() * 1.2));
                    break;
                case 2:
                    fee = prop.getLevel3Fee();
                    System.out.println("3 house");
                    prop.setRent ((int)(prop.getRent() * 1.3));
                    break;
                default:
                    throw new InvalidNumberOfHousesException();
            }

            player.setMoney(player.getMoney() - fee);
            prop.setNumberOfHouses(prop.getNumberOfHouses() + 1);

        }
    }

    public static void move(Player player, int diceRoll) {
        player.setPosition(player.getPosition() + diceRoll);
        if (player.getPosition() > Board.BOARD_SIZE) {
            int difference = player.getPosition() - Board.BOARD_SIZE;
            player.setPosition(difference);
        }
        player.setMoney(player.getMoney() + 200);
        payRent(player);
        if(player.getPosition() == 2 || player.getPosition() == 7 || player.getPosition() == 17 || player.getPosition() == 33){
            Monopoly.getCard(player.getPosition(), player);
        }

    }

    public static void payRent(Player player) {
        Property property =  Board.propertyAt(player.getPosition());
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

        if (owner != null && owner != player) {
            owner.setMoney(owner.getMoney() + propertyTax);
            player.setMoney(player.getMoney() - propertyTax);
        }
    }

    public static void buyProperty(Player player, int p) {
        if(Board.propertyAt(p).getOwner() == null) {
            player.setMoney(player.getMoney() - Board.propertyAt(p).getPrice());
            player.getPlayerProperties().add(Board.propertyAt(p));
            Board.propertyAt(p).setOwner(player);
        } else {
            System.out.println("This property already has an owner");
        }

    }

    public static String getCard (int position, Player player) {
      int i = (int) (Math.random() * (cards.size())) + 1;

      if(cards.get(i).getId() == 1){
          player.setMoney(player.getMoney() + cards.get(i).getFee());
      }
      if(cards.get(i).getId() == 2){
          player.setPosition(cards.get(i).getPosition());
      }
      if(cards.get(i).getId() == 3){
          for(Player playerOther : players){
              if(playerOther != player)
                  playerOther.setMoney(player.getMoney() + cards.get(i).getFee());
          }
          player.setMoney(player.getMoney() + cards.get(i).getFee());
      }
      if(cards.get(i).getId()  == 0){
          player.setMoney(player.getMoney() + cards.get(i).getFee());
          player.setPosition(cards.get(i).getPosition());
      }
        return cards.get(i).getContent();
    }
}
