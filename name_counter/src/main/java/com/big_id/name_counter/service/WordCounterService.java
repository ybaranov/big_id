package com.big_id.name_counter.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.big_id.name_counter.model.LineKey;
import com.big_id.name_counter.model.LineValue;
import com.big_id.name_counter.model.Word;
import com.big_id.name_counter.tasks.Aggregator;
import com.big_id.name_counter.tasks.Matcher;

public class WordCounterService implements WordCounter {

	ThreadPoolExecutor executor = 
			  (ThreadPoolExecutor) Executors.newCachedThreadPool();
	
	@Override
	public void calculateWordsInFileParallel(String fileUrl, List<String> words) {
		try {
			calculate(fileUrl, words);
		} catch (IOException | InterruptedException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

	private void calculate(String fileUrl, List<String> words) 
			throws MalformedURLException, IOException, InterruptedException {
		URL url = new URL(fileUrl);
		List<CompletableFuture<Map<String, Word>>> futures = new LinkedList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			Map<LineKey, LineValue> linesInfo = new TreeMap<>();
			int lineNumber = 1;
			int lineStartIndex = 0;
			StringBuilder chunk = new StringBuilder();
			String inputLine;
	        while ((inputLine = br.readLine()) != null) {
	        	chunk.append(inputLine).append("\n");
	        	final int lineEndIndex = chunk.length();
	        	linesInfo.put(new LineKey(lineStartIndex, lineEndIndex), new LineValue(lineNumber, lineStartIndex));
	        	lineNumber++;
	        	lineStartIndex = lineEndIndex;
	        	if (lineNumber % 1000 == 0) {
	        		futures.add(calculateAsync(new Matcher(chunk.toString(), words, linesInfo)));
	        		chunk = new StringBuilder();
	        		lineStartIndex = 0;
	        		linesInfo = new TreeMap<>();
	        	}
		    }
	        if (chunk.length() > 0) {
        		futures.add(calculateAsync(new Matcher(chunk.toString(), words, linesInfo)));
	        }
		}
		futures.stream().forEach(CompletableFuture::join);
		List<Map<String, Word>> maps = futures.stream().map(t -> {
			try {
				return t.get();
			} catch (ExecutionException | InterruptedException ex) {
				ex.printStackTrace();
				return new HashMap<String, Word>();
			}
		}).collect(Collectors.toList());
		new Aggregator().aggregateAndPrintWords(maps);
	}
	
	private CompletableFuture<Map<String, Word>> calculateAsync(Matcher matcher) throws InterruptedException {
		CompletableFuture<Map<String, Word>> completableFuture 
	      = new CompletableFuture<>();
	 
		executor.submit(() -> {
			try {
				completableFuture.complete(matcher.call());
			} catch(Exception ex) {
				completableFuture.completeExceptionally(ex);
			}
	        return null;
	    });
	 
	    return completableFuture;
	}

}
