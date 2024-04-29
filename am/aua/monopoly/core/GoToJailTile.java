package am.aua.monopoly.core;

public class GoToJailTile extends Tile{

    public GoToJailTile(String name) {
        super(name);
    }


    public void performAction(Player player, Board board) {
        System.out.println(player.getName() + " has been sent to jail.");
        player.setPosition(30);
    }


}
