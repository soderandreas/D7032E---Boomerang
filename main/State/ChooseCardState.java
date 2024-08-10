package State;

import java.util.ArrayList;

import Player.*;

/**
 * Extension of game state that handles letting players choose what card
 * to draft.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class ChooseCardState extends GameState{
    /**
     * Runs the state. Goes through each player and allows them to
     * choose a card to draft.
     */
    public GameState runState(){
        ArrayList<Player> allPlayers = currGameClient.getPlayers();     

        for(int i = 0; i < allPlayers.size(); i++){
            Player currPlayer = allPlayers.get(i);

            // Gives each player the cards they were passed by their neighbor
            if(currPlayer.cardsLeft() < this.currGameClient.getRules().getCardsPerPlayer()){
                currPlayer.getHand().setCards(currPlayer.getHand().getNextCards());
            }

            currPlayer.makeChoiceCard();
        }

        ViewDraftState newState = new ViewDraftState();
        newState.setGameClient(this.currGameClient);
        return newState;
    }
}