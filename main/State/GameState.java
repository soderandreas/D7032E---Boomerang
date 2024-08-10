package State;

import GameClient.GameClient;
import Exception.OptionalScoreException;

/**
 * An abstract class representing a state that the game can find itself in.
 * Used to implement a state pattern.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public abstract class GameState {
    /**
     * The current game client being used.
     */
    protected GameClient currGameClient;

    /**
     * Sets the GameClient being used.
     * 
     * @param gameClient The GameClient being used.
     */
    public void setGameClient(GameClient gameClient){
        this.currGameClient = gameClient;
    }

    /**
     * Runs the current game state.
     * 
     * @return The next game state.
     * @throws OptionalScoreException If a player tries to score the same optional action twice.
     */
    public abstract GameState runState() throws OptionalScoreException;
}
