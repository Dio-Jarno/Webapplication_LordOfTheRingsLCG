package de.fhb.webapp.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import de.fhb.webapp.access.internet.CardgameDBConnector;
import de.fhb.webapp.data.CardgameDB_CardVO;

/**
 * This is the RESTful webservice. It provides different methods for showing cards of the living card game "The Lord of the Rings".
 * The informations are fetched from the site www.cardgamedb.com. <br>
 * 
 * @author Arvid Grunenberg, Thomas Habiger
 * @version 0.4
 *
 */
@Path("cards")
public class CardService {
	
	protected CardgameDBConnector connector;
	
	/**
	 * Default constructor
	 */
	public CardService() {
		connector = new CardgameDBConnector();
	}
	
	/**
	 * Creates a JSON object from given game cards.
	 * 
	 * @param cards - The game cards, which should be transformed into a JSON object.
	 * @return the given cards as JSON
	 */
	protected JSONObject createJSON(List<CardgameDB_CardVO> cards) {
		JSONObject json = new JSONObject();
		if (cards != null) {
			for (CardgameDB_CardVO card : cards) {
				if (card.getTitle() != null && card.getValues().size() > 0) {
					json.put(card.getTitle(), card.getValues());
				}
			}
		}
		return json;
	}
	
	/**
	 * This method returns one or more result cards of a search.
	 * 
	 * @param value - The name of the card, which should be searched for.
	 * @return the result cards as JSON
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{value}")
	public JSONObject getCards(@PathParam("value") String value) {
		List<CardgameDB_CardVO> cards = connector.getCards(value);
		return createJSON(cards);
	}
	
	/**
	 * This method returns the cards from the core game set.
	 * 
	 * @return the result cards as JSON
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("set/core")
	public JSONObject getCoreCards() {
		List<CardgameDB_CardVO> cards = connector.getCoreCards();
		return createJSON(cards);
	}
	
	/**
	 * This method returns the cards from the khazad-dum extension game set.
	 * 
	 * @return the result cards as JSON
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("set/khazad-dum")
	public JSONObject getKhazadDumCards() {
		List<CardgameDB_CardVO> cards = connector.getKhazadDumCards();
		return createJSON(cards);
	}
	
	/**
	 * This method returns an error, if the given card set is not valid, i.g. no card set exists with such a name.
	 * 
	 * @param value - The name of the given card set.
	 * @return an error message as JSON (key is 'error')
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("set/{value}")
	public JSONObject showErrorSet(@PathParam("value") String value) {
		JSONObject json = new JSONObject();
		json.put("error", value + " is no valid card set. Availabily sets are: core, khazad-dum");
		return json;
	}
	
	/**
	 * This method returns the cards from a small extension game set.
	 * 
	 * @param value - The name of the card extension set, which should be searched for.
	 * @return the result cards as JSON
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("extension/{value}")
	public JSONObject getExtensionCards(@PathParam("value") String value) {
		List<CardgameDB_CardVO> cards = null;
		JSONObject json = new JSONObject();
		try {
			int number = Integer.valueOf(value);
			cards = connector.getExtensionCards(number);
		} catch (Exception e) {
			json.put("error", value + " is no valid extension set. Availabily sets are: (1) gollum, (2) carrock, (3) rhosgobel, (4) emyn-muil, (5) dead-marshes, (6) mirkwood");
		}
		return createJSON(cards);
	}
	
}
