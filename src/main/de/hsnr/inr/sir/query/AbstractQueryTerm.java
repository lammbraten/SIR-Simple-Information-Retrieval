package de.hsnr.inr.sir.query;

import java.util.HashSet;
import java.util.LinkedList;

import com.google.common.collect.TreeMultiset;

import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.PostingNameComparator;

public abstract class AbstractQueryTerm extends QueryItem {
	
	private LinkedList<Posting> postings;	
	private boolean ghost = true;
	protected boolean positive = true;
	
	AbstractQueryTerm(String name){
		super(name);
	}
		
	AbstractQueryTerm(String name, LinkedList<Posting> postings) {
		super(name);
		this.postings = postings;
		this.postings.sort(new PostingNameComparator());
		this.ghost = false;
	}

	AbstractQueryTerm(LinkedList<Posting> postings){ //Dummy
		super(null);
		this.postings = new LinkedList<Posting>(new HashSet<Posting>(postings)); //deep copy
		this.postings.sort(new PostingNameComparator());
		this.ghost = false;
	}
	
	public boolean isPositive(){
		return positive;
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
		this.postings.sort(new PostingNameComparator());
		ghost = false;
	}

	public void setPostings(TreeMultiset<Posting> postings) {
		this.postings = new LinkedList<Posting>();
		
		for(Posting p : postings)
			this.postings.add(p);
		
		this.postings.sort(new PostingNameComparator());
		ghost = false;
	}
	
	public abstract void setPostingsFromIndex(Index index);
	@Override
	public abstract void invert();
}
