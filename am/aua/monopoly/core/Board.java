package am.aua.monopoly.core;

import java.util.ArrayList;


/**
 * Represents the game board in the am.aua.monopoly.core.Monopoly game with properties and special spaces.
 * Methods include moving players and handling landing actions.
 */
public class Board {

    public static final int BOARD_SIZE = 22;
    public static ArrayList<Tile> tiles = new ArrayList<>();

        public void initializeBoard() {
                tiles.add(new GoTile("GoTile"));  // 0
                tiles.add(new Property("Mediterranean Avenue", 60, 2, 10, 30, 90, 50, Property.PropertyType.BROWN, true));  // 1
            //    tiles.add(new CommunityChestTile("Community Chest Tile"));  // 2
                tiles.add(new Property("Baltic Avenue", 60, 4, 20, 60, 180, 50, Property.PropertyType.BROWN, true));  // 3
           //     tiles.add(new IncomeTaxTile());  // 4
            //    tiles.add(new Railroad("Reading am.aua.monopoly.core.Railroad"));  // 5
                tiles.add(new Property("Oriental Avenue", 100, 6, 30, 90, 270, 50, Property.PropertyType.LIGHT_BLUE, true));  // 6
             //   tiles.add(new ChanceTile());  // 7
                tiles.add(new Property("Vermont Avenue", 100, 6, 30, 90, 270, 50, Property.PropertyType.LIGHT_BLUE, true));  // 8
                tiles.add(new Property("Connecticut Avenue", 120, 8, 40, 100, 300, 60, Property.PropertyType.LIGHT_BLUE, true));  // 9
                tiles.add(new GoToJailTile("GoToJail"));  // 30
                tiles.add(new Property("St. Charles Place", 140, 10, 50, 150, 450, 100, Property.PropertyType.PINK, true));  // 11
            //    tiles.add(new Utility("Electric Company"));  // 12
                tiles.add(new Property("States Avenue", 140, 10, 50, 150, 450, 100, Property.PropertyType.PINK, true));  // 13
                tiles.add(new Property("Virginia Avenue", 160, 12, 60, 180, 500, 100, Property.PropertyType.PINK, true));  // 14
            //    tiles.add(new Railroad("Pennsylvania am.aua.monopoly.core.Railroad"));  // 15
                tiles.add(new Property("St. James Place", 180, 14, 70, 200, 550, 100, Property.PropertyType.ORANGE, true));  // 16
            //    tiles.add(new CommunityChestTile());  // 17
                tiles.add(new Property("Tennessee Avenue", 180, 14, 70, 200, 550, 100, Property.PropertyType.ORANGE, true));  // 18
                tiles.add(new Property("New York Avenue", 200, 16, 80, 220, 600, 100, Property.PropertyType.ORANGE, true));  // 19
//                tiles.add(new FreeParkingTile());  // 20
                tiles.add(new Property("Kentucky Avenue", 220, 18, 90, 250, 700, 150, Property.PropertyType.RED, true));  // 21
             //   tiles.add(new ChanceTile());  // 22
                tiles.add(new Property("Indiana Avenue", 220, 18, 90, 250, 700, 150, Property.PropertyType.RED, true));  // 23
                tiles.add(new Property("Illinois Avenue", 240, 20, 100, 300, 750, 150, Property.PropertyType.RED, true));  // 24
            //    tiles.add(new Railroad("B&O am.aua.monopoly.core.Railroad"));  // 25
                tiles.add(new Property("Atlantic Avenue", 260, 22, 110, 330, 800, 150, Property.PropertyType.YELLOW, true));  // 26
                tiles.add(new Property("Ventnor Avenue", 260, 22, 110, 330, 800, 150, Property.PropertyType.YELLOW, true));  // 27
                //    tiles.add(new Utility("Water Works"));  // 28
                tiles.add(new Property("Marvin Gardens", 280, 24, 120, 360, 850, 150, Property.PropertyType.YELLOW, true));  // 29
               tiles.add(new CourtTile("Court"));
            tiles.add(new Property("Pacific Avenue", 300, 26, 130, 390, 900, 200, Property.PropertyType.GREEN, true));  // 31
                tiles.add(new Property("North Carolina Avenue", 300, 26, 130, 390, 900, 200, Property.PropertyType.GREEN, true));  // 32
           //     tiles.add(new CommunityChestTile());  // 33
                tiles.add(new Property("Pennsylvania Avenue", 320, 28, 150, 450, 1000, 200, Property.PropertyType.GREEN, true));  // 34
            //    tiles.add(new Railroad("Short Line"));  // 35
              //  tiles.add(new ChanceTile());  // 36
                tiles.add(new Property("Park Place", 350, 35, 175, 500, 1100, 200, Property.PropertyType.BLUE, true));  // 37
           //     tiles.add(new LuxuryTaxTile());  // 38
                tiles.add(new Property("Boardwalk", 400, 50, 200, 600, 1400, 200, Property.PropertyType.BLUE, true));  // 39
        }
        public static Tile tileAt (int p){
            return tiles.get(p);
        }

        public static Property propertyAt (int p){
                return (Property) tiles.get(p);
        }




    public class GoTile extends Tile{
        public GoTile(String name) {
            super(name);
        }

        @Override
        public void performAction(Player player, Board board) {
            return ;
        }
    }

}
