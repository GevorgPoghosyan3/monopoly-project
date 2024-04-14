package am.aua.monopoly.cli;

import am.aua.monopoly.core.*;

import java.util.Scanner;

public class MonopolyConsole {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Monopoly!");
        System.out.println("Enter the number of Players:");
        int numberOfPLayers = scanner.nextInt();

        try {
            Monopoly monopoly = new Monopoly(numberOfPLayers);
            monopoly.setPlayers(numberOfPLayers);
        }catch (InvalidNumberOfPlayersException e){
            System.out.println(e.getMessage());
        }
    }
}
