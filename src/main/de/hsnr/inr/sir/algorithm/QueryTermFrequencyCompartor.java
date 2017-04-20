package de.hsnr.inr.sir.algorithm;

import java.util.Comparator;

import de.hsnr.inr.sir.query.QueryTerm;

public class QueryTermFrequencyCompartor implements Comparator<QueryTerm> {

	@Override
	public int compare(QueryTerm o1, QueryTerm o2) {
		return o1.getPostings().size() - o2.getPostings().size();
	}

}
