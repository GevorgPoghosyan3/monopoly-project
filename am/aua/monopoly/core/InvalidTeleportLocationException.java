package am.aua.monopoly.core;

public class InvalidTeleportLocationException extends Exception{
    public InvalidTeleportLocationException(){
        super("Invalid Teleport Location.");
    }
    public InvalidTeleportLocationException(String msg){
        super(msg);
    }
}
