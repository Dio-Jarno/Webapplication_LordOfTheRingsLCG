package de.fhb.webapp.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Arvid Grunenberg, Thomas Habiger
 *
 */
public class CardgameDB_CardVO {
	
	protected Map<String, Object> values;
	protected String title;
	
	
	public CardgameDB_CardVO(String cardTable) {
		values = new HashMap<String, Object>();
		title = null;
		buildVO(cardTable);
	}
	
	protected void buildVO(String cardTable) {
		if (cardTable.contains("<b>")) {
			String[] properties = cardTable.split("<b>");
			if (properties[0].contains("javascript:showcarddetails")) {
				properties[0] = properties[0].replaceAll("</a>.*</span>", "");
			}
			title = normalizeString(properties[0]);
			if (properties.length >= 2) {
				// at least one more property was found
				for (int i=1; i<properties.length; i++) {
					String property = properties[i];
					if (property.contains("<br />")) {
						// card has nested properties (e.g. 'text')
						String[] parts = property.split("<br />");
						if (!parts[0].contains(":")) {
							// traits were found
							values.put("Traits", normalizeString(parts[0]));
						}
						String textPart = property.replaceAll(parts[0], "");
						property = parts[0];
						if (!textPart.isEmpty()) {
							// text was found
							String text = "";
							if (textPart.contains("<i>")) {
								String[] textParts = textPart.split("<i>");
								text = normalizeString(textParts[0]);
								if (textParts.length >= 2) {
									//TODO was ist, wenn Karte nur favor text hat?
									// flavor text was found
									String flavorText = normalizeString(textParts[1]);
									if (!flavorText.equals("")) {
										values.put("Favor Text", flavorText);
									}
								}
							} else {
								text = normalizeString(textPart);
							}
							if (!text.equals("")) {
								values.put("Text", text);
							}
						}
					}
					if (property.contains("</td>")) {
						// cut comments
						property = property.split("</td>")[0];
					}
					property = normalizeString(property);
					if (!property.equals("")) {
						if (property.contains(": ")) {
							// valid property with key:value
						 	String[] keyValue = property.split(": ");
						 	if (keyValue.length == 2) {
						 		values.put(keyValue[0], keyValue[1]);
						 	}
						 }
					}
				}
			}
		}
	}
	
	protected String normalizeString(String string) {
		string = string.replaceAll("<.*?>", "");
		string = string.replaceAll("-->", "");
		string = string.replaceAll("[\r\n\t]", "");
		string = string.replaceAll("&quot;", "\"");
		string = string.replaceAll("&#39;", "'");
		string = string.replaceAll("&#33;", "!");
		string = string.replaceAll("&bull; ", "*");
		return string;
	}
	
	public Object getValue(String key) {
		return values.get(key);
	}
	
	public Map<String, Object> getValues() {
		return values;
	}
	
	public String getTitle() {
		return title;
	}
	
}
