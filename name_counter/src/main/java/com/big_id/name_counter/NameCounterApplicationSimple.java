package com.big_id.name_counter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.big_id.name_counter.model.Word;
import com.big_id.name_counter.service.WordCounterService;

public class NameCounterApplicationSimple {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Application should have two arguments: 1) url to target file; "
					+ "2) is the Names delimited by comma only.");
			return;
		}
		List<String> names = Arrays.asList(args[1].split(","));
		
		new WordCounterService().calculateWordsInFileParallel(args[0], names);	
	}
}
