package am.aua.monopoly.core;

import java.util.ArrayList;
import java.util.Scanner;

public class Monopoly {
    private int numberOfPLayers;
    private ArrayList<Player> players;
    Player.Type[] types = Player.Type.values();


    public Monopoly(int numberOfPlayers) throws InvalidNumberOfPlayersException {
        players = new ArrayList<>();
        if(numberOfPlayers < 2 || numberOfPlayers > 8){
            throw new InvalidNumberOfPlayersException();
        }else this.numberOfPLayers = numberOfPlayers;
    }

    public void setPlayers(int numberOfPLayers) {
        for (int i = 0; i < numberOfPLayers; i++) {
            System.out.print("Enter player " + (i + 1) + " name: ");
            Scanner scanner = new Scanner(System.in);
            String playerName = scanner.nextLine();

            // Assign a color to the player using the ordinal of the enum, which is automatically the index
            players.add(new Player(playerName, types[i]));
        }
    }


//    public void startGame() {
//        System.out.println("Starting am.aua.monopoly.core.Monopoly with " + players.size() + " players.");
//        boolean gameOver = false;
//
//        while (!gameOver) {
//            am.aua.monopoly.core.Player currentPlayer = players.get(i);
//            takeTurn(currentPlayer);
//            gameOver = checkGameOver();
//            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
//        }
//        System.out.println("Game Over!");
//    }
//
//    private void takeTurn(am.aua.monopoly.core.Player player) {
//        System.out.println(player.getName() + "'s turn with color " + player.getColor());
//
//        // am.aua.monopoly.core.Player rolls dice
//        int rollTotal = dice.roll();
//        System.out.println(player.getName() + " rolled a " + rollTotal);
//
//        // Move the player
//        player.move(rollTotal);
//
//        // Resolve the tile action
//        am.aua.monopoly.core.Tile currentTile = board.getTile(player.getPosition());
//        currentTile.performAction(player, board);
//    }
//
//    private boolean checkGameOver() {
//        // Implement logic to check if the game should end, such as one player left with money
//        return false; // Placeholder for actual game-over conditions
//    }

}
