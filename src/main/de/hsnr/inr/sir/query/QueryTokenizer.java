package de.hsnr.inr.sir.query;

import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class QueryTokenizer {
	private static String DEFAULT_SPLIT_CHARS = " \"";
	
	public static List<String> tokenize(String str) {
		return split(str);
	}
	
	private static List<String> split(String str){
		return Lists.newArrayList(Splitter.on(CharMatcher.anyOf(DEFAULT_SPLIT_CHARS))
				.omitEmptyStrings()
				.trimResults()
				.split(str));
	}
}
