package am.aua.monopoly.core;

public class OutOfMoneyException extends Exception{
    public OutOfMoneyException(){
        super("You are out of money. Put your properties under mortgage.");
    }
    public OutOfMoneyException(String msg){
        super(msg);
    }
}
