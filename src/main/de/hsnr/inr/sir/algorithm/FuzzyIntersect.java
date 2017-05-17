package de.hsnr.inr.sir.algorithm;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.hsnr.inr.sir.dictionary.FuzzyIndex;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.WeightedPosting;
import de.hsnr.inr.sir.dictionary.WeightedPostingComparator;
import de.hsnr.inr.sir.query.AbstractQueryTerm;

/**
 * Class that contains the fuzzy operators for query processing.
 * Needs a affiliation-function to get the truth-value for each document and term.
 * Implements zadeh's operators.
 *
 */
public class FuzzyIntersect extends Intersect {
	


	
	//TODO: Disjunction
	//TODO: Negation
	
	public static LinkedList<Posting> not(LinkedList<Posting> postings, FuzzyIndex index) {
		LinkedList<Posting> answer  = Intersect.not(postings, index.getPostings());

		return answer;
	}
	//TODO: ANDNOT?
	//TODO: POSITIONAL?



	/**
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
			float myB = fi.getFuzzyAffiliationDegree(d, qt0);
			
			answer.add(new WeightedPosting(d, Math.min(myA, myB)));
		}
		
		Collections.sort(answer);
		
		return answer;
	}

}
