package am.aua.monopoly.cli;

import am.aua.monopoly.core.*;

import java.util.ArrayList;
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
            monopoly.setPlayers();
        } catch (InvalidNumberOfPlayersException e) {
            System.out.println(e.getMessage());
        }

        int turn = 0;
        Player player = monopoly.getPlayers().get(turn);

//        Property propa = (Property)Board.tiles.get(1);
//        Property propa1 = (Property)Board.tiles.get(3);
//
//        propa.setOwner(player);
//        propa1.setOwner(player);
//        player.getPlayerProperties().add(propa1);
//        player.getPlayerProperties().add(propa);


        System.out.println(player.getName() + "'s turn with type " + player.getType());
        boolean hasRolled = false;
        boolean hasRolledDouble = false;

        String inputLine = scanner.nextLine();

        while (!inputLine.equals("q") && !monopoly.gameOver()) {
            inputLine = scanner.nextLine();
            System.out.println(player.getMoney());
            System.out.println(player.getPosition());
            if (inputLine.equals("r")) {
                if (!hasRolled || hasRolledDouble) {
                    monopoly.move(player, Dice.roll());
                    System.out.println(Dice.toStringDice());

                    hasRolled = true;
                    hasRolledDouble = Dice.isDouble();


                } else {
                    System.out.println("You have already rolled the dice");
                }

            } else if (inputLine.equals("n")) {
                if (hasRolled && !hasRolledDouble) {
                    if (turn == monopoly.getPlayers().size() - 1) {
                        turn = 0;
                    } else {
                        turn++;
                    }

                    player = monopoly.getPlayers().get(turn);
                    System.out.println(player.getName() + "'s turn with type " + player.getType());
                    hasRolled = false;


                } else {
                    System.out.println("You should roll the Dice");
                }

            } else if (inputLine.equals("Buy") && hasRolled) {
                try {
                    monopoly.buyProperty(player, player.getPosition());
                }catch (InvalidPurchaseException e){
                    System.err.println(e.getMessage());
                }
            } else if (inputLine.equals("P")) {
                for (Property prop : player.getPlayerProperties()) {
                    System.out.println(prop.getName() + prop.getNumberOfHouses());
                }
            }else if (inputLine.equals("quit")) {
                monopoly.leaveTheGame(player);
                player = monopoly.getPlayers().get(turn);
                System.out.println(player.getName() + "'s turn with type " + player.getType());
            }
            else if (inputLine.equals("Build")) {
                System.out.print("You properties - > ");

                ArrayList<Property> sameColorProps = new ArrayList<>();
                for (Property prop : player.getPlayerProperties()) {
                    if (monopoly.canBuildOn(prop)) {
                        sameColorProps.add(prop);
                        System.out.print(prop.getName() + ", ");
                    }
                }
                System.out.println("Choose the property e.g 1, 2, 3");
                int property = scanner.nextInt();
                if (property <= sameColorProps.size()) {
                    Property buildProperty = null;
                    try {
                        buildProperty = sameColorProps.get(property - 1);
                        monopoly.build(player, buildProperty);
                        System.out.println(buildProperty.getNumberOfHouses() + ", on " + buildProperty.getName());
                    } catch (InvalidNumberOfHousesException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("There is not " + property + " th property");
                }


            } else if (inputLine.equals("m")) {


                System.out.print("You properties - > ");
                ArrayList<Property> notUnderMortgage = new ArrayList<>();
                for (Property prop : player.getPlayerProperties()) {
                    if(!prop.getIsUnderMortgage()) {
                        System.out.print(prop.getName() + ", ");
                        notUnderMortgage.add(prop);
                    }


                }

                System.out.println("Choose the property e.g 1, 2, 3");
                int property = scanner.nextInt();

                if(property <= notUnderMortgage.size()) {
                    Property mortgageProperty = player.getPlayerProperties().get(property - 1);
                    monopoly.putUnderMortgage(player, mortgageProperty);
                    System.out.println(mortgageProperty.getName() + ", put under mortgage, you got " + player.getMoney() + "$");
                }

            } else if (inputLine.equals("dm")) {
                System.out.print("properties under mortgage - > ");

               ArrayList<Property> mortgageProps = new ArrayList<>();
                for (Property prop : player.getPlayerProperties()) {
                    if (prop.getIsUnderMortgage()) {
                        mortgageProps.add(prop);
                        System.out.print(prop.getName() + ", ");
                    }

                }
                System.out.println("Choose the property e.g 1, 2, 3");
                int property = scanner.nextInt();

                if(property <= mortgageProps.size()) {
                    Property mortgageProperty = player.getPlayerProperties().get(property - 1);
                    monopoly.deMortgage(player, mortgageProperty);
                    System.out.println(mortgageProperty.getName() + ", put under mortgage, you got " + player.getMoney() + "$");
                }

            }



        }
        System.out.println("Game Over " + player.getName() + "Won!!!");
    }

}
