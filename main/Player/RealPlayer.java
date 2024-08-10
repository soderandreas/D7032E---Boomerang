package Player;

import java.util.ArrayList;
import java.util.HashMap;

import Cards.Card;
import Exception.BoomerangIOException;
import IO.*;
import Message.*;

/**
 * An extension of the player class that handles all real players. This includes the local
 * player (host) and all online players.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class RealPlayer extends Player {
    /**
     * The HandleIO used for input and output.
     */
    HandleIO handler;

    /**
     * Initiates a real player. All real player have a HandleIO class which
     * handles the input and output to and from the player.
     * 
     * @param ID The player ID.
     * @param playerScore The players scoresheet.
     * @param handler The players handler for input and output.
     */
    public RealPlayer(int ID, Scoresheet playerScore, HandleIO handler){
        super(ID, playerScore);
        this.handler = handler;
    }

    /**
     * Outputs the players drafted cards. Used every time the player makes a choice involving a card.
     * 
     * @param ending True if the ending of round. False otherwise.
     */
    public void outputOwnDraft(boolean ending){
        String output = "";

        if(ending){
            output = "Your draft this round:\n";
        } else {
            output = "Your current draft:\n";
        }
        output += this.playerHand.getInfoDraftComplete();

        this.output(new Information(output));
    }

    /**
     * Outputs the draft by another player to this player.
     * 
     * @param draftPlayer The players whose draft in being output.
     */
    public void outputDraft(Player draftPlayer){
        String output = "Player " + draftPlayer.getID() + " has drafted \n";
        output += draftPlayer.getHand().getInfoDraft();

        this.output(new Information(output));
        //System.out.println(output);
    }

    /**
     * Outputs that the player won the game.
     */
    public void outputWinner(){
        this.output(new Information("YOU WIN!"));
    }

    /**
     * Outputs that the player lost the game.
     */
    public void outputLoser(){
        this.output(new Information("YOU LOSE!"));
    }

    /**
     * Outputs the final score for all players.
     * 
     * @param allPlayers All the players that participated in the game
     */
    public void outputPlayersFinalscore(ArrayList<Player> allPlayers){
        String output = "Final score for each player:\n\n";
        String thisPlayerScoreOutput = "";
        String otherPlayersScoreOutput = "";

        for(Player currPlayer : allPlayers){
            int currPlayerID = currPlayer.getID();
            if(currPlayerID == this.getID()){ // this players score
                thisPlayerScoreOutput = "You (Player " + currPlayer.getID() + ") got " + this.getTotalScore() + " points!\n\n";
            } else {
                otherPlayersScoreOutput += "Player " + currPlayerID + " got " + currPlayer.getTotalScore() + " points. \n";
            }
        }

        this.output(new Information(output + thisPlayerScoreOutput + otherPlayersScoreOutput));
    }

    /**
     * The player makes a choice of what card from their current hand they
     * want to draft.
     */
    public void makeChoiceCard(){
        char[] currChoices = this.playerHand.getChoiceOfSites();
        String strCardsInHand = this.playerHand.getInfoHand() + "\nType the letter of the card to draft: ";

        char charPlayerChoice;
        String error = "";

        Choice choices = new Choice(strCardsInHand, currChoices);
        Response playerChoice = new Response(choices, " ");

        while(!(playerChoice.responseInChoice())){
            try{
                handler.outputMessage(new Information(error));
                handler.outputMessage(choices);
                playerChoice = handler.getInput(choices);
            } catch (Exception e){
                handler.outputError(e);
            }
            error = "INVALID INPUT, TRY AGAIN";
        }

        charPlayerChoice = playerChoice.getResponse().charAt(0);
        this.playerHand.selectCard(charPlayerChoice);
    }

    /**
     * The output from the last round.
     */
    public void endOfRoundOutput(){
        boolean countOptionals = this.askAboutOptionals();
        String roundScoreStr;

        try{
            playerScore.setRoundScore(this.playerHand, countOptionals);
            roundScoreStr = this.playerScore.getRoundScoreFormated(this.playerHand, countOptionals);
        } catch(Exception e) {
            handler.outputError(e);
            roundScoreStr = "Could not get the score for the round";
        }

        this.output(new Information(roundScoreStr));
    }

    /**
     * Ask the players if they want to count their optional points for
     * the round. They can either answer "Y" or "N".
     * @throws BoomerangIOException
     */
    boolean askAboutOptionals(){
        HashMap<String, Integer> whichOptionals = new HashMap<String, Integer>();

        for(Card selectCard : playerHand.getSelectedCards()){
            handleOptional(whichOptionals, selectCard.getOptional());
        }

        ArrayList<String> performedOptionals = this.playerScore.previousOptional;
        String optionalsStr = "";
        String mostOptionalStr = "";
        int mostOptional = 0;

        for (String i : whichOptionals.keySet()){
            if(!performedOptionals.contains(i)){
                optionalsStr += i + "(#" + whichOptionals.get(i) + "), ";
                if(whichOptionals.get(i) > mostOptional){
                    mostOptionalStr = i;
                    mostOptional = whichOptionals.get(i);
                }
            }
        }

        Information optionals = new Information("This round you have gathered the following new " + playerHand.getSelectedCards()[0].getOptionalName() + ": \n" + optionalsStr);

        char[] choices = {'Y', 'N'};

        Choice optionalsChoice = new Choice("Do you want keep " + mostOptionalStr + " (Y/N)", choices);

        Response playerResponse = new Response(optionalsChoice);

        this.output(optionals);
        while(!(playerResponse.responseInChoice())){
            this.output(optionalsChoice);
            playerResponse = this.input(optionalsChoice);
        }

        if(playerResponse.getResponse().equals("Y")){
            this.playerHand.setRoundOptional(mostOptionalStr);
            return true;
        } else if (playerResponse.getResponse().equals("N")){
            return false;
        } else {
            return false;
        }
    }

    /**
     * Handles input from the player.
     * 
     * @param choice The choices for the player.
     * @return A resonse for the player or a response with an empty
     * string if something goes wrong with in input.
     */
    private Response input(Choice choice){
        try{
            return handler.getInput(choice);
        } catch (Exception e) {
            handler.outputError(e);
        }

        return new Response(choice);
    }

    /**
     * Handles output from the player.
     * 
     * @param message The Message being output to the player.
     */
    private void output(Message message){
        try{
            handler.outputMessage(message);
        } catch (Exception e) {
            handler.outputError(e);
        }
    }
}
