package GameClient;

import IO.Console;
import Networking.ClientConnect;
import Message.*;

/**
 * A class used for handling online interactions as the player joining a game.
 */
public class GameJoin {
    /**
     * The Console used for the IO by the online player.
     */
    Console console = new Console();

    /**
     * A ClientConnect which handles the communication with the host.
     */
    ClientConnect connectToServer;

    /**
     * Used to store the latest message sent by the host.
     */
    Message message;

    /**
     * Handles the process of joining somebody elses game.
     * 
     * @param ipAddress The IP-address of the host computer that the online player is joining.
     */
    public GameJoin(String ipAddress) {
        try {
            this.connectToServer = new ClientConnect(ipAddress);
            this.message = connectToServer.readMessage();
        } catch (Exception e) {
            outputError(e);
        }
    }

    /**
     * Runs the main loop of playing Boomerang online as a joining player.
     * This loop will continue until the game either finishes or the host player
     * stops the game.
     */
    public void run(){
        while(!message.getMessage().contains("GAME ENDING")){
            try{
                console.outputMessage(message);
                if (message instanceof Choice){
                    Response response = console.getInput((Choice)message);
                    connectToServer.sendMessage(response);
                }
                message = connectToServer.readMessage();
            } catch (Exception e){
                outputError(e);
            }
        }
    }

    /**
     * Disconnects the player from the main host
     */
    public void disconnect(){
        this.connectToServer.disconnect();
    }
    
    /**
     * A function called when an exception has occured. Displays the
     * exception to the player.
     * 
     * @param e The exception to be displayed to the player
     */
    private void outputError(Exception e){
        console.outputMessage(new Information(e.getMessage()));
        if(e.getMessage().contains("Connection reset")){
            message = new Information("GAME ENDING");
        }
    }

}
