package de.fhb.webapp.access.internet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import de.fhb.webapp.data.LotrLCG_CardVO;

/**
 * 
 * @author Arvid Grunenberg
 * @version 0.1 
 *
 */
public class LotrLCGConnector {
	
	private static final String URL = "http://lotrlcg.com/cardFocus.php?Title=";
	
	/**
	 * 
	 * @param card
	 * @return
	 */
	private String loadCard(String card) {
		StringBuffer content = new StringBuffer();
		try {
			URL url = new URL(URL + card);
			InputStream iStream = url.openStream();
			int read = 0;
			while ((read = iStream.read() ) != -1){
				content.append((char)read);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	
	/**
	 * 
	 * @param card
	 * @return
	 */
	public LotrLCG_CardVO getCard(String cardName) {
		String content = loadCard(cardName);
		LotrLCG_CardVO card = new LotrLCG_CardVO(content);
		return card;
	}

}
