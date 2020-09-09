package com.big_id.name_counter.model;

import java.util.LinkedList;
import java.util.List;

public class Word {

	private final String text;
	private final List<TextLocation> locations = new LinkedList<>();
	
	public Word(String text) {
		super();
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public List<TextLocation> getLocations() {
		return locations;
	}

	public void addLocation(TextLocation location) {
		this.locations.add(location);
	}

	@Override
	public String toString() {
		return text  + " --> " +  locations;
		
	}
}
