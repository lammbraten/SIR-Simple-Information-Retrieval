package de.hsnr.inr.sir.algorithm;

import java.util.Comparator;

import de.hsnr.inr.sir.query.ConcreteQueryTerm;

public class QueryTermFrequencyCompartor implements Comparator<ConcreteQueryTerm> {

	@Override
	public int compare(ConcreteQueryTerm o1, ConcreteQueryTerm o2) {
		//System.err.println(o1 +" " + o1.getPostings().size() + "-" + o2 + " " +o2.getPostings().size() + "=" + (o1.getPostings().size() - o2.getPostings().size()));
		return o1.getPostings().size() - o2.getPostings().size();
	}

}
