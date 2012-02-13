package de.fhb.webapp.controller.web;

import de.fhb.webapp.commons.web.HttpRequestActionBase;
import de.fhb.webapp.commons.web.HttpServletControllerBase;
import de.fhb.webapp.controller.web.actions.XAction;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* This servlet is the main controller, which reacts with GET and POST requests.
* It takes the statement from the "do" parameter and pass on to an action.
* 
* @author Arvid Grunenberg
* @date 08.01.2012
* @version 0.1
*/

public class MainController extends HttpServletControllerBase {
	
	private static final long serialVersionUID = 1L;
	
    public MainController() {
        super();
    }
    
    /**
     * Creates and adds all actions to manage a request.
     * 
     * @param config - standard configuration for the servlet
     */
    public void init(ServletConfig config) {
		HttpRequestActionBase action = null;
		this.actions = new HashMap<String, HttpRequestActionBase>();
		
        action = new XAction();
        this.actions.put("", action);
    }

    /**
     * Function to react with a GET request.
     * 
     * @param request - HttpServletRequest, which came from the browser
     * @param response - HttpServletResponse, which is sent back to the browser as answer
     */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
	}

	/**
	 * Function to react with a POST request.
	 * 
	 * @param request - HttpServletRequest, which came from the browser
	 * @param response - HttpServletResponse, which is sent back to the browser as answer
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}
	
	/**
	 * Returns the statement, which action should be loaded.
	 * 
     * @param request - HttpServletRequest, which came from the browser
	 */
	protected String getOperation(HttpServletRequest request) {
		return request.getParameter("do");
	}

}