package Rules;

/**
 * An interface for creating the rules followed for the game. This includes
 * the direction that cards are passed, the number of rounds being played, 
 * the total number of cards, the number of cards per player, the maximum number
 * of player, and the minimum number of players.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public interface GameRules {
    /**
     * Gets the direction that cards are being passed.
     * 
     * @return The direction that cards are being passed.
     * False = left, True = right.
     */
    public boolean getDirection();

    /**
     * Gets the number of rounds for the game.
     * 
     * @return The number of rounds for the game
     */
    public int getNumRounds();

    /**
     * Gets the total number of cards in the deck.
     * 
     * @return The total number of cards in the deck.
     */
    public int getNumCardsTotal();

    /**
     * Gets the total number of cards per player.
     * 
     * @return The total number of cards per player.
     */
    public int getCardsPerPlayer();

    /**
     * Gets the maximum number of players.
     *  
     * @return The maximum number of players.
     */
    public int getMaxNumPlayers();

    /**
     * Gets the minimum number of players.
     * 
     * @return The minimum number of players.
     */
    public int getMinNumPlayers();
}