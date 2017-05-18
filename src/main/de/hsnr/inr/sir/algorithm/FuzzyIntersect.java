package de.hsnr.inr.sir.algorithm;

import java.util.Iterator;
import java.util.LinkedList;
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
			float myB = fi.getFuzzyAffiliationDegree(d, qt1);
			
			answer.add(new WeightedPosting(d, Math.min(myA, myB)));
		}
		
		//answer.sort(new WeightedPostingComparator());
		
		return answer;
		
		/*
		LinkedList<Posting> answer = new LinkedList<Posting>();
		Iterator<Posting> p1 = qt0.getPostings().iterator();
		Iterator<Posting> p2 = qt1.getPostings().iterator();
		
		Posting doc1 = hasNextSetNext(p1);
		Posting doc2 = hasNextSetNext(p2);
		
		while(doc1 != null && doc2 != null){
			if(doc1.equals(doc2)){
				float myA = fi.getFuzzyAffiliationDegree(doc1, qt0);
				float myB = fi.getFuzzyAffiliationDegree(doc1, qt1);
				
				answer.add(new WeightedPosting(doc1, Math.min(myA, myB)));				
				//answer.add(doc1);
				doc1 = hasNextSetNext(p1);
				doc2 = hasNextSetNext(p2);
			} else if(doc1.compareTo(doc2) < 1){
				doc1 = hasNextSetNext(p1);
			}else{
				doc2 = hasNextSetNext(p2);
			}
		}
		
		return answer;*/
		
		
	}

}
