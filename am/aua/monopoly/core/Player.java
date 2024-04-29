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

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<Property> getPlayerProperties() {
        return playerProperties;
    }

}