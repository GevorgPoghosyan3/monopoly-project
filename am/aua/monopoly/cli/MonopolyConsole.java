package am.aua.monopoly.cli;

import am.aua.monopoly.core.*;

import java.util.Scanner;

/**
 * The MonopolyConsole class represents the command-line interface for playing the Monopoly game.
 */
public class MonopolyConsole {
    /**
     * The run method starts the Monopoly game in the console.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Monopoly!");
        System.out.print("Enter the number of Players: ");
        int numberOfPLayers = scanner.nextInt();

        Monopoly monopoly = null;
        try {
            monopoly = new Monopoly(numberOfPLayers);
        } catch (InvalidNumberOfPlayersException e) {
            System.err.println(e.getMessage());
        }

        Scanner sc = new Scanner(System.in);

        String[] playerNames = new String[numberOfPLayers];
        for (int i = 0; i < numberOfPLayers; i++) {
            System.out.print("Enter player " + (i + 1) + " name: ");
            String name = sc.nextLine();
            playerNames[i] = name;
        }
        monopoly.setPlayers(playerNames);


        Player player = monopoly.getTurn();

//        Property propa = (Property)Board.tiles.get(1);
//        Property propa1 = (Property)Board.tiles.get(3);
//
//        propa.setOwner(player);
//        propa1.setOwner(player);
//        player.getPlayerProperties().add(propa1);
//        player.getPlayerProperties().add(propa);


        System.out.println(player.getName() + "'s turn with type " + player.getType());

        String inputLine = scanner.nextLine();

        while (!inputLine.equals("q") && !monopoly.gameOver()) {
            monopoly.bankrupt(player);
//            monopoly.goToJail(player);
//            try {
//                monopoly.getOutOfJail(player);
//            } catch (OutOfMoneyException e) {
//                System.err.println(e.getMessage());
//            }
            inputLine = scanner.nextLine();
            if (inputLine.equals("r")) {
                if (!monopoly.getHasRolled() || monopoly.getHasRolledDouble()) {
                    try {
                        monopoly.move(player, Dice.roll());
//                        Dice.checkSpeeding();
                    } catch (OutOfMoneyException e) {
                        System.err.println(e.getMessage());
                    }
                    System.out.println(Dice.toStringDice());
                } else System.out.println("You have already rolled the dice");


                System.out.println(player.getMoney());
                System.out.println(Board.tileAt(player.getPosition()).getName());


            } else if (inputLine.equals("n")) {
                if (monopoly.getHasRolled() && !monopoly.getHasRolledDouble()) {
                    player = monopoly.getTurn();
                    System.out.println(player.getName() + "'s turn with type " + player.getType());
                } else System.out.println("You should roll the dice.");

            } else if (inputLine.equals("teleport")) {
                System.out.println("Your properties - > ");
                monopoly.print(Monopoly.ElevatorChecker(player));

                System.out.println("Choose the property e.g 1, 2, 3");
                int property = scanner.nextInt();
                if (property <= Monopoly.ElevatorChecker(player).size()) {
                    try {
                        monopoly.teleport(player, Monopoly.ElevatorChecker(player).get(property - 1));
                    } catch (InvalidTeleportLocationException e) {
                        System.err.println(e.getMessage());
                    }
                }
            } else if (inputLine.equals("Buy")) {
                if (monopoly.getHasRolled()) {
                    try {
                        Monopoly.buyProperty(player, player.getPosition());
                    } catch (OutOfMoneyException | InvalidPurchaseException e) {
                        System.err.println(e.getMessage());
                    }
                } else System.out.println("You should roll the dice.");
            } else if (inputLine.equals("p")) {
                if (!Monopoly.showProperties(player).isEmpty()) {
                    monopoly.print(Monopoly.showProperties(player));
                } else System.out.println("You don't have properties yet.");
            } else if (inputLine.equals("quit")) {
                monopoly.leaveTheGame(player);
                player = monopoly.getTurn();
                System.out.println(player.getName() + "'s turn with type " + player.getType());
            } else if (inputLine.equals("sell")) {
                System.out.println("You properties - > ");
                monopoly.print(Monopoly.showProperties(player));

                System.out.println("Choose the property e.g 1, 2, 3");
                int property = scanner.nextInt();
                if (property <= Monopoly.showProperties(player).size()) {
                    try {
                        Monopoly.sellProperty(player, Monopoly.showProperties(player).get(property - 1));
                    } catch (OutOfMoneyException e) {
                        System.err.println(e.getMessage());
                    }
                }
            } else if (inputLine.equals("Build")) {
                System.out.println("You properties - > ");
                monopoly.print(Monopoly.checkSameColorProps(player));

                System.out.println("Choose the property e.g 1, 2, 3");
                int property = scanner.nextInt();
                if (property <= Monopoly.checkSameColorProps(player).size()) {
                    Property buildProperty;
                    try {
                        buildProperty = Monopoly.checkSameColorProps(player).get(property - 1);
                        Monopoly.build(player, buildProperty);
                        System.out.println(buildProperty.getNumberOfHouses() + ", on " + buildProperty.getName());
                    } catch (InvalidNumberOfHousesException | OutOfMoneyException e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    System.out.println("There is no " + property + " th property");
                }


            } else if (inputLine.equals("m")) {
                System.out.println("You properties - > ");
                monopoly.print(Monopoly.checkNotUnderMortgage(player));

                System.out.println("Choose the property e.g 1, 2, 3");
                int property = scanner.nextInt();

                if (property <= Monopoly.checkNotUnderMortgage(player).size()) {
                    Property mortgageProperty = player.getPlayerProperties().get(property - 1);
                    try {
                        Monopoly.putUnderMortgage(player, mortgageProperty);
                    } catch (OutOfMoneyException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println(mortgageProperty.getName() + ", put under mortgage, you got " + mortgageProperty.getPrice() + "$");
                }

            } else if (inputLine.equals("dm")) {
                System.out.println("properties under mortgage - > ");
                monopoly.print(Monopoly.checkUnderMortgage(player));
                System.out.println("Choose the property e.g 1, 2, 3");
                int property = scanner.nextInt();

                if (property <= Monopoly.checkUnderMortgage(player).size()) {
                    Property mortgageProperty = player.getPlayerProperties().get(property - 1);
                    try {
                        Monopoly.deMortgage(player, mortgageProperty);
                    } catch (OutOfMoneyException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println(mortgageProperty.getName() + ", put under mortgage, you lost " + mortgageProperty.getPrice() + "$");
                }

            }

        }
        System.out.println("Game Over " + player.getName() + " Won!!!");
    }
}
