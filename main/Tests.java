import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.nio.file.*;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import Rules.*;
import Cards.*;
import Bot.*;
import GameClient.*;
import Player.*;
import State.*;
import Message.*;

public class Tests {

    AustraliaCardsFactory cardsFactory = new AustraliaCardsFactory();
    StandardRules rules = new StandardRules();
    BotBehavior botBehavior = new StandardBehavior();

    Card[] unShuffledCard;
    Card[] shuffledCards;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    GameClient gameClient;

    @Before
    public void init() {
        Path dirName = Paths.get("resources/Card/AustraliaCards");
        ArrayList<Card> cards = new ArrayList<Card>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirName)) {
            for (Path entry: stream) {
                Card card = this.cardsFactory.createCard(entry.getFileName().toString());
                cards.add(card);
            }
        } catch (Exception e){
            System.out.println("ERROR");
        }

        unShuffledCard = cards.toArray(new Card[cards.size()]);

        Collections.shuffle(cards);

        shuffledCards = cards.toArray(new Card[cards.size()]);
    }

    /**
     * Tests the lower limit of rule 1
     */
    @Test
    public void test1lower(){
        GameClientBuilder gameBuilder = new GameClientBuilder();
        gameBuilder.setNumPlayers(1);
        gameBuilder.setNumBots(0);
        gameBuilder.setEdition(1);
        gameBuilder.setRules(1); // normal rules
        gameBuilder.setBotBehavior(1);

        try{
            gameClient = gameBuilder.getClient();
        } catch (Exception e){
            assertEquals(e.getMessage(), "The number of players must always be more than one.");
        }
    }

    /**
     * Tests the upper limit of rule 1
     */
    @Test
    public void test1higher(){
        GameClientBuilder gameBuilder = new GameClientBuilder();
        gameBuilder.setNumPlayers(1);
        gameBuilder.setNumBots(4);
        gameBuilder.setEdition(1);
        gameBuilder.setRules(1); // normal rules
        gameBuilder.setBotBehavior(1);

        try{
            gameClient = gameBuilder.getClient();
        } catch (Exception e){
            assertEquals(e.getMessage(), "To many players! The maximum is 4 but 5 players have been initialised.");
        }
    }

    /**
     * Tests rule 1 working as intended
     */
    @Test
    public void test1working() throws Exception{
        GameClientBuilder gameBuilder1 = new GameClientBuilder();
        gameBuilder1.setNumPlayers(1);
        gameBuilder1.setNumBots(1);
        gameBuilder1.setEdition(1);
        gameBuilder1.setRules(1); // normal rules
        gameBuilder1.setBotBehavior(1);

        GameClientBuilder gameBuilder2 = new GameClientBuilder();
        gameBuilder2.setNumPlayers(1);
        gameBuilder2.setNumBots(2);
        gameBuilder2.setEdition(1);
        gameBuilder2.setRules(1); // normal rules
        gameBuilder2.setBotBehavior(1);

        GameClientBuilder gameBuilder3 = new GameClientBuilder();
        gameBuilder3.setNumPlayers(1);
        gameBuilder3.setNumBots(3);
        gameBuilder3.setEdition(1);
        gameBuilder3.setRules(1); // normal rules
        gameBuilder3.setBotBehavior(1);

        GameClient gameClient1 = gameBuilder1.getClient();
        GameClient gameClient2 = gameBuilder2.getClient();
        GameClient gameClient3 = gameBuilder3.getClient();

        ArrayList<Player> players1 = gameClient1.getPlayers();
        ArrayList<Player> players2 = gameClient2.getPlayers();
        ArrayList<Player> players3 = gameClient3.getPlayers();

        assertEquals(players1.size(), 2);
        assertEquals(players2.size(), 3);
        assertEquals(players3.size(), 4);
    }

    /**
     * Test 2 working
     */
    @Test
    public void test2() throws Exception{
        GameClientBuilder gameBuilder = new GameClientBuilder();
        gameBuilder.setNumPlayers(1);
        gameBuilder.setNumBots(1);
        gameBuilder.setEdition(1);
        gameBuilder.setRules(1); // normal rules
        gameBuilder.setBotBehavior(1);

        GameClient gameClient = gameBuilder.getClient();

        ArrayList<Card> deck = gameClient.getDeck();

        assertEquals(deck.get(0).getName(), "Barossa Valley");
        assertEquals(((AustraliaCard)deck.get(0)).getAnimal(), "Koalas");
        assertEquals(((AustraliaCard)deck.get(0)).getOptional(), "Bushwalking");
        assertEquals(((AustraliaCard)deck.get(0)).getNumber(), 3);
        assertEquals(((AustraliaCard)deck.get(0)).getRegion(), "South Australia");
        assertEquals(((AustraliaCard)deck.get(0)).getSite(), 'M');
        assertEquals(((AustraliaCard)deck.get(3)).getCollection(), "Souvenirs");
    }

    /**
     * Test 3 and 4 working
     */
    @Test
    public void test3and4() throws Exception{ // create a deck and compare to the deck used in the gameClient that is shuffled
        GameClientBuilder gameBuilder = new GameClientBuilder();
        gameBuilder.setNumPlayers(1);
        gameBuilder.setNumBots(1);
        gameBuilder.setEdition(1);
        gameBuilder.setRules(1); // normal rules
        gameBuilder.setBotBehavior(1);

        GameClient gameClient = gameBuilder.getClient();

        ArrayList<Card> deck = gameClient.getDeck();

        GameState gameState = new ShuffleAndDealState();
        gameState.setGameClient(gameClient);
        gameState.runState();

        assertNotEquals(deck, unShuffledCard);

        ArrayList<Player> allPlayers = gameClient.getPlayers();

        for(Player currPlayer : allPlayers){
            assertEquals(currPlayer.cardsLeft(), 7);
        }
    }

    /**
     * Test 5, 6, 7, 8 and 9 working
     */
    @Test
    public void test5and6and7and8and9() throws Exception{ 

        /**
         * Implementation of BotBehavior that is completely predicable, always chooses the first card in its hand
         */
        class PredictableBot implements BotBehavior{
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
        
            public Card chooseCard(Card[] choices){
                return choices[0];
            }
        
            public Response scoreOptional(Choice Question){
                return new Response(Question, "N");
            }
        }

        System.setOut(new PrintStream(outputStreamCaptor));

        GameClientBuilder gameBuilder = new GameClientBuilder();
        gameBuilder.setNumPlayers(1);
        gameBuilder.setNumBots(1);
        gameBuilder.setEdition(1);
        gameBuilder.setRules(1); // normal rules
        gameBuilder.setBotBehavior(1);

        // input 
        String input = "M" + System.getProperty("line.separator") + "N" + System.getProperty("line.separator") + "S" + System.getProperty("line.separator") + "H" + System.getProperty("line.separator") + "T" + System.getProperty("line.separator") + "P";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        GameClient gameClient = new GameClient(1, 1, new AustraliaCardsFactory(), new ScoresheetAustralia(), new StandardRules(), new PredictableBot());

        Player realPlayer = gameClient.getPlayers().get(0);
        Player botPlayer = gameClient.getPlayers().get(1);

        GameState gameState = new ShuffleAndDealState();
        gameState.setGameClient(gameClient);
        gameState = gameState.runState();

        Card[] cardsForHand = new Card[7];
        System.arraycopy(unShuffledCard, 0, cardsForHand, 0, 7);
        realPlayer.setNewHand(cardsForHand);

        System.arraycopy(unShuffledCard, 7, cardsForHand, 0, 7);
        botPlayer.setNewHand(cardsForHand);

        gameState = gameState.runState();

        Card realPlayerThrowCard = realPlayer.getHand().getThrowCard();
        Card botPlayerThrowCard = botPlayer.getHand().getThrowCard();

        // Check that throw cards have been selected
        assertEquals(realPlayerThrowCard.getName(), "Barossa Valley"); // The real player chooses its first card (Barossa Valley)
        assertEquals(botPlayerThrowCard.getName(), "Margaret River"); // The bot chooses the card with the highest/lowest throw value (Margaret Valley)

        int i = 0;
        String realPlayerOutput = "Player 0 has drafted \nDrafted cards: ";
        String botPlayerOutput = "Player 1 has drafted \nDrafted cards: ";

        gameState = gameState.runState(); // ViewDraftState

        while(true){ // Runs step 6 and 7 on loop until there is only one card left, showing that step 6, 7 and 8 works
            Card[] realPlayerCardsFirstRound = realPlayer.getHand().getCards();   
            Card[] botPlayerCardsFirstRound = botPlayer.getHand().getCards();  
            
            gameState = gameState.runState(); // PassCardsState

            if(gameState instanceof FinalCardState){ // When next state is FinalCard
                break;
            }

            Card[] realPlayerCardsSecondRound = realPlayer.getHand().getNextCards();   
            Card[] botPlayerCardsSecondRound = botPlayer.getHand().getNextCards();

            // Check that remaining cards have been passed to next player
            assertArrayEquals(realPlayerCardsFirstRound, botPlayerCardsSecondRound);
            assertArrayEquals(botPlayerCardsFirstRound, realPlayerCardsSecondRound);

            gameState = gameState.runState(); // ChooseCardState
            gameState = gameState.runState(); // ViewDraftState

            Card[] realPlayerSelectedCards = realPlayer.getHand().getSelectedCards();
            Card[] botPlayerSelectedCards = botPlayer.getHand().getSelectedCards();
            String output = outputStreamCaptor.toString().trim();

            // Check that the draft from the players is shown to players
            realPlayerOutput += "\nName: " + ((AustraliaCard)realPlayerSelectedCards[i]).getName() + ", Region: " + ((AustraliaCard)realPlayerSelectedCards[i]).getRegion() + ", Collection: " + ((AustraliaCard)realPlayerSelectedCards[i]).getCollection() + ", Animal: " + ((AustraliaCard)realPlayerSelectedCards[i]).getAnimal() + ", Activity: " + ((AustraliaCard)realPlayerSelectedCards[i]).getOptional() + ", Number: " + ((AustraliaCard)realPlayerSelectedCards[i]).getNumber() + ", Site: " + ((AustraliaCard)realPlayerSelectedCards[i]).getSite() + "";
            botPlayerOutput += "\nName: " + ((AustraliaCard)botPlayerSelectedCards[i]).getName() + ", Region: " + ((AustraliaCard)botPlayerSelectedCards[i]).getRegion() + ", Collection: " + ((AustraliaCard)botPlayerSelectedCards[i]).getCollection() + ", Animal: " + ((AustraliaCard)botPlayerSelectedCards[i]).getAnimal() + ", Activity: " + ((AustraliaCard)botPlayerSelectedCards[i]).getOptional() + ", Number: " + ((AustraliaCard)botPlayerSelectedCards[i]).getNumber() + ", Site: " + ((AustraliaCard)botPlayerSelectedCards[i]).getSite() + "";
            assertTrue(output.contains(realPlayerOutput));
            assertTrue(output.contains(botPlayerOutput));
            i++;
        }

        Card realPlayerFinalCardBeforePass = realPlayer.getHand().getCards()[0];
        Card botPlayerFinalCardBeforePass = botPlayer.getHand().getCards()[0];

        gameState = gameState.runState(); // FinalCard

        Card realPlayerCatchCard = realPlayer.getHand().getCatchCard();
        Card botPlayerCatchCard = botPlayer.getHand().getCatchCard();

        // Tests rule 9, compares the last card the players had left before passing it to the next player
        assertEquals(realPlayerFinalCardBeforePass, botPlayerCatchCard);
        assertEquals(botPlayerFinalCardBeforePass, realPlayerCatchCard);
    }

    /**
     * Rule 10a working
     */
    @Test
    public void test10a() throws Exception{
        Card[] cards = Arrays.copyOfRange(unShuffledCard, 0, 6);

        Hand playerHand = new Hand(cards);
        ScoresheetAustralia playerScore = new ScoresheetAustralia();

        Player player = new BotPlayer(0, /*playerHand,*/ playerScore, new StandardBehavior());
        player.setNewHand(cards);

        playerHand.selectCard(cards[0]); // choose throw card (first card)

        playerHand.setCatchCard(cards[1]); // choose catch card

        playerScore.setRoundScore(playerHand, false);

        assertEquals(2, playerScore.getThrowCatchScore());
    }

    /**
     * Rule 10bI working
     */
    @Test
    public void test10bI() throws Exception{
        ArrayList<String> visitedRegions = new ArrayList<String>(); // regions visited in previous rounds
        ArrayList<String> newVisitedRegions = new ArrayList<String>(); // regions visited in previous rounds

        Card[] cards = {unShuffledCard[21], unShuffledCard[24], unShuffledCard[10], unShuffledCard[6], unShuffledCard[1], unShuffledCard[2], unShuffledCard[3]};

        Hand playerHand = new Hand(cards);
        Hand playerHand2 = new Hand(cards);
        ScoresheetAustralia playerScore = new ScoresheetAustralia();
        ScoresheetAustralia playerScore2 = new ScoresheetAustralia();
        playerScore.setVisitedRegions(visitedRegions, newVisitedRegions);
        playerScore2.setVisitedRegions(visitedRegions, newVisitedRegions);

        playerHand.selectCard(cards[0]); // choose throw card (first card)
        playerHand2.selectCard(cards[0]); // choose throw card (first card)

        playerHand.selectCard(cards[1]);
        playerHand.selectCard(cards[2]);
        playerHand.selectCard(cards[3]);
        playerHand.selectCard(cards[4]);
        playerHand.selectCard(cards[5]);
        playerHand2.selectCard(cards[1]);
        playerHand2.selectCard(cards[2]);
        playerHand2.selectCard(cards[3]);
        playerHand2.selectCard(cards[4]);
        playerHand2.selectCard(cards[5]);

        playerHand.setCatchCard(cards[6]); // choose catch card
        playerHand2.setCatchCard(cards[6]); // choose catch card

        Player player = new BotPlayer(0, playerScore, new StandardBehavior());
        player.setNewHand(cards);

        playerScore.setRoundScore(playerHand, false);

        assertEquals(playerScore.getTouristScore(), 10); // first person to finish region 

        for(String region : newVisitedRegions){ // update regions at end of round
            visitedRegions.add(region);
            newVisitedRegions = new ArrayList<String>();
        }

        playerScore2.setRoundScore(playerHand, false);

        assertEquals(playerScore2.getTouristScore(), 7); // second person to finish region 
    }

    /**
     * Rule 10bII working
     */
    @Test
    public void test10bII() throws Exception{
        ArrayList<String> visitedRegions = new ArrayList<String>(); // regions visited in previous rounds
        ArrayList<String> newVisitedRegions = new ArrayList<String>(); // regions visited in previous rounds

        Card[] cards = {unShuffledCard[21], unShuffledCard[24], unShuffledCard[10], unShuffledCard[6], unShuffledCard[1], unShuffledCard[2], unShuffledCard[3]};

        Hand playerHand = new Hand(cards);
        Hand playerHand2 = new Hand(cards);
        ScoresheetAustralia playerScore = new ScoresheetAustralia();
        ScoresheetAustralia playerScore2 = new ScoresheetAustralia();
        playerScore.setVisitedRegions(visitedRegions, newVisitedRegions);
        playerScore2.setVisitedRegions(visitedRegions, newVisitedRegions);

        playerHand.selectCard(cards[0]); // choose throw card (first card)
        playerHand2.selectCard(cards[0]); // choose throw card (first card)

        playerHand.selectCard(cards[1]);
        playerHand.selectCard(cards[2]);
        playerHand.selectCard(cards[3]);
        playerHand.selectCard(cards[4]);
        playerHand.selectCard(cards[5]);
        playerHand2.selectCard(cards[1]);
        playerHand2.selectCard(cards[2]);
        playerHand2.selectCard(cards[3]);
        playerHand2.selectCard(cards[4]);
        playerHand2.selectCard(cards[5]);

        playerHand.setCatchCard(cards[6]); // choose catch card
        playerHand2.setCatchCard(cards[6]); // choose catch card

        Player player = new BotPlayer(0, playerScore, new StandardBehavior());
        player.setNewHand(cards);

        playerScore.setRoundScore(playerHand, false);
        playerScore2.setRoundScore(playerHand2, false);

        assertEquals(playerScore.getTouristScore(), 10); // first person to finish region during round one
        assertEquals(playerScore2.getTouristScore(), 10); // second person to finish region during round one
    }

    /**
     * Rule 10cI working
     */
    @Test
    public void test10cI() throws Exception{
        Card[] cards = Arrays.copyOfRange(unShuffledCard, 0, 7);

        Hand playerHand = new Hand(cards);
        ScoresheetAustralia playerScore = new ScoresheetAustralia();

        playerHand.selectCard(cards[0]); // choose throw card (first card)

        playerHand.selectCard(cards[1]);
        playerHand.selectCard(cards[2]);
        playerHand.selectCard(cards[3]);
        playerHand.selectCard(cards[4]);
        playerHand.selectCard(cards[5]);

        playerHand.setCatchCard(cards[6]); // choose catch card

        Player player = new BotPlayer(0, playerScore, new StandardBehavior());
        player.setNewHand(cards);

        playerScore.setRoundScore(playerHand, false);

        assertEquals(playerScore.getCollectionScore(), 14);
    }

    /**
     * Rule 10cII working
     */
    @Test
    public void test10cII() throws Exception{
        Card[] cards = Arrays.copyOfRange(unShuffledCard, 0, 11);

        Hand playerHand = new Hand(cards);
        ScoresheetAustralia playerScore = new ScoresheetAustralia();

        playerHand.selectCard(cards[4]); // choose throw card (first card)

        playerHand.selectCard(cards[5]);
        playerHand.selectCard(cards[6]);
        playerHand.selectCard(cards[7]);
        playerHand.selectCard(cards[8]);
        playerHand.selectCard(cards[9]);

        playerHand.setCatchCard(cards[10]); // choose catch card

        Player player = new BotPlayer(0, playerScore, new StandardBehavior());
        player.setNewHand(cards);

        playerScore.setRoundScore(playerHand, false);

        assertEquals(playerScore.getCollectionScore(), 10);
    }

    /**
     * Rule 10d working
     */
    @Test
    public void test10d() throws Exception{
        Card[] cards1 = {unShuffledCard[7], unShuffledCard[10], unShuffledCard[16], unShuffledCard[24], unShuffledCard[0], unShuffledCard[1], unShuffledCard[2]}; // 4 Kangaroos + 2 wombats = 11
        Card[] cards2 = {unShuffledCard[7], unShuffledCard[10], unShuffledCard[16], unShuffledCard[4], unShuffledCard[0], unShuffledCard[1], unShuffledCard[2]}; // 3 Kangaroos + 2 wombats = 8
        Card[] cards3 = {unShuffledCard[7], unShuffledCard[10], unShuffledCard[4], unShuffledCard[5], unShuffledCard[0], unShuffledCard[1], unShuffledCard[2]}; // 2 Kangaroos + 2 wombats = 8

        Hand playerHand = new Hand(cards1);
        Hand playerHand2 = new Hand(cards2);
        Hand playerHand3 = new Hand(cards3);
        ScoresheetAustralia playerScore = new ScoresheetAustralia();
        ScoresheetAustralia playerScore2 = new ScoresheetAustralia();
        ScoresheetAustralia playerScore3 = new ScoresheetAustralia();

        playerHand.selectCard(cards1[0]); // choose throw card (first card)
        playerHand2.selectCard(cards2[0]); // choose throw card (first card)
        playerHand3.selectCard(cards3[0]); // choose throw card (first card)

        playerHand.selectCard(cards1[1]);
        playerHand.selectCard(cards1[2]);
        playerHand.selectCard(cards1[3]);
        playerHand.selectCard(cards1[4]);
        playerHand.selectCard(cards1[5]);
        playerHand2.selectCard(cards2[1]);
        playerHand2.selectCard(cards2[2]);
        playerHand2.selectCard(cards2[3]);
        playerHand2.selectCard(cards2[4]);
        playerHand2.selectCard(cards2[5]);
        playerHand3.selectCard(cards3[1]);
        playerHand3.selectCard(cards3[2]);
        playerHand3.selectCard(cards3[3]);
        playerHand3.selectCard(cards3[4]);
        playerHand3.selectCard(cards3[5]);

        playerHand.setCatchCard(cards1[6]); // choose catch card
        playerHand2.setCatchCard(cards2[6]); // choose catch card
        playerHand3.setCatchCard(cards3[6]); // choose catch card

        playerScore.setRoundScore(playerHand, false);
        playerScore2.setRoundScore(playerHand2, false);
        playerScore3.setRoundScore(playerHand3, false);

        assertEquals(playerScore.getAnimalScore(), 11);
        assertEquals(playerScore2.getAnimalScore(), 8);
        assertEquals(playerScore3.getAnimalScore(), 8);
    }

    /**
     * Rule 10e working
     */
    @Test
    public void test10e(){ // write exception for trying to score activity more than once
        Card[] cards = {unShuffledCard[21], unShuffledCard[27], unShuffledCard[25], unShuffledCard[1], unShuffledCard[23], unShuffledCard[15]}; // six cards, all Indigenous Culture
        Hand playerHand = new Hand(cards);
        ScoresheetAustralia playerScore = new ScoresheetAustralia();

        playerHand.selectCard(cards[0]); // choose throw card (first card)

        playerHand.selectCard(cards[1]);
        playerHand.selectCard(cards[2]);
        playerHand.selectCard(cards[3]);
        playerHand.selectCard(cards[4]);

        playerHand.setCatchCard(cards[5]); // choose catch card

        Player player = new BotPlayer(0, /*playerHand,*/ playerScore, new StandardBehavior());
        player.setNewHand(cards);

        playerHand.setRoundOptional("Indigenous Culture");

        try{ // first time scoring activity
            playerScore.setRoundScore(playerHand, true);
        } catch (Exception e) {}

        assertEquals(playerScore.getOptionalScore(), 15);

        try{ // second time trying to score activity. there is no need to test for 10ei and 10eii separately because once an activity is registered as having been performed it will remembered as long as the players hand remains the same
            playerScore.setRoundScore(playerHand, true);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Trying to score Activity that has already been scored");
        }
    }

    /**
     * Rule 11 works
     */
    @Test
    public void test11() throws Exception{
        System.setOut(new PrintStream(outputStreamCaptor));

        Card[] cards1 = Arrays.copyOfRange(unShuffledCard, 0, 7);
        Card[] cards2 = Arrays.copyOfRange(unShuffledCard, 7, 14);

        GameClientBuilder gameBuilder = new GameClientBuilder();
        gameBuilder.setNumPlayers(1);
        gameBuilder.setNumBots(1);
        gameBuilder.setEdition(1);
        gameBuilder.setRules(1);
        gameBuilder.setBotBehavior(1);

        provideInput("Y");

        GameClient gameClient = gameBuilder.getClient();

        ArrayList<Player> players = gameClient.getPlayers();
        Player realPlayer = players.get(0);
        Player botPlayer = players.get(1);

        realPlayer.setNewHand(cards1);
        botPlayer.setNewHand(cards2);

        Hand realPlayerHand = realPlayer.getHand();
        Hand botPlayerHand = botPlayer.getHand();

        realPlayerHand.selectCard(cards1[0]); // choose throw card (first card)

        realPlayerHand.selectCard(cards1[1]);
        realPlayerHand.selectCard(cards1[2]);
        realPlayerHand.selectCard(cards1[3]);
        realPlayerHand.selectCard(cards1[4]);
        realPlayerHand.selectCard(cards1[5]);

        realPlayerHand.setCatchCard(cards1[6]); // choose catch card

        botPlayerHand.selectCard(cards2[0]); // choose throw card (first card)

        botPlayerHand.selectCard(cards2[1]);
        botPlayerHand.selectCard(cards2[2]);
        botPlayerHand.selectCard(cards2[3]);
        botPlayerHand.selectCard(cards2[4]);
        botPlayerHand.selectCard(cards2[5]);

        botPlayerHand.setCatchCard(cards2[6]); // choose catch card

        GameState gameState = new RoundOverState();

        gameState.setGameClient(gameClient);
        gameState = gameState.runState();

        String output = outputStreamCaptor.toString().trim();

        assertEquals(gameState.getClass(), ShuffleAndDealState.class);
        assertTrue(output.contains("This round you scored 2 as your Throw and catch score\nThis round you scored 7 region points\nThis round you scored 14 collection points\nThis round you scored 5 animal points\nThis round you scored 7 activity points from Bushwalking"));
    }

    /**
     * Rule 12 works
     */
    @Test
    public void test12() throws Exception{
        System.setOut(new PrintStream(outputStreamCaptor));

        Card[] cards1 = Arrays.copyOfRange(unShuffledCard, 0, 7);
        Card[] cards2 = Arrays.copyOfRange(unShuffledCard, 7, 14);

        GameClientBuilder gameBuilder = new GameClientBuilder();
        gameBuilder.setNumPlayers(1);
        gameBuilder.setNumBots(1);
        gameBuilder.setEdition(1);
        gameBuilder.setRules(1);
        gameBuilder.setBotBehavior(1);

        provideInput("Y");

        GameClient gameClient = gameBuilder.getClient();

        ArrayList<Player> players = gameClient.getPlayers();
        ScoresheetAustralia botPlayerScore = new ScoresheetAustralia();
        Player botPlayer = new BotPlayer(1, botPlayerScore, new StandardBehavior());
        botPlayer.setNewHand(cards2);
        players.remove(1);
        players.add(botPlayer);

        Player realPlayer = players.get(0);

        realPlayer.setNewHand(cards1);

        Hand realPlayerHand = players.get(0).getHand();
        Hand botPlayerHand = players.get(1).getHand();

        gameClient.decRounds(); // one round left
        gameClient.decRounds();
        gameClient.decRounds();

        realPlayerHand.selectCard(cards1[0]); // choose throw card (first card)

        realPlayerHand.selectCard(cards1[1]);
        realPlayerHand.selectCard(cards1[2]);
        realPlayerHand.selectCard(cards1[3]);
        realPlayerHand.selectCard(cards1[4]);
        realPlayerHand.selectCard(cards1[5]);

        realPlayerHand.setCatchCard(cards1[6]); // choose catch card

        botPlayerHand.selectCard(cards2[0]); // choose throw card (first card)

        botPlayerHand.selectCard(cards2[1]);
        botPlayerHand.selectCard(cards2[2]);
        botPlayerHand.selectCard(cards2[3]);
        botPlayerHand.selectCard(cards2[4]);
        botPlayerHand.selectCard(cards2[5]);

        botPlayerHand.setCatchCard(cards2[6]); // choose catch card

        GameState gameState = new RoundOverState();

        gameState.setGameClient(gameClient);
        gameState = gameState.runState();
        botPlayerScore.addTotalScore(4);
        gameState = gameState.runState();

        String output = outputStreamCaptor.toString().trim();

        assertEquals(gameState, null);
        assertTrue(output.contains("YOU LOSE"));
    }

    /**
     * Set the input from the player used for a test
     */
    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

}