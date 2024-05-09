package am.aua.monopoly.core;

/**
 * Represents an exception thrown when a property purchase is invalid.
 */
public class InvalidPurchaseException extends Exception {

    /**
     * Constructs an InvalidPurchaseException with a default error message.
     */
    public InvalidPurchaseException() {
        super("The purchase either has an owner or is not purchasable.");
    }

    /**
     * Constructs an InvalidPurchaseException with the specified error message.
     *
     * @param msg The error message.
     */
    public InvalidPurchaseException(String msg) {
        super(msg);
    }
}
