package Exception;

/**
 * Exception for when a player tries to count an optional action more than once.
 */
public class OptionalScoreException extends Exception {
    public OptionalScoreException(){}

    public OptionalScoreException(String message){
        super(message);
    }

    public OptionalScoreException(Throwable cause){
        super(cause);
    }

    public OptionalScoreException(String message, Throwable cause){
        super(message, cause);
    }
}
