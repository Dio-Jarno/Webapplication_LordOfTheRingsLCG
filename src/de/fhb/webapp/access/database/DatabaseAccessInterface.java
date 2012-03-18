package de.fhb.webapp.access.database;

import java.util.List;

public interface DatabaseAccessInterface {
	
	public List searchCards(String value);
	public List loadCoreCards();
	public List loadKhazadDumCards();
	public List loadExtensionCards(int number);
	
	public boolean saveCards(List cards);
}
