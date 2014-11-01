package com.asep.util;

import java.util.*;

public class Topic {
	
	public String name;
	public List<Event> events;
	
	public Topic(String name){
		this.name = name;
		events = new ArrayList<Event>();
	}

	public static Topic getTopic(String name){
		return new Topic(name);
	}
}
