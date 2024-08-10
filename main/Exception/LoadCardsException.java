package Exception;

import java.io.IOException;

/**
 * Exception for when something goes wrong with loading a card.
 */
public class LoadCardsException extends IOException {
    public LoadCardsException() {
        super();
    }

    public LoadCardsException(String s) {
        super(s);
    }

    public LoadCardsException(Throwable cause){
        super(cause);
    }
}
