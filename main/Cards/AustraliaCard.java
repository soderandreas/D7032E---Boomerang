package Cards;

import java.io.FileNotFoundException; 
import java.util.HashMap;

import Exception.LoadCardsException;

/**
 * An extention of the Card class that represents a card used in Boomerang Australia.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class AustraliaCard extends Card {
    /**
     * Saves all information about a card used in Boomerang Australia
     * including: the name of the tourist site, the name of the region,
     * the name of the collection, the name of the animal, the name of the
     * activity, the throw/catch number, and the letter associated with each site.
     */
    private String name, region, collection, animal, activity;
    private int number;
    private char site;

    /**
     * Loads a the information about a card.
     * 
     * @param name The name of the JSON file with the card information.
     * @throws LoadCardsException The card could not be loaded.
     */
    public AustraliaCard(String name) throws LoadCardsException{
        String path = "resources/Card/AustraliaCards/"+ name;

        HashMap<String, String> json = null;
        try {
            json = getCardInfo(path);
        } catch (FileNotFoundException e) {
            throw new LoadCardsException(e);
        }

        this.name = json.get("name");
        this.region = json.get("region");
        this.collection = json.get("collection");
        this.animal = json.get("animal");
        this.activity = json.get("activity");
        this.number = Integer.parseInt(json.get("number"));
        this.site = json.get("site").charAt(0);
    }

    /**
     * Gets the name of the tourist site.
     * 
     * @return The name of the tourist site.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the name of the region.
     * 
     * @return The name of the region.
     */
    public String getRegion(){
        return this.region;
    }

    /**
     * Gets the collection.
     * 
     * @retuns The name of the collection of a tourist site. If there is no collection for the tourist site
     * it returns "".
     */
    public String getCollection(){
        return this.collection;
    }

    /**
     * Gets the animal.
     * 
     * @returns The name of the animal of a tourist site. If there is no animal for the tourist site
     * it returns "".
     */
    public String getAnimal(){
        return this.animal;
    }

    /**
     * Gets the activity.
     * 
     * @returns The name of the activity of a tourist site. If there is no activity for the tourist site
     * it returns "".
     */
    public String getOptional(){
        return this.activity;
    }

    /**
     * @returns The word "activites". Used for output to the player.
     */
    public String getOptionalName(){
        return "activites";
    }

    /**
     * Get the throw/catch number.
     * 
     * @return The throw/catch number for the card.
     */
    public int getNumber(){
        return this.number;
    }

    /**
     * Get the site character. Used by real players to make choices.
     * 
     * @return The site character.
     */
    public char getSite(){
        return this.site;
    }

    /**
     * Gets a formated string containing all information associated with a card.
     * 
     * @return All information about the card.
     */
    public String getAllInfo(){
        return "Name: " + this.name + ", Region: " + this.region + ", Collection: " + this.collection + ", Animal: " + this.animal + ", Activity: " + this.activity + ", Number: " + this.number + ", Site: " + this.site;
    }
}
