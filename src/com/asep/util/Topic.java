package com.asep.util;

import java.util.*;

public class Topic {
	
	public String name;
	public List<Event> events;
	public int topicId;
	
	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public Topic(String name, int topicId){
		this.name = name;
		this.topicId = topicId;		
		events = new ArrayList<Event>();
	}
	
	
	public Topic(String name){
		this.name = name;	
		events = new ArrayList<Event>();
	}

	public static Topic getTopic(String name){
		return new Topic(name);
	}
}
