package de.fhb.webapp.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.fhb.webapp.MainManager;
import de.fhb.webapp.ManagerInterface;
import de.fhb.webapp.data.CardVO;

/**
 * A servlet as a web service, which reacts to GET requests.
 * 
 * @author Arvid Grunenberg, Thomas Habiger
 * @version 0.1
 *
 */
public class CardWebservice extends HttpServlet {

	private static final long serialVersionUID = -1791214405002670519L;
	
	protected ManagerInterface manager;
	
	/**
	 * Default constructor
	 */
	public CardWebservice() {
		manager = new MainManager();
	}
	
	/**
	 * Function to react to a GET request. It reads the given parameter and calls the CardService.
	 * 
	 * @param request - HttpServletRequest, which came from the browser. It needs to have one of the parameters "search", "set" or "extension".
     * @param response - HttpServletResponse, which is sent back to the browser as answer.
	 * @throws ServletException If the servlet cannot forward to the next page.
	 * @throws IOException If the servlet cannot write the response.
	 * 
	 * @see CardService
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String search = request.getParameter("search");
		if (search != null) {
			out.println(searchCards(search));
		} else {
			String set = request.getParameter("set");
			if (set != null) {
				if (set.equals("core")) {
					out.println(getCoreCards());
				} else if (set.equals("khazad-dum")) {
					out.println(getKhazadDumCards());
				}
			} else {
				String extension = request.getParameter("extension");
				if (extension != null) {
					out.println(getExtensionCards(extension));
				}
			}
		}
	}
	
	/**
	 * Creates a JSON object from given game cards.
	 * 
	 * @param cards - The game cards, which should be transformed into a JSON object.
	 * @return the given cards as JSON
	 */
	protected String createJSON(List cards) {
		JSONObject json = new JSONObject();
		if (cards != null) {
			int length = cards.size();
			for (int i=0; i<length; i++) {
				CardVO card = (CardVO)cards.get(i);
				if (card.getTitle() != null && card.getValues().size() > 0) {
					json.put(card.getTitle(), card.getValues());
				}
			}
		}
		return json.toJSONString();
	}
	
	/**
	 * This method returns one or more result cards of a search.
	 * 
	 * @param value - The name of the card, which should be searched for.
	 * @return the result cards as JSON
	 */
	protected String searchCards(String value) {
		List cards = manager.searchCards(value);
		return createJSON(cards);
	}

	/**
	 * This method returns the cards from the core game set.
	 * 
	 * @return the result cards as JSON
	 */
	protected String getCoreCards() {
		List cards = manager.getCoreCards();
		return createJSON(cards);
	}
	
	/**
	 * This method returns the cards from the khazad-dum extension game set.
	 * 
	 * @return the result cards as JSON
	 */
	protected String getKhazadDumCards() {
		List cards = manager.getKhazadDumCards();
		return createJSON(cards);
	}
	
	/**
	 * This method returns the cards from a small extension game set.
	 * 
	 * @param value - The name of the card extension set, which should be searched for.
	 * @return the result cards as JSON
	 */
	protected String getExtensionCards(String value) {
		List cards = null;
		JSONObject json = new JSONObject();
		try {
			int number = Integer.parseInt(value);
			cards = manager.getExtensionCards(number);
		} catch (Exception e) {
			json.put("error", value + " is no valid extension set. Availabily sets are: (1) gollum, (2) carrock, (3) rhosgobel, (4) emyn-muil, (5) dead-marshes, (6) mirkwood");
		}
		return createJSON(cards);
	}
}
