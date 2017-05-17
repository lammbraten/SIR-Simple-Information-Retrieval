package de.hsnr.inr.sir.algorithm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.hsnr.inr.sir.dictionary.FuzzyIndex;
import de.hsnr.inr.sir.dictionary.Posting;

/**
 * Class that contains the fuzzy operators for query processing.
 * Needs a affiliation-function to get the truth-value for each document and term.
 * Implements zadeh's operators.
 *
 */
public class FuzzyIntersect extends Intersect {
	
	//TODO: Conjunction
	public static LinkedList<Posting> and(List<Posting> pl1, List<Posting> pl2){
		LinkedList<Posting> answer = new LinkedList<Posting>();
		Iterator<Posting> p1 = pl1.iterator();
		Iterator<Posting> p2 = pl2.iterator();
		
		Posting doc1 = hasNextSetNext(p1);
		Posting doc2 = hasNextSetNext(p2);
		
		while(doc1 != null && doc2 != null){
			if(doc1.equals(doc2)){
				answer.add(doc1);
				doc1 = hasNextSetNext(p1);
				doc2 = hasNextSetNext(p2);
			} else if(doc1.compareTo(doc2) < 1){
				doc1 = hasNextSetNext(p1);
			}else{
				doc2 = hasNextSetNext(p2);
			}
		}
		
		return answer;
	}



	
	//TODO: Disjunction
	//TODO: Negation
	
	public static LinkedList<Posting> not(LinkedList<Posting> postings, FuzzyIndex index) {
		// TODO Auto-generated method stub
		return null;
	}
	//TODO: ANDNOT?
	//TODO: POSITIONAL?

}
