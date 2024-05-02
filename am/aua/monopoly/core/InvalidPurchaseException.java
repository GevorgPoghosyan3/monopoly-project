package am.aua.monopoly.core;

public class InvalidPurchaseException extends Exception {
    public InvalidPurchaseException(){
        super("The purchase either has an owner or is not purchasable.");
    }
    public InvalidPurchaseException(String msg) {
        super(msg);
    }
}
