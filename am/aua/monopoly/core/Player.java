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
    private int money;
    private String name;
    private boolean isInProbation;
    private boolean hasFreeFromProbationCard;
    private int position;
    private ArrayList<Property> playerProperties;



    public Player(String name, Type type) {
        this.money = 2000;
        this.name = name;
        this.playerType = type;
        this.position = 0;
        this.playerProperties = new ArrayList<>();
        this.isInProbation = false;
    }

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


    public Type getType() {
        return this.playerType;
    }

    public boolean getHasFreeFromProbationCard() {
        return hasFreeFromProbationCard;
    }

    public void setHasFreeFromProbationCard(boolean hasFreeFromProbationCard) {
        this.hasFreeFromProbationCard = hasFreeFromProbationCard;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) throws OutOfMoneyException {
        if(money > 0) {
            this.money = money;
        }else throw new OutOfMoneyException();
    }

    public String getName() {
        return this.name;
    }
    public boolean getIsInProbation(){
        return this.isInProbation;
    }
    public void setIsInProbation(boolean isInProbation){
        this.isInProbation = isInProbation;
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

    public void removeFromPlayerProperties(Property prop){
        playerProperties.remove(prop);
    }

}