package de.hsnr.inr.sir.dictionary;

import java.util.LinkedList;

public class Index {
	
	/**
	 * TODO: Baum implementieren?
	 */
	//private LinkedList<Term> dictionary;
	private LinkedList<Term> dictionary;
	
	public Index(){
		this.dictionary = new LinkedList<Term>();
	}
	
	public void add(Term t){
		int index = this.dictionary.indexOf(t);
		if(index != -1)
			dictionary.get(index).append(t.getPostings());
		else
			dictionary.add(t);
	}

}
