package am.aua.monopoly.core;

import java.util.Objects;

/**
 * Represents a property on the am.aua.monopoly.core.Monopoly game board with attributes such as name, price, rent, and owner.
 */
public class Tile {
    private String name;

    // Simplified constructor for new tile creation
    public Tile(String name) {
        this.name = name;
    }

    // Copy constructor - removed unnecessary cloning where not needed
    public Tile(Tile other) {
        this.name = other.name;
    }

    public String getName() {
        return this.name;
    }

    public int getPosition() {
        int position = 0;
        for (int i = 0; i < Board.tiles.size(); i++) {
            if (Board.tiles.get(i).equals(this)) {
                position = i;
            }
        }
        return position;
    }


    public boolean equals(Tile other) {
        if (other == null)
            return false;
        else if (other.getClass() != this.getClass()) {
            return false;
        }
        return (Objects.equals(this.name, other.name));
    }

}
