package de.hsnr.inr.sir.query;

import java.util.LinkedList;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import de.hsnr.inr.sir.algorithm.Intersect;
import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.dictionary.Posting;

public class PhraseQuery extends AbstractQueryTerm{

	LinkedList<AbstractQueryTerm> terms = new LinkedList<AbstractQueryTerm>();
	
	PhraseQuery(String name) {
		super(name);
		for(String termName : split(name))
			terms.add((AbstractQueryTerm) ConcreteQueryTerm.create(termName));
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

	@Override
	public void setPostingsFromIndex(Index index) {
		if(isGhost()){
			for(AbstractQueryTerm qt : terms)
				qt.setPostingsFromIndex(index);
			this.setPostings(intersectPostings());
		}
	}

	private LinkedList<Posting> intersectPostings() {
		LinkedList<Posting> postings = new LinkedList<Posting>();
		for(int i = 0; i < terms.size()-1; i++){ //TODO: FIX for false positives(add iterate by distance)
			postings.addAll(Intersect.positional(terms.get(i).getPostings(), terms.get(i+1).getPostings(), 1));
		}
		return postings;
	}
}
