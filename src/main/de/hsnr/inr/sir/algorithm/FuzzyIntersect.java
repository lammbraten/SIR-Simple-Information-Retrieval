package de.hsnr.inr.sir.algorithm;

import java.util.LinkedList;
import de.hsnr.inr.sir.dictionary.FuzzyIndex;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.WeightedPosting;
import de.hsnr.inr.sir.query.AbstractQueryTerm;

/**
 * Class that contains the fuzzy operators for query processing.
 * Needs a affiliation-function to get the truth-value for each document and term.
 * Implements zadeh's operators.
 *
 */
public class FuzzyIntersect extends Intersect {
	
	/**
	 * Fuzzy negation
	 * @param qt0
	 * @param qt1
	 * @param fi
	 * @return
	 */
	public static LinkedList<Posting> or(AbstractQueryTerm qt0, AbstractQueryTerm qt1, FuzzyIndex fi) {
	
		LinkedList<Posting> answer  = new LinkedList<Posting>();
		
		for(Posting d : Intersect.or(qt0.getPostings(), qt1.getPostings())){
			float myA = fi.getFuzzyAffiliationDegree(d, qt0);
			float myB = fi.getFuzzyAffiliationDegree(d, qt1);
			
			answer.add(new WeightedPosting(d, Math.max(myA, myB)));
		}
	
		return answer;
	}
	
	/**
	 * Fuzzy conjunction
	 * answer = min(my(A), my(B))
	 * @param qt0
	 * @param qt1
	 * @param fi
	 * @return
	 */
	public static LinkedList<Posting> and(AbstractQueryTerm qt0, AbstractQueryTerm qt1, FuzzyIndex fi) {
		LinkedList<Posting> answer  = new LinkedList<Posting>();
		
		for(Posting d : Intersect.and(qt0.getPostings(), qt1.getPostings())){
			float myA = fi.getFuzzyAffiliationDegree(d, qt0);
			float myB = fi.getFuzzyAffiliationDegree(d, qt1);
			
			answer.add(new WeightedPosting(d, Math.min(myA, myB)));
		}
	
		return answer;
	}

	/**
	 * Fuzzy disjunction
	 * @param postings
	 * @param index
	 * @return
	 */
	public static LinkedList<Posting> not(AbstractQueryTerm qt, FuzzyIndex index) {
		LinkedList<Posting> answer  = new LinkedList<Posting>();
		
		for(Posting d : Intersect.not(qt.getPostings(), index.getPostings())){
			float my = index.getFuzzyAffiliationDegree(d, qt);
			
			answer.add(new WeightedPosting(d, 1- my));
		}
		
		return answer;
	}
	
	public static LinkedList<Posting> andNot(AbstractQueryTerm qt0, AbstractQueryTerm qt1, FuzzyIndex fi) {
		LinkedList<Posting> answer  = new LinkedList<Posting>();
		
		for(Posting d : Intersect.and(qt0.getPostings(), qt1.getPostings())){
			float myA = fi.getFuzzyAffiliationDegree(d, qt0);
			Float myB = 1- fi.getFuzzyAffiliationDegree(d, qt1);

			answer.add(new WeightedPosting(d, Math.min(myA, myB)));
		}
	
		return answer;

	}
	
	public static LinkedList<Posting> notAndNot(AbstractQueryTerm qt0, AbstractQueryTerm qt1, FuzzyIndex fi) {
		
		LinkedList<Posting> answer  = new LinkedList<Posting>();
		
		for(Posting d : Intersect.notAndNot(qt0.getPostings(), qt1.getPostings(), fi.getPostings())){
			float myA = 1 -fi.getFuzzyAffiliationDegree(d, qt0);
			float myB = 1- fi.getFuzzyAffiliationDegree(d, qt1);
			
			answer.add(new WeightedPosting(d, Math.min(myA, myB)));
		}
	
		return answer;
	}
}
