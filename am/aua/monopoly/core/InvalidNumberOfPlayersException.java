package am.aua.monopoly.core;

public class InvalidNumberOfPlayersException extends Exception{
    public InvalidNumberOfPlayersException(){
        super("Invalid number of players!");
    }
    public InvalidNumberOfPlayersException(String msg){
        super(msg);
    }
}
