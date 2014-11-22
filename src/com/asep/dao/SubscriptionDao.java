package com.asep.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.asep.util.DBConn;
import com.asep.util.Event;

public class SubscriptionDao {

	public static boolean subscribeTopic(String user_name, int topic_id) {
		return DBConn.subscribe(user_name, topic_id);
	}

	public static List<com.asep.util.Event> getMyUpcomingEvents(String username) {
		// Event e1 = new com.asep.util.Event("Football", "FootBall Match",
		// "Dallas Main stadium", new java.sql.Date(2014, 12, 12));
		// Event e2 = new com.asep.util.Event("Movie", "Imitation Game",
		// "AMC Garland", new java.sql.Date(2014, 11, 22));
		//
		// List<Event> events = new ArrayList<Event>();
		// events.add(e1);
		// events.add(e2);
		try {
			List<Event> events = new ArrayList<Event>();
			String xml = DBConn.getSubscriptionXML(username);
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doce = db.parse(new InputSource(new StringReader(xml)));
			Element elem = doce.getDocumentElement();
			NodeList idNodeList = elem.getElementsByTagName("event_id");
			List<Integer> idList = new ArrayList<Integer>();
			int num = idNodeList.getLength();
			for (int i = 0; i < num; i++) {
				idList.add(Integer.parseInt(idNodeList.item(i).getTextContent()
						.trim()));
			}

			List<Event> all = EventDao.getEvents();
			Iterator<Event> it = all.iterator();
			while (it.hasNext()) {
				Event event = it.next();
				if (idList.contains(event.getId()))
					events.add(event);
			}
			return events;
		} catch (Exception e) {
			return null;
		}
	}

}
