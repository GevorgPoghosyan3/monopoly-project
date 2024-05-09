package am.aua.monopoly.core;

import java.util.ArrayList;

/**
 * Represents a player in the Monopoly game with attributes such as name, position, money, and owned properties.
 * Methods include moving, buying properties, paying and receiving rent.
 */
public class Player {
    public enum Type {
        CAR, HAT, SHIP, BOOT, IRON;
    }

    private Type playerType;
    private int money;
    private String name;
    private boolean isInProbation;
    private boolean hasFreeFromProbationCard;
    private int position;
    private ArrayList<Property> playerProperties;

    /**
     * Constructs a player with the given name and type, initializing money to 2000 and other attributes.
     *
     * @param name The name of the player.
     * @param type The type of the player (CAR, HAT, SHIP, BOOT, IRON).
     */
    public Player(String name, Type type) {
        this.money = 2000;
        this.name = name;
        this.playerType = type;
        this.position = 0;
        this.playerProperties = new ArrayList<>();
        this.isInProbation = false;
    }

    /**
     * Copy constructor to create a new player with the same attributes as another player.
     *
     * @param other The player to copy attributes from.
     */
    public Player(Player other) {
        this.playerType = other.playerType;
        this.money = other.money;
        this.name = other.name;
        this.position = other.position;
        this.isInProbation = other.isInProbation;

        this.playerProperties = new ArrayList<>();
        for (Property property : other.playerProperties) {
            this.playerProperties.add(new Property(property));
        }
    }

    /**
     * Gets the type of the player.
     *
     * @return The type of the player (CAR, HAT, SHIP, BOOT, IRON).
     */
    public Type getType() {
        return this.playerType;
    }

    /**
     * Checks if the player has a free from probation card.
     *
     * @return true if the player has a free from probation card, false otherwise.
     */
    public boolean getHasFreeFromProbationCard() {
        return hasFreeFromProbationCard;
    }

    /**
     * Sets whether the player has a free from probation card.
     *
     * @param hasFreeFromProbationCard true if the player has the card, false otherwise.
     */
    public void setHasFreeFromProbationCard(boolean hasFreeFromProbationCard) {
        this.hasFreeFromProbationCard = hasFreeFromProbationCard;
    }

    /**
     * Gets the amount of money the player has.
     *
     * @return The amount of money the player has.
     */
    public int getMoney() {
        return this.money;
    }

    /**
     * Sets the amount of money the player has.
     *
     * @param money The amount of money to set.
     * @throws OutOfMoneyException if the money amount is negative.
     */
    public void setMoney(int money) throws OutOfMoneyException {
        if (money > 0) {
            this.money = money;
        } else throw new OutOfMoneyException();
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if the player is in probation.
     *
     * @return true if the player is in probation, false otherwise.
     */
    public boolean getIsInProbation() {
        return this.isInProbation;
    }

    /**
     * Sets whether the player is in probation.
     *
     * @param isInProbation true if the player is in probation, false otherwise.
     */
    public void setIsInProbation(boolean isInProbation) {
        this.isInProbation = isInProbation;

    }

    /**
     * Gets the position of the player on the board.
     *
     * @return The position of the player on the board.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the player on the board.
     *
     * @param position The position to set.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Gets the properties owned by the player.
     *
     * @return An ArrayList containing the properties owned by the player.
     */
    public ArrayList<Property> getPlayerProperties() {
        return playerProperties;
    }

    /**
     * Removes a property from the list of properties owned by the player.
     *
     * @param prop The property to remove.
     */
    public void removeFromPlayerProperties(Property prop) {
        playerProperties.remove(prop);
    }

}