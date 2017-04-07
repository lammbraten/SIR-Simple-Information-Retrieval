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

	@Override
	public int compareTo(Term o) {
		return this.value.compareTo(o.value);
	}
}
