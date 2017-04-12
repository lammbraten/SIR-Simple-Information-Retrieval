package de.hsnr.inr.sir.query;

public class QueryTerm extends QueryItem {
	
	private boolean positive = true;
		
	QueryTerm(String name){
		super(name);
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
		return new QueryTerm(name);
	}
	
	@Override
	public String toString(){
		if(positive)
			return name + " ";
		return "NOT " + name + " ";
	}
	
}
