package de.fhb.webapp.controller.web.actions;

import de.fhb.webapp.commons.web.HttpRequestActionBase;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action to manage a HttpServletRequest.
 * 
 * @author Arvid Grunenberg
 * @date 30.12.2011
 * @version 0.1
 */
public class XAction extends HttpRequestActionBase {
	
    
	public XAction() {
		super();
	}

	/**
     * This function forwards 
     * 
     * @param request - HttpServletRequest, which came from the browser.
     * @param response - HttpServletResponse, which is sent back to the browser as answer.
	 * @throws ServletException When the action cannot forward to the page, i.e. the site is not availably.
     */
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
        	forward(request, response, "");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        	e.printStackTrace();
        }
	}
	
}