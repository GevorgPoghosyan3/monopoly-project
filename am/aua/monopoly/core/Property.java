package am.aua.monopoly.core;

public class Property extends Tile {

    @Override
    public void performAction(Player player, Board board) {
        return;
    }

    public enum PropertyType {
        BROWN, LIGHT_BLUE, PINK, ORANGE, RED, YELLOW, GREEN, BLUE
    }

    private int price;
    private int rent;
    private int level1Fee;
    private int level2Fee;
    private int level3Fee;
    private int mortgage;

    private int numberOfHouses = 0;
    private PropertyType propertyType;
    private Player owner;
    private boolean isBuildable = false;

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getLevel1Fee() {
        return level1Fee;
    }

    public void setLevel1Fee(int level1Fee) {
        this.level1Fee = level1Fee;
    }

    public int getLevel2Fee() {
        return level2Fee;
    }

    public void setLevel2Fee(int level2Fee) {
        this.level2Fee = level2Fee;
    }

    public int getLevel3Fee() {
        return level3Fee;
    }

    public void setLevel3Fee(int level3Fee) {
        this.level3Fee = level3Fee;
    }

    public int getMortgage() {
        return mortgage;
    }

    public void setMortgage(int mortgage) {
        this.mortgage = mortgage;
    }




//    public void upgrade(){
//        if(owner.canBuildOn(this)){
//            numberOfHouses++;
//            owner.setMoney(owner.getMoney() - );
//        }
//    }


    public Property(String name, int price, int rent, int level1Fee, int level2Fee, int level3Fee, int mortgage, PropertyType propertyType, boolean isBuildable) {
        super(name);
        this.price = price;
        this.rent = rent;
        this.level1Fee = level1Fee;
        this.level2Fee = level2Fee;
        this.level3Fee = level3Fee;
        this.mortgage = mortgage;
        this.propertyType = propertyType;
        this.isBuildable = isBuildable;
        this.numberOfHouses = 0; // Initially, there are no houses
        this.owner = null;       // am.aua.monopoly.core.Property is initially not owned
    }



    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    public void setNumberOfHouses(int numberOfHouses) {
        this.numberOfHouses = numberOfHouses;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public PropertyType getPropertyType() {
        return propertyType;
    }


    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }
}
