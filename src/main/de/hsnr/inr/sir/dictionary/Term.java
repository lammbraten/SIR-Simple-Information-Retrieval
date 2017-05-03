package de.hsnr.inr.sir.dictionary;

import java.util.LinkedList;

public class Term implements Comparable<Term>{
	private String value;
	private LinkedList<Posting> postings; 
	
	public Term(String value){
		this.value = value;
		
		//postings = TreeMultiset.create();
		postings = new LinkedList<Posting>();
	}
	
	public Term(String value, Posting posting){
		this(value);
		postings.add(posting);
	}
	
	public int getFrequence(){
		return postings.size();
	}
	
	@Override
	public int hashCode(){
		return value.hashCode();
	}

	public LinkedList<Posting> getPostings() {
		return postings;
	}

	public void append(LinkedList<Posting> postings) {
		//this.postings.addAll(postings);		
		for(Posting p : postings){
			int pIndex = this.postings.indexOf(p);
			if(pIndex != -1)
				this.postings.get(pIndex).mergePositions(p);
			else
				this.postings.add(p);
		}
	}

	public boolean hasPosting(Posting p){
		return this.postings.contains(p);
	}
	
	@Override
	public int compareTo(Term o) {
		if(o instanceof Term){
			Term t = (Term) o;
			return value.compareTo(t.getValue());			
		}
		throw new ClassCastException("Couldn't compare these classes");
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Term){
			Term t = (Term) o;
			return t.value.equals(this.value);
		}
		return false;
	}
	
	@Override
	public String toString(){
		return value +": " + postings;
	}

	public String getValue() {
		return value;
	}
	
}
