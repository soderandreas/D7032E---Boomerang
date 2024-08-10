package State;

import java.util.ArrayList;

import Player.*;
/**
 * Handles passing each player passing their last card to their neighbor
 * and choosing this as their catch card.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class FinalCardState extends GameState{
    /**
     * Runs the state. Goes through each player and chooses their catch card.
     */
    public GameState runState(){
        ArrayList<Player> allPlayers = currGameClient.getPlayers();

        for(Player currPlayer : allPlayers){
            Hand playerHand = currPlayer.getHand();
            playerHand.setCards(currPlayer.getHand().getNextCards());
            playerHand.setCatchCard(playerHand.getCards()[0]);
            if(currPlayer instanceof RealPlayer){
                ((RealPlayer)currPlayer).outputOwnDraft(true);
            }
        }

        RoundOverState newState = new RoundOverState();
        newState.setGameClient(this.currGameClient);
        return newState;
    }
}
