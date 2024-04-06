/**
 * Represents a property on the Monopoly game board with attributes such as name, price, rent, and owner.
 */
public class Property{

    private String name;
    private Player owner = null;
    private int costOfProperty;
    public enum PropertyType {
        BROWN, WHITE, PINK, ORANGE, RED, YELLOW, GREEN, BLUE
    };

    public Property() {

    }

    public Property(Property other) {
        this.setName(other.name);
        this.setCostOfProperty(other.costOfProperty);
        this.setOwner(other.owner);
    }

    public String getName() {
        return this.name;
    }

    public int getCostOfProperty() {
        return this.costOfProperty;
    }

    public Player getOwner() {
        return new Player(this.owner);
    }

    public void setName(String otherName) {
        this.name = otherName;
    }

    public void setCostOfProperty(int otherCostOfProperty) {
        this.costOfProperty = otherCostOfProperty;
    }

    public void setOwner(Player otherOwner) {
        this.owner = new Player(otherOwner);
    }

    public Property[] appendToArray(Property[] properties, Property prop) {
        Property[] newArr = new Property[properties.length + 1];

        for (int i = 0; i < properties.length; i++) {
            newArr[i] = properties[i];
        }

        newArr[properties.length + 1] = prop;

        return newArr;
    }


}
