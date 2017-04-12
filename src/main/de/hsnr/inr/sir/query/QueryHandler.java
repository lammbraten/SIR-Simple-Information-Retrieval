package de.hsnr.inr.sir.query;

import java.util.LinkedList;
import java.util.List;

public class QueryHandler {
	
	
	public static Query parseQuery(String query){
		List<String> queryStringList = QueryTokenizer.tokenize(query);
		LinkedList<QueryItem> queryitems = new LinkedList<QueryItem>();
		
		for(String queryStringItem : queryStringList)
			queryitems.add(QueryItem.create(queryStringItem));
		
		return new Query(queryitems);
	}
}
