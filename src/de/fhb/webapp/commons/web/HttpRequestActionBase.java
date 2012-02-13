package de.fhb.webapp.commons.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Aktion die durch Ableitung definiert werden muss.
 * 
 * Die Aktion fuehrt Operationen mit dem Modell aus, und 
 * bereitet die Daten fuer die Ausgabe auf. Als letzte Aktion sollte eine 
 * Aktion zu einer View den Request weiterleiten, wo dann das Ergebnis
 * der Aktion eingelesen und in eine (HTML-)Seite eingebunden wird.
 * 
 * @author berdux
 * @version 1.0
 */
public abstract class HttpRequestActionBase {

	protected RequestDispatcher requestDispatcher;
	
	/**
	 * Standard-Methode, die durch Servlet aufgerufen wird.
	 * 
	 * @param request aktueller Request der bearbeitet werden soll
	 * @param resonse Response-Objekt fuer die Weiterleitung zu dem View
	 * @throws ServletException 
	 * @throws IOException 
	 * @throws TwitterException 
	 */
    public abstract void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException;
	
	/**
	 * Kapselt das Weiterleiten zu einer weiteren Action oder View. Die Action/der View
	 * wird als Name angeben.
	 * 
	 * @param request aktueller Request der bearbeitet werden soll und in dem Ergebnisse der Action abgelegt sind
	 * @param response  Response-Objekt zum Schreiben des Ergebnisses
	 * @param forwardName Name von Seite/JSP/Servlet, an die Kontrolle uebergeben wird
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void forward(HttpServletRequest request, HttpServletResponse response, String forwardName) throws ServletException, IOException {
		requestDispatcher = request.getRequestDispatcher(forwardName);
		requestDispatcher.forward(request, response);
	}
	
}