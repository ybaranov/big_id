package com.big_id.name_counter.model;

public class LineValue {

	private final int lineNumber;
	private final int lineStartIndex;
	
	public LineValue(int lineNumber, int lineStartIndex) {
		super();
		this.lineNumber = lineNumber;
		this.lineStartIndex = lineStartIndex;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public int getLineStartIndex() {
		return lineStartIndex;
	}
}
