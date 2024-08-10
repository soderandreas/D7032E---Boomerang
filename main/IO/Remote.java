package IO;

import Exception.BoomerangIOException;
import Message.*;
import Networking.OnlinePlayerConnect;

/**
 * An implememtation of HandleIO used for local IO in a console setting. 
 * Sends and receives messages from a online player. Handles IO for Host -> OnlinePlayer. 
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class Remote implements HandleIO {
    /**
     * An OnlinePlayerConnect which handles the connetion between the host and online player.
     */
    private OnlinePlayerConnect connection;

    /**
     * Initiates a new remote IO between a host and a online player.
     * 
     * @param connection The connection between the host and online player.
     */
    public Remote(OnlinePlayerConnect connection){
        this.connection = connection;
    }

    /**
     * Changes the OnlinePlayerConnect connection that is saved.
     * 
     * @param connection New OnlinePlayerConnect
     */
    public void setConnection(OnlinePlayerConnect connection){
        this.connection = connection;
    }

    /**
     * Gets the input from an online player by waiting for a message.
     */
    public Response getInput(Choice choice) throws BoomerangIOException, ClassNotFoundException{
        Response playerResponse = (Response)connection.readMessage();
        return playerResponse;
    }

    /**
     * Outputs a message to the online player by sending a message.
     */
    public void outputMessage(Message message) throws BoomerangIOException{
        connection.sendMessage(message);
    }

    /**
     * Tries to output an exception to online player. If this fails the exception
     * of this exception is shown to the host.
     */
    public void outputError(Exception e){
        try{
            outputMessage(new Information(e.getMessage()));
        } catch (Exception doubleError) {
            doubleError.printStackTrace();
        }
    }
}
