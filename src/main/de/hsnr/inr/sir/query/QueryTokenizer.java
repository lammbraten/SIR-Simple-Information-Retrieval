package de.hsnr.inr.sir.query;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * Not very efficient, but Queries shouldn't be that big anyway.
 * @author lammbraten
 *
 */
public class QueryTokenizer {
	public static final String DEFAULT_SPLIT_CHARS = " ";
	public static final String DEFAULT_PHRASE_SPLIT_CHARS = "\"";
	public static final String DEFAULT_PROXIMITY_SPLIT_CHARS = "/";
	
	public static List<String> tokenize(String str) {
		//return split(str);
		//return parseForPhrases(split(str));

		return parseForProximity(parseForPhrases(split(str)));
	}
	
	private static List<String> parseForProximity(List<String> tokens) {
		LinkedList<String> l = new LinkedList<String>();
		String merged = "";
		boolean mergeStarted = false;
		for(String token : tokens){
			if(token.startsWith(DEFAULT_PROXIMITY_SPLIT_CHARS)){
				merged = l.getLast();
				l.removeLast();
				merged += " " + token;
				mergeStarted = true;
				continue;
			}
			if(mergeStarted){
				mergeStarted = false;
				merged += " " + token;
				l.add(merged);
				merged = "";
			}else{
				l.add(token);
			}
		}
		if(mergeStarted)
			throw new IllegalArgumentException("Error while parsing proximity-query");

		return l;
	}

	private static List<String> parseForPhrases(List<String> tokens) {
		ArrayList<String> l = new ArrayList<String>();
		String mergedToken = "";
		boolean mergeStarted = false;
		for(String token : tokens){
			if(token.startsWith(DEFAULT_PHRASE_SPLIT_CHARS) && !mergeStarted){
				mergeStarted = true;
				mergedToken += token + " ";
			}else if(token.endsWith(DEFAULT_PHRASE_SPLIT_CHARS)){
				mergeStarted = false;
				mergedToken += token;
				l.add(mergedToken);
				mergedToken = "";
			}else if(mergeStarted){
				mergedToken += token + " ";
			}else {
				l.add(token);
			}
		}
		if(mergeStarted)
			throw new IllegalArgumentException("Error while parsing phrase-query");

		return l;
	}

	private static List<String> split(String str){
		return Lists.newArrayList(Splitter.on(CharMatcher.anyOf(DEFAULT_SPLIT_CHARS))
				.omitEmptyStrings()
				.trimResults()
				.split(str));
	}
}
