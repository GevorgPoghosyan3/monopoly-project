package am.aua.monopoly.core;

public class InvalidInputException extends Exception{
    public InvalidInputException(){
        super("Invalid input.");
    }
    public InvalidInputException(String msg){
        super(msg);
    }
}
