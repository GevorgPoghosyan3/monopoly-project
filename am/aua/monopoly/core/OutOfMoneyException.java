package am.aua.monopoly.core;

/**
 * Represents an exception thrown when a player runs out of money.
 */
public class OutOfMoneyException extends Exception {

    /**
     * Constructs an OutOfMoneyException with a default error message.
     */
    public OutOfMoneyException() {
        super("You are out of money. Put your properties under mortgage.");
    }

    /**
     * Constructs an OutOfMoneyException with the specified error message.
     *
     * @param msg The error message.
     */
    public OutOfMoneyException(String msg) {
        super(msg);
    }
}
