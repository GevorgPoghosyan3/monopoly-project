/**
 * Represents the game board in the Monopoly game with properties and special spaces.
 * Methods include moving players and handling landing actions.
 */
public class Board{

    public final int BOARD_SIZE = 40;
    private Property[] properties = new Property[BOARD_SIZE];

    public Board() {};

    public Property propertyAt(int p) {
        return properties[p];
    }





}
