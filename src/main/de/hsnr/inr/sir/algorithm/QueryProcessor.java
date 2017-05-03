package de.hsnr.inr.sir.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryTerm;

public class QueryProcessor {
	
	private Index index;
	
	public QueryProcessor(Index index){
		this.setIndex(index);
	}
	
	
	public HashSet<Posting> process(Query query){
		LinkedList<Posting> documents = new LinkedList<Posting>();
		
		for(LinkedList<QueryTerm> qtl : query.getAndConjunctions()){
			documents.addAll(intersectQueryTerm(qtl));
		}
		
		return new HashSet<Posting>(documents);
	}
	
	
	


	private LinkedList<Posting> intersectQueryTerm(LinkedList<QueryTerm> qtl) {
		switch(qtl.size()){
			case 0: throw new IllegalArgumentException("EmptyQuery");
			case 1: return processSingleQueryTerm(qtl.getFirst());
			case 2: return processTupleQueryTermList(qtl);
			default: return processMuliQueryTermList(qtl);
				
		}
	}

	private LinkedList<Posting> processMuliQueryTermList(LinkedList<QueryTerm> qtl) {
		PriorityQueue<QueryTerm> terms = getTermsSortedByFrequency(qtl);
		LinkedList<Posting> result = new LinkedList<Posting>();
		while(!terms.isEmpty()){
			QueryTerm qt0 = terms.poll();
			result = qt0.getPostings();
			if(!terms.isEmpty()){
				QueryTerm qt1 = terms.poll();
				result = decideAndCallAndMethod(qt0, qt1);
				terms.add(new QueryTerm(result));
			}
		}
		return result;
		
	}

	private PriorityQueue<QueryTerm> getTermsSortedByFrequency(LinkedList<QueryTerm> qtl) {
		PriorityQueue<QueryTerm> terms = new PriorityQueue<QueryTerm>(new QueryTermFrequencyCompartor());
		
		for(QueryTerm qt : qtl){
			getPostingList(qt);
			terms.add(qt);
		}
				
		return terms;
	}

	private LinkedList<Posting> processTupleQueryTermList(LinkedList<QueryTerm> qtl) {
		//TODO: Change List-parameter to tuple
		QueryTerm qt0 = qtl.get(0);	
		QueryTerm qt1 = qtl.get(1);	
		getPostingList(qt0);
		getPostingList(qt1);
		
		return decideAndCallAndMethod(qt0, qt1);
	}
	
	private LinkedList<Posting> decideAndCallAndMethod(QueryTerm qt0, QueryTerm qt1) {
		if(qt0.isPositive() && qt1.isPositive()) //both positive
			return Intersect.and(qt0.getPostings(), qt1.getPostings());
		else if(qt0.isPositive() && !qt1.isPositive()) //qt0 positive, qt1 negative
			return Intersect.andNot(qt0.getPostings(), qt1.getPostings());
		else if(!qt0.isPositive() && qt1.isPositive()) //qt0 negative, qt1 positive
			return Intersect.andNot(qt1.getPostings(), qt0.getPostings());
		else if(!qt0.isPositive() && !qt1.isPositive()) //qt0 negative, qt1 negative
			return Intersect.notAndNot(qt0.getPostings(), qt1.getPostings(), index.getPostings());
		throw new IllegalStateException("Something went terrible wrong!");
	}

	private LinkedList<Posting> processSingleQueryTerm(QueryTerm qt) {
		getPostingList(qt);
		if(qt.isPositive())
			return qt.getPostings();
		else
			return Intersect.not(qt.getPostings(), index.getPostings());

	}
	
	

	private void getPostingList(QueryTerm qt){
		if(qt.isGhost())
			qt.setPostings(index.getTerm(qt.getName()).getPostings());
	}
	
	/*
	private String tupleIntersect(List<QueryTerm> qtl) {
		TreeMultiset<Posting> p1 = index.getTerm(qtl.get(1).getName()).getPostings();
		//TODO merge(QUERYTERM qt1, QUERYTERM qt2);
		return Intersect.merge(p0, p1);
	}*/

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

}
