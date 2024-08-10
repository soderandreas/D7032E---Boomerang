package Player;

import java.util.ArrayList;
import java.util.Arrays;

import Cards.Card;

/**
 * The players hand. Keeps track of what cards the player has drafted,
 * what cards the player can draft, and what cards the player have been
 * passed by the other players.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class Hand {
    /**
     * Cards held by the player. These are the cards the player
     * can choose between.
     */
    protected ArrayList<Card> cards;

    /**
     * Cards passed from other players. These are the cards the player
     * can choose between the next time it is their turn.
     */
    protected ArrayList<Card> nextCards = null;

    /**
     * The players current throw card.
     */
    private Card throwCard;

    /**
     * The players current catch card.
     */
    Card catchCard;

    /**
     * The cards the player has selected to draft for the round.
     */
    ArrayList<Card> selectedCards = new ArrayList<Card>();

    /**
     * The optional action the player has chosen to score for the round.
     */
    String roundOptional = null;

    /**
     * Initiates a new hand with new cards.
     * 
     * @param newCards The cards for the players hand.
     */
    public Hand(Card[] newCards){
        setCards(newCards);
    }

    /**
     * Changes the cards the player has in hand.
     * 
     * @param newCards New cards for the hand.
     */
    public void setCards(Card[] newCards){
        this.cards = new ArrayList<Card>(Arrays.asList(newCards));
    }

    /**
     * Sets the cards the player will use the next time it is their turn.
     * 
     * @param nextCards The cards passed by another player.
     */
    public void setNextCards(Card[] nextCards){
        this.nextCards = new ArrayList<Card>(Arrays.asList(nextCards));
    }

    /**
     * Sets the players catch card.
     * 
     * @param card The players catch card.
     */
    public void setCatchCard(Card card){
        this.cards.remove(card);
        this.catchCard = card;
    }

    /**
     * Sets what optional action the player has chosen for the round.
     * 
     * @param optional The players optional action for the round.
     */
    public void setRoundOptional(String optional){ 
        this.roundOptional = optional;
    }

    /**
     * Gets the optional action the player has chosen for the round.
     * 
     * @return The players chosen optional action.
     */
    public String getRoundOptional(){
        return roundOptional;
    }

    /**
     * Gets the players catch card.
     * 
     * @return The players catch card.
     */
    public Card getCatchCard(){
        return this.catchCard;
    }

    /**
     * Gets the players throw card.
     * 
     * @return The players throw card.
     */
    public Card getThrowCard(){
        return this.throwCard;
    }

    /**
     * Gets all the players selected cards, this includes the throw card and catch card.
     * 
     * @return All the players selected cards.
     */
    public Card[] getSelectedCards(){
        ArrayList<Card> allSelectedCards = new ArrayList<Card>(selectedCards);
        if(throwCard != null){
            allSelectedCards.add(throwCard);
        }
        if(catchCard != null){
            allSelectedCards.add(catchCard);
        }
        return allSelectedCards.toArray(new Card[allSelectedCards.size()]);
    }

    /**
     * Gets all the cards the player can choose from.
     * 
     * @return All the cards the player can choose from.
     */
    public Card[] getCards(){
        return this.cards.toArray(new Card[this.cards.size()]);
    }

    /**
     * Gets the cards the cards the player has been passed by another player.
     * 
     * @return The cards the player have been passed by another player.
     */
    public Card[] getNextCards(){
        return this.nextCards.toArray(new Card[this.nextCards.size()]);
    }

    /**
     * Gets a string with all the cards the player can choose from.
     * 
     * @return A string with all the cards the player can choose from.
     */
    public String getInfoHand(){
        String output = "YOUR CURRENT HAND: \n";
        
        for(Card currCard : this.cards){
            output += currCard.getAllInfo() + "\n";
        }

        return output;
    }

    /**
     * Gets a string with all the cards the player has drafted excluding the throw card.
     * Used to allow players to show each other their drafts.
     * 
     * @return A string with all the cards the player has drafted excluding the throw card.
     */
    public String getInfoDraft(){
        String output = "Drafted cards: \n";
        for(Card currCard : this.selectedCards){
            output += currCard.getAllInfo() + "\n";
        }

        return output;
    }

    /**
     * Gets a string with all the cards the player has drafted. Used to show a player
     * what cards they have drafted, including the throw card.
     * 
     * @return A String with all the cards the player has drafted.
     */
    public String getInfoDraftComplete(){
        String output = "";
        for(Card currCard : this.getSelectedCards()){
            output += currCard.getAllInfo() + "\n";
        }

        return output;
    }

    /**
     * Gets an array of characters for the cards the player currently has on hand.
     * 
     * @return An array of characters for the cards the player currently has on hand
     */
    public char[] getChoiceOfSites(){
        char[] choices = new char[this.cards.size()];
        for(int i = 0; i < this.cards.size(); i++){
            choices[i] = this.cards.get(i).getSite();
        }
        return choices;
    }

    /**
     * Gets an array of numbers for the cards the player currently has on hand.
     * 
     * @return An array of numbers for the cards the player currently has on hand.
     */
    public int[] getChoiceOfNumber(){
        int[] choices = new int[this.cards.size()];
        for(int i = 0; i < this.cards.size(); i++){
            choices[i] = this.cards.get(i).getNumber();
        }
        return choices;
    }

    /**
     * Select a Card to draft from the players hand based on a character (site).
     * 
     * @param site The site of the card the player wants to choose.
     */
    public void selectCard(char site){
        if(this.throwCard == null){
            selectThrowCard(site);
        } else {
            Card throwCard = this.getCardFromSite(site);
            this.selectedCards.add(throwCard);
            this.cards.remove(throwCard);
        }
    }

    /**
     * Select a Card to draft from the players hand based on a Card.
     * 
     * @param card The Card the player wants to choose.
     */
    public void selectCard(Card card){
        if(this.throwCard == null){
            selectThrowCard(card);
        } else {
            this.selectedCards.add(card);
            this.cards.remove(card);
        }
    }

    /**
     * Selects a throw card from the players hand based on a character (site).
     * 
     * @param site The character used to find what card the player wants to choose as their throw card.
     */
    private void selectThrowCard(char site){
        Card throwCard = this.getCardFromSite(site);
        this.cards.remove(throwCard);
        this.throwCard = throwCard;
    }

    /**
     * Selects a throw card from the players hand based on a Card.
     * 
     * @param card The Card the player wants as their throw card.
     */
    private void selectThrowCard(Card card){
        this.cards.remove(card);
        this.throwCard = card;
    }

    /**
     * Resets the players throw card for the start of a new round.
     */
    void resetThrowCard(){
        this.throwCard = null;
    }

    /**
     * Gets a card based on a character (site)
     * 
     * @param site The site of the requested card.
     * @return If the card can be found it is returned. Otherwise null is returned.
     */
    private Card getCardFromSite(char site){
        for(Card currCard : this.cards){
            if(currCard.getSite() == site){
                return currCard;
            }
        }
        return null;
    }
}
