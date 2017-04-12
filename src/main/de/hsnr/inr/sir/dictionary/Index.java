package de.hsnr.inr.sir.dictionary;

import java.util.LinkedList;

import com.google.common.collect.TreeMultiset;

public class Index {
	
	/**
	 * TODO: Baum implementieren?
	 */
	private LinkedList<Term> dictionary;

	
	public Index(){
		this.dictionary =  new LinkedList<Term>();
	}
	

	public void add(Term t){
		int index = this.dictionary.indexOf(t);
		if(index != -1)
			dictionary.get(index).append(t.getPostings());
		else
			dictionary.add(t);
	}
	
	public Term getTerm(String termval){
		return getTerm(new Term(termval));
	}
	
	public Term getTerm(Term t){
		int index = this.dictionary.indexOf(t);
		if(index != -1)
			return this.dictionary.get(index);
		throw new IllegalArgumentException("No such term found");
	}
	
}
