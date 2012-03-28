package de.fhb.webapp;

import java.util.List;

import de.fhb.webapp.access.AccessInterface;
import de.fhb.webapp.access.database.*;
import de.fhb.webapp.access.internet.CardgameDBConnector;

public class MainManager implements ManagerInterface {
	
	protected AccessInterface dataAccess;
	
	
	public MainManager() {
		dataAccess = new MainframeAccess();
	}

	public List searchCards(String value) {
		List cards = null;
		cards = dataAccess.searchCards(value);
		if (cards == null || cards.isEmpty()) {
			System.out.println("Keine Karten in der Datenbank gefunden.");
		} else {
			System.out.println("Karten erfolgreich aus der Datenbank geladen.");
		}
		return cards;
	}

	public List getCoreCards() {
		List cards = null;
		cards = dataAccess.loadCoreCards();
		if (cards == null || cards.isEmpty()) {
			System.out.println("Keine Karten in der Datenbank vorhanden.");
			dataAccess = new CardgameDBConnector();
			cards = dataAccess.loadCoreCards();
			System.out.println("Karten aus dem Internet geladen.");
			dataAccess.saveCards(cards);
		} else {
			System.out.println("Karten erfolgreich aus der Datenbank geladen.");
		}
		return cards;
	}

	public List getKhazadDumCards() {
		List cards = null;
		cards = dataAccess.loadKhazadDumCards();
		if (cards == null || cards.isEmpty()) {
			System.out.println("Keine Karten in der Datenbank vorhanden.");
			dataAccess = new CardgameDBConnector();
			cards = dataAccess.loadKhazadDumCards();
			System.out.println("Karten aus dem Internet geladen.");
			dataAccess.saveCards(cards);
		} else {
			System.out.println("Karten erfolgreich aus der Datenbank geladen.");
		}
		return cards;
	}

	public List getExtensionCards(int number) {
		List cards = null;
		cards = dataAccess.loadExtensionCards(number);
		if (cards == null || cards.isEmpty()) {
			System.out.println("Keine Karten in der Datenbank vorhanden.");
			dataAccess = new CardgameDBConnector();
			cards = dataAccess.loadExtensionCards(number);
			System.out.println("Karten aus dem Internet geladen.");
			dataAccess.saveCards(cards);
		} else {
			System.out.println("Karten erfolgreich aus der Datenbank geladen.");
		}
		return cards;
	}

}
