package com.big_id.name_counter.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.big_id.name_counter.model.Word;

public class Aggregator {

	public void aggregateAndPrintWords(List<Map<String, Word>> notCombinedWords) {
		Map<String, Word> result = new HashMap<>();
		notCombinedWords.forEach(subMap -> {
			subMap.entrySet().stream().forEach( entry -> {
				if (!result.containsKey(entry.getKey())) {
					result.put(entry.getKey(), entry.getValue());
				} else {
					result.get(entry.getKey()).getLocations().addAll(entry.getValue().getLocations());
				}
			});
		});
		result.keySet().stream().sorted().forEach(name -> System.out.println(result.get(name)));
	}
}
