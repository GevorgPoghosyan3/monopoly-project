/**
 * Represents a pair of dice used in the Monopoly game to roll.
 */
import java.util.Random;
public class Dice {

    public class TwoDice {
        private int die1;
        private int die2;
        private Random random;

        public TwoDice() {
            random = new Random();
            roll(); // Initial roll when creating the object
        }

        // Method to roll the dice
        public void roll() {
            die1 = random.nextInt(6) + 1; // Random number between 1 and 6 for die 1
            die2 = random.nextInt(6) + 1; // Random number between 1 and 6 for die 2
        }

        public int getTotal() {
            return die1 + die2;
        }

        // Method to check if the two dice are the same
        public boolean isDouble() {
            return die1 == die2;
        }

        // Method to get a String representation of the two dice
        @Override
        public String toString() {
            return "Die 1: " + die1 + ", Die 2: " + die2;
        }
    }


}
