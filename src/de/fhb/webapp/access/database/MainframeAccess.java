package de.fhb.webapp.access.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

import de.fhb.webapp.data.CardVO;

public class MainframeAccess implements DatabaseAccessInterface {

	protected final String URL = "jdbc:db2://binks.informatik.uni-leipzig.de:4019/S1D931";
	protected final String USER = "***";
	protected final String PASSWORD = "***";
	
	protected final static String DRIVER = "com.ibm.db2.jcc.DB2Driver";
	
	protected Connection connection;
	
	/**
	 * Default-Constructor
	 */
	public MainframeAccess() {
		connection = null;
		loadDriver();
	}
	
	/**
	 * Loads the current driver.
	 * 
	 * @return True if the driver is loaded.
	 */
	protected boolean loadDriver() {
		try {
			Class.forName(DRIVER).newInstance();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public List searchCards(String value) {
		List cards = new ArrayList();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT * FROM ALLY WHERE NAME LIKE '%"+value+"%'");
			cards.addAll(createAllyCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM ATTACHMENT WHERE NAME LIKE '%"+value+"%'");
			cards.addAll(createAttachmentCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM ENEMY WHERE NAME LIKE '%"+value+"%'");
			cards.addAll(createEnemyCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM EVENT WHERE NAME LIKE '%"+value+"%'");
			cards.addAll(createEventCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM HERO WHERE NAME LIKE '%"+value+"%'");
			cards.addAll(createHeroCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM LOCATION WHERE NAME LIKE '%"+value+"%'");
			cards.addAll(createLocationCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM OBJECTIVE WHERE NAME LIKE '%"+value+"%'");
			cards.addAll(createObjectiveCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM QUEST WHERE NAME LIKE '%"+value+"%'");
			cards.addAll(createQuestCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM TREACHERY WHERE NAME LIKE '%"+value+"%'");
			cards.addAll(createTreacheryCards(resultSet));
			
			resultSet.close();
			statement.close();
		} catch (Exception e) {
			System.out.println("Query konnte nicht ausgeführt werden!");
//			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				try { 
					connection. close(); 
				} catch(Exception e) {
//					e.printStackTrace(); 
					return null;
				}
			}
		}
		return cards;
	}
	
	protected List loadCards(String value) {
		List cards = new ArrayList();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT * FROM ALLY WHERE CARDSET = '"+value+"'");
			cards.addAll(createAllyCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM ATTACHMENT WHERE CARDSET = '"+value+"'");
			cards.addAll(createAttachmentCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM ENEMY WHERE CARDSET = '"+value+"'");
			cards.addAll(createEnemyCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM EVENT WHERE CARDSET = '"+value+"'");
			cards.addAll(createEventCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM HERO WHERE CARDSET = '"+value+"'");
			cards.addAll(createHeroCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM LOCATION WHERE CARDSET = '"+value+"'");
			cards.addAll(createLocationCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM OBJECTIVE WHERE CARDSET = '"+value+"'");
			cards.addAll(createObjectiveCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM QUEST WHERE CARDSET = '"+value+"'");
			cards.addAll(createQuestCards(resultSet));
			
			resultSet = statement.executeQuery("SELECT * FROM TREACHERY WHERE CARDSET = '"+value+"'");
			cards.addAll(createTreacheryCards(resultSet));
			
			resultSet.close();
			statement.close();
		} catch (Exception e) {
			System.out.println("Karten konnten nicht aus der Datenbank geladen werden!");
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				try { 
					connection. close(); 
				} catch(Exception e) {
//					e.printStackTrace(); 
					return null;
				}
			}
		}
		return cards;
	}
	
	public List loadCoreCards() {
		return loadCards("Core");
	}
	
	public List loadKhazadDumCards() {
		return loadCards("KD");
	}
	
	public List loadExtensionCards(int number) {
		switch (number) {
			case 1: return loadCards("THFG");
			case 2: return loadCards("CatC");
			case 3: return loadCards("AJtR");
			case 4: return loadCards("THoEM");
			case 5:	return loadCards("TDM");
			case 6:	return loadCards("RtM");
			default: return null;
		}
	}
	
	protected CardVO createCard(ResultSet currentCard) throws SQLException {
		CardVO card = new CardVO();
		card.addValue("Number", new Integer(currentCard.getInt("NUMBER")));
		card.setTitle(currentCard.getString("NAME"));
		card.addValue("Type", currentCard.getString("TYPE"));
		card.addValue("Text", currentCard.getString("TEXT"));
		card.addValue("Favor Text", currentCard.getString("FAVORTEXT"));
		card.addValue("Illustrator", currentCard.getString("ILLUSTRATOR"));
		card.addValue("Set", currentCard.getString("CARDSET"));
		card.addValue("Quantity", new Integer(currentCard.getInt("QUANTITY")));
		return card;
	}
	
	protected List createAllyCards(ResultSet allyCardsTable) throws SQLException {
		List cards = new ArrayList();
		CardVO card;
		while (allyCardsTable.next()) {
			card = createCard(allyCardsTable);
			card.addValue("Sphere", allyCardsTable.getString("SPHERE"));
			card.addValue("Cost", new Integer(allyCardsTable.getInt("COST")));
			card.addValue("Hit Points", new Integer(allyCardsTable.getInt("HITPOINTS")));
			card.addValue("Willpower", new Integer(allyCardsTable.getInt("WILLPOWER")));
			card.addValue("Attack", new Integer(allyCardsTable.getInt("ATTACK")));
			card.addValue("Defese", new Integer(allyCardsTable.getInt("DEFENSE")));
			card.addValue("Traits", allyCardsTable.getString("TRAITS"));
			cards.add(card);
		}
		return cards;
	}
	
	protected List createAttachmentCards(ResultSet attachmentCardsTable) throws SQLException {
		List cards = new ArrayList();
		CardVO card;
		while (attachmentCardsTable.next()) {
			card = createCard(attachmentCardsTable);
			card.addValue("Sphere", attachmentCardsTable.getString("SPHERE"));
			card.addValue("Cost", new Integer(attachmentCardsTable.getInt("COST")));
			card.addValue("Traits", attachmentCardsTable.getString("TRAITS"));
			cards.add(card);
		}
		return cards;
	}
	
	protected List createEnemyCards(ResultSet enemyCardsTable) throws SQLException {
		List cards = new ArrayList();
		CardVO card;
		while (enemyCardsTable.next()) {
			card = createCard(enemyCardsTable);
			card.addValue("Encounter Set", enemyCardsTable.getString("ENCOUNTERSET"));
			card.addValue("Threat Threashold", new Integer(enemyCardsTable.getInt("THREATTHREASHOLD")));
			card.addValue("Hit Points", new Integer(enemyCardsTable.getInt("HITPOINTS")));
			card.addValue("Threat", enemyCardsTable.getString("THREAT"));
			card.addValue("Attack", enemyCardsTable.getString("ATTACK"));
			card.addValue("Defese", enemyCardsTable.getString("DEFENSE"));
			card.addValue("Traits", enemyCardsTable.getString("TRAITS"));
			cards.add(card);
		}
		return cards;
	}
	
	protected List createEventCards(ResultSet eventCardsTable) throws SQLException {
		List cards = new ArrayList();
		CardVO card;
		while (eventCardsTable.next()) {
			card = createCard(eventCardsTable);
			card.addValue("Sphere", eventCardsTable.getString("SPHERE"));
			card.addValue("Cost", eventCardsTable.getString("COST"));
			cards.add(card);
		}
		return cards;
	}
	
	protected List createHeroCards(ResultSet heroCardsTable) throws SQLException {
		List cards = new ArrayList();
		CardVO card;
		while (heroCardsTable.next()) {
			card = createCard(heroCardsTable);
			card.addValue("Sphere", heroCardsTable.getString("SPHERE"));
			card.addValue("Starting Threat", new Integer(heroCardsTable.getInt("STARTINGTHREAT")));
			card.addValue("Hit Points", new Integer(heroCardsTable.getInt("HITPOINTS")));
			card.addValue("Willpower", new Integer(heroCardsTable.getInt("WILLPOWER")));
			card.addValue("Attack", new Integer(heroCardsTable.getInt("ATTACK")));
			card.addValue("Defese", new Integer(heroCardsTable.getInt("DEFENSE")));
			card.addValue("Traits", heroCardsTable.getString("TRAITS"));
			cards.add(card);
		}
		return cards;
	}
	
	protected List createLocationCards(ResultSet locationCardsTable) throws SQLException {
		List cards = new ArrayList();
		CardVO card;
		while (locationCardsTable.next()) {
			card = createCard(locationCardsTable);
			card.addValue("Encounter Set", locationCardsTable.getString("ENCOUNTERSET"));
			card.addValue("Threat", locationCardsTable.getString("THREAT"));
			card.addValue("Quest Points", locationCardsTable.getString("QUESTPOINTS"));
			card.addValue("Traits", locationCardsTable.getString("TRAITS"));
			cards.add(card);
		}
		return cards;
	}
	
	protected List createObjectiveCards(ResultSet objectiveCardsTable) throws SQLException {
		List cards = new ArrayList();
		CardVO card;
		while (objectiveCardsTable.next()) {
			card = createCard(objectiveCardsTable);
			card.addValue("Encounter Set", objectiveCardsTable.getString("ENCOUNTERSET"));
			card.addValue("Traits", objectiveCardsTable.getString("TRAITS"));
			cards.add(card);
		}
		return cards;
	}
	
	protected List createQuestCards(ResultSet questCardsTable) throws SQLException {
		List cards = new ArrayList();
		CardVO card;
		while (questCardsTable.next()) {
			card = createCard(questCardsTable);
			card.addValue("Encounter Set", questCardsTable.getString("ENCOUNTERSET"));
			card.addValue("Encounter Info", questCardsTable.getString("ENCOUNTERINFO"));
			card.addValue("Quest Points", questCardsTable.getString("QUESTPOINTS"));
			cards.add(card);
		}
		return cards;
	}
	
	protected List createTreacheryCards(ResultSet treacheryCardsTable) throws SQLException {
		List cards = new ArrayList();
		CardVO card;
		while (treacheryCardsTable.next()) {
			card = createCard(treacheryCardsTable);
			card.addValue("Encounter Set", treacheryCardsTable.getString("ENCOUNTERSET"));
			cards.add(card);
		}
		return cards;
	}
	
	public boolean saveCards(List cards) {
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			int length = cards.size();
			for (int i=0; i<length; i++) {
				CardVO card = (CardVO) cards.get(i);
				String type = (String) card.getValue("Type");
				if (type.equals("Ally")) {
					saveAllyCard(statement, card);
				} else if (type.equals("Attachment")) {
					saveAttachmentCard(statement, card);
				} else if (type.equals("Enemy")) {
					saveEnemyCard(statement, card);
				} else if (type.equals("Event")) {
					saveEventCard(statement, card);
				} else if (type.equals("Hero")) {
						saveHeroCard(statement, card);
				} else if (type.equals("Location")) {
					saveLocationCard(statement, card);
				} else if (type.equals("Objective")) {
					saveObjectiveCard(statement, card);
				} else if (type.equals("Quest")) {
					saveQuestCard(statement, card);
				} else if (type.equals("Treachery")) {
					saveTreacheryCard(statement, card);
				}
			}
			statement.close();
		} catch (Exception e) {
			System.out.println("Karten konnten nicht gespeichert werden!");
			e.printStackTrace();
			return false;
		} finally {
			if (connection != null) {
				try { 
					connection. close(); 
				} catch(Exception e) {
					e.printStackTrace(); 
					return false;
				}
			}
		}
		System.out.println("Karten in Datenbank gespeichert.");
		return true;
	}
	
	protected String createStandardValues(CardVO card) {
		StringBuffer query = new StringBuffer();
		query.append(card.getValue("Number"));
		query.append(",");
		query.append("'" + card.getTitle() + "'");
		query.append(",");
		query.append("'" + card.getValue("Type") + "'");
		query.append(",");
		if (card.getValue("Text") != null) {
			query.append("'" + card.getValue("Text") + "'");
		} else {
			query.append("null");
		}
		query.append(",");
		if (card.getValue("Favor Text") != null) {
			query.append("'" + card.getValue("Favor Text") + "'");
		} else {
			query.append("null");
		}
		query.append(",");
		query.append("'" + card.getValue("Illustrator") + "'");
		query.append(",");
		query.append("'" + card.getValue("Set") + "'");
		query.append(",");
		query.append(card.getValue("Quantity"));
		query.append(",");
		return query.toString();
	}
	
	protected void saveAllyCard(Statement statement, CardVO card) throws SQLException {
		System.out.println("INSERT INTO ALLY VALUES ("+
				createStandardValues(card) + "'" +
				card.getValue("Sphere") + "'," +
				card.getValue("Cost") + "," +
				card.getValue("Hit Points") + "," +
				card.getValue("Willpower") + "," +
				card.getValue("Attack") + "," +
				card.getValue("Defense") + ",'" +
				card.getValue("Traits") + "');");
		statement.executeUpdate("INSERT INTO ALLY VALUES ("+
			createStandardValues(card) + "'" +
			card.getValue("Sphere") + "'," +
			card.getValue("Cost") + "," +
			card.getValue("Hit Points") + "," +
			card.getValue("Willpower") + "," +
			card.getValue("Attack") + "," +
			card.getValue("Defense") + ",'" +
			card.getValue("Traits") + "');"
		);
	}
	
	protected void saveAttachmentCard(Statement statement, CardVO card) throws SQLException {
		System.out.println("INSERT INTO ATTACHMENT VALUES ("+
				createStandardValues(card) + "'" +
				card.getValue("Sphere") + "'," +
				card.getValue("Cost") + ",'" +
				card.getValue("Traits") + "');");
		statement.executeUpdate("INSERT INTO ATTACHMENT VALUES ("+
			createStandardValues(card) + "'" +
			card.getValue("Sphere") + "'," +
			card.getValue("Cost") + ",'" +
			card.getValue("Traits") + "');"
		);
	}
	
	protected void saveEnemyCard(Statement statement, CardVO card) throws SQLException {
		System.out.println("INSERT INTO ENEMY VALUES ("+
				createStandardValues(card) + "'" +
				card.getValue("Encounter Set") + "'," +
				card.getValue("Threat Threashold") + "," +
				card.getValue("Hit Points") + "," +
				card.getValue("Threat") + "," +
				card.getValue("Attack") + "," +
				card.getValue("Defense") + ",'" +
				card.getValue("Traits") + "');");
		statement.executeUpdate("INSERT INTO ENEMY VALUES ("+
			createStandardValues(card) + "'" +
			card.getValue("Encounter Set") + "'," +
			card.getValue("Threat Threashold") + "," +
			card.getValue("Hit Points") + ",'" +
			card.getValue("Threat") + "','" +
			card.getValue("Attack") + "','" +
			card.getValue("Defense") + "','" +
			card.getValue("Traits") + "');"
		);
	}

	protected void saveEventCard(Statement statement, CardVO card) throws SQLException {
		System.out.println("INSERT INTO EVENT VALUES ("+
				createStandardValues(card) + "'" +
				card.getValue("Sphere") + "','" +
				card.getValue("Cost") + "');");
		statement.executeUpdate("INSERT INTO EVENT VALUES ("+
			createStandardValues(card) + "'" +
			card.getValue("Sphere") + "','" +
			card.getValue("Cost") + "');"
		);
	}
	
	protected void saveHeroCard(Statement statement, CardVO card) throws SQLException {
		System.out.println("INSERT INTO HERO VALUES ("+
				createStandardValues(card) + "'" +
				card.getValue("Sphere") + "'," +
				card.getValue("Starting Threat") + "," +
				card.getValue("Hit Points") + "," +
				card.getValue("Willpower") + "," +
				card.getValue("Attack") + "," +
				card.getValue("Defense") + ",'" +
				card.getValue("Traits") + "');");
		statement.executeUpdate("INSERT INTO HERO VALUES ("+
			createStandardValues(card) + "'" +
			card.getValue("Sphere") + "'," +
			card.getValue("Starting Threat") + "," +
			card.getValue("Hit Points") + "," +
			card.getValue("Willpower") + "," +
			card.getValue("Attack") + "," +
			card.getValue("Defense") + ",'" +
			card.getValue("Traits") + "');"
		);
	}
	
	protected void saveLocationCard(Statement statement, CardVO card) throws SQLException {
		System.out.println("INSERT INTO LOCATION VALUES ("+
				createStandardValues(card) + "'" +
				card.getValue("Encounter Set") + "'," +
				card.getValue("Threat") + "," +
				card.getValue("Quest Points") + ",'" +
				card.getValue("Traits") + "');");
		statement.executeUpdate("INSERT INTO LOCATION VALUES ("+
			createStandardValues(card) + "'" +
			card.getValue("Encounter Set") + "','" +
			card.getValue("Threat") + "','" +
			card.getValue("Quest Points") + "','" +
			card.getValue("Traits") + "');"
		);
	}
	
	protected void saveObjectiveCard(Statement statement, CardVO card) throws SQLException {
		System.out.println("INSERT INTO OBJECTIVE VALUES ("+
				createStandardValues(card) + "'" +
				card.getValue("Encounter Set") + "','" +
				card.getValue("Traits") + "');");
		statement.executeUpdate("INSERT INTO OBJECTIVE VALUES ("+
			createStandardValues(card) + "'" +
			card.getValue("Encounter Set") + "','" +
			card.getValue("Traits") + "');"
		);
	}
	
	protected void saveQuestCard(Statement statement, CardVO card) throws SQLException {
		System.out.println("INSERT INTO QUEST VALUES ("+
				createStandardValues(card) + "'" +
				card.getValue("Encounter Set") + "','" +
				card.getValue("Encounter Info") + "'," +
				card.getValue("Quest Points") + ");");
		statement.executeUpdate("INSERT INTO QUEST VALUES ("+
			createStandardValues(card) + "'" +
			card.getValue("Encounter Set") + "','" +
			card.getValue("Encounter Info") + "','" +
			card.getValue("Quest Points") + "');"
		);
	}
	
	protected void saveTreacheryCard(Statement statement, CardVO card) throws SQLException {
		System.out.println("INSERT INTO TREACHERY VALUES ("+
				createStandardValues(card) + "'" +
				card.getValue("Encounter Set") + "');");
		statement.executeUpdate("INSERT INTO TREACHERY VALUES ("+
			createStandardValues(card) + "'" +
			card.getValue("Encounter Set") + "');"
		);
	}
	
}
