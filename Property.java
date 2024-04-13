public class Property extends Tile implements FeeChangeable{

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
        this.owner = null;       // Property is initially not owned
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
