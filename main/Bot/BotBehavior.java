package Bot;

import Cards.Card;
import Message.*;

/**
 * An interface for the behavior a bot player will follow.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public interface BotBehavior {
    /**
     * Handles the choice of throw card for the bot.
     * 
     * @param choices The cards that the bot player can choose its throw card from
     * @return The card the bot has chosen as its throw card.
     */
    public Card chooseThrowCard(Card[] choices);

    /**
     * Handles the choice of drafting a card for the bot
     * 
     * @param choices The cards that the bot player can choose to draft.
     * @return The card that the bot has chosen to draft.
     */
    public Card chooseCard(Card[] choices);

    /**
     * Handles the bots response to if it wants to score optional points each round.
     * 
     * @param Question The choice to count the optional score.
     * @return Returns the bots reponse to the question if it wants to score the optional points.
     */
    public Response scoreOptional(Choice Question);
}
