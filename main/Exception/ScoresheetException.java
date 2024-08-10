package Exception;

/**
 * Exception for when creating the scoresheets for the players fails.
 */
public class ScoresheetException extends Exception {
    public ScoresheetException(){}

    public ScoresheetException(String message){
        super(message);
    }

    public ScoresheetException(Throwable cause){
        super(cause);
    }

    public ScoresheetException(String message, Throwable cause){
        super(message, cause);
    }
}
