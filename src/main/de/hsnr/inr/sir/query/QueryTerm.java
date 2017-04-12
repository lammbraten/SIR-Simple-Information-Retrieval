package de.hsnr.inr.sir.query;

public class QueryTerm extends QueryItem {
	
	QueryTerm(String name){
		super(name);
	}
	
	public static QueryItem create(String name){
		return new QueryTerm(name);
	}
	
}
