/**
 * Represents a player in the Monopoly game with attributes such as name, position, money, and owned properties.
 * Methods include moving, buying properties, paying and receiving rent.
 */

public class Player{
    public enum Color{
        RED, GREEN, BLUE, YELLOW, PURPLE, BLACK, WHITE, PINK;
    }
    private Color playerColor;
    private int money;

    public Player(){
        this.money = 1500;
        playerColor = Color.(int)Math.random() * 8 + 1;
    }
    public int getMoney(){
        return this.money;
    }
    void move(int numSpaces);

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
