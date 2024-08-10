package GameClient;

import Cards.*;
import Rules.*;
import Player.*;
import Bot.*;
import Exception.PlayerAmountException;
import Exception.ScoresheetException;
import Exception.BoomerangIOException;
import Exception.CardAmountException;
import Exception.LoadCardsException;

/**
 * A Builder class that is used to create instances of the GameClient with
 * specific settings. Used to set the number of real and bot players, the rules
 * that will used, what edition of Boomerang is being played (affects what Cards
 * and what Scoresheet gets used), and the behavior of the bot players.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class GameClientBuilder {
    /**
     * The number of real and bot players, must be set.
     */
    private int numOfPlayers, numOfBots;

    /**
     * What rules are being used.
     */
    private GameRules rules;

    /**
     * What set of cards are used.
     */
    private AbstractCardsFactory editionCards;

    /**
     * What scoresheet is used by the players.
     */
    private Scoresheet editionScoresheet;

    /**
     * The behavior of the bot player.
     */
    private BotBehavior botBehavior;

    /**
     * Runs the reset() function when a new instance of the class is initiated.
     */
    public GameClientBuilder(){
        this.reset();
    }

    /**
     * Sets the number of players to 0, the edition of the Game to Boomerang
     * Australia, the rules to the standard rules, and the behavior of the bot
     * to the standard behavior.
     */
    public void reset(){
        this.numOfPlayers = 0;
        this.numOfBots = 0;
        this.editionCards = new AustraliaCardsFactory();
        this.editionScoresheet = new ScoresheetAustralia();
        this.rules = new StandardRules();
        this.botBehavior = new StandardBehavior();
    }

    /**
     * Sets the number of real players for the GameClient. Must be set.
     * 
     * @param numPlayers The number of real players.
     */
    public void setNumPlayers(int numPlayers){
        this.numOfPlayers = numPlayers;
    }

    /**
     * Sets the number of bot players for the GameClient. Must be set.
     * 
     * @param numBots The number of bot players.
     */
    public void setNumBots(int numBots){
        this.numOfBots = numBots;
    }

    /**
     * Sets the behavior of the bot players. Will be set to standard settings
     * if not otherwise specified.
     * 
     * @param botBehaviorChoice The choice of bot behavior. In version 1.0 only one
     * version of the bot behavior is implemented and that is for botBehaviorChoice = 1,
     * which sets it to standard settings.
     */
    public void setBotBehavior(int botBehaviorChoice){
        BotBehavior botBehavior;

        // Add more cases for other types of behavior
        switch(botBehaviorChoice){
            case 1:
                botBehavior = new StandardBehavior();
                break;
            default:
                botBehavior = new StandardBehavior();
        }

        this.botBehavior = botBehavior;
    }
    
    /**
     * Sets the edition of the game. Will be set to Boomerang Australia
     * if not otherwise specified.
     * 
     * @param edition The choice of edition. In version 1.0 only one edition is available and 
     * that is Boomerang Australia, which is chosen when edition = 1.
     */
    public void setEdition(int edition){
        AbstractCardsFactory factory;
        Scoresheet scoresheet;

        // Add more cases for other factories
        switch(edition){
            case 1:
                factory = new AustraliaCardsFactory();
                scoresheet = new ScoresheetAustralia();
                break;
            default:
                factory = new AustraliaCardsFactory();
                scoresheet = new ScoresheetAustralia();
        }

        this.editionCards = factory;
        this.editionScoresheet = scoresheet;
    }

    /**
     * Sets the rules of the game. Will be set to standard rules
     * if not otherwise specified.
     * 
     * @param set The choice of rules. In version 1.0 only one rule set has
     * been implemented. These are the standard rules for Boomerang, which are
     * chosen by set = 1.
     */
    public void setRules(int set){
        GameRules rules;

        switch(set){
            case 1:
                rules = new StandardRules();
                break;
            default:
                rules = new StandardRules();
        }

        this.rules = rules;
    }

    /**
     * Returns a GameClient with the settings specified using the setters.
     * 
     * @return A GameClient with the settings chosen with the setter functions.
     * @throws PlayerAmountException Thrown when the number of players chosen breaks the rules.
     * @throws CardAmountException Thrown when the number of cards loaded breaks the rules.
     * @throws BoomerangIOException Thrown when something goes wrong with the IO.
     * @throws ScoresheetException Thrown when the creation of scoresheets for the players failed.
     * @throws LoadCardException Thrown when loading a new card goes wrong.
     */
    public GameClient getClient() throws PlayerAmountException, CardAmountException, BoomerangIOException, LoadCardsException, ScoresheetException{
        if(numOfPlayers > 0 && numOfBots+numOfPlayers > 1){
            return new GameClient(this.numOfPlayers, this.numOfBots, this.editionCards, this.editionScoresheet, this.rules, this.botBehavior);
        } else {
            if(numOfPlayers <= 0){
                throw new PlayerAmountException("The number of real players must always be more than zero.");
            } else {
                throw new PlayerAmountException("The number of players must always be more than one.");
            }
        }
    }
}
