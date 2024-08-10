package Message;

/**
 * An implementaion of Message used to send information to the players. This is all
 * output that the player does not need to respond to.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class Information extends Message {

    /**
     * Initiates a Information Message, which is a message that just contains
     * information that is going to be shown to the player.
     * 
     * @param info The information that is being sent to the player.
     */
    public Information(String info){
        this.message = info;
    }

    /**
     * Gets the information as a String.
     */
    public String getMessage(){
        return this.message;
    }
}
