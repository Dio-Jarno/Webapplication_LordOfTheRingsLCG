package de.fhb.webapp.commons.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
* Abstract servlet, which reacts with GET and POST requests.
*/

public abstract class HttpServletControllerBase extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected HashMap<String, HttpRequestActionBase> actions;
	  
	public void init(ServletConfig config) throws ServletException {
		actions = new HashMap<String, HttpRequestActionBase>(); 
	}
	
	/**
	 * Function to react with a GET request.
	 * 
	 * @param request - HttpServletRequest, which came from the browser
	 * @param response - HttpServletResponse, which is sent back to the browser as answer
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    String op = getOperation(request);
        HttpRequestActionBase action = actions.get(op);
		action.perform(request, response);
	}
	  
	/**
	 * Function to react with a POST request.
	 * 
     * @param request - HttpServletRequest, which came from the browser
     * @param response - HttpServletResponse, which is sent back to the browser as answer
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    String op = getOperation(request);
        HttpRequestActionBase action = actions.get(op);
		action.perform(request, response);
	}
	
	/**
	 * Returns the statement, which action should be loaded.
	 * 
     * @param request - HttpServletRequest, which came from the browser
	 */
	protected abstract String getOperation(HttpServletRequest request);
	
}