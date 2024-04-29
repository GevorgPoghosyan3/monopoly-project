package am.aua.monopoly.core;

/**
 * Represents a pair of dice used in the am.aua.monopoly.core.Monopoly game to roll.
 */
public class Dice {

        private static int die1;
        private static int die2;


        public static int roll() {
             die1 = (int) (Math.random() * 6) + 1; // Random number between 1 and 6 for die 1
             die2 = (int) (Math.random() * 6) + 1; // Random number between 1 and 6 for die 2
            return die1 + die2;
        }


        // Method to check if the two dice are the same
        public static boolean isDouble() {
            return die1 == die2;
        }

        // Method to get a String representation of the two dice

        public static String toStringDice() {
            return "Die 1: " + die1 + ", Die 2: " + die2;
        }

}
