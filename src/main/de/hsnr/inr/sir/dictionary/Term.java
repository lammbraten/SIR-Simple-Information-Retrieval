package de.hsnr.inr.sir.dictionary;

import com.google.common.collect.TreeMultiset;

public class Term implements Comparable<Term>{
	private String value;
	TreeMultiset<Posting> postings;
	
	
	public Term(String value){
		this.value = value;
		
		postings = TreeMultiset.create();
	}
	
	@Override
	public int hashCode(){
		return value.hashCode();
	}

	public TreeMultiset<Posting> getPostings() {
		return postings;
	}

	public void append(TreeMultiset<Posting> postings) {
		this.postings.addAll(postings);		
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

	public String getValue() {
		return value;
	}
}
