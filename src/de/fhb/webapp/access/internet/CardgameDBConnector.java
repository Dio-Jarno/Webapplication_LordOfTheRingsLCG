package de.fhb.webapp.access.internet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.fhb.webapp.data.CardgameDB_CardVO;

/**
 * 
 * @author Arvid Grunenberg, Thomas Habiger
 * @version 0.1 
 *
 */
public class CardgameDBConnector {
	
	protected static final String URL = "http://www.cardgamedb.com/index.php/lotr/";
	protected static final String CARDS = "lord-of-the-rings-card-spoiler/_/";
	protected static final String SEARCH = "lord-of-the-rings-advanced-card-search";
	
	/**
	 * 
	 * @param domain
	 * @return
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	
	public List<CardgameDB_CardVO> getCoreCards() {
		String content = loadContent(CARDS + "core/?per_page=200");
		return createCardsSet(content);
	}
	
	public List<CardgameDB_CardVO> getKhazadDumCards() {
		String content = loadContent(CARDS + "khazad-dum/?per_page=200");
		return createCardsSet(content);
	}
	
	public List<CardgameDB_CardVO> getExtensionCards(int extension) {
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
	
	protected List<CardgameDB_CardVO> createCardsSet(String content) {
		List<CardgameDB_CardVO> cards = new ArrayList<CardgameDB_CardVO>();
		CardgameDB_CardVO card;
		content = content.split("<h3 class='maintitle'>")[1];
		String[] cardTables = content.split("<table>");
		for (String cardTable : cardTables) {
			card = new CardgameDB_CardVO(cardTable);
			if (card.getTitle() != null && !card.getTitle().equals("")) {
				cards.add(card);
			}
		}
		return cards;
	}
	
	public List<CardgameDB_CardVO> getCards(String value) {
		String content = loadContent(SEARCH + "?name=" + value);
		return createCards(content);
	}
	
	protected List<CardgameDB_CardVO> createCards(String content) {
		List<CardgameDB_CardVO> cards = new ArrayList<CardgameDB_CardVO>();
		CardgameDB_CardVO card;
		content = content.split("id=\"resultsContainer\"")[1];
		String[] cardTables = content.split("</li>");
		for (String cardTable : cardTables) {
			card = new CardgameDB_CardVO(cardTable);
			if (card.getTitle() != null && !card.getTitle().equals("")) {
				cards.add(card);
			}
		}
		return cards;
	}

}
