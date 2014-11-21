package com.asep.dao;

import java.util.ArrayList;
import java.util.List;

import com.asep.util.Event;

public class EventDao {
	
	
	public List<com.asep.util.Event> getMyUpcomingEvents(String username){
		Event e1 = new com.asep.util.Event("Football", "FootBall Match","Dallas Main stadium",new java.sql.Date(2014, 12, 12));
		Event e2 = new com.asep.util.Event("Movie", "Imitation Game","AMC Garland",new java.sql.Date(2014, 11, 22));

		List<Event> events = new ArrayList<Event>();
		events.add(e1);
		events.add(e2);

		return 	events;
		}
	
	public List<com.asep.util.Event> getEvents(){
		Event e1 = new com.asep.util.Event("Football", "FootBall Match","Dallas Main stadium",new java.sql.Date(2014, 12, 12));
		Event e2 = new com.asep.util.Event("Movie", "Imitation Game","AMC Garland",new java.sql.Date(2014, 11, 22));

		List<Event> events = new ArrayList<Event>();
		events.add(e1);
		events.add(e2);

		return 	events;
		}

}
