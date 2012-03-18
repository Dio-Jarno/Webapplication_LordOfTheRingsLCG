package de.fhb.webapp;

import java.util.List;

import de.fhb.webapp.access.database.*;
import de.fhb.webapp.access.internet.CardgameDBConnector;

public class MainManager {
	
	protected DatabaseAccessInterface databaseAccess;
	protected CardgameDBConnector cardgameDBconnector;
	
	
	public MainManager() {
		databaseAccess = new MainframeAccess();
	}

	public List searchCards(String value) {
		List cards = null;
		cards = databaseAccess.searchCards(value);
		if (cards == null || cards.isEmpty()) {
			System.out.println("Keine Karten in der Datenbank gefunden.");
		} else {
			System.out.println("Karten erfolgreich aus der Datenbank geladen.");
		}
		return cards;
	}

	public List getCoreCards() {
		List cards = null;
		cards = databaseAccess.loadCoreCards();
		if (cards == null || cards.isEmpty()) {
			System.out.println("Keine Karten in der Datenbank vorhanden.");
			cardgameDBconnector = new CardgameDBConnector();
			cards = cardgameDBconnector.getCoreCards();
			System.out.println("Karten aus dem Internet geladen.");
			databaseAccess.saveCards(cards);
		} else {
			System.out.println("Karten erfolgreich aus der Datenbank geladen.");
		}
		return cards;
	}

	public List getKhazadDumCards() {
		List cards = null;
		cards = databaseAccess.loadKhazadDumCards();
		if (cards == null || cards.isEmpty()) {
			System.out.println("Keine Karten in der Datenbank vorhanden.");
			cardgameDBconnector = new CardgameDBConnector();
			cards = cardgameDBconnector.getKhazadDumCards();
			System.out.println("Karten aus dem Internet geladen.");
			databaseAccess.saveCards(cards);
		} else {
			System.out.println("Karten erfolgreich aus der Datenbank geladen.");
		}
		return cards;
	}

	public List getExtensionCards(int number) {
		List cards = null;
		cards = databaseAccess.loadExtensionCards(number);
		if (cards == null || cards.isEmpty()) {
			System.out.println("Keine Karten in der Datenbank vorhanden.");
			cardgameDBconnector = new CardgameDBConnector();
			cards = cardgameDBconnector.getExtensionCards(number);
			System.out.println("Karten aus dem Internet geladen.");
			databaseAccess.saveCards(cards);
		} else {
			System.out.println("Karten erfolgreich aus der Datenbank geladen.");
		}
		return cards;
	}

}
