package de.hsnr.inr.sir.query;

import java.util.LinkedList;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class PhraseQuery extends AbstractQueryTerm{

	LinkedList<QueryItem> terms = new LinkedList<QueryItem>();
	
	PhraseQuery(String name) {
		super(name);
		for(String termName : split(name))
			terms.add(ConcreteQueryTerm.create(termName));
	}

	@Override
	public void invert() {
		for(QueryItem qui : terms)
			qui.invert();
	}
	
	@Override
	public String toString(){
		String str = "\"";
		for(QueryItem qui : terms)
			str += qui;
		return str + "\" ";
	}
	
	public static QueryItem create(String name){
		if(!isParseable(name))
			throw new IllegalArgumentException();
		return new PhraseQuery(name.toLowerCase());
	}

	private static boolean isParseable(String name) {
		if(name.startsWith(QueryTokenizer.DEFAULT_PHRASE_SPLIT_CHARS) && 
				name.endsWith(QueryTokenizer.DEFAULT_PHRASE_SPLIT_CHARS))
			return true;
		return false;
	}

	private static List<String> split(String str){
		return Lists.newArrayList(Splitter.on(CharMatcher.anyOf(QueryTokenizer.DEFAULT_SPLIT_CHARS + QueryTokenizer.DEFAULT_PHRASE_SPLIT_CHARS ))
				.omitEmptyStrings()
				.trimResults()
				.split(str));
	}
	
	
	
}
