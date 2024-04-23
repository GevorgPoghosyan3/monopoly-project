package am.aua.monopoly.core;

public class InvalidNumberOfHousesException extends Exception {
    public InvalidNumberOfHousesException(){
        super("Invalid number of houses");
    }

    public InvalidNumberOfHousesException(String msg) {
        super(msg);
    }

}
