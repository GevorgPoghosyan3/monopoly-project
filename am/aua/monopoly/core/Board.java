package am.aua.monopoly.core;

import java.util.ArrayList;


/**
 * Represents the game board in the am.aua.monopoly.core.Monopoly game with properties and special spaces.
 * Methods include moving players and handling landing actions.
 */
public class Board {
    public static ArrayList<Tile> tiles = new ArrayList<>();
    public static int BOARD_SIZE = 0;
    public void initializeBoard() {
        tiles.add(new Tile("Entrance"));  // 0
        tiles.add(new Property("Student Union", 60, 2, 10, 30, 90, Property.PropertyType.BROWN, true));  // 1
        tiles.add(new Tile("Community Chest Tile"));  // 2
        tiles.add(new Property("Amphitheater", 60, 4, 20, 60, 180, Property.PropertyType.BROWN, true));  // 3
        tiles.add(new Tile("Admission Fee", -200));  // 4
        tiles.add(new Property("Elevator 1", 200, 50, 0, 0, 0, Property.PropertyType.ELEVATOR, false));  // 5
        tiles.add(new Property("AUA Gym", 100, 6, 30, 90, 270, Property.PropertyType.LIGHT_BLUE, true));  // 6
        tiles.add(new Tile("Community Chest Tile"));  // 7
        tiles.add(new Property("Health Center", 100, 6, 30, 90, 270, Property.PropertyType.LIGHT_BLUE, true));  // 8
        tiles.add(new Property("EPIC", 120, 8, 40, 100, 300, Property.PropertyType.LIGHT_BLUE, true));  // 9
        tiles.add(new Tile("Probation"));  // 10P
        tiles.add(new Property("Coffee House", 140, 10, 50, 150, 450,  Property.PropertyType.PINK, true));  // 11
        tiles.add(new Tile("Electricity fee", -100));  // 12
        tiles.add(new Property("Eat’n Act", 140, 10, 50, 150, 450,  Property.PropertyType.PINK, true));  // 13
        tiles.add(new Property("Kofu", 160, 12, 60, 180, 500,  Property.PropertyType.PINK, true));  // 14
        tiles.add(new Property("Elevator 2", 200, 50, 0, 0, 0,  Property.PropertyType.ELEVATOR, false));  // 5
        tiles.add(new Property("Triangular park", 180, 14, 70, 200, 550,  Property.PropertyType.ORANGE, true));  // 16
        tiles.add(new Tile("Community Chest Tile"));  // 17
        tiles.add(new Property("Najarian building", 180, 14, 70, 200, 550,  Property.PropertyType.ORANGE, true));  // 18
        tiles.add(new Property("Dorm", 200, 16, 80, 220, 600,  Property.PropertyType.ORANGE, true));  // 19
        tiles.add(new Tile("Parking"));  // 20
        tiles.add(new Property("Aquarium", 220, 18, 90, 250, 700,  Property.PropertyType.RED, true));  // 21
        tiles.add(new Tile("Chance Tile"));  // 22
        tiles.add(new Property("Lab", 220, 18, 90, 250, 700,  Property.PropertyType.RED, true));  // 23
        tiles.add(new Property("Library", 240, 20, 100, 300, 750,  Property.PropertyType.RED, true));  // 24
        tiles.add(new Property("Elevator 3", 200, 50, 0, 0, 0, Property.PropertyType.ELEVATOR, false));  // 5
        tiles.add(new Property("Small Auditorium (SA)", 260, 22, 110, 330, 800,  Property.PropertyType.YELLOW, true));  // 26
        tiles.add(new Property("Large Auditorium (LA)", 260, 22, 110, 330, 800,  Property.PropertyType.YELLOW, true));  // 27
        tiles.add(new Tile("Water Works", -100));  // 28
        tiles.add(new Property("Manoogian Hall", 280, 24, 120, 360, 850, Property.PropertyType.YELLOW, true));  // 29
        tiles.add(new Tile("CourtRoom")); //30
        tiles.add(new Property("The Bridge", 300, 26, 130, 390, 900, Property.PropertyType.GREEN, true));  // 31
        tiles.add(new Property("Akian Gallery", 300, 26, 130, 390, 900, Property.PropertyType.GREEN, true));  // 32
        tiles.add(new Tile("Community Chest Tile"));  // 33
        tiles.add(new Property("The Presidents office", 320, 28, 150, 450, 1000,  Property.PropertyType.GREEN, true));  // 34
        tiles.add(new Property("Elevator 4", 200, 50, 0, 0, 0,  Property.PropertyType.ELEVATOR, false));  // 5
        tiles.add(new Tile("Chance Tile"));  // 36
        tiles.add(new Property("Cafeteria", 350, 35, 175, 500, 1100,  Property.PropertyType.BLUE, true));  // 37
        tiles.add(new Tile("Student Service Fee", -75));  // 38
        tiles.add(new Property("The Green Bean Cafe", 400, 50, 200, 600, 1400,  Property.PropertyType.BLUE, true));  // 39

        BOARD_SIZE = tiles.size();
    }


    public static Tile tileAt(int p) {
        return tiles.get(p);
    }

    public static Property propertyAt(int p) {
        if(tiles.get(p).getClass() == Property.class){
            return (Property) tiles.get(p);
        }
        else return null;
    }


}
