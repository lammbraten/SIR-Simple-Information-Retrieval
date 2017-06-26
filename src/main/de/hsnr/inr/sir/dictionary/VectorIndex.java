package de.hsnr.inr.sir.dictionary;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class VectorIndex extends Index {

	private static final long serialVersionUID = -4056771211575350476L;

	//protected LinkedList<WeightedTerm> dictionary;
	
	public VectorIndex(File corpus){
		super(corpus);
	}
	
	private int getNumberOfDocs(){
		return documents.size();
	}
	

	
	public float calcW_td(WeightedTerm t, Posting d){
		return (float) (1+ Math.log10(t.getTf_td(d)) * (Math.log((float)getNumberOfDocs()/t.getDf_t())));
	}
	
	/**
	 * Unter der Annahme, dass in einem Query ein Query-Term immer genau einmal vorkommt, 
	 * kann man diese Methode verwenden und sich den Posting-Parameter sparen.
	 * 
	 * 
	 * @param t
	 * @return
	 */
	public float calcW_tq(WeightedTerm t){
		return (float) (Math.log((float)getNumberOfDocs()/t.getDf_t()));
	}
	

	public List<Entry<Posting, Float>> cosineScore(LinkedList<Term> q, int topK){
		HashMap<Posting, Float> scores = new HashMap<Posting, Float>();
		List<Entry<Posting, Float>> returnVal = new LinkedList<Entry<Posting, Float>>();
				
		for(Posting p : documents)
			scores.put(p, 0f);
		//float Scores[] = new float[getNumberOfDocs()]; //Sollte laut java-spec mit 0 initialisiert weden.
		
		for(Term t : q){
			WeightedTerm indexTerm = new WeightedTerm(this.getTerm(t));
			float w_tq = calcW_tq(indexTerm);
			
	
			for(Posting p : indexTerm.getPostings()){
				float w_td = calcW_td(indexTerm, p);
				float n_val = scores.get(p) + (w_tq * w_td);
				scores.put(p, n_val);
			}
		}
		
		for(Entry<Posting, Float> e : scores.entrySet()){
			Posting d = e.getKey();
			float val = e.getValue();
			
			
			scores.put(d, val/this.getAllTermsIn(d).size());
			returnVal.add(e);
		}
		
		returnVal.sort(new EntryComparator());
		
		return returnVal;
		
	}

}
