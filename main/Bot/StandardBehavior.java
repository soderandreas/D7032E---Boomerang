package Bot;

import Cards.Card;
import Message.*;

import java.util.Random;

/**
 * The standard behavior of a bot player. If no choice is made for the bots
 * behavior this is the behavior that will be followed.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class StandardBehavior implements BotBehavior {
    /**
     * Handles how a throw card is chosen in standard behavior. The card will be
     * chosen to give the highest possible points, meaning it will choose the card
     * closest to 1 or 7.
     * 
     * @param choices The cards that the bot has to choose its throw card from.
     * @return The card that could give the highest throw-catch score.
     */
    public Card chooseThrowCard(Card[] choices){
        Card currentBest = choices[0];

        for(int i = 1; i < choices.length; i++){
            Card currentChoice = choices[i];
            if(currentBest.getNumber() - 1 > currentChoice.getNumber() - 1){
                currentBest = currentChoice;
            } else if (7 - currentChoice.getNumber() > 7 - currentChoice.getNumber()) {
                currentBest = currentChoice;
            }
        }
        
        return currentBest;
    }

    /**
     * Handles how the bot will choose what card to draft. For this standard behavior
     * the card will be chosen at random.
     * 
     * @param choices The cards that the bot has to choose a card to draft from.
     * @return The random card the bot has chosen to draft.
     */
    public Card chooseCard(Card[] choices){
        Random rnd = new Random();
		Card choice = choices[rnd.nextInt(choices.length)];
        return choice;
    }

    /**
     * Handles how the bot player reponds to the question if it wants to
     * score its optinal points. For this standard behavior it will always 
     * respond not to count the optional points.
     * 
     * @param Question A Choice Message the contains the question about if the 
     * player wants to score its optional points.
     * @return A Response Message with the response "N".
     */
    public Response scoreOptional(Choice Question){ 
        return new Response(Question, "N");
    }
}
