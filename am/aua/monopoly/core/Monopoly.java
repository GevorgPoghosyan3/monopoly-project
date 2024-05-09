package am.aua.monopoly.core;

import java.util.ArrayList;

public class Monopoly {
    private int numberOfPLayers;

    private static ArrayList<Player> players;

    Player.Type[] types = Player.Type.values();

    private static ArrayList<Card> cards;

    private int turn;
    private boolean hasRolled;
    private boolean hasRolledDouble;
    private boolean playerHasBeenRemoved;
    private int diceDoubleCounter = 0;


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


    public static boolean canBuildOn(Property prop) {
        if (prop.getIsBuildable()) {
            Property.PropertyType type = prop.getPropertyType();
            Player owner = prop.getOwner();

            for (int i = 0; i < Board.tiles.size(); i++) {
                if (Board.tiles.get(i).getClass() == Property.class) {
                    Property property = (Property) Board.tiles.get(i);
                    if (property.getPropertyType() == type && property.getOwner() != owner) {
                        return false;
                    }
                }
            }
            return true;
        } else return false;
    }

    public static void build(Player player, Property prop) throws InvalidNumberOfHousesException, OutOfMoneyException {
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

    public String move(Player player, int diceRoll) throws OutOfMoneyException {
        player.setPosition(player.getPosition() + diceRoll);
        if (player.getPosition() > Board.BOARD_SIZE - 1) {
            int difference = player.getPosition() - Board.BOARD_SIZE;
            player.setPosition(difference);
            player.setMoney(player.getMoney() + 200);
        }

        if ((Board.tileAt(player.getPosition()).getName().equals("Community Chest Tile")) || (Board.tileAt(player.getPosition()).getName().equals("Chance Tile"))) {
            return getCard(player);
        } else if (((Board.tileAt(player.getPosition()).getName().equals("Student Service Fee"))) || (Board.tileAt(player.getPosition()).getName().equals("Water Works")) || (Board.tileAt(player.getPosition()).getName().equals("Electricity fee")) || (Board.tileAt(player.getPosition()).getName().equals("Admission Fee"))) {
            player.setMoney(player.getMoney() + (Board.tileAt(player.getPosition()).getFee()));
            return null;
        } else  if(Board.tileAt(player.getPosition()).getName().equals("CourtRoom")) {
            goToProbation(player);
//            System.out.println("From Court");
            return null;
        } else {
            payRent(player);

        }


        hasRolled = true;
        hasRolledDouble = Dice.isDouble();
        if(hasRolledDouble) {
           diceDoubleCounter++;
        } else {
            diceDoubleCounter = 0;
        }

        if(diceDoubleCounter == 3) {
            goToProbation(player);
            diceDoubleCounter = 0;
//            System.out.println("From 3 times");
        }

        return null;
    }

    public void teleport(Player player, Property property) throws InvalidTeleportLocationException {
        boolean foundProperty1 = false;
        boolean foundProperty2 = false;

        Property currentProperty = Board.propertyAt(player.getPosition());
        for (Property playerProperty : player.getPlayerProperties()) {
            if (playerProperty == property) {
                foundProperty1 = true;
                break;
            }
        }
        for (Property playerProperty : player.getPlayerProperties()) {
            if (playerProperty == currentProperty) {
                foundProperty2 = true;
                break;
            }
        }
        if (!foundProperty1 && !foundProperty2) {
            throw new InvalidTeleportLocationException();
        }

        if (currentProperty != null && currentProperty.getPropertyType() == Property.PropertyType.ELEVATOR &&
                property != currentProperty && property.getPropertyType() == Property.PropertyType.ELEVATOR) {
            player.setPosition(property.getPosition());
        }
    }


    public static void payRent(Player player) throws OutOfMoneyException {
        Property property = Board.propertyAt(player.getPosition());
        if (property != null) {
            Player owner = property.getOwner();
            int propertyTax = 0;
            int elevatorCount = 0;

            if (property.getPropertyType() == Property.PropertyType.ELEVATOR) {
                for (int i = 0; i < player.getPlayerProperties().size(); i++) {
                    if (player.getPlayerProperties().get(i).getPropertyType() == Property.PropertyType.ELEVATOR) {
                        elevatorCount++;
                    }
                }
                propertyTax = elevatorCount * property.getRent();

            } else {
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
            }

            if (owner != null && owner != player && !property.getIsUnderMortgage()) {
                owner.setMoney(owner.getMoney() + propertyTax);
                player.setMoney(player.getMoney() - propertyTax);
            }
        }

    }

    public static void buyProperty(Player player, int p) throws InvalidPurchaseException, OutOfMoneyException {

        if (Board.tileAt(p).getClass() == Property.class && Board.propertyAt(p).getOwner() == null) {
            player.setMoney(player.getMoney() - Board.propertyAt(p).getPrice());
            player.getPlayerProperties().add(Board.propertyAt(p));
            Board.propertyAt(p).setOwner(player);
        } else {
            throw new InvalidPurchaseException();
        }

    }

    public static void sellProperty(Player player, Property prop) throws OutOfMoneyException {
        for (int i = 0; i < player.getPlayerProperties().size(); i++) {
            if (prop == player.getPlayerProperties().get(i)) {
                player.removeFromPlayerProperties(prop);
                player.setMoney(player.getMoney() + prop.getPrice());
            }
        }
    }

    public static String getCard(Player player) throws OutOfMoneyException {
        int i = (int) (Math.random() * (cards.size()));

        if (cards.get(i).getId() == 1) {
            player.setMoney(player.getMoney() + cards.get(i).getFee());
        }
        if (cards.get(i).getId() == 2) {
            if (cards.get(i).getPosition() == Property.nameToPosition("Probation")){
                player.setIsInProbation(true);
            }
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
        if (cards.get(i).getId() == 5) {
            player.setHasFreeFromProbationCard(true);
        }
        return cards.get(i).getContent();
    }

    public void goToProbation(Player player) {
//        if (player.getPosition() == Property.nameToPosition("Courtroom") || Dice.checkSpeeding()) {
            player.setPosition(10);
            player.setIsInProbation(true);
            System.out.println("You are in jail");
//        }
    }

    public boolean getOutOfProbationByCard(Player player) {
        if (player.getIsInProbation()) {
            if (player.getHasFreeFromProbationCard()) {
                getTurn();
                player.setIsInProbation(false);
                player.setHasFreeFromProbationCard(false);
                return true;
            }
        }
        return false;
    }

    public void getOutOfProbationByMoney(Player player) throws OutOfMoneyException {
        if (player.getIsInProbation() && !getOutOfProbationByCard(player)) {
            player.setMoney(player.getMoney() - 200);
            player.setIsInProbation(false);
        }
    }



    public static void putUnderMortgage(Player player, Property prop) throws OutOfMoneyException {
        if (prop.getOwner() == player) {
            prop.setIsUnderMortgage(true);
            player.setMoney(player.getMoney() + prop.getPrice());
        }
    }

    public static void deMortgage(Player player, Property prop) throws OutOfMoneyException {
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
        playerHasBeenRemoved = true;
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
        hasRolled = false;
        hasRolledDouble = false;

        if (playerHasBeenRemoved) {
            turn = turn % players.size();
            playerHasBeenRemoved = false;
        } else {
            turn = (turn + 1) % players.size();
        }

        return players.get(turn);
    }


    public static ArrayList<Property> showProperties(Player player) {
        return player.getPlayerProperties();
    }

    public void print(ArrayList<Property> arrayList) {
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

    public static ArrayList<Property> ElevatorChecker(Player player) {
        ArrayList<Property> elevators = new ArrayList<>();
        for (Property prop : player.getPlayerProperties()) {
            if (prop.getPropertyType() == Property.PropertyType.ELEVATOR) {
                elevators.add(prop);
            }

        }
        return elevators;
    }
}