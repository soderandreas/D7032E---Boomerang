package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Message.*;
import Exception.BoomerangIOException;

/**
 * 
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class OnlinePlayerConnect {
    /**
     * The output-stream to the online player.
     */
    private ObjectOutputStream outToClient;

    /**
     * The input-stream from the online player.
     */
    private ObjectInputStream inFromClient;

    /**
     * Initiates a new connection between a player and the host of a game. All
     * OnlinePlayers has one of these objects.
     * 
     * @param inFromServer The input-stream from the online player.
     * @param outToServer The output-stream to the online player.
     */
    public OnlinePlayerConnect(ObjectInputStream inFromServer, ObjectOutputStream outToServer){
        this.outToClient = outToServer;
        this.inFromClient = inFromServer;
    }

    /**
     * Sends a Message to the online player.
     * 
     * @param message The message being sent from the host to the online player.
     * @throws BoomerangIOException Is thrown if something goes wrong with the IO.
     */
    public void sendMessage(Message message) throws BoomerangIOException{
        try{
            outToClient.writeObject(message);
        } catch (IOException e){
            throw new BoomerangIOException(e);
        }
    }

    /**
     * Reads Messages being sent by the online player.
     * 
     * @return The latest message sent from the online player or the error that caused
     * the message to not be sent.
     * @throws BoomerangIOException Is thrown if something goes wrong with the IO.
     * @throws ClassNotFoundException Is thrown if the class sent by the online player can
     * not be found.
     */
    public Message readMessage() throws BoomerangIOException, ClassNotFoundException{
        Message word = null; 

        try{
            word = (Message) inFromClient.readObject();
        } catch (IOException e) {
            word = new Information(e.getMessage());
            throw new BoomerangIOException(e);
        }

        return word;
    }
}
