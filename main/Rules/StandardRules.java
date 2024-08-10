package Rules;

/**
 * An implementation of GameRules. The standard rules for playing Boomerang.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class StandardRules implements GameRules {
    final private boolean DIRECTION = true;
    final private int ROUNDS = 4;
    final private int NUM_CARDS = 28;
    final private int NUM_CARDS_PLAYER = 7;
    final private int MAX_NUM_PLAYERS = 4;
    final private int MIN_NUM_PLAYERS = 2;

    /**
     * Keeps track of the number of cards each player has.
     */
    private int cardsLeft = this.NUM_CARDS_PLAYER;

    /**
     * Gets the direction cards are passed. Cards are passed to the right for every card except
     * for the last one.
     */
    public boolean getDirection(){ 
        this.cardsLeft--;
        if(this.cardsLeft == 1){
            this.cardsLeft = this.NUM_CARDS_PLAYER;
            return !this.DIRECTION;
        }
        return this.DIRECTION;
    } 

    public int getNumRounds(){
        return this.ROUNDS;
    }

    public int getNumCardsTotal(){
        return this.NUM_CARDS;
    }

    public int getCardsPerPlayer(){
        return this.NUM_CARDS_PLAYER;
    }

    public int getMaxNumPlayers(){
        return this.MAX_NUM_PLAYERS;
    }

    public int getMinNumPlayers(){
        return this.MIN_NUM_PLAYERS;
    }
}
