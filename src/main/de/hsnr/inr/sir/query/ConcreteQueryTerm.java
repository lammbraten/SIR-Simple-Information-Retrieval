package de.hsnr.inr.sir.query;

import java.util.LinkedList;
import de.hsnr.inr.sir.dictionary.Posting;

public class ConcreteQueryTerm extends AbstractQueryTerm {

	private boolean positive = true;

		
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
	
	public boolean isPositive(){
		return positive;
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


	
}
