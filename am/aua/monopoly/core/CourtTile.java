package am.aua.monopoly.core;

import am.aua.monopoly.core.Board;
import am.aua.monopoly.core.Player;
import am.aua.monopoly.core.Property;
import am.aua.monopoly.core.Tile;

public class CourtTile extends Tile {

    public CourtTile(String name) {
        super(name);
    }


    public void performAction(Player player, Board board) {
        player.setPosition(Property.nameToPosition("GoToJail"));
    }
}
