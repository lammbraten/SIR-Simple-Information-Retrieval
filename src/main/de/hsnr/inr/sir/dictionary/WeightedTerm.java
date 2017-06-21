package de.hsnr.inr.sir.dictionary;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class WeightedTerm extends Term implements Serializable {

	private static final long serialVersionUID = -2817490402556439578L;

	/** weight in document (posting) d */
	//private HashMap<Posting, Float> w_td = new HashMap<Posting, Float>();
	
	/** Number of occurrences in document d */
	private HashMap<Posting, Integer> tf_td = new HashMap<Posting, Integer>();
	
	/** Number of documents in which this term occurs. */
	private int df_t = 0;


	public WeightedTerm(String value) {
		super(value);
	}
	
	public WeightedTerm(String value, Posting posting){
		super(value, posting);
		updateTf_td(posting);
		df_t++;
	}
	
	private void updateTf_td(Posting p){
		Integer tf = tf_td.get(p);
		if(tf == null)
			tf_td.put(p, 1);
		else
			tf_td.put(p, tf + 1);
	}
	
	@Override
	public void append(LinkedList<Posting> postings) {	
		//super.append(postings);
		for(Posting p : postings){
			int pIndex = this.postings.indexOf(p);
			if(pIndex != -1)
				this.postings.get(pIndex).mergePositions(p);
			else{
				this.postings.add(p);
				df_t++;
			}
			updateTf_td(p);
		}
	}

	public int getTf_td(Posting d){
		Integer tf = tf_td.get(d);
		if(tf == null)
			return 0;
		else
			return tf;
	}
	
	/*
	public HashMap<Posting, Float> getW_td() {
		return w_td;
	}


	public void setW_td(HashMap<Posting, Float> w_td) {
		this.w_td = w_td;
	}


	public HashMap<Posting, Integer> getTf_td() {
		return tf_td;
	}


	public void setTf_td(HashMap<Posting, Integer> tf_td) {
		this.tf_td = tf_td;
	}	*/


	public int getDf_t() {
		return df_t;
	}

}
