package am.aua.monopoly.core;

import java.util.Objects;

/**
 * Represents a tile on the Monopoly game board with attributes such as name and fee.
 */
public class Tile {
    private String name;
    private int fee;

    /**
     * Constructs a tile with the given name.
     *
     * @param name The name of the tile.
     */
    public Tile(String name) {
        this.name = name;
    }

    /**
     * Constructs a tile with the given name and fee.
     *
     * @param name The name of the tile.
     * @param fee  The fee associated with the tile.
     */
    public Tile(String name, int fee) {
        this(name);
        this.fee = fee;
    }

    /**
     * Gets the fee associated with the tile.
     *
     * @return The fee associated with the tile.
     */
    public int getFee() {
        return fee;
    }

    /**
     * Gets the name of the tile.
     *
     * @return The name of the tile.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the position of the tile on the board.
     *
     * @return The position of the tile on the board.
     */
    public int getPosition() {
        int position = 0;
        for (int i = 0; i < Board.tiles.size(); i++) {
            if (Board.tiles.get(i).equals(this)) {
                position = i;
            }
        }
        return position;
    }

    /**
     * Checks if this tile is equal to another tile.
     *
     * @param other The other tile to compare with.
     * @return True if the tiles are equal, false otherwise.
     */
    public boolean equals(Tile other) {
        if (other == null) return false;
        else if (other.getClass() != this.getClass()) {
            return false;
        }
        return (Objects.equals(this.name, other.name));
    }

}
