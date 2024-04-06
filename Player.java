/**
 * Represents a player in the Monopoly game with attributes such as name, position, money, and owned properties.
 * Methods include moving, buying properties, paying and receiving rent.
 */

public class Player{
    public enum Color{
        BROWN, WHITE, PINK, ORANGE, RED, YELLOW, GREEN, BLUE;
    }

    private Color playerColor;
    private int money;
    private String name;
    private int position;
    private Property[] playerProperties;

    public Player(String name, Color color) {
        this.money = 1500;
        this.name = name;
        this.color = color;
        this.position = 0;
        this.playerProperties = new Property[0];
    }
    public int getMoney(){
        return this.money;
    }

    public void setMoney(int money){
        this.money = money;
    }

    public void buyProperty(int p){
        this.money = this.money - getPropertyPrice(propertyAt(p));
        this.playerProperties = appendToArray(propertyAt(p));
        propertyAt(p).setOwner = this;

    }
    public void move(int numSpaces){
        this.position + Dice.getTotal();
    }


    void moveTo(int pos);

    int position();


    void excMoney(int money);

    void toJail();

    boolean stayJail();

    void sellProp(Square sq);

    void leaveJail();

    boolean inJail();

    void addJailFree(boolean chance);

    boolean useJailFree();

    int numJailFree();

    int getAssets();

    /* Input stuff */
    boolean inputBool(Monopoly.State state);

    int inputInt(Monopoly.State state);

    int inputDecision(Monopoly.State state, String[] choices);

    Player inputPlayer(Monopoly.State state, Player notAllowed);
}
