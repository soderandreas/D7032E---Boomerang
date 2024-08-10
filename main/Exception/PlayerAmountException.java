package Exception;

/**
 * Exception for when there are to few or to many players created.
 */
public class PlayerAmountException extends Exception {
    public PlayerAmountException(){}

    public PlayerAmountException(String message){
        super(message);
    }

    public PlayerAmountException(Throwable cause){
        super(cause);
    }

    public PlayerAmountException(String message, Throwable cause){
        super(message, cause);
    }
}
