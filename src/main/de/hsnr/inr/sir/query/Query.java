package de.hsnr.inr.sir.query;

import java.util.LinkedList;

public class Query {
	private LinkedList<QueryItem> queryitems;
	
	public Query(LinkedList<QueryItem> queryitems){
		this.queryitems = queryitems;
	}
	
	
	//TODO: Getter setters;
	
	
	public String toString(){
		String lstStr = "";
		for(QueryItem qui : queryitems)
			lstStr += qui.toString();
		
		lstStr += ";";
		return lstStr;
		
	}
}
