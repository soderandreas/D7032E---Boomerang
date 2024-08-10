package Message;

/**
 * An implementation of the message class that is a response to a choice.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class Response extends Message {
    /**
     * The Choice that the player is responding to.
     */
    private Choice question;

    /**
     * The response from the player.
     */
    private String response;
    
    /**
     * The initiator for the response. Takes the Choice given to the player
     * and the players response as parameters.
     * 
     * @param choice The Choice Message that the player was sent.
     * @param response The response the player makes to the Choice they were given.
     */
    public Response(Choice choice, String response){
        this.question = choice;
        this.response = response;
    }
    
    /**
     * Creates an empty response. Used if they user fails to give a functional response.
     * 
     * @param choice The Choice Message that the player was sent.
     */
    public Response(Choice choice){
        this.question = choice;
        this.response = "";
    }

    /**
     * Checks if the reponse from the player was one of the possible choices.
     * 
     * @return True if the response from the player was one of the choices, false
     * if it was not. 
     */
    public boolean responseInChoice(){
        String test = new String(this.question.getChoices());
        return test.contains(this.response) && !this.response.equals("");
    }

    /**
     * Sets the response.
     * 
     * @param response Sets the response from a player.
     */
    public void setResponse(String response){
        this.response = response;
    }

    /**
     * Gets the message from the choice.
     */
    public String getMessage(){
        return this.question.getMessage();
    }

    /**
     * Gets the choice the player is responding to.
     * 
     * @return the choice the player is responding to.
     */
    public Choice getQuestion(){
        return this.question;
    }

    /**
     * Gets the response from the player.
     * 
     * @return the response from the player.
     */
    public String getResponse(){
        return this.response;
    }
}
