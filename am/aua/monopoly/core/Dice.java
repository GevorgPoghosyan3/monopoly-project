package am.aua.monopoly.core;

/**
 * Represents a pair of dice used in the am.aua.monopoly.core.Monopoly game to roll.
 */
public class Dice {

        private static int dice1;
        private static int dice2;


        public static int roll() {
             dice1 = (int) (Math.random() * 6) + 1; // Random number between 1 and 6 for die 1
             dice2 = (int) (Math.random() * 6) + 1; // Random number between 1 and 6 for die 2
            return dice1 + dice2;
        }


        // Method to check if the two dice are the same
        public static boolean isDouble() {
            return dice1 == dice2;
        }

        // Method to get a String representation of the two dice

        public static String toStringDice() {
            return "Die 1: " + dice1 + ", Die 2: " + dice2;
        }

}
