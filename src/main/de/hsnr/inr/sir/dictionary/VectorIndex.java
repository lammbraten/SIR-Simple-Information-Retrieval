package de.hsnr.inr.sir.dictionary;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.hsnr.inr.sir.query.Query;

public class VectorIndex extends Index {

	private static final long serialVersionUID = -4056771211575350476L;

	//protected LinkedList<WeightedTerm> dictionary;
		
	private int getNumberOfDocs(){
		return documents.size();
	}
	

	
	public float calcW_td(WeightedTerm t, Posting d){
		return (float) (1+ Math.log10(t.getTf_td(d)) * (Math.log(getNumberOfDocs()/t.getDf_t())));
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
		return (float)  (Math.log(getNumberOfDocs()/t.getDf_t()));
	}
	

	public void cosineScore(LinkedList<Term> q){
		HashMap<Posting, Float> scores = new HashMap<Posting, Float>();
		for(Posting p : documents)
			scores.put(p, 0f);
		//float Scores[] = new float[getNumberOfDocs()]; //Sollte laut java-spec mit 0 initialisiert weden.
		
		for(Term t : q){
			WeightedTerm indexTerm = (WeightedTerm) this.getTerm(t);
			float w_tq = calcW_tq(indexTerm);
			
	
			for(Posting p : indexTerm.getPostings()){
				float w_td = calcW_td(indexTerm, p);
				scores.put(p, scores.get(p) + (w_tq * w_td));
			}
		}
		
		for(Posting d : scores.keySet())
			scores.put(d, scores.get(d)/this.getAllTermsIn(d).size());
		
	}
}
