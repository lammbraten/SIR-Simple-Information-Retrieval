package de.hsnr.inr.sir.query;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Query {
	private LinkedList<QueryItem> queryitems;
	
	public Query(LinkedList<QueryItem> queryitems){
		this.queryitems = queryitems;
	}
	/**
	 * A AND B AND C 
	 * OR
	 * A AND D;
	 * @return
	 */
	public HashSet<LinkedList<QueryTerm>> getAndConjunctions(){
		HashSet<LinkedList<QueryTerm>> conjunctionsSet = new HashSet<LinkedList<QueryTerm>>();
		LinkedList<QueryTerm> terms = new LinkedList<QueryTerm>();
		
		for(QueryItem qui : queryitems){
			if(qui instanceof QueryConjunction){
				conjunctionsSet.add((LinkedList<QueryTerm>) terms.clone());
				terms = new LinkedList<QueryTerm>();
			} else 
				terms.add((QueryTerm) qui);
		}
		conjunctionsSet.add(terms);
		return conjunctionsSet;
	}

	
	
	public String toString(){
		String lstStr = "";
		for(QueryItem qui : queryitems)
			lstStr += qui.toString();
		
		lstStr += ";";
		return lstStr;
		
	}
}
