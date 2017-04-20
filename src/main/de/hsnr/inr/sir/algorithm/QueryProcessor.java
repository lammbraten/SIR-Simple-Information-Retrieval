package de.hsnr.inr.sir.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
			intersectQueryTerm(documents, qtl);
		}
		
		return new HashSet<Posting>(documents);
	}
	
	
	


	private void intersectQueryTerm(LinkedList<Posting> documents, LinkedList<QueryTerm> qtl) {
		switch(qtl.size()){
			case 0: throw new IllegalArgumentException("EmptyQuery");
			case 1: processSingleQueryTermList(documents, qtl);
				break;
			case 2: processTupleQueryTermList(documents, qtl);
				break;
			default: processMuliQueryTermList(documents, qtl);
				
		}
	}

	private void processMuliQueryTermList(LinkedList<Posting> documents, LinkedList<QueryTerm> qtl) {
		PriorityQueue<QueryTerm> terms = getTermsSortedByFrequency(qtl);
		LinkedList<Posting> result = new LinkedList<Posting>();
		while(!terms.isEmpty()){
			QueryTerm qt0 = terms.poll();
			result = qt0.getPostings();
			if(!terms.isEmpty()){
				QueryTerm qt1 = terms.poll();
				decideAndCallAndMethod(result, qt0, qt1);
				terms.add(new QueryTerm(result));
			}
		}
		documents.addAll(result);
		
	}

	private PriorityQueue<QueryTerm> getTermsSortedByFrequency(LinkedList<QueryTerm> qtl) {
		PriorityQueue<QueryTerm> terms = new PriorityQueue<QueryTerm>(new QueryTermFrequencyCompartor());
		
		for(QueryTerm qt : qtl){
			getPostingList(qt);
			terms.add(qt);
		}
				
		return terms;
	}

	private void processTupleQueryTermList(LinkedList<Posting> documents, LinkedList<QueryTerm> qtl) {
		QueryTerm qt0 = qtl.get(0);	
		QueryTerm qt1 = qtl.get(1);	
		getPostingList(qt0);
		getPostingList(qt1);
		
		decideAndCallAndMethod(documents, qt0, qt1);
	}
	
	private void decideAndCallAndMethod(LinkedList<Posting> documents, QueryTerm qt0, QueryTerm qt1) {
		if(qt0.isPositive() && qt1.isPositive()) //both positive
			documents.addAll(Intersect.and(qt0.getPostings(), qt1.getPostings()));
		else if(qt0.isPositive() && !qt1.isPositive()) //qt0 positive, qt1 negative
			documents.addAll(Intersect.andNot(qt0.getPostings(), qt1.getPostings()));
		else if(!qt0.isPositive() && qt1.isPositive()) //qt0 negative, qt1 positive
			documents.addAll(Intersect.andNot(qt1.getPostings(), qt0.getPostings()));
		else if(!qt0.isPositive() && !qt1.isPositive()) //qt0 negative, qt1 negative
			documents.addAll(Intersect.notAndNot(qt0.getPostings(), qt1.getPostings(), index.getPostings()));
	}

	private void processSingleQueryTermList(LinkedList<Posting> documents, List<QueryTerm> qtl) {
		for(QueryTerm qt : qtl){
			getPostingList(qt);
			documents.addAll(qt.getPostings());
		}
	}
	
	

	private void getPostingList(QueryTerm qt){
		if(qt.isGhost())
			qt.setPostings(index.getTerm(qt.getName()).getPostings().elementSet());
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
