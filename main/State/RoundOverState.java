package State;

import java.util.ArrayList;

import Player.*;
import Exception.OptionalScoreException;

/**
 * Handles end of round options.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class RoundOverState extends GameState  {
    /**
     * Runs the state. Handles asking players if they want to score their optional actions and updating visited regions.
     */
    public GameState runState() throws OptionalScoreException{
        int round = this.currGameClient.getRoundsLeft();
        ArrayList<Player> allPlayers = currGameClient.getPlayers();
        GameState newState;

        if(round > 1) {
            this.currGameClient.decRounds();
            newState = new ShuffleAndDealState();
        } else {
            newState = new GameOverState(); 
        } 

        // Ask about scoring activites
        for(Player currPlayer : allPlayers) {
            if(currPlayer instanceof RealPlayer){
                ((RealPlayer)currPlayer).endOfRoundOutput();
            } else if (currPlayer instanceof BotPlayer){
                ((BotPlayer)currPlayer).endOfRound();
            }
            currPlayer.endOfRoundReset();
        }

        this.currGameClient.updateVisitedRegions();

        newState.setGameClient(this.currGameClient);
        return newState;
    }
}
