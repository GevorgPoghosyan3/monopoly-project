package am.aua.monopoly.core;

import java.util.ArrayList;

/**
 * The Monopoly class represents the core logic of the Monopoly game.
 */
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


    /**
     * Constructs a Monopoly game with the specified number of players.
     *
     * @param numberOfPlayers The number of players in the game.
     * @throws InvalidNumberOfPlayersException If the number of players is invalid.
     */
    public Monopoly(int numberOfPlayers) throws InvalidNumberOfPlayersException {
        players = new ArrayList<>();
        if (numberOfPlayers < 2 || numberOfPlayers > 5) {
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

    /**
     * Retrieves the list of players in the game.
     *
     * @return The list of players.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Retrieves the flag indicating if a player has rolled the dice.
     *
     * @return True if a player has rolled the dice, otherwise false.
     */
    public boolean getHasRolled() {
        return hasRolled;
    }

    /**
     * Retrieves the flag indicating if a player has rolled a double.
     *
     * @return True if a player has rolled a double, otherwise false.
     */
    public boolean getHasRolledDouble() {
        return hasRolledDouble;
    }

    /**
     * Moves to the next player's turn and returns the player.
     *
     * @return The player whose turn is next.
     */
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

    /**
     * Sets the names of the players in the game.
     *
     * @param playerNames The array of player names.
     */
    public void setPlayers(String[] playerNames) {
        for (int i = 0; i < this.numberOfPLayers; i++) {
            players.add(new Player(playerNames[i], types[i]));
        }
    }

    /**
     * Determines if a property can be built upon.
     *
     * @param prop The property to check.
     * @return True if building is allowed on the property, otherwise false.
     */
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

    /**
     * Builds a house on the specified property for the given player.
     *
     * @param player The player who is building.
     * @param prop   The property on which to build.
     * @throws InvalidNumberOfHousesException If the number of houses is invalid.
     * @throws OutOfMoneyException            If the player does not have enough money.
     */
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

    /**
     * Moves the player on the game board based on the dice roll and performs actions based on the tile landed on.
     *
     * @param player   The player making the move.
     * @param diceRoll The value rolled on the dice.
     * @return A message indicating special actions taken based on the tile landed on, or null if no special action is needed.
     * @throws OutOfMoneyException If the player does not have enough money to perform an action.
     */
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
        } else if (Board.tileAt(player.getPosition()).getName().equals("CourtRoom")) {
            goToProbation(player);
            return "You were sent to Probation from the Court. GUILTY!";
        } else {
            payRent(player);

        }

        hasRolled = true;
        hasRolledDouble = Dice.isDouble();
        if (hasRolledDouble) {
            diceDoubleCounter++;
        } else {
            diceDoubleCounter = 0;
        }

        if (diceDoubleCounter == 3) {
            goToProbation(player);
            diceDoubleCounter = 0;
//            System.out.println("From 3 times");
        }

        return null;
    }

    /**
     * Teleports the player to the specified property if both properties are elevators.
     *
     * @param player   The player to be teleported.
     * @param property The property to teleport to.
     * @throws InvalidTeleportLocationException If the teleportation location is invalid.
     */
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

    /**
     * Pays the rent to the owner of the property where the player landed.
     *
     * @param player The player paying the rent.
     * @throws OutOfMoneyException If the player does not have enough money to pay the rent.
     */
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

    /**
     * Allows the player to buy a property if it is available for purchase.
     *
     * @param player The player buying the property.
     * @param p      The position of the property to buy.
     * @throws InvalidPurchaseException If the property cannot be purchased.
     * @throws OutOfMoneyException      If the player does not have enough money to buy the property.
     */
    public static void buyProperty(Player player, int p) throws InvalidPurchaseException, OutOfMoneyException {

        if (Board.tileAt(p).getClass() == Property.class && Board.propertyAt(p).getOwner() == null) {
            player.setMoney(player.getMoney() - Board.propertyAt(p).getPrice());
            player.getPlayerProperties().add(Board.propertyAt(p));
            Board.propertyAt(p).setOwner(player);
        } else {
            throw new InvalidPurchaseException();
        }

    }

    /**
     * Allows the player to sell a property they own.
     *
     * @param player The player selling the property.
     * @param prop   The property to sell.
     * @throws OutOfMoneyException If the player does not have enough money to sell the property.
     */
    public static void sellProperty(Player player, Property prop) throws OutOfMoneyException {
        for (int i = 0; i < player.getPlayerProperties().size(); i++) {
            if (prop == player.getPlayerProperties().get(i)) {
                player.removeFromPlayerProperties(prop);
                player.setMoney(player.getMoney() + prop.getPrice());
            }
        }
    }

    /**
     * Selects a random card and performs the corresponding action on the player.
     *
     * @param player The player drawing the card.
     * @return The content of the card drawn.
     * @throws OutOfMoneyException If the player does not have enough money to perform an action indicated by the card.
     */
    public static String getCard(Player player) throws OutOfMoneyException {
        int i = (int) (Math.random() * (cards.size()));

        if (cards.get(i).getId() == 1) {
            player.setMoney(player.getMoney() + cards.get(i).getFee());
        }
        if (cards.get(i).getId() == 2) {
            if (cards.get(i).getPosition() == Property.nameToPosition("Probation")) {
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

    /**
     * Sends the player to the probation tile.
     *
     * @param player The player being sent to probation.
     */
    public void goToProbation(Player player) {
//        if (player.getPosition() == Property.nameToPosition("Courtroom") || Dice.checkSpeeding()) {
        player.setPosition(10);
        player.setIsInProbation(true);
        System.out.println("You are in jail");
//        }
    }

    /**
     * Attempts to release the player from probation using a "Get Out of Jail Free" card.
     *
     * @param player The player trying to get out of probation.
     * @return True if the player successfully uses the card, false otherwise.
     */
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

    /**
     * Releases the player from probation by paying a fine.
     *
     * @param player The player trying to get out of probation.
     * @throws OutOfMoneyException If the player does not have enough money to pay the fine.
     */
    public void getOutOfProbationByMoney(Player player) throws OutOfMoneyException {
        if (player.getIsInProbation() && !getOutOfProbationByCard(player)) {
            player.setMoney(player.getMoney() - 200);
            player.setIsInProbation(false);
        }
    }

    /**
     * Puts the specified property under mortgage, increasing the player's money.
     *
     * @param player The player putting the property under mortgage.
     * @param prop   The property to put under mortgage.
     * @throws OutOfMoneyException If the player does not have enough money to perform the operation.
     */
    public static void putUnderMortgage(Player player, Property prop) throws OutOfMoneyException {
        if (prop.getOwner() == player) {
            prop.setIsUnderMortgage(true);
            player.setMoney(player.getMoney() + prop.getPrice());
        }
    }

    /**
     * Removes the mortgage from the specified property, decreasing the player's money.
     *
     * @param player The player removing the mortgage.
     * @param prop   The property to remove the mortgage from.
     * @throws OutOfMoneyException If the player does not have enough money to perform the operation.
     */
    public static void deMortgage(Player player, Property prop) throws OutOfMoneyException {
        if (prop.getIsUnderMortgage()) {
            if (prop.getOwner() == player) {
                prop.setIsUnderMortgage(false);
                player.setMoney(player.getMoney() - prop.getPrice());
            }
        }
    }

    /**
     * Removes the player from the game, releasing all their properties.
     *
     * @param player The player leaving the game.
     */
    public void leaveTheGame(Player player) {
        for (Property property : player.getPlayerProperties()) {
            property.setOwner(null);
        }
        players.remove(player);
        playerHasBeenRemoved = true;
    }

    /**
     * Checks if the player has gone bankrupt and removes them from the game if conditions are met.
     *
     * @param player The player to check for bankruptcy.
     */
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

    /**
     * Checks if the game is over by determining if there's only one player left.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean gameOver() {
        if (players.size() == 1) {
            return true;
        }
        return false;
    }

    /**
     * Shows the properties owned by the player.
     *
     * @param player The player whose properties to display.
     * @return The list of properties owned by the player.
     */
    public static ArrayList<Property> showProperties(Player player) {
        return player.getPlayerProperties();
    }

    /**
     * Prints the names and number of houses of properties in the given list.
     *
     * @param arrayList The list of properties to print.
     */
    public void print(ArrayList<Property> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i).getName() + arrayList.get(i).getNumberOfHouses());
        }
    }

    /**
     * Checks and returns properties owned by the player that can be built on.
     *
     * @param player The player to check for properties.
     * @return The list of properties owned by the player that can be built on.
     */
    public static ArrayList<Property> checkSameColorProps(Player player) {
        ArrayList<Property> sameColorProps = new ArrayList<>();
        for (Property prop : player.getPlayerProperties()) {
            if (canBuildOn(prop)) {
                sameColorProps.add(prop);
            }
        }
        return sameColorProps;
    }

    /**
     * Checks and returns properties owned by the player that are not under mortgage.
     *
     * @param player The player to check for properties.
     * @return The list of properties owned by the player that are not under mortgage.
     */
    public static ArrayList<Property> checkNotUnderMortgage(Player player) {
        ArrayList<Property> notUnderMortgage = new ArrayList<>();
        for (Property prop : player.getPlayerProperties()) {
            if (!prop.getIsUnderMortgage()) {
                notUnderMortgage.add(prop);
            }
        }
        return notUnderMortgage;
    }

    /**
     * Checks and returns properties owned by the player that are under mortgage.
     *
     * @param player The player to check for properties.
     * @return The list of properties owned by the player that are under mortgage.
     */
    public static ArrayList<Property> checkUnderMortgage(Player player) {
        ArrayList<Property> mortgageProps = new ArrayList<>();
        for (Property prop : player.getPlayerProperties()) {
            if (prop.getIsUnderMortgage()) {
                mortgageProps.add(prop);
            }

        }
        return mortgageProps;
    }

    /**
     * Checks and returns properties owned by the player that are elevators.
     *
     * @param player The player to check for properties.
     * @return The list of elevator properties owned by the player.
     */
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