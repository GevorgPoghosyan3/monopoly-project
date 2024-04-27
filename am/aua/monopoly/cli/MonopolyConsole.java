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
            monopoly.setPlayers();
        } catch (InvalidNumberOfPlayersException e) {
            System.out.println(e.getMessage());
        }

        int turn = 0;
        Player player = monopoly.getPlayers().get(turn);

//        Property propa = (Property)Board.tiles.get(0);
//        Property propa1 = (Property)Board.tiles.get(1);
//
//        propa.setOwner(player);
//        propa1.setOwner(player);
//        player.getPlayerProperties().add(propa1);
//        player.getPlayerProperties().add(propa);
//


        System.out.println(player.getName() + "'s turn with type " + player.getType());
        int rollTotal = 0;
        boolean hasRolled = false;
        boolean hasRolledDouble = false;

      String inputLine = scanner.nextLine();

        while (!inputLine.equals("q")) {
                System.out.println(player.getMoney());
            if (inputLine.equals("r")) {
                if(!hasRolled || hasRolledDouble) {
                    player.move(Dice.roll());
                    System.out.println(Dice.toStringDice());

                    hasRolled = true;
                    hasRolledDouble = Dice.isDouble();


                } else {
                    System.out.println("You have already rolled the dice");
                }

            } else if (inputLine.equals("n")) {
                if(hasRolled) {
                    if(turn == monopoly.getPlayers().size() - 1) {
                        turn = 0;
                    } else  {
                        turn++;
                        System.out.println("Turn increment");
                    }

                    player =  monopoly.getPlayers().get(turn);
                    System.out.println(player.getName() + "'s turn with type " + player.getType());
                    hasRolled = false;


                } else {
                    System.out.println("You should roll the Dice");
                }

            } else if (inputLine.equals("Buy") && hasRolled) {
                player.buyProperty(player.getPosition());
            } else if (inputLine.equals("P")) {
                for(Property prop: player.getPlayerProperties()) {
                    System.out.println(prop.getName() + prop.getNumberOfHouses());
                }
            } else if(inputLine.equals("Build")) {
                System.out.print("You properties - > ");
                int sameColor = 0;
                for(Property prop: player.getPlayerProperties()) {
                    if(player.canBuildOn(prop)){
                    System.out.print(prop.getName() + ", ");
                    sameColor++;
                    }
                }
                System.out.println("Choose the property e.g 1, 2, 3");
                int property = scanner.nextInt();
                if(property <= sameColor) {
                    try {
                        Property buildProperty = player.getPlayerProperties().get(property - 1);
                        player.build(buildProperty);
                        System.out.println( buildProperty.getNumberOfHouses()+ ", on " + buildProperty.getName());
                    } catch (InvalidNumberOfHousesException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("There is not " + property + " th property");
                }


            }else if (inputLine.equals("Mortgage")) {
                //Gev u Armen qich boxoqveq gorc areq
            } else if (inputLine.equals("DeMortgage")) {
                //Gev u Armen qich boxoqveq gorc areq
            }inputLine = scanner.nextLine();

        }
    }

}



// chest, parking, jail, voshm sax boardy
//bancrupty inqniran ashxati
//chesty Hovagi het xosal
