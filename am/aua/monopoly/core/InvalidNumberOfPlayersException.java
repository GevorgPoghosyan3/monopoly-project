package am.aua.monopoly.core;

/**
 * Represents an exception thrown when the number of players in the game is invalid.
 */
public class InvalidNumberOfPlayersException extends Exception {

    /**
     * Constructs an InvalidNumberOfPlayersException with a default error message.
     */
    public InvalidNumberOfPlayersException() {
        super("Invalid number of players!");
    }

    /**
     * Constructs an InvalidNumberOfPlayersException with the specified error message.
     *
     * @param msg The error message.
     */
    public InvalidNumberOfPlayersException(String msg) {
        super(msg);
    }
}
