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

    public Player(Player other) {
        this.playerType = other.playerType;
        this.money = other.money;
        this.name = other.name;
        this.position = other.position;
        // Deep copy of playerProperties ArrayList
        this.playerProperties = new ArrayList<>();
        for (Property property : other.playerProperties) {
            this.playerProperties.add(new Property(property));
        }
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
        if(Board.propertyAt(p).getOwner() == null) {
            setMoney(this.getMoney() - Board.propertyAt(p).getPrice());
            playerProperties.add(Board.propertyAt(p));
            Board.propertyAt(p).setOwner(this);
        } else {
            System.out.println("This property already has an owner");
        }

    }

    public void setPosition(int position) {
        this.position = position;
    }


    public ArrayList<Property> getPlayerProperties() {

//        ArrayList<Property> properties = new ArrayList<>();
//        for (Property property : this.playerProperties) {
//            properties.add(new Property(property)); // Assuming Player class has a copy constructor
//        }
//        return properties;

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
        Property property =  Board.propertyAt(this.position);
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
                }

//                else if(property.getPropertyType() == type && property.getOwner() == owner && Math.abs(houses - prop.getNumberOfHouses()) > 1) {
//                    return false;
//                }
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