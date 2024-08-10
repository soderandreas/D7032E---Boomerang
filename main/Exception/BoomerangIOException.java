package Exception;

import java.io.IOException;

/**
 * Handles the IOExceptions associated with Boomerang.
 */
public class BoomerangIOException extends IOException {
    public BoomerangIOException(){
        super();
    }

    public BoomerangIOException(String message){
        super(message);
    }

    public BoomerangIOException(Throwable cause){
        super(cause);
    }

    public BoomerangIOException(String message, Throwable cause){
        super(message, cause);
    }
}
