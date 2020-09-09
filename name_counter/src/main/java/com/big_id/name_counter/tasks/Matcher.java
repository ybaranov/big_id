package com.big_id.name_counter.tasks;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.big_id.name_counter.model.LineKey;
import com.big_id.name_counter.model.LineValue;
import com.big_id.name_counter.model.TextLocation;
import com.big_id.name_counter.model.Word;

public class Matcher implements Callable<Map<String, Word>> {
	
	private final String text; 
	private final List<String> searchWords; 
	private final Map<LineKey, LineValue> lineInfo; 

	public Matcher(String text, List<String> searchWords, Map<LineKey, LineValue> lineInfo) {
		super();
		this.text = text;
		this.searchWords = searchWords;
		this.lineInfo = lineInfo;
	}

	@Override
	public Map<String, Word> call() throws Exception {
		Map<String, Word> result = new HashMap<>();
		this.searchWords.stream().forEach(searchWord -> {
			int index = text.indexOf(searchWord);
			if (index != -1) {
				Word word = new Word(searchWord);
				while (index != -1) {
					LineValue lv = lineInfo.get(new LineKey(index));
					word.addLocation(new TextLocation(lv.getLineNumber(), index - lv.getLineStartIndex()));
					index = text.indexOf(searchWord, index + searchWord.length());
				}
				result.put(searchWord, word);
			}
		});
		return result;
	}

}
