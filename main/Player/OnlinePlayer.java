package Player;

import Networking.OnlinePlayerConnect;
import IO.Remote;

/**
 * An extension of the RealPlayer class. Used for the players that are connected
 * are playing the game over the internet.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class OnlinePlayer extends RealPlayer {
    /**
     * The connection information for the online player.
     */
    private OnlinePlayerConnect connectInfo;

    /**
     * Initiation of a local player. Sets the players ID, Scoresheet, connection information and HandleIO. 
     * 
     * @param ID The ID of the player.
     * @param playerScore The players Scoresheet.
     * @param connectInfo The connection information for the player.
     * @param remote The Remote being used for input and output to the player.
     */
    public OnlinePlayer(int ID, Scoresheet playerScore, OnlinePlayerConnect connectInfo, Remote remote){
        super(ID, playerScore, remote);
        this.connectInfo = connectInfo;
    }

    /**
     * Gets the connection information for the player.
     * 
     * @return The players connection information.
     */
    public OnlinePlayerConnect getConnectInfo(){
        return this.connectInfo;
    }
}
