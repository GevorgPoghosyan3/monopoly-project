package am.aua.monopoly.core;

/**
 * Represents an exception thrown when a teleportation location is invalid.
 */
public class InvalidTeleportLocationException extends Exception {

    /**
     * Constructs an InvalidTeleportLocationException with a default error message.
     */
    public InvalidTeleportLocationException() {
        super("Invalid Teleport Location.");
    }

    /**
     * Constructs an InvalidTeleportLocationException with the specified error message.
     *
     * @param msg The error message.
     */
    public InvalidTeleportLocationException(String msg) {
        super(msg);
    }
}
