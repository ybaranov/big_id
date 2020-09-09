package com.big_id.name_counter.model;

import java.util.Optional;

public class LineKey implements Comparable<LineKey> {

	private final Optional<Integer> lineStartIndex;
	private final Optional<Integer> lineEndIndex;
	private final Optional<Integer> wordMatchIndex;
	
	public LineKey (Integer lineStartIndex, Integer lineEndIndex) {
		super();
		this.lineStartIndex = Optional.of(lineStartIndex);
		this.lineEndIndex = Optional.of(lineEndIndex);
		this.wordMatchIndex = Optional.empty();
	}

	public LineKey(Integer wordMatchIndex) {
		super();
		this.wordMatchIndex = Optional.of(wordMatchIndex);
		this.lineStartIndex = Optional.empty();
		this.lineEndIndex = Optional.empty();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LineKey) {
			LineKey other = (LineKey) obj;
			if (this.wordMatchIndex.isEmpty() || other.wordMatchIndex.isPresent()) {
				return isPar1InScopeOfPar0(this, other);
			} else if (this.wordMatchIndex.isPresent() || other.wordMatchIndex.isEmpty()) {
				return isPar1InScopeOfPar0(other, this);
			}
		}
		return false;
	}
	
	private boolean isPar1InScopeOfPar0(LineKey par0, LineKey par1) {
		if (par0.lineStartIndex.isPresent() && par0.lineEndIndex.isPresent() && par1.wordMatchIndex.isPresent()) {
			Integer intWordMatchIndex = par1.wordMatchIndex.get();
			return par0.lineStartIndex.get() < intWordMatchIndex && par0.lineEndIndex.get() > intWordMatchIndex;
		}
		return false;
	}

	@Override
	public int compareTo(LineKey other) {
		if (this.lineStartIndex.isPresent() && this.lineEndIndex.isPresent() && 
				other.lineStartIndex.isPresent() && other.lineEndIndex.isPresent()) {
			if (this.lineStartIndex.get() < other.lineStartIndex.get() 
					&& this.lineEndIndex.get() < other.lineEndIndex.get()) {
				return -1;
			} else if (this.lineStartIndex.get() > other.lineStartIndex.get() 
					&& this.lineEndIndex.get() > other.lineEndIndex.get()) {
				return 1;
			} else if (this.lineStartIndex.get() == other.lineStartIndex.get() 
					&& this.lineEndIndex.get() == other.lineEndIndex.get()) {
				return 0;
			}
		} else {
			if (this.wordMatchIndex.isEmpty() || other.wordMatchIndex.isPresent()) {
				return comparePar1InScopeOfPar0(this, other);
			} else if (this.wordMatchIndex.isPresent() || other.wordMatchIndex.isEmpty()) {
				return comparePar1InScopeOfPar0(other, this);
			}
		}
		throw new IllegalStateException("Wrong data structure content.");
	}
	
	private int comparePar1InScopeOfPar0(LineKey par0, LineKey par1) {
		if (par0.lineStartIndex.isPresent() && par0.lineEndIndex.isPresent() && par1.wordMatchIndex.isPresent()) {
			Integer intWordMatchIndex = par1.wordMatchIndex.get();
			if (par0.lineStartIndex.get() > intWordMatchIndex && par0.lineEndIndex.get() > intWordMatchIndex) {
				return -1;
			} else if (par0.lineStartIndex.get() < intWordMatchIndex && par0.lineEndIndex.get() <= intWordMatchIndex) {
				return 1;
			} else if (par0.lineStartIndex.get() <= intWordMatchIndex && par0.lineEndIndex.get() > intWordMatchIndex) {
				return 0;
			}
		}
		throw new IllegalStateException("Wrong data structure content.");
	}
}
