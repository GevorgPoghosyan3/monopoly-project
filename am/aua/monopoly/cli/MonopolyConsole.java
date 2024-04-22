package am.aua.monopoly.cli;

import am.aua.monopoly.core.*;

import java.util.Scanner;

public class MonopolyConsole {
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Monopoly!");
        System.out.println("Enter the number of Players:");
        int numberOfPLayers = scanner.nextInt();

        Monopoly monopoly = null;
        try {
            monopoly = new Monopoly(numberOfPLayers);
            monopoly.setPlayers(numberOfPLayers);
        } catch (InvalidNumberOfPlayersException e) {
            System.out.println(e.getMessage());
        }

        int k = 0;
        Player player = monopoly.getPlayers().get(k);
        System.out.println(player.getName() + "'s turn with type " + player.getType());
        int rollTotal = 0;
        boolean hasRolled = false;

      String inputLine = scanner.nextLine();

        while (!inputLine.equals("I am Bankrupt")) {
            if (inputLine.equals("R")) {
                player.move(Dice.roll());
                System.out.println(Dice.toStringDice());
                hasRolled = true;
            } else if (inputLine.equals("Next")) {
                if(k == monopoly.getPlayers().size() - 1) {
                    k = 0;
                }
                player =  monopoly.getPlayers().get(++k);
                System.out.println(player.getName() + "'s turn with type " + player.getType());

            } else if (inputLine.equals("Buy") && hasRolled) {
                player.buyProperty(player.getPosition());
            } else if (inputLine.equals("P")) {
                for(Property prop: player.getPlayerProperties()) {
                    System.out.println(prop.getName());
                }
            }else if (inputLine.equals("Mortgage")) {
                //Gev u Armen qich boxoqveq gorc areq
            } else if (inputLine.equals("DeMortgage")) {
                //Gev u Armen qich boxoqveq gorc areq
            }inputLine = scanner.nextLine();

        }
    }

}
