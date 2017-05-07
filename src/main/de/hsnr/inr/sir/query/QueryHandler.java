package de.hsnr.inr.sir.query;

import java.util.LinkedList;
import java.util.List;

public class QueryHandler {
	
	public static Query parseQuery(String query){
		List<String> queryStringList = QueryTokenizer.tokenize(query);
		LinkedList<QueryItem> queryitems = new LinkedList<QueryItem>();
		int notCounter = 0;
		
		for(String queryStringItem : queryStringList){
			if(isNot(queryStringItem)){
				notCounter++;
			}else{
				QueryItem qui = QueryItem.create(queryStringItem);				
				if((notCounter % 2) == 1)
					qui.invert();
				
				queryitems.add(qui);
				notCounter = 0;
			}	
		}
		
		return new Query(queryitems);
	}
	
	private static boolean isNot(String str){
		return str.equals(QueryConjunction.NOT);
	}
}
