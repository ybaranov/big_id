package com.big_id.name_counter.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.big_id.name_counter.model.Word;

public interface WordCounter {

	void calculateWordsInFileParallel(String fileUrl, List<String> words);
}
