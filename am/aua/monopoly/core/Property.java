package am.aua.monopoly.core;

/**
 * Represents a property tile on the Monopoly game board with attributes such as price, rent, and owner.
 * Methods include performing actions on the property and accessing its details.
 */
public class Property extends Tile {

    /**
     * Enumerates the types of properties such as BROWN, LIGHT_BLUE, etc.
     */
    public enum PropertyType {
        BROWN, LIGHT_BLUE, PINK, ORANGE, RED, YELLOW, GREEN, BLUE, ELEVATOR
    }

    private int price;
    private int rent;
    private int level1Fee;
    private int level2Fee;
    private int level3Fee;
    private int numberOfHouses;
    private PropertyType propertyType;
    private Player owner;
    private boolean isBuildable;
    private boolean isUnderMortgage;

    /**
     * Constructs a property with the given attributes.
     *
     * @param name         The name of the property.
     * @param price        The price of the property.
     * @param rent         The rent value.
     * @param level1Fee    The fee for the first level of development (e.g., adding a house).
     * @param level2Fee    The fee for the second level of development.
     * @param level3Fee    The fee for the third level of development.
     * @param propertyType The type of the property (e.g., BROWN, LIGHT_BLUE).
     * @param isBuildable  Determines if the property can be developed with houses.
     */
    public Property(String name, int price, int rent, int level1Fee, int level2Fee, int level3Fee, PropertyType propertyType, boolean isBuildable) {
        super(name);
        this.price = price;
        this.rent = rent;
        this.level1Fee = level1Fee;
        this.level2Fee = level2Fee;
        this.level3Fee = level3Fee;
        this.propertyType = propertyType;
        this.numberOfHouses = 0;
        this.owner = null;
        this.isBuildable = isBuildable;
        this.isUnderMortgage = false;
    }

    /**
     * Copy constructor to create a new property with the same attributes as another property.
     *
     * @param other The property to copy attributes from.
     */
    public Property(Property other) {
        super(other.getName());
        this.price = other.price;
        this.rent = other.rent;
        this.level1Fee = other.level1Fee;
        this.level2Fee = other.level2Fee;
        this.level3Fee = other.level3Fee;
        this.numberOfHouses = other.numberOfHouses;
        this.propertyType = other.propertyType;

        this.owner = (other.owner != null) ? new Player(other.owner) : null;

        this.isBuildable = other.isBuildable;
        this.isUnderMortgage = other.isUnderMortgage;
    }

    /**
     * Gets the rent value of the property.
     *
     * @return The rent value.
     */
    public int getRent() {
        return rent;
    }

    /**
     * Sets the rent value of the property.
     *
     * @param rent The rent value to set.
     */
    public void setRent(int rent) {
        this.rent = rent;
    }

    /**
     * Gets the fee for the first level of development (e.g., adding a house).
     *
     * @return The fee for the first level of development.
     */
    public int getLevel1Fee() {
        return level1Fee;
    }

    /**
     * Gets the fee for the second level of development.
     *
     * @return The fee for the second level of development.
     */
    public int getLevel2Fee() {
        return level2Fee;
    }

    /**
     * Gets the fee for the third level of development.
     *
     * @return The fee for the third level of development.
     */
    public int getLevel3Fee() {
        return level3Fee;
    }

    /**
     * Gets the number of houses built on the property.
     *
     * @return The number of houses built on the property.
     */
    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    /**
     * Sets the number of houses built on the property.
     *
     * @param numberOfHouses The number of houses to set.
     */
    public void setNumberOfHouses(int numberOfHouses) {
        this.numberOfHouses = numberOfHouses;
    }

    /**
     * Gets the price of the property.
     *
     * @return The price of the property.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets the type of the property.
     *
     * @return The type of the property (e.g., BROWN, LIGHT_BLUE).
     */
    public PropertyType getPropertyType() {
        return propertyType;
    }

    /**
     * Gets the owner of the property.
     *
     * @return The owner of the property.
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Sets the owner of the property.
     *
     * @param player The player to set as the owner.
     */
    public void setOwner(Player player) {
        this.owner = player;
    }

    /**
     * Sets whether the property is under mortgage.
     *
     * @param isUnderMortgage true if the property is under mortgage, false otherwise.
     */
    public void setIsUnderMortgage(boolean isUnderMortgage) {
        this.isUnderMortgage = isUnderMortgage;
    }

    /**
     * Checks if the property is under mortgage.
     *
     * @return true if the property is under mortgage, false otherwise.
     */
    public boolean getIsUnderMortgage() {
        return isUnderMortgage;
    }

    /**
     * Sets the price of the property.
     *
     * @param price The price to set.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Checks if the property is buildable.
     *
     * @return true if the property is buildable, false otherwise.
     */
    public boolean getIsBuildable() {
        return isBuildable;
    }

    /**
     * Converts the name of a property to its corresponding position on the board.
     *
     * @param name The name of the property.
     * @return The position of the property on the board.
     */
    public static int nameToPosition(String name) {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            if (name.equals(Board.tiles.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }
}
