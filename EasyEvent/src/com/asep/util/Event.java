package com.asep.util;

public class Event {
	public String name;
	public String description;
	public Event(String name){
		this.name = name;
		this.description = "This event is " + name + "!";
	}
	
	public static Event getEvent(String name){
		return new Event(name);
	}
}
