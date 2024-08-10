package Player;

import java.util.Arrays;
import java.util.HashMap;

import Cards.Card;
import Exception.OptionalScoreException;
import Bot.*;
import Message.*;

/**
 * An extension of the player class that handles bot players. 
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class BotPlayer extends Player {
    /**
     * The behavior that the bot player will follow.
     */
    private BotBehavior behavior;

    /**
     * Initiates a bot player. Sets the players ID, Scoresheet, and behavior.
     * 
     * @param ID The players ID.
     * @param playerScore The players Scoresheet.
     * @param behavior The behavior that the bot will follow.
     */
    public BotPlayer(int ID, Scoresheet playerScore, BotBehavior behavior){
        super(ID, playerScore);
        this.behavior = behavior;
    }

    /**
     * The bot player chooses a card.
     */
    public void makeChoiceCard(){
        Card botChoice = null;

        if(this.playerHand.getThrowCard() == null){    
            botChoice = behavior.chooseThrowCard(this.playerHand.getCards());
        } else {
            botChoice = behavior.chooseCard(this.playerHand.getCards());
        }
        this.playerHand.selectCard(botChoice);
    }

    /**
     * Handles end of round things like asking the player if they want to count their optinal score and
     * sets the score for the round.
     * 
     * @throws OptionalScoreException Thrown if the player tries to score the same optional action more than once.
     */
    public void endOfRound() throws OptionalScoreException{
        boolean countOptionals = this.askAboutOptionals();
        playerScore.setRoundScore(this.playerHand, countOptionals);
    }

    /**
     * Asks the bot if it wants to count its optional score for the round.
     */
    boolean askAboutOptionals(){
        HashMap<String, Integer> whichRegions = new HashMap<String, Integer>();

        for(Card selectCard : playerHand.getSelectedCards()){
            handleOptional(whichRegions, selectCard.getOptional());
        }

        String[] performedOptionals = this.getOptionals();
        String mostOptionalStr = "";
        int mostOptional = 0;

        for (String i : whichRegions.keySet()){
            if(!Arrays.stream(performedOptionals).anyMatch(i::equals)){
                if(whichRegions.get(i) > mostOptional){
                    mostOptionalStr = i;
                    mostOptional = whichRegions.get(i);
                }
            }
        }

        char[] choices = {'Y', 'N'};

        Choice optionalsChoice = new Choice("Do you want keep " + mostOptionalStr + " (Y/N)", choices);

        Response playerResponse = behavior.scoreOptional(optionalsChoice);
        if(playerResponse.getResponse() == "Y"){
            this.playerHand.setRoundOptional(mostOptionalStr);
            return true;
        } else if (playerResponse.getResponse() == "N"){
            return false;
        } else {
            return false;
        }
    }
}
