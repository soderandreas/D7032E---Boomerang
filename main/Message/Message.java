package Message;
import java.io.Serializable;

/**
 * An abstract class that handles messages being sent to and between the players.
 * These messages are used to handle input and output.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public abstract class Message implements Serializable {
    /**
     * A message being sent to the player. All Messages will contain this.
     */
    protected String message = "";

    /**
     * Gets the message as a string.
     * 
     * @return The message as a string.
     */
    public abstract String getMessage();
}
