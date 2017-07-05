package de.hsnr.inr.sir.dictionary;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class KGramIndex extends Index {

	private static final String FILLING_SYMBOL = "$";
	private static final long serialVersionUID = 3719653519465517041L;
	private HashMap<String, Set<Term>> kgrams;
	private int k = 2;
	private float treshold = 0.4f;
	
	public KGramIndex(File corpus, int k){
		super(corpus);
		this.k = k;
		kgrams = new HashMap<String, Set<Term>>();
		calcKGrams();
	}

	public Set<Term> getTermsForKGram(String kgram){
		if(kgram.length() != k)
			throw new IndexOutOfBoundsException("Has to be the length of k");
		
		return kgrams.get(kgram);
	}
	
	public Set<String> getCorrectedTermsForString(String q){
		Set<String> correctedSetCandidates = new HashSet<String>();
		SortedSet<String> bestCorrectedSet = new TreeSet<String>(new LevenshteinComparator(q));
		Set<Term> y_termSet = new HashSet<Term>();
		Set<String> x_kGrams = calcKGrams(q, k);
		
		for(String kgram : x_kGrams) 
			y_termSet.addAll(getTermsForKGram(kgram));
			
		for(Term t : y_termSet){
			Set<String> yt_kGrams = calcKGrams(t.getValue(), k); //TODO: access the preCalculates Values
			if(jaccard(x_kGrams, yt_kGrams) > treshold )
				correctedSetCandidates.add(t.getValue());
		}
		
		bestCorrectedSet.addAll(correctedSetCandidates);
		
		return (Set<String>) bestCorrectedSet;
	}
	
	private float jaccard(Set<String> x, Set<String> y) {
		int xAndY = calcIntersectionSize(x, y);
		int xOrY = x.size() + x.size() - xAndY;
		
		return (float)xAndY/xOrY;
	}
	
	private static int calcIntersectionSize(Set<String> x, Set<String> y){
		int size = 0;
		Set<String> greatestSet, otherSet;
		
		if(x.size() > y.size()){
			greatestSet = x;
			otherSet = y;
		}else{
			greatestSet = y;
			otherSet = x;
		}
			
		for(String str : otherSet)
			if(greatestSet.contains(str))
				size++;
		
		return size;
	}

	private void calcKGrams() {
		for(Term t : this.dictionary){
			for(String kgram_t : calcKGrams(t.getValue(), k)){
				if(kgrams.containsKey(kgram_t)){
					Set<Term> terms = kgrams.get(kgram_t);
					terms.add(t);
					kgrams.put(kgram_t, terms);
				}else{
					Set<Term> terms = new HashSet<Term>();
					kgrams.put(kgram_t, terms);				
				}

			}
		}
		
	}

	public static Set<String> calcKGrams(String t, int k) {
		Set<String> kgram_t = new HashSet<String>();
		
		for(int i = -1; i < t.length()+2-k; i++ ){	
			if(i < 0)
				kgram_t.add(FILLING_SYMBOL + t.substring(0, k-1));
			else {
				String substr;
				try{
					substr = t.substring(i, i + k);
				}catch(IndexOutOfBoundsException e){
					substr = t.substring(i) +FILLING_SYMBOL;
				}
				kgram_t.add(substr);
			}
		}
		return kgram_t;
	}
	
	
	
}
