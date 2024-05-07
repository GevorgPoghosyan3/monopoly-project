package am.aua.monopoly.core;

import java.util.ArrayList;

/**
 * Represents a pair of dice used in the am.aua.monopoly.core.Monopoly game to roll.
 */
public class Dice {

    private static int dice1;
    private static int dice2;
//    private boolean threwThreeConsecutiveDoubles;
//    private static ArrayList<int[]> diceValues = new ArrayList<>();
//    private static int doubleCounter = 0;


    public static int roll() {
//        diceValues.add(new int[]{0, 0});
        int[] onePair = new int[2];
        dice1 = (int) (Math.random() * 6) + 1; // Random number between 1 and 6 for die 1
        dice2 = (int) (Math.random() * 6) + 1; // Random number between 1 and 6 for die 2
        onePair[0] = dice1;
        onePair[1] = dice2;
//        diceValues.add(onePair);
        return dice1 + dice2;
    }

//    public static boolean checkSpeeding() {
////        if (doubleCounter == 3) {
////            for (int i = 0; i < diceValues.size(); i++) {
////                if (diceValues.get(0). && diceValues.get(1) == diceValues.get(2)) {
////                    diceValues.remove(0);
////                    return true;
////                }
////            }
////        } return false;
//
//        if(doubleCounter == 3) {
//            return true;
//        }
//
//        return false;
//    }

    // Method to check if the two dice are the same
    public static boolean isDouble() {
        return dice1 == dice2;

    }


    public static void setDoubleCounter(int doubleCounter) {
        doubleCounter = doubleCounter;
    }

    // Method to get a String representation of the two dice

    public static String toStringDice() {
        return "Die 1: " + dice1 + ", Die 2: " + dice2;
    }

}
