package com.asep.util;

import java.util.*;

public class Topic {

	private String name;
	private List<Event> events;
	private int topicId;

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public Topic(String name, int topicId) {
		this.setName(name);
		this.topicId = topicId;
		setEvents(new ArrayList<Event>());
	}

	public Topic(String name) {
		this.setName(name);
		this.topicId = 0;
		setEvents(new ArrayList<Event>());
	}

	public static Topic getTopic(String name) {
		return new Topic(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
}
