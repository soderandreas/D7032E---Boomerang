package Cards;

import Exception.LoadCardsException;

/**
 * An abstract factory used to create cards.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public interface AbstractCardsFactory {
    /**
     * Creates a new card.
     * 
     * @param name The name of JSON file being loaded.
     * @return A Card with the information from the JSON file.
     */
    public Card createCard(String name) throws LoadCardsException;

    /**
     * Gets the name of the folder where the cards are saved.
     * 
     * @return The name of the folder where the cards for the factory are stored.
     */
    public String getFolderNameCards();
}
