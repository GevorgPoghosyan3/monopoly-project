import java.util.ArrayList;
import java.util.Scanner;

public class Monopoly {
    private ArrayList<Player> players;
    Player.Color[] colors = Player.Color.values();
    private int numberOfPLayers;


    public Monopoly(int numberOfPlayers) throws InvalidGameException{
        if(numberOfPlayers < 2 || numberOfPlayers > 8){
            throw new InvalidGameException();
        }else this.numberOfPLayers = numberOfPlayers;
    }

    public void setPlayers() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < this.numberOfPLayers; i++) {
            System.out.print("Enter player " + (i + 1) + " name: ");
            String playerName = scanner.nextLine();

            // Assign a color to the player using the ordinal of the enum, which is automatically the index
            players.add(new Player(playerName, colors[i]));
        }
    }

//    public void startGame() {
//        System.out.println("Starting Monopoly with " + players.size() + " players.");
//        boolean gameOver = false;
//
//        while (!gameOver) {
//            Player currentPlayer = players.get(i);
//            takeTurn(currentPlayer);
//            gameOver = checkGameOver();
//            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
//        }
//        System.out.println("Game Over!");
//    }
//
//    private void takeTurn(Player player) {
//        System.out.println(player.getName() + "'s turn with color " + player.getColor());
//
//        // Player rolls dice
//        int rollTotal = dice.roll();
//        System.out.println(player.getName() + " rolled a " + rollTotal);
//
//        // Move the player
//        player.move(rollTotal);
//
//        // Resolve the tile action
//        Tile currentTile = board.getTile(player.getPosition());
//        currentTile.performAction(player, board);
//    }
//
//    private boolean checkGameOver() {
//        // Implement logic to check if the game should end, such as one player left with money
//        return false; // Placeholder for actual game-over conditions
//    }

}
