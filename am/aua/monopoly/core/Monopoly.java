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
        Board board = new Board();
        board.initializeBoard();
    }



    public void setPlayers() {
        for (int i = 0; i < this.numberOfPLayers; i++) {
            System.out.print("Enter player " + (i + 1) + " name: ");
            Scanner scanner = new Scanner(System.in);
            String playerName = scanner.nextLine();

            // Assign a color to the player using the ordinal of the enum, which is automatically the index
            players.add(new Player(playerName, types[i]));
        }
    }


    public ArrayList<Player> getPlayers() {
//        ArrayList<Player> players = new ArrayList<>();
//        for (Player player : this.players) {
//            players.add(new Player(player)); // Assuming Player class has a copy constructor
//        }
        return players;
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

//
//    private boolean checkGameOver() {
//        // Implement logic to check if the game should end, such as one player left with money
//        return false; // Placeholder for actual game-over conditions
//    }

}
