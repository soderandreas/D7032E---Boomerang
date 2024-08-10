package Exception;

/**
 * Exception thrown when the game can not be launched.
 */
public class GameLaunchException extends Exception {
    public GameLaunchException(){}

    public GameLaunchException(String message){
        super(message);
    }

    public GameLaunchException(Throwable cause){
        super(cause);
    }

    public GameLaunchException(String message, Throwable cause){
        super(message, cause);
    }
}
