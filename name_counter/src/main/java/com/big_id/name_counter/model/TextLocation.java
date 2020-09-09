package com.big_id.name_counter.model;

public class TextLocation {

	private int lineOffset;
	private int charOffset;
	
	public TextLocation(int lineOffset, int charOffset) {
		super();
		this.lineOffset = lineOffset;
		this.charOffset = charOffset;
	}

	@Override
	public String toString() {
		return "[lineOffset=" +lineOffset +", charOffset=" + charOffset + "]";
	}
}
