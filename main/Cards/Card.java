package Cards;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.google.gson.Gson;

/**
 * The main class for cards. This class provides a framework for other types of cards and
 * is where the information associated with cards is loaded in.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public abstract class Card {

    /**
     * This method is used by to load a JSON file that contains the information about a specific card.
     *  
     * 
     * @param path The path to the JSON file that is being read.
     * @return     Returns a HashMap that containing the information from the JSON file.
     * @throws FileNotFoundException If the JSON file can not be found.
     */
    HashMap<String, String> getCardInfo(String path) throws FileNotFoundException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        Gson gson = new Gson();
        HashMap<String, String> json = gson.fromJson(bufferedReader, HashMap.class);

        return json;
    }

    /**
     * Get function for the throw/catch number associated with a card.
     * @return The throw/catch number.
     */
    public abstract int getNumber();

    /**
     * Get function for the character associated with a site. Used to identify cards and for players to make chocies.
     * @return The letter of a site.
     */
    public abstract char getSite();

    /**
     * Get function for the name of a tourist site.
     * @return The name of a tourist site.
     */
    public abstract String getName();

    /**
     * Get function for the category for optional scoring. In Boomerang Australia this optional scouring would be the activity score. 
     * @return The name of the optional scouring action connected with a card.
     */
    public abstract String getOptional(); 

    /**
     * Get the name of the optional scouring machanic in a specific version of Boomerang. Used for user output.
     * @return The name if the optional scouring action.
     */
    public abstract String getOptionalName(); // for australia = activites

    /**
     * Get a formated string with all information about a card. Used for user output.
     * @return A formated string with all information about a card.
     */
    public abstract String getAllInfo();
}
