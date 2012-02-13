package de.fhb.webapp.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Arvid Grunenberg
 *
 */
public class LotrLCG_CardVO {
	
	protected Map<String, Object> values;
	
	
	public LotrLCG_CardVO(String content) {
		values = new HashMap<String, Object>();
		buildVO(content);
	}
	
	protected void buildVO(String content) {
		String cardInfo = content.split("<div class=\"cMain\">")[2].split("<col class=\"rightCol\" />")[1];
		String[] table = cardInfo.split("</tr>");
		for (String string : table) {
			string = string.replaceAll("icon_willpower.jpg", " > willpower<");
			string = string.replaceAll("icon_attack.jpg", " > attack<");
			string = string.replaceAll("icon_defense.jpg", " > defense<");
			string = string.replaceAll("<.*?>", "");
			string = string.replaceAll("[\r\n\t]", "");
			string = string.replaceAll("&nbsp;", "");
			string = string.replaceAll("&#149;", "");
			String[] keyValue = string.split(":");
			if (keyValue.length == 2) {
				if (keyValue[0].equals("Stats")) {
					System.out.println(keyValue[1]);
					String[] stats = keyValue[1].split(" ");
					
//					Map<String, Integer> stats = ;
//					values.put(keyValue[0], stats);
				} else {
					values.put(keyValue[0], keyValue[1]);
				}
			}
		}
//		System.out.println(values);
	}
	
	public Object getValue(String key) {
		return values.get(key);
	}
	
	public Map<String, Object> getValues() {
		return values;
	}
	
}
