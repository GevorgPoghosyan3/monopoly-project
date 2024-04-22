package am.aua.monopoly.core;

import java.util.ArrayList;

/**
 * Represents a player in the am.aua.monopoly.core.Monopoly game with attributes such as name, position, money, and owned properties.
 * Methods include moving, buying properties, paying and receiving rent.
 */

public class Player {

    public enum Type {
        CAT, CAR, BOOT, IRON, HAT, SHIP, MONEYBAG, BALL;
    }

    private Type playerType;
    private double money;
    private String name;



    private int position;


    private ArrayList<Property> playerProperties;

    public Player(String name, Type type) {
        this.money = 1500;
        this.name = name;
        this.playerType = type;
        this.position = 0;
        this.playerProperties = new ArrayList<>();
    }

//    public am.aua.monopoly.core.Player(am.aua.monopoly.core.Player other) {
//        this.money = other.money;
//        this.name = other.name;
//        this.playerColor = other.playerColor;
//        this.position = other.position;
//        // Since arrays are reference types, we need to deep copy the array
//        this.playerProperties = new am.aua.monopoly.core.Property[other.playerProperties.length];
//        for (int i = 0; i < other.playerProperties.length; i++) {
//            this.playerProperties[i] = new am.aua.monopoly.core.Property(other.playerProperties[i].getName());
//        }
//    }

    public Type getType() {
        return this.playerType;
    }


    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return this.name;
    }

    public int getPosition() {
        return position;
    }

    public void buyProperty(int p) {
        setMoney(this.getMoney() - Board.propertyAt(p).getPrice());
        playerProperties.add(Board.propertyAt(p));
        Board.propertyAt(p).setOwner(this);

    }

    public ArrayList<Property> getPlayerProperties() {
        return playerProperties;
    }


    public void move(int diceRoll) {
        this.position = this.position + diceRoll;
        if (this.position > Board.BOARD_SIZE) {
            int difference = this.position - Board.BOARD_SIZE;
            this.position = difference;
            setMoney(this.getMoney() + 200);
            payRent();
        }
    }

    public void payRent() {
        Player owner = Board.propertyAt(this.position).getOwner();
        double propertyTax = Board.propertyAt(this.position).getPrice() * 0.1;
        if (owner != null && owner != this) {
            owner.setMoney(owner.getMoney() + propertyTax);
            this.setMoney(this.getMoney() - propertyTax);
        }
    }

    public boolean canBuildOn(Property prop) {
        Property.PropertyType type = prop.getPropertyType();
        Player owner = prop.getOwner();

        for (int i = 0; i < Board.tiles.size(); i++) {
            if(Board.tiles.get(i).getClass() == Property.class) {
                Property property = (Property) Board.tiles.get(i);
                int houses = property.getNumberOfHouses();
                if(property.getPropertyType() == type && property.getOwner() != owner) {
                    return false;
                } else if(property.getPropertyType() == type && property.getOwner() == owner && Math.abs(houses - prop.getNumberOfHouses()) > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void build(Property prop) throws InvalidNumberOfHousesException{
        if(this.canBuildOn(prop)) {
            int fee = 0;
            switch (prop.getNumberOfHouses()) {
                case 0:
                    fee = prop.getLevel1Fee();
                    prop.setRent ((int)(prop.getRent() * 1.1));
                    break;
                case 1:

                    fee = prop.getLevel2Fee();
                    prop.setRent ((int)(prop.getRent() * 1.2));
                    break;
                case 2:
                    fee = prop.getLevel3Fee();
                    prop.setRent ((int)(prop.getRent() * 1.3));
                    break;
                default:
                    throw new InvalidNumberOfHousesException();
            }

            this.setMoney(this.getMoney() - fee);
            prop.setNumberOfHouses(prop.getNumberOfHouses() + 1);

        }
    }


    //    void moveTo(int pos);
//
//    int position();
//
//
//    void excMoney(int money);
//
    public void toJail() {
        this.position = 10;
    }
//
//    boolean stayJail();
//
//    void sellProp(Square sq);
//
//    void leaveJail();
//
//    boolean inJail();
//
//    void addJailFree(boolean chance);
//
//    boolean useJailFree();
//
//    int numJailFree();
//
//    int getAssets();
//
//    /* Input stuff */
//    boolean inputBool(am.aua.monopoly.core.Monopoly.State state);
//
//    int inputInt(am.aua.monopoly.core.Monopoly.State state);
//
//    int inputDecision(am.aua.monopoly.core.Monopoly.State state, String[] choices);
//
//    am.aua.monopoly.core.Player inputPlayer(am.aua.monopoly.core.Monopoly.State state, am.aua.monopoly.core.Player notAllowed);
//
}