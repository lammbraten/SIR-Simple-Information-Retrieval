package de.hsnr.inr.sir.query;

import java.util.LinkedList;

import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.dictionary.Posting;

public class ConcreteQueryTerm extends AbstractQueryTerm {



		
	ConcreteQueryTerm(String name){
		super(name);
	}
	
	ConcreteQueryTerm(String name, LinkedList<Posting> postings){
		super(name, postings);
	}
	
	public ConcreteQueryTerm(LinkedList<Posting> postings){ //Dummy
		super(postings);
	}
	
	public void invert(){
		if(positive)
			positive = false;
		else
			positive = true;		
	}
	
	public static QueryItem create(String name){
		return new ConcreteQueryTerm(name.toLowerCase());
	}
	
	@Override
	public String toString(){
		if(positive)
			return name + " ";
		return "NOT " + name + " ";
	}

	@Override
	public void setPostingsFromIndex(Index index) {
		if(isGhost())
			setPostings(index.getTerm(getName()).getPostings());
		
	}


	
}
