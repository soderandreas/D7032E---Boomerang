package Cards;

import Exception.LoadCardsException;

/**
 * An abstract card factory that creates cards for Boomerang Australia.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class AustraliaCardsFactory implements AbstractCardsFactory {

    /**
     * Creates a new Card for Boomerang Australia.
     * 
     * @param name The name of the JSON file being loaded.
     * @return A card for Boomerang Australia.
     */
    @Override
    public Card createCard(String name) throws LoadCardsException {
        return new AustraliaCard(name);
    }

    /**
     * @return Returns the string "AustraliaCards". This is where all cards for Boomerang Australia are stored.
     */
    public String getFolderNameCards(){
        return "AustraliaCards";
    }
}
