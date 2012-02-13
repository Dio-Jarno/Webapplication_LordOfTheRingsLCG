package de.fhb.webapp;

import de.fhb.webapp.access.internet.CardgameDBConnector;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CardgameDBConnector connector = new CardgameDBConnector();
		connector.getCoreCards();
	}

}
