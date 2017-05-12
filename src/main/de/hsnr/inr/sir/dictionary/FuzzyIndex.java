package de.hsnr.inr.sir.dictionary;

import java.util.HashMap;

public class FuzzyIndex extends Index{

	public static final float JACCARD_THRESHOLD = 0.00f;
	private HashMap<String, JaccardDegree> jaccardDegreeMap = new HashMap<String, JaccardDegree>();
	private HashMap<Posting, HashMap<Term, Float>> fuzzyAffiliationDegree = new HashMap<Posting, HashMap<Term, Float>>();

	
	
	public void calcJaccardDegreeMatrix(){
		for(Term t : this.dictionary){
			for(Term u : this.dictionary){
				String key = JaccardDegree.calcKey(t, u);

				if(!jaccardDegreeMap.containsKey(key)){
					float degree = JaccardDegree.calcDegree(t, u);
					if(degree >= JACCARD_THRESHOLD)
						jaccardDegreeMap.put(key, new JaccardDegree(t, u, key, degree));
				}
			}
		}
	}
	
	/*	

	public void calcFuzzyAffiliationDegreeMatrix(){
		for(Posting p : this.postings){
			fuzzyAffiliationDegree.put(p, new HashMap<Term, Float>());
			for(Term t : this.dictionary){
				fuzzyAffiliationDegree.get(p).put(t, calcOgawa(p, t));
			}			
		}
	}

*/	

	public void calcFuzzyAffiliationDegreeMatrix(){
		for(Posting p : this.postings){
			fuzzyAffiliationDegree.put(p, new HashMap<Term, Float>());
		}
	
		float product;
		for(Term u : this.dictionary){	
			for(Term t : this.dictionary){
				for(Posting p : u.getPostings()){
					if(u.hasPosting(p)){

						if(fuzzyAffiliationDegree.get(p).get(t) == null)
							product = 1f;
						else
							product = fuzzyAffiliationDegree.get(p).get(t);
						product *= 1f - getJaccardDegreeOf(u, t); 
						fuzzyAffiliationDegree.get(p).put(t, 1f-product);
					}
					
				}				
			}
		}
	}

	public HashMap<String, JaccardDegree> getJaccardDegreeMap() {
		return jaccardDegreeMap;
	}

	public void setJaccardDegreeMap(HashMap<String, JaccardDegree> jaccardDegreeMap) {
		this.jaccardDegreeMap = jaccardDegreeMap;
	}
	
	public float getJaccardDegreeOf(Term t, Term u){
		JaccardDegree jaccardDegree = jaccardDegreeMap.get(JaccardDegree.calcKey(t, u));
		
		if(jaccardDegree == null)
			return 0.0f;
		
		return jaccardDegree.getDegree();
	}
	
	public float getOgawaDegreeOf(Posting p, Term t){
		return fuzzyAffiliationDegree.get(p).get(t);
	}
	
	/**
	 * W(D,t) = 1 - (PRODUCT(1-c(u,t)) (for each Term u in Document d))
	 * @return
	 */
	/*
	private float calcOgawa(Posting p, Term t){
		float product = 1f;
		
		for(Term u : getAllTermsIn(p))
			product *= 1 - getJaccardDegreeOf(u, t); 
		
		return 1 - product;
	}
	*/
	private float calcOgawa(Posting p, Term t){
		float product = 1f;
		
		for(Term u : dictionary)
			if(u.hasPosting(p))
				product *= 1f - getJaccardDegreeOf(u, t); 
		
		return 1f - product;
	}

}
