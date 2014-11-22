package com.asep.dao;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.asep.util.Topic;

public class TopicDao {
	DatabaseXMLAcces dataAccess = new DatabaseXMLAcces();

	public static List<Topic> getTopics() {
		try {
			List<Topic> topics = new ArrayList<Topic>();
			List<Event> allEvents = EventDao.getEvents();
			
			String xml = DBConn.getTopicsXML();
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doce = db.parse(new InputSource(new StringReader(xml)));
			Element elem = doce.getDocumentElement();

			NodeList topic_idNL = elem.getElementsByTagName("topic_id");
			NodeList titleNL = elem.getElementsByTagName("title");
			int num = topic_idNL.getLength();
			for (int i = 0; i < num; i++) {
				Topic temp = new Topic("temp");
				int topic_id = Integer.parseInt(topic_idNL.item(i)
						.getTextContent().trim());
				String title = titleNL.item(i).getTextContent().trim();
				List<Event> tempEvents = new ArrayList<Event>();
				
				temp.setTopicId(topic_id);
				temp.setName(title);
				
				Iterator<Event> it = allEvents.iterator();
				while(it.hasNext()){
					Event tmpEvent = it.next();
					if(tmpEvent.getTopic_id()==temp.getTopicId()){
						tempEvents.add(tmpEvent);
					}
				}
				temp.setEvents(tempEvents);
				topics.add(temp);
			}
			return topics;
		} catch (Exception e) {
			return null;
		}

	}

	public static boolean addTopic(String title){
		return DBConn.addTopic(title);
	}
}
