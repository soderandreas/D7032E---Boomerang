package Player;

import IO.Console;

/**
 * An extension of the RealPlayer class. Used for the player whose computer the game is being
 * played/hosted on
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class LocalPlayer extends RealPlayer {
    /**
     * Initiation of a local player. Sets the players ID, Scoresheet and HandleIO. 
     * 
     * @param ID The players ID.
     * @param playerScore The players Scoresheet.
     * @param console The players IO handler. Since this is local player that will be a Console.
     */
    public LocalPlayer(int ID, Scoresheet playerScore, Console console){
        super(ID, playerScore, console);
    }
}
