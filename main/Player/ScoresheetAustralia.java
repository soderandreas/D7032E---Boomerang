package Player;

import Cards.Card;
import Cards.AustraliaCard;
import Exception.OptionalScoreException;

import java.util.Arrays;

/**
 * An extension of the Scoresheet used for playing Boomerang Australia.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class ScoresheetAustralia extends Scoresheet {

    /**
     * The collection score for the round.
     */
    private int collectionScore = 0;

    /**
     * The tourist score for the round.
     */
    private int touristScore = 0;

    /**
     * The animal score for the round.
     */
    private int animalScore = 0;

    /**
     * Sets the score for the round.
     */
    @Override
    public void setRoundScore(Hand hand, boolean countOptional) throws OptionalScoreException {

        this.countThrowCatch(hand.getThrowCard(), hand.getCatchCard());
        this.countTourist(hand);
        this.countCollection(hand);
        this.countAnimal(hand);
        if(countOptional){
            this.countActivities(hand, hand.getRoundOptional());
        }

        int total = this.getThrowCatchScore() + this.getTouristScore() + this.getCollectionScore() + this.getAnimalScore() + this.getOptionalScore();

        this.addTotalScore(total);
    }

    /**
     * Gets score for the round formated as a string.
     */
    public String getRoundScoreFormated(Hand hand, boolean countOptional) throws OptionalScoreException {

        String output = "This round you scored " + this.getThrowCatchScoreRound() + " as your Throw and catch score\n";
        output += "This round you scored " + this.getTouristScore() + " region points\n";
        output += "This round you scored " + this.getCollectionScore() + " collection points\n";
        output += "This round you scored " + this.getAnimalScore() + " animal points\n";
        if(countOptional){
            output += "This round you scored " + this.getOptionalScore() + " activity points from " + hand.roundOptional + "\n";
        }

        return output;
    }

    /**
     * Counts the players tourist score.
     * 
     * @param hand The players Hand.
     */
    private void countTourist(Hand hand){
        int totalScore = 0;
        Card allSelectedCards[] = hand.getSelectedCards();

        for(int i = 0; i < allSelectedCards.length; i++){
            AustraliaCard currCard = (AustraliaCard)allSelectedCards[i];

            if(!siteVisited(currCard.getSite())){ // check if player has visited site previously
                totalScore++;
                this.addPreviousSite(currCard.getSite());
                String completedRegion = checkRegionComplete(currCard);
                if(!completedRegion.equals("")){ // check if any region has been completed
                    if(checkFirstPlayerRegion(completedRegion)){
                        totalScore += 3;   
                    }
                }
            }
        }

        this.touristScore = totalScore;
    }

    /**
     * Gets the players tourist score.
     * 
     * @return The players tourist score.
     */
    public int getTouristScore(){
        return this.touristScore;
    }

    /**
     * Checks if a site has been visisted.
     * 
     * @param site A character representing the site.
     * @return True = the site has been visited. False = site has not been visisted.
     */
    private boolean siteVisited(char site){
        for (char visited : this.previousSite){
            if(visited == site){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the player is the first to complete a region.
     * 
     * @param completedRegion The region that the player has completed.
     * @return True = first player to complete the region. False = not the first player to complete the region.
     */
    private boolean checkFirstPlayerRegion(String completedRegion){
        if(this.getVisitedRegions().contains(completedRegion)){
            return false;
        } else {
            this.addNewRegion(completedRegion);
            return true;
        }
        
    }

    /**
     * Checks if the player has completed a region.
     * 
     * @param card The Card whose region is being checked.
     * @return The name of the region that the player has completed.
     */
    private String checkRegionComplete(AustraliaCard card){
        String region = card.getRegion();

        if(region.equals("Western Australia") && this.previousSite.containsAll(Arrays.asList('A', 'B', 'C', 'D'))){
            return "Western Australia";
        } else if (region.equals("Northern Territory") && this.previousSite.containsAll(Arrays.asList('E', 'F', 'G', 'H'))){
            return "Northern Territory";
        } else if (region.equals("Queensland") && this.previousSite.containsAll(Arrays.asList('I', 'J', 'K', 'L'))){
            return "Queensland";
        } else if (region.equals("South Australia") && this.previousSite.containsAll(Arrays.asList('M', 'N', 'O', 'P'))){
            return "South Australia";
        } else if (region.equals("New South Whales") && this.previousSite.containsAll(Arrays.asList('Q', 'R', 'S', 'T'))){
            return "New South Whales";
        } else if (region.equals("Victoria") && this.previousSite.containsAll(Arrays.asList('U', 'V', 'W', 'X'))) {
            return "Victoria";
        } else if (region.equals("Tasmania") && this.previousSite.containsAll(Arrays.asList('Y', 'Z', '*', '-'))) {
            return "Tasmania";
        }
        
        return "";
    }

    /**
     * Counts the player collection score for the round.
     * 
     * @param hand The players hand.
     */
    private void countCollection(Hand hand){
        int cardScore = 0;
        Card allSelectedCards[] = hand.getSelectedCards();

        for(int i = 0; i < allSelectedCards.length; i++){
            AustraliaCard currCard = (AustraliaCard)allSelectedCards[i];
            String collection = currCard.getCollection();
            if(!collection.equals("")){
                if(collection.equals("Leaves")){
                    cardScore++;
                } else if (collection.equals("Wildflowers")){
                    cardScore += 2;
                } else if (collection.equals("Shells")){
                    cardScore += 3;
                } else if (collection.equals("Souvenirs")){
                    cardScore += 5;
                }
            }
        }

        if(cardScore < 8 && cardScore > 0){
            this.collectionScore = cardScore*2;
        } else {
            this.collectionScore = cardScore;
        }
    }

    /**
     * Gets the players collection score for the round.
     * 
     * @return The collection score for the round.
     */
    public int getCollectionScore(){
        return this.collectionScore;
    }

    /**
     * Counts the players animal score for the round.
     * 
     * @param hand The players hand.
     */
    private void countAnimal(Hand hand){
        int cardScore = 0;
        Card allSelectedCards[] = hand.getSelectedCards();
        String[] animalarr = {"Kangaroos", "Emus", "Wombats", "Koalas", "Platypuses"};

        for(String thisAnim : animalarr) {
            int frequency = numberAnimals(thisAnim, allSelectedCards);
            if(frequency % 2 != 0){
                frequency -= (frequency % 2);
            }
            if(frequency != 0){
                if(frequency % 2 == 0 && thisAnim.equals("Kangaroos")) {
                    cardScore+=3*(frequency/2);
                } else if(frequency % 2 == 0 && thisAnim.equals("Emus")) {
                    cardScore+=4*(frequency/2);
                } else if(frequency % 2 == 0 && thisAnim.equals("Wombats")) {
                    cardScore+=5*(frequency/2);			
                } else if(frequency % 2 == 0 && thisAnim.equals("Koalas")) {
                    cardScore+=7*(frequency/2);
                } else if(frequency % 2 == 0 && thisAnim.equals("Platypuses")) {
                    cardScore+=9*(frequency/2);				
                }
            }
        }

        this.animalScore = cardScore;
    }

    /**
     * Gets the players animal score for the round.
     * 
     * @return The players animal score for the round.
     */
    public int getAnimalScore(){
        return this.animalScore;
    }

    /**
     * Counts the number of each animal the player has collected in each round.
     * 
     * @param animal The animal being checked.
     * @param draft The cards the player drafed that round.
     * @return The number of one specific animal the player collected during the round.
     */
    private int numberAnimals(String animal, Card[] draft){
        int nrAnimals = 0;
        for(Card aCard : draft) {
            AustraliaCard currCard = (AustraliaCard)aCard;
            if(!currCard.getAnimal().equals("")) {
                if(currCard.getAnimal().equals(animal)) {nrAnimals++;}
            }
        }
        return nrAnimals;
    }

    /**
     * Counts the activity score for the players current hand.
     * 
     * @param hand The players hand.
     * @param activity The activity chosen by the player.
     * @throws OptionalScoreException Thrown if the player tries to count the same activity twice.
     */
    private void countActivities(Hand hand, String activity) throws OptionalScoreException {
        if(!this.previousOptional.contains(activity)){
            this.previousOptional.add(activity);
            int cardScore = 0, numOfActivityCard = 0;
            Card allSelectedCards[] = hand.getSelectedCards();

            for(int i = 0; i < allSelectedCards.length; i++){
                AustraliaCard currCard = (AustraliaCard)allSelectedCards[i];
                String cardActivity = currCard.getOptional();
                if(cardActivity.equals(activity)){
                    numOfActivityCard++;
                }
            }

            if(numOfActivityCard == 1) {
                cardScore = 0;
            } else if (numOfActivityCard == 2){
                cardScore = 2;
            } else if (numOfActivityCard == 3){
                cardScore = 4;
            } else if (numOfActivityCard == 4){
                cardScore = 7;
            } else if (numOfActivityCard == 5){
                cardScore = 10;
            } else if (numOfActivityCard == 6){
                cardScore = 15;
            }

            this.optionalScore = cardScore;
            
        }
    }
}
