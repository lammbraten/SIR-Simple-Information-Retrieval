package de.hsnr.inr.sir.query;

import java.util.HashSet;
import java.util.LinkedList;

import com.google.common.collect.TreeMultiset;

import de.hsnr.inr.sir.dictionary.Posting;

public abstract class AbstractQueryTerm extends QueryItem {
	
	private LinkedList<Posting> postings;	
	private boolean ghost = true;
	
	AbstractQueryTerm(String name){
		super(name);
	}
		
	AbstractQueryTerm(String name, LinkedList<Posting> postings) {
		super(name);
		this.postings = postings;
		this.ghost = false;
	}

	AbstractQueryTerm(LinkedList<Posting> postings){ //Dummy
		super(null);
		this.postings = new LinkedList<Posting>(new HashSet<Posting>(postings)); //deep copy
		this.ghost = false;
	}
	
	@Override
	public void invert() {
		// TODO Auto-generated method stub

	}
	
	public boolean isGhost(){
		return ghost;
	}

	public LinkedList<Posting> getPostings() {
		if(isGhost())
			throw new IllegalStateException("Tried to get some postings of a ghost term");
		return postings;
	}

	public void setPostings(LinkedList<Posting> postings) {
		this.postings = postings;
		ghost = false;
	}

	public void setPostings(TreeMultiset<Posting> postings) {
		this.postings = new LinkedList<Posting>();
		
		for(Posting p : postings)
			this.postings.add(p);
		
		ghost = false;
	}
	
}
