public class InvalidGameException extends Exception{
    public InvalidGameException(){
        super("Invalid number of players!");
    }
    public InvalidGameException(String msg){
        super(msg);
    }
}
