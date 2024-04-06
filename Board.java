/**
 * Represents the game board in the Monopoly game with properties and special spaces.
 * Methods include moving players and handling landing actions.
 */
public class Board {

    public static final int BOARD_SIZE = 40;
    public static Property[] properties = new Property[BOARD_SIZE];

        public Board() {
                properties[0] = new Property("Go", 0, Property.PropertyType.SPECIAL);
                properties[1] = new Property("Mediterranean Avenue", 60, Property.PropertyType.BROWN);
                properties[2] = new Property("Community Chest", 0, Property.PropertyType.SPECIAL);
                properties[3] = new Property("Baltic Avenue", 60, Property.PropertyType.BROWN);
                properties[4] = new Property("Income Tax", 0, Property.PropertyType.SPECIAL);
                properties[5] = new Property("Reading Railroad", 200, Property.PropertyType.RAILROAD);
                properties[6] = new Property("Oriental Avenue", 100, Property.PropertyType.LIGHT_BLUE);
                properties[7] = new Property("Chance", 0, Property.PropertyType.SPECIAL);
                properties[8] = new Property("Vermont Avenue", 100, Property.PropertyType.LIGHT_BLUE);
                properties[9] = new Property("Connecticut Avenue", 120, Property.PropertyType.LIGHT_BLUE);
                properties[10] = new Property("Jail", 0, Property.PropertyType.SPECIAL);
                properties[11] = new Property("St. Charles Place", 140, Property.PropertyType.PINK);
                properties[12] = new Property("Electric Company", 150, Property.PropertyType.UTILITY);
                properties[13] = new Property("States Avenue", 140, Property.PropertyType.PINK);
                properties[14] = new Property("Virginia Avenue", 160, Property.PropertyType.PINK);
                properties[15] = new Property("Pennsylvania Railroad", 200, Property.PropertyType.RAILROAD);
                properties[16] = new Property("St. James Place", 180, Property.PropertyType.ORANGE);
                properties[17] = new Property("Community Chest", 0, Property.PropertyType.SPECIAL);
                properties[18] = new Property("Tennessee Avenue", 180, Property.PropertyType.ORANGE);
                properties[19] = new Property("New York Avenue", 200, Property.PropertyType.ORANGE);
                properties[20] = new Property("Free Parking", 0, Property.PropertyType.SPECIAL);
                properties[21] = new Property("Kentucky Avenue", 220, Property.PropertyType.RED);
                properties[22] = new Property("Chance", 0, Property.PropertyType.SPECIAL);
                properties[23] = new Property("Indiana Avenue", 220, Property.PropertyType.RED);
                properties[24] = new Property("Illinois Avenue", 240, Property.PropertyType.RED);
                properties[25] = new Property("B. & O. Railroad", 200, Property.PropertyType.RAILROAD);
                properties[26] = new Property("Atlantic Avenue", 260, Property.PropertyType.YELLOW);
                properties[27] = new Property("Ventnor Avenue", 260, Property.PropertyType.YELLOW);
                properties[28] = new Property("Water Works", 150, Property.PropertyType.UTILITY);
                properties[29] = new Property("Marvin Gardens", 280, Property.PropertyType.YELLOW);
                properties[30] = new Property("Go To Jail", 0, Property.PropertyType.SPECIAL);
                properties[31] = new Property("Pacific Avenue", 300, Property.PropertyType.GREEN);
                properties[32] = new Property("North Carolina Avenue", 300, Property.PropertyType.GREEN);
                properties[33] = new Property("Community Chest", 0, Property.PropertyType.SPECIAL);
                properties[34] = new Property("Pennsylvania Avenue", 320, Property.PropertyType.GREEN);
                properties[35] = new Property("Short Line", 200, Property.PropertyType.RAILROAD);
                properties[36] = new Property("Chance", 0, Property.PropertyType.SPECIAL);
                properties[37] = new Property("Park Place", 350, Property.PropertyType.BLUE);
                properties[38] = new Property("Luxury Tax", 0, Property.PropertyType.SPECIAL);
                properties[39] = new Property("Boardwalk", 400, Property.PropertyType.BLUE);
        }
        public static Property propertyAt ( int p){
            return properties[p];
        } //


    }
