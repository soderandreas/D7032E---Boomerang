package State;

import java.util.ArrayList;
import Player.*;

/**
 * Handles showing the players what cards they and other players have drafted.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class ViewDraftState extends GameState {
    /**
     * Runs the state. Outputs the drafted cards by each player and shows
     * all their own cards (including throw card).
     */
    public GameState runState(){
        ArrayList<Player> allPlayers = currGameClient.getPlayers();

        for(Player currPlayer : allPlayers){
            if(currPlayer instanceof RealPlayer){
                for(Player draftPlayer : allPlayers){
                    ((RealPlayer)currPlayer).outputDraft(draftPlayer);
                }

                ((RealPlayer)currPlayer).outputOwnDraft(false);
            }
        }

        PassCardsState newState = new PassCardsState();
        newState.setGameClient(this.currGameClient);
        return newState;
    }
}
