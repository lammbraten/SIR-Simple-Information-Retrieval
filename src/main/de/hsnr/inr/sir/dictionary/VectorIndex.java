package de.hsnr.inr.sir.dictionary;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class VectorIndex extends KGramIndex {

	private static final long serialVersionUID = -4056771211575350476L;
	
	public VectorIndex(File corpus){
		super(corpus);
	}
	
	private int getNumberOfDocs(){
		return documents.size();
	}
	

	
	private float calcW_td(WeightedTerm t, Posting d){
		return (float) (1+ Math.log10(t.getTf_td(d)) * (Math.log((float)getNumberOfDocs()/t.getDf_t())));
	}
	
	private HashMap<Term, Integer> calcTf_tqs(LinkedList<Term> q){
		HashMap<Term, Integer> tf_tqs = new HashMap<Term, Integer>();
		
		for(Term t : q){
			Integer val = tf_tqs.get(t);
			if( val != null)
				tf_tqs.put(t, val + 1);
			else
				tf_tqs.put(t, 1);
		}
		return tf_tqs;
	}
	
	private float calcW_tq(WeightedTerm t, HashMap<Term, Integer> tf_tqs){
		Integer tf_td = tf_tqs.get(t);
		if(tf_td == null)
			throw new IllegalArgumentException("No such term");
		
		return (float) (1+ Math.log10(tf_td) * Math.log((float)getNumberOfDocs()/(t.getDf_t()+1)));
	}
	
	public 	List<Posting> cosineScore(LinkedList<Term> q, int topK){
		HashMap<Posting, Float> scores = new HashMap<Posting, Float>();
				
		for(Posting p : documents)
			scores.put(p, 0f);
		
		HashMap<Term, Integer> tf_tqs = calcTf_tqs(q);
		
		for(Term t : q){
			WeightedTerm indexTerm = new WeightedTerm(this.getTerm(t));
			float w_tq = calcW_tq(indexTerm, tf_tqs);
			
			for(Posting p : indexTerm.getPostings()){
				float w_td = calcW_td(indexTerm, p);
				float n_val = scores.get(p) + (w_tq * w_td);
				scores.put(p, n_val);
			}
		}
		
		return sortList(topK, scores);
	}

	public List<Posting> fastCosineScore(LinkedList<Term> q, int topK){
		HashMap<Posting, Float> scores = new HashMap<Posting, Float>();
				
		for(Posting p : documents)
			scores.put(p, 0f);

		for(Term t : q){
			WeightedTerm indexTerm = new WeightedTerm(this.getTerm(t));

			for(Posting p : indexTerm.getPostings()){
				float w_td = calcW_td(indexTerm, p);
				float n_val = scores.get(p) + w_td;
				scores.put(p, n_val);
			}
		}
		
		return sortList(topK, scores);
		
	}

	private List<Posting> sortList(int topK, HashMap<Posting, Float> scores) {
		LinkedList<Posting> returnVal = new LinkedList<Posting>();
		
		for(Entry<Posting, Float> e : scores.entrySet()){
			Posting d = e.getKey();
			float val = e.getValue();
			
			if(val <= 0)
				continue;
			
			scores.put(d, val/this.getAllTermsIn(d).size());
			returnVal.add(new WeightedPosting(d, e.getValue()));
			
		}
		
		//TODO: implement Queue for speedup
		returnVal.sort(new WeightedPostingComparator());
		
		if(returnVal.size() < topK)		
			return returnVal;
		return returnVal.subList(0, topK-1);
	}

}
