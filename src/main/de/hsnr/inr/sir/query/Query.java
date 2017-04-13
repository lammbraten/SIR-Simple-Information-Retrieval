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
	public LinkedList<LinkedList<QueryTerm>> getAndConjunctions(){
		LinkedList<LinkedList<QueryTerm>> conjunctions = new LinkedList<LinkedList<QueryTerm>>();
		LinkedList<QueryTerm> terms = new LinkedList<QueryTerm>();
		
		for(QueryItem qui : queryitems){
			if(qui instanceof QueryConjunction){
				if(qui.getName().equals(QueryConjunction.OR)){
					conjunctions.add((LinkedList<QueryTerm>) terms.clone());
					terms = new LinkedList<QueryTerm>();
				}
			} else 
				terms.add((QueryTerm) qui);
		}
		conjunctions.add(terms);
		return conjunctions;
	}

	
	
	public String toString(){
		String lstStr = "";
		for(QueryItem qui : queryitems){
			if(qui.getName().equals(QueryConjunction.OR))
				lstStr += "\n";
			lstStr += qui.toString();
		}
		
		lstStr += ";";
		return lstStr;
		
	}
}