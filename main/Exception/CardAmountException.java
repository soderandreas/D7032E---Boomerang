package Exception;

/**
 * Handles exceptions for when too many or too few cards are created.
 */
public class CardAmountException extends Exception {
    public CardAmountException(){}

    public CardAmountException(String message){
        super(message);
    }

    public CardAmountException(Throwable cause){
        super(cause);
    }

    public CardAmountException(String message, Throwable cause){
        super(message, cause);
    }
}
