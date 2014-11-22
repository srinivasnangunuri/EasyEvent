package com.asep.util;

import java.util.*;

public class Event {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;
	private String description;
	private String venue;
	private java.util.Date eventDate;
	private int topic_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Event(String name) {
		this.name = name;
		this.description = "";
		this.venue = "";
		this.eventDate = Calendar.getInstance().getTime();
		this.description = "This event is " + name + "!";
	}

	public Event(String name, String desc, String venue, Date date) {
		this.name = name;
		this.description = desc;
		this.venue = venue;
		this.eventDate = date;
	}

	public static Event getEvent(String name) {
		return new Event(name);
	}

	public int getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
	}
}
