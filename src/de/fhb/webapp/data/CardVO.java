package de.fhb.webapp.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Arvid Grunenberg, Thomas Habiger
 *
 */
public class CardVO {
	
	protected Map values;
	protected String title;
	
	public CardVO() {
		values = new HashMap();
		title = null;
	}
	
	public CardVO(String cardTable) {
		this();
		buildVO(cardTable);
	}
	
	protected void buildVO(String cardTable) {
		if (cardTable.lastIndexOf("<b>") != -1) {
			String[] properties = cardTable.split("<b>");
			if (properties[0].lastIndexOf("javascript:showcarddetails") != -1) {
				properties[0] = properties[0].replaceAll("</a>.*</span>", "");
			}
			title = normalizeString(properties[0]);
			if (properties.length >= 2) {
				// at least one more property was found
				for (int i=1; i<properties.length; i++) {
					String property = properties[i];
					if (property.lastIndexOf("<br />") != -1) {
						// card has nested properties (e.g. 'text')
						String[] parts = property.split("<br />");
						if (parts[0].lastIndexOf(":") == -1) {
							// traits were found
							values.put("Traits", normalizeString(parts[0]));
						}
						String textPart = property.replaceAll(parts[0], "");
						property = parts[0];
						if (textPart != "") {
							// text was found
							String text = "";
							if (textPart.lastIndexOf("<i>") != -1) {
								String[] textParts = textPart.split("<i>");
								text = normalizeString(textParts[0]);
								if (textParts.length >= 2) {
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
					if (property.lastIndexOf("</td>") != -1) {
						// cut comments
						property = property.split("</td>")[0];
					}
					property = normalizeString(property);
					if (!property.equals("")) {
						if (property.lastIndexOf(": ") != -1) {
							// valid property with key:value
						 	String[] keyValue = property.split(": ");
						 	if (keyValue.length == 2) {
						 		if (keyValue[1].startsWith(" ")) {
						 			keyValue[1] = keyValue[1].substring(1);
								}
								if (keyValue[1].endsWith(" ")) {
									keyValue[1] = keyValue[1].substring(0, keyValue[1].length() -1);
								}
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
		string = string.replaceAll("'", "`");
		return string;
	}
	
	public Object getValue(String key) {
		return values.get(key);
	}
	
	public Map getValues() {
		return values;
	}
	
	public void addValue(String key, Object value) {
		values.put(key, value);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
}
