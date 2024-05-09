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

    /**
     * Rolls the dice and returns the sum of the two dice.
     *
     * @return The sum of the two dice rolled.
     */
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

    /**
     * Checks if the two dice rolled are the same.
     *
     * @return true if the two dice have the same value, false otherwise.
     */
    public static boolean isDouble() {
        return dice1 == dice2;

    }

    public static void setDoubleCounter(int doubleCounter) {
        doubleCounter = doubleCounter;
    }

    /**
     * Returns a string representation of the two dice values.
     *
     * @return A string representing the two dice values.
     */
    public static String toStringDice() {
        return "Dice 1: " + dice1 + ", Dice 2: " + dice2;
    }

}