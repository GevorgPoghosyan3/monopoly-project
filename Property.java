/**
 * Represents a property on the Monopoly game board with attributes such as name, price, rent, and owner.
 */
public class Property{

    private String name;
    private Player owner = null;
    private double propertyPrice;

    private PropertyType propertyType;
    public enum PropertyType {
        BROWN, LIGHT_BLUE, PINK, ORANGE, RED, YELLOW, GREEN, BLUE, RAILROAD, UTILITY, SPECIAL
    };


    public Property(String name, double propertyPrice, PropertyType propertyType){
        this.name = name;
        this.propertyPrice = propertyPrice;
        this.propertyType = propertyType;
    }
    public Property() {

    }

    public Property(Property other) {
        this.setName(other.name);
        this.setPropertyPrice(other.getPropertyPrice());
        this.setOwner(other.owner);
    }

    public String getName() {
        return this.name;
    }

    public double getPropertyPrice() {
        return this.propertyPrice;
    }

    public Player getOwner() {
        return new Player(this.owner);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPropertyPrice(double propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public void setOwner(Player otherOwner) {
        this.owner = new Player(otherOwner);
    }

    public static Property[] appendToArray(Property[] properties, Property prop) {
        Property[] newArr = new Property[properties.length + 1];

        for (int i = 0; i < properties.length; i++) {
            newArr[i] = properties[i];
        }

        newArr[properties.length + 1] = prop;

        return newArr;
    }


}
