package de.hsnr.inr.sir.query;

import java.util.LinkedList;

public class Query {
	private LinkedList<QueryItem> queryitems;
	
	public Query(LinkedList<QueryItem> queryitems){
		this.queryitems = queryitems;
	}
	
	/**
	 * TO KNF </br></br>
	 * A AND B AND C </br>
	 * OR </br>
	 * A AND D; </br>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<LinkedList<QueryItem>> getAndConjunctions(){
		LinkedList<LinkedList<QueryItem>> conjunctions = new LinkedList<LinkedList<QueryItem>>();
		LinkedList<QueryItem> terms = new LinkedList<QueryItem>();
		
		for(QueryItem qui : queryitems){
			if(qui instanceof QueryConjunction){
				if(qui.getName().equals(QueryConjunction.OR)){
					conjunctions.add((LinkedList<QueryItem>) terms.clone());
					terms = new LinkedList<QueryItem>();
				}
			} else 
				terms.add((QueryTerm) qui);
		}
		conjunctions.add(terms);
		return conjunctions;
	}
	
	public LinkedList<QueryItem> getQueryitems() {
		return queryitems;
	}

	public void setQueryitems(LinkedList<QueryItem> queryitems) {
		this.queryitems = queryitems;
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
	public boolean isEmpty() {
		return queryitems.isEmpty();
	}
}
