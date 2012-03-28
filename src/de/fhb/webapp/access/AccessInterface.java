package de.fhb.webapp.access;

import java.util.List;

/**
 * Interface for data access.
 * 
 * @author Arvid Grunenberg, Thomas Habiger
 * @version 0.1 
 *
 */
public interface AccessInterface {
	
	/**
	 * Function to search for a set of cards with the given value in the card name.
	 * 
	 * @param value - the key word, which should be searched for
	 * @return the result cards; null if there is an error
	 */
	public List searchCards(String value);
	
	/**
	 * Loads all cards of the core card set.
	 * 
	 * @return the result cards; null if there is an error
	 */
	public List loadCoreCards();
	
	/**
	 * Loads all cards of the khazad-dûm core extension.
	 * 
	 * @return the result cards; null if there is an error
	 */
	public List loadKhazadDumCards();
	
	/**
	 * Loads all cards of an extension.
	 * 
	 * @param number - the number of the extension (1-6)
	 * @return the result cards; null if there is an error
	 */
	public List loadExtensionCards(int number);
	
	/**
	 * Saves all given cards.
	 * 
	 * @param cards - cards, which should be saved
	 * @return true, if all cards were saved, otherwise false
	 */
	public boolean saveCards(List cards);
}
