package com.asep.dao;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import com.asep.util.*;

import java.io.*;

public class EventDao {

	public static List<com.asep.util.Event> getEvents() {
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

			String xml = DBConn.getEventsXML();
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doce = db.parse(new InputSource(new StringReader(xml)));
			Element elem = doce.getDocumentElement();

			NodeList event_idNL = elem.getElementsByTagName("event_id");
			NodeList nameNL = elem.getElementsByTagName("name");
			NodeList descNL = elem.getElementsByTagName("desc");
			NodeList venueNL = elem.getElementsByTagName("venue");
			NodeList topic_idNL = elem.getElementsByTagName("topic_id");
			NodeList event_dateNL = elem.getElementsByTagName("event_date");
			int num = event_idNL.getLength();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < num; i++) {
				Event temp = new Event("temp");
				int event_id = Integer.parseInt(event_idNL.item(i)
						.getTextContent().trim());
				String name = nameNL.item(i).getTextContent().trim();
				String desc = descNL.item(i).getTextContent().trim();
				String venue = venueNL.item(i).getTextContent().trim();
				int topic_id = Integer.parseInt(topic_idNL.item(i)
						.getTextContent().trim());
				Date event_date = sdf.parse(event_dateNL.item(i)
						.getTextContent().trim());
				temp.setId(event_id);
				temp.setName(name);
				temp.setDescription(desc);
				temp.setVenue(venue);
				temp.setEventDate(event_date);
				temp.setTopic_id(topic_id);
				events.add(temp);
			}
			return events;
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean addEvents(String name, String desc, String venue, int topic_id, Date event_date){
		return DBConn.addEvent(name, desc, venue, topic_id, event_date);
	}
}
