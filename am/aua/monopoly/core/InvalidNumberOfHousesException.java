package am.aua.monopoly.core;

/**
 * Represents an exception thrown when the number of houses on a property is invalid.
 */
public class InvalidNumberOfHousesException extends Exception {

    /**
     * Constructs an InvalidNumberOfHousesException with a default error message.
     */
    public InvalidNumberOfHousesException() {
        super("Invalid number of houses");
    }

    /**
     * Constructs an InvalidNumberOfHousesException with the specified error message.
     *
     * @param msg The error message.
     */
    public InvalidNumberOfHousesException(String msg) {
        super(msg);
    }
}
