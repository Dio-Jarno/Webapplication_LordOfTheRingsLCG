package de.fhb.webapp.access.internet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.fhb.webapp.access.AccessInterface;
import de.fhb.webapp.data.CardVO;

/**
 * This class fetches informations of the lord of the rings cards game from a internet site.
 * 
 * @author Arvid Grunenberg, Thomas Habiger
 * @version 0.1 
 *
 */
public class CardgameDBConnector implements AccessInterface {
	
	/**
	 * main part of the address
	 */
	protected static final String URL = "http://www.cardgamedb.com/index.php/lotr/";
	
	/**
	 * context to game cards part
	 */
	protected static final String CARDS = "lord-of-the-rings-card-spoiler/_/";
	
	/**
	 * context to search part
	 */
	protected static final String SEARCH = "lord-of-the-rings-advanced-card-search";
	
	
	/**
	 * Loads an HTML page.
	 * 
	 * @param domain - context of the HTML page
	 * @return the HTML code of the result site; null if there is an error (e.g. the page is not availably)
	 */
	protected String loadContent(String domain) {
		StringBuffer content = new StringBuffer();
		try {
			URL url = new URL(URL + domain);
			InputStream iStream = url.openStream();
			int read = 0;
			while ((read = iStream.read() ) != -1) {
				content.append((char)read);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return content.toString();
	}
	
	/**
	 * Loads all cards of the core card set.
	 * 
	 * @return the result cards; null if there is an error
	 */
	public List loadCoreCards() {
		String content = loadContent(CARDS + "core/?per_page=200");
		return createCardsSet(content);
	}
	
	public List loadKhazadDumCards() {
		String content = loadContent(CARDS + "khazad-dum/?per_page=200");
		return createCardsSet(content);
	}
	
	public List loadExtensionCards(int extension) {
		final String MIRKWOOD = "shadows-of-mirkwood/";
		String content = "";
		switch (extension) {
			case 1: content = loadContent(CARDS + MIRKWOOD + "the-hunt-for-gollum");
					break;
			case 2: content = loadContent(CARDS + MIRKWOOD + "conflict-at-the-carrock");
					break;
			case 3: content = loadContent(CARDS + MIRKWOOD + "a-journey-to-rhosgobel");
					break;
			case 4: content = loadContent(CARDS + MIRKWOOD + "the-hills-of-emyn-muil");
					break;
			case 5: content = loadContent(CARDS + MIRKWOOD + "the-dead-marshes");
					break;
			case 6: content = loadContent(CARDS + MIRKWOOD + "return-to-mirkwood");
					break;
		}
		if (!content.equals("")) {
			return createCardsSet(content);
		}
		return null;
	}
	
	protected List createCardsSet(String content) {
		List cards = new ArrayList();
		CardVO card;
		if (content.lastIndexOf("<h3 class='maintitle'>") != -1) {
			content = content.split("<h3 class='maintitle'>")[1];
			String[] cardTables = content.split("<table>");
			for (int i=0; i<cardTables.length; i++) {
				card = new CardVO(cardTables[i]);
				if (card.getTitle() != null && !card.getTitle().equals("")) {
					cards.add(card);
				}
			}
		}
		return cards;
	}
	
	public List searchCards(String value) {
		String content = loadContent(SEARCH + "?name=" + value);
		return createCards(content);
	}
	
	protected List createCards(String content) {
		List cards = new ArrayList();
		CardVO card;
		if (content.lastIndexOf("id=\"resultsContainer\"") != -1) {
			content = content.split("id=\"resultsContainer\"")[1];
			String[] cardTables = content.split("</li>");
			for (int i=0; i<cardTables.length; i++) {
				card = new CardVO(cardTables[i]);
				if (card.getTitle() != null && !card.getTitle().equals("")) {
					cards.add(card);
				}
			}
		}
		return cards;
	}

	public boolean saveCards(List cards) {
		return false;
	}

}
