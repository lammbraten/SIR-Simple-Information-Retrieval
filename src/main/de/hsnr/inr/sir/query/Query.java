package de.hsnr.inr.sir.query;

import java.util.LinkedList;

public class Query {
	private LinkedList<QueryItem> queryitems;
	
	//TODO: Getter setters;
	
	public String toString(){
		String lstStr = "";
		for(QueryItem qui : queryitems)
			lstStr += qui.toString();
		
		lstStr += ";";
		return lstStr;
		
	}
}
