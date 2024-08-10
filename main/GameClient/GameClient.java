package GameClient;

import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.nio.file.*;

import Cards.*;
import Rules.*;
import Player.*;
import Networking.*;
import Bot.*;
import Message.*;
import IO.*;
import Exception.*;

/**
 * The game client which will hold all the information about the game.
 * It is here all players are saved, the rules are saved here, all cards
 * are created here using the abstract card factory, the deck is saved here,
 * how many rounds are left is saved here, and what regions the players have 
 * visited.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class GameClient {
    /**
     * Keeps track of how many rounds are left of the game.
     */
    private int roundsLeft;

    /**
     * Which regions had been visisted last round.
     */
    private ArrayList<String> visitedRegions = new ArrayList<String>();

    /**
     * Which new regions have been visited this round.
     */
    private ArrayList<String> newRegions = new ArrayList<String>();

    /**
     * The abstract card factory used to create the deck.
     */
    private AbstractCardsFactory cardsFactory;

    /**
     * The type of scoresheet that all players will have.
     */
    private Scoresheet scoresheetType;

    /**
     * The rules that must be followed.
     */
    private GameRules rules;

    /**
     * The deck of cards used.
     */
    private ArrayList<Card> deck;

    /**
     * All the players.
     */
    private ArrayList<Player> players = new ArrayList<Player>();

    /**
     * A server that it is possible to connect to. Is initiated if there is
     * more than one real player.
     */
    private ServerConnect server;

    /**
     * The behavior for all bots.
     */
    private BotBehavior botBehavior;

    /**
     * The initiation of the game client. Sets the variables that will be important for the rest of the game.
     * 
     * @param numOfPlayers The number of real players.
     * @param numOfBots The number of bot players.
     * @param editionCard The edition of cards used for the abstract card factory.
     * @param editionScoresheet The edition of the scoresheet used for each player.
     * @param rules The rules used for the game.
     * @param botBehavior The behavior for all bot players.
     * @throws PlayerAmountException Thrown when there are too many or too few players.
     * @throws CardAmountException Thrown when the number of cards being created are too many or too few.
     * @throws BoomerangIOException Thrown when something goes wrong with the IO.
     * @throws LoadCardsException Thrown when something goes wrong with the ability to load in cards from the JSON files.
     * @throws ScoresheetException
     */
    public GameClient(int numOfPlayers, int numOfBots, AbstractCardsFactory editionCard, Scoresheet editionScoresheet, GameRules rules, BotBehavior botBehavior) throws PlayerAmountException, CardAmountException, BoomerangIOException, LoadCardsException, ScoresheetException{
        this.cardsFactory = editionCard;
        this.scoresheetType = editionScoresheet;
        this.rules = rules;
        this.deck = createDeck(cardsFactory.getFolderNameCards());
        this.botBehavior = botBehavior;

        this.roundsLeft = rules.getNumRounds();

        if (numOfPlayers > 1){
            this.server = new ServerConnect();
        }

        checkRules(numOfPlayers + numOfBots); // makes sure no rules are being broken
        createPlayers(numOfPlayers, numOfBots);
    }

    /**
     * Update which regions have been visisted. This is called at the
     * end of each round.
     */
    public void updateVisitedRegions(){
        for(String region : this.newRegions){
            this.visitedRegions.add(region);
        }
        this.newRegions = new ArrayList<String>();
    }

    /**
     * Gets the visited regions.
     * 
     * @return The visited regions.
     */
    public ArrayList<String> getVisitedRegions(){
        return this.visitedRegions;
    }

    /**
     * Shuffles the deck.
     */
    public void shuffleDeck(){
        Collections.shuffle(this.deck);
    }

    /**
     * Gets all players.
     * 
     * @return All players.
     */
    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    /**
     * Gets how many rounds are left.
     * 
     * @return
     */
    public int getRoundsLeft(){
        return this.roundsLeft;
    }

    /**
     * Decreases how many rounds are left.
     * Called at the end of a round.
     */
    public void decRounds(){
        this.roundsLeft--;
    }

    /**
     * Gets the rules for the game.
     * 
     * @return The rules of the game.
     */
    public GameRules getRules(){
        return this.rules;
    }

    /**
     * Gets the deck.
     * 
     * @return The deck.
     */
    public ArrayList<Card> getDeck(){
        return this.deck;
    }

    /**
     * Makes sure that no rules related to the number
     * of players or number of cards are being broken.
     * 
     * @param numPlayers The number of players.
     * @throws CardAmountException Thrown when the amount of
     * cards created does not match the amount in the rules.
     * @throws PlayerAmountException Thrown when the number of
     * of player exceed or subceed the limits set in the rules.
     */
    private void checkRules(int numPlayers) throws CardAmountException, PlayerAmountException{
        int maxPlayers = rules.getMaxNumPlayers();
        int minPlayers = rules.getMinNumPlayers();

        if(this.deck.size() != rules.getNumCardsTotal()){
            throw new CardAmountException("The amount of cards loaded does not match the amount of cards set in the rules!");
        }

        if(numPlayers > maxPlayers){
            throw new PlayerAmountException("To many players! The maximum is " + maxPlayers +  " but " + numPlayers + " players have been initialised.");
        } else if (numPlayers < minPlayers){
            throw new PlayerAmountException("To few players! The minimum is " + minPlayers + " but " + numPlayers + " players have been initialised.");
        }
    }

    /**
     * Creates the players for the game.
     * 
     * @param numOfPlayers The number of reals players to create.
     * @param numOfBots The number of bot players to create.
     * @throws BoomerangIOException Thrown if something goes with the IO.
     * @throws ScoresheetException Thrown if something goes wrong with creating Scoresheets for the players.
     */
    private void createPlayers(int numOfPlayers, int numOfBots) throws BoomerangIOException, ScoresheetException{
        int playerIDinc = 0;

        // create players
        for(int i = 0; i < numOfPlayers+numOfBots; i++){
            Scoresheet playerScoresheet = null;

            try{ // create the Scoresheet for the player
                playerScoresheet = this.scoresheetType.getClass().getDeclaredConstructor().newInstance();
                playerScoresheet.setVisitedRegions(this.visitedRegions, this.newRegions);
            } catch (Exception e){
                throw new ScoresheetException("The Scoresheet could not be created.", e);
            }

            if (i == 0) { // create local player
                this.players.add(new LocalPlayer(playerIDinc, playerScoresheet, new Console()));
            } else if (i < numOfPlayers) { // create online players
                OnlinePlayerConnect connectInfo = this.server.createConnection();
                connectInfo.sendMessage(new Information("You have connected to the host. You are player: " + (playerIDinc+1)));
                this.players.add(new OnlinePlayer(playerIDinc, playerScoresheet, connectInfo, new Remote(connectInfo)));
            } else { // create bots
                this.players.add(new BotPlayer(playerIDinc, playerScoresheet, botBehavior));
            }

            playerIDinc++;
        }
    }

    /**
     * Creates a new deck using the card factory.
     * 
     * @param deckType The type of deck being created (Australia, Europe, USA)
     * @return The new deck.
     * @throws BoomerangIOException Thrown when something goes wrong with the IO.
     * @throws LoadCardsException Thrown when the cards could not be loaded.
     */
    private ArrayList<Card> createDeck(String deckType) throws BoomerangIOException, LoadCardsException{
        Path dirName = Paths.get("resources/Card/" + deckType);
        ArrayList<Card> cards = new ArrayList<Card>();
        DirectoryStream<Path> stream;
        try{
            stream = Files.newDirectoryStream(dirName);
        } catch (IOException e) {
            throw new BoomerangIOException(e);
        }
        
        for (Path entry: stream) {
            Card card = this.cardsFactory.createCard(entry.getFileName().toString());
            cards.add(card);
        }

        return cards;
    }
}
