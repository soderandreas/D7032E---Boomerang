package Message;

/**
 * An implementation of Message that handles choices for the player. Each
 * Choice Message contins a message for the player as well as an array of 
 * all acceptable choices.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class Choice extends Message {
    /**
     * An array of the acceptable choices the player can make.
     */
    private char[] choices;

    /**
     * The initiator for the class. Saves the message for the player
     * as well as all acceptable choices.
     * 
     * @param info The information being stored in the message
     * @param choices The choices the player can make.
     */
    public Choice(String info, char[] choices){
        this.message = info;
        this.choices = choices;
    }

    /**
     * Gets the message.
     */
    public String getMessage(){
        return this.message;
    }

    /**
     * Returns the array with all acceptable choices.
     * 
     * @return The acceptable choices.
     */
    public char[] getChoices(){
        return this.choices;
    }
}
