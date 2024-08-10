package State;

import java.util.ArrayList;

import Player.*;

/**
 * Handles passing cards between the players.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class PassCardsState extends GameState {
    /**
     * Runs the state. Gets the direction that cards are moved in from the
     * rules.
     */
    public GameState runState(){
        ArrayList<Player> allPlayers = currGameClient.getPlayers();
        boolean direction = currGameClient.getRules().getDirection();

        for(int i = 0; i < allPlayers.size(); i++){
            Player currPlayer = allPlayers.get(i);
            Player passPlayer = null;

            if (direction){ // true if passing cards right
                if(i != allPlayers.size()-1){
                    passPlayer = allPlayers.get(i+1);
                } else {
                    passPlayer = allPlayers.get(0);
                }
            } else {
                if(i != 0){
                    passPlayer = allPlayers.get(i-1);
                } else {
                    passPlayer = allPlayers.get(allPlayers.size()-1);
                }
            }

            passPlayer.getHand().setNextCards(currPlayer.getHand().getCards());
        }

        GameState newState;
        
        if(allPlayers.get(0).getHand().getCards().length > 1){
            newState = new ChooseCardState();
        } else {
            newState = new FinalCardState();
        }

        newState.setGameClient(this.currGameClient);
        return newState;
    }
}
