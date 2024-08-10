package State;

import java.util.ArrayList;

import Cards.*;
import Player.*;

/**
 * Handles shuffeling the deck and dealing the cards to the players.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class ShuffleAndDealState extends GameState {
    /**
     * Runs the State. Shuffles the cards of the current deck and deals the newly shuffled
     * cards to the players.
     */
    public GameState runState(){
        ArrayList<Player> allPlayers = currGameClient.getPlayers();
        int numOfCards = this.currGameClient.getRules().getCardsPerPlayer();

        // Shuffle cards
        this.currGameClient.shuffleDeck();
        ArrayList<Card> deck = this.currGameClient.getDeck();
        Card[] deckArr = deck.toArray(new Card[deck.size()]);

        // Deal cards 
        int posInDeck = 0;
        for(Player currPlayer : allPlayers){
            Card[] cardsForHand = new Card[numOfCards];
            System.arraycopy(deckArr, posInDeck, cardsForHand, 0, numOfCards);
            currPlayer.setNewHand(cardsForHand);

            posInDeck += numOfCards;
        }

        GameState newState = new ChooseCardState();
        newState.setGameClient(this.currGameClient);
        return newState;
    }
}
