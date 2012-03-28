package de.fhb.webapp;

import java.util.List;

public interface ManagerInterface {
	
	public List searchCards(String value);
	public List getCoreCards();
	public List getKhazadDumCards();
	public List getExtensionCards(int number);
	
}
