package de.hsnr.inr.sir.query;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.NavigableSet;

import com.google.common.collect.TreeMultiset;

import de.hsnr.inr.sir.dictionary.Posting;

public class QueryTerm extends QueryItem {
	
	private LinkedList<Posting> postings;	
	private boolean positive = true;
	private boolean ghost = true;
		
	QueryTerm(String name){
		super(name);
	}
	
	QueryTerm(String name, LinkedList<Posting> postings){
		super(name);
		this.postings = postings;
		this.ghost = false;
	}
	
	public QueryTerm(LinkedList<Posting> postings){ //Dummy
		super(null);
		this.postings = new LinkedList<Posting>(new HashSet<Posting>(postings)); //deep copy
		this.ghost = false;
	}
	

	public void invert(){
		if(positive)
			positive = false;
		else
			positive = true;		
	}
	
	public boolean isPositive(){
		return positive;
	}
	
	public boolean isGhost(){
		return ghost;
	}
	
	public static QueryItem create(String name){
		return new QueryTerm(name);
	}
	
	@Override
	public String toString(){
		if(positive)
			return name + " ";
		return "NOT " + name + " ";
	}

	public LinkedList<Posting> getPostings() {
		if(isGhost())
			throw new IllegalStateException("Tried to get some postings of a ghost term");
		return postings;
	}

	public void setPostings(NavigableSet<Posting> postings) {
		this.postings = new LinkedList<Posting>(postings);
		ghost = false;
	}

	public void setPostings(TreeMultiset<Posting> postings) {
		this.postings = new LinkedList<Posting>();
		
		for(Posting p : postings)
			this.postings.add(p);
		
		ghost = false;
	}
	
}
