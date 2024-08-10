package Player;

import java.util.ArrayList;
import java.util.HashMap;

import Cards.Card;

/**
 * The framework for all types of players,
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public abstract class Player {
    /**
     * The ID of the player. Is unique.
     */
    private int playerID;

    /**
     * A list of all the optional activities the player has performed.
     */
    ArrayList<String> performedOptionals = new ArrayList<String>();

    /**
     * The players Scoresheet. Checks track of the players score.
     */
    Scoresheet playerScore;

    /**
     * The players Hand. Holds the players cards.
     */
    Hand playerHand;

    /**
     * Initiates a new player. Sets the ID of the new player and their scoresheet.
     * 
     * @param ID The players ID.
     * @param playerScore The players Scoresheet.
     */
    public Player(int ID, Scoresheet playerScore){
        this.playerID = ID;
        this.playerScore = playerScore;
    }

    /**
     * Gets the players ID.
     * 
     * @return The players ID.
     */
    public int getID(){
        return this.playerID;
    }

    /**
     * Gets the players total score.
     * 
     * @return The players total score.
     */
    public int getTotalScore(){
        return this.playerScore.getTotalScore();
    }

    /**
     * Gets the players throw-catch score.
     * 
     * @return The players throw-catch score.
     */
    public int getThrowCatchScore(){
        return this.playerScore.getThrowCatchScore();
    }

    /**
     * Sets the players new hand.
     * 
     * @param hand The players new hand.
     */
    public void setNewHand(Card[] hand){
        this.playerHand = new Hand(hand);
    }

    /**
     * Gets the players current hand.
     * 
     * @return The players current hand.
     */
    public Hand getHand(){
        return this.playerHand;
    }

    /**
     * Gets all optional activities the player has perfomed.
     * 
     * @return An array of all optinal actions the player has performed.
     */
    public String[] getOptionals(){
        return performedOptionals.toArray(new String[performedOptionals.size()]);
    }

    /**
     * Adds a new optional action the user has perfomed.
     * 
     * @param optional A new optional action taken by the user.
     */
    public void addOptional(String optional){
        performedOptionals.add(optional);
    }

    /**
     * Returns the number of cards left in the players hand.
     * 
     * @return Number of cards left in Hand.
     */
    public int cardsLeft(){
        return this.playerHand.cards.size();
    }

    /**
     * Resets the chosen optional action, selected cards, cards from other players,
     * and the players catch and throw cards for the start of a new round.
     */
    public void endOfRoundReset(){
        playerHand.roundOptional = null;
        playerHand.selectedCards = new ArrayList<Card>();
        playerHand.nextCards = null;
        playerHand.catchCard = null;
        playerHand.resetThrowCard();
        playerScore.throwCatchScoreRound = 0;
    }

    /**
     * Asks a player if they want to score their optional action for the round.
     * 
     * @return True = player wants to score optional action. False = player does not want to score
     * optional action.
     */
    abstract boolean askAboutOptionals();
    
    /**
     * The player makes a choice about what card to keep
     */
    public abstract void makeChoiceCard();

    /**
     * Handles counting which optionals the player has visited and how many times each was visited.
     * 
     * @param whichOptional A HashMap of what optional and how many of each the player
     * collected.
     * @param optional The optional being checked.
     */
    void handleOptional(HashMap<String, Integer> whichOptional, String optional){
        if(!optional.equals("")){
            if(whichOptional.containsKey(optional)){
                whichOptional.put(optional, whichOptional.get(optional)+1);
            } else {
                whichOptional.put(optional, 1);
            }
        }
    }
}
