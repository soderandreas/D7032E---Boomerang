package Player;

import java.util.ArrayList;

import Exception.OptionalScoreException;
import Cards.Card;

/**
 * A Scoresheet used by each player to keep track of their score.
 * Is extended for different versions of the game with different scouring mechanisms.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public abstract class Scoresheet {
    /**
     * The players total score for the game.
     */
    private int totalScore;

    /**
     * The total throw catch score for all rounds.
     */
    private int throwCatchScore;

    /**
     * The throw catch score for the round.
     */
    int throwCatchScoreRound;

    /**
     * The optional score for the round.
     */
    int optionalScore = 0;

    /**
     * An ArrayList of all visited regions for all players.
     */
    private ArrayList<String> visitedRegionsGlobal;

    /**
     * An ArrayList of all regions the new regions the player has visited this round.
     */
    private ArrayList<String> newVisitedRegions = new ArrayList<String>();

    /**
     * Previously visited sites.
     */
    ArrayList<Character> previousSite = new ArrayList<Character>();

    /**
     * Previously choosen optional actions.
     */
    ArrayList<String> previousOptional = new ArrayList<String>();

    /**
     * Sets the score for the round.
     * 
     * @param hand The players Hand.
     * @param countOptional If the player should count optional actions.
     * @throws OptionalScoreException Thrown if player tries to count optional twice.
     */
    public abstract void setRoundScore(Hand hand, boolean countOptional) throws OptionalScoreException;

    /**
     * Gets a formated string with the score for the round.
     * 
     * @param hand The players hand.
     * @param countOptional If the player should count optional actions.
     * @return A formated string with the score for the round.
     * @throws OptionalScoreException Thrown if player tries to count optional twice.
     */
    public abstract String getRoundScoreFormated(Hand hand, boolean countOptional) throws OptionalScoreException;

    /**
     * Adds score to the total score for the game.
     * 
     * @param addedScore Score to be added to total.
     */
    public void addTotalScore(int addedScore){
        this.totalScore += addedScore;
    }

    /**
     * Gets the players total score for the game.
     * 
     * @return The players total score for the game.
     */
    public int getTotalScore(){
        return this.totalScore;
    }

    /**
     * Gets the players total throw catch score.
     * 
     * @return The players total throw catch score.
     */
    public int getThrowCatchScore(){
        return this.throwCatchScore;
    }

    /**
     * Gets the players throw catch score for the round.
     * 
     * @return The players throw catch score for the round.
     */
    public int getThrowCatchScoreRound(){
        return this.throwCatchScoreRound;
    }

    /**
     * Adds score to the total throw catch score for all rounds.
     * 
     * @param addedScore The score to be added.
     */
    public void addThrowCatchScore(int addedScore){
        this.throwCatchScore += addedScore;
    }

    /**
     * Add a site that has been visited.
     * 
     * @param site The character that represents the site that has been visited.
     */
    public void addPreviousSite(char site){
        this.previousSite.add(site);
    }

    /**
     * Sets the ArrayLists used to keep track of what regions the players have explored and what regions are new for the round.
     * 
     * @param visitedRegions ArrayList for all regions that have been visited in previous rounds.
     * @param newRegions ArrayList for all new regions that have been visited this round.
     */
    public void setVisitedRegions(ArrayList<String> visitedRegions, ArrayList<String> newRegions){
        this.visitedRegionsGlobal = visitedRegions;
        this.newVisitedRegions = newRegions;
    }

    /**
     * Adds a new region to the list of all new regions that have been visited this round.
     * 
     * @param completedRegion The region the player has completed.
     */
    public void addNewRegion(String completedRegion){
        if(!this.newVisitedRegions.contains(completedRegion)){
            newVisitedRegions.add(completedRegion);
        }
    }

    /**
     * Gets the list of what regions players have been visited. 
     * 
     * @return The list of what regions players have visited.
     */
    public ArrayList<String> getVisitedRegions(){
        return this.visitedRegionsGlobal;
    }

    /**
     * Gets the optional score for the player.
     * 
     * @return The players optional score.
     */
    public int getOptionalScore(){
        return this.optionalScore;
    }

    /**
     * Counts the throw catch score for the player.
     * 
     * @param throwCard The players throw card.
     * @param finalCard The players final (catch) card.
     */
    void countThrowCatch(Card throwCard, Card finalCard){
        int newThrowCatch = Math.abs(throwCard.getNumber() - finalCard.getNumber());
        this.throwCatchScoreRound = newThrowCatch;
        this.addThrowCatchScore(newThrowCatch);
    }
}
