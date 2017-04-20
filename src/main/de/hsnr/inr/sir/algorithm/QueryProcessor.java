package de.hsnr.inr.sir.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import de.hsnr.inr.sir.dictionary.FrequencyCompator;
import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.Term;
import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryTerm;

public class QueryProcessor {
	
	private Index index;
	
	public QueryProcessor(Index index){
		this.setIndex(index);
	}
	
	public HashSet<Posting> process(Query query){
		HashSet<Posting> documents = new HashSet<Posting>();
		
		for(LinkedList<QueryTerm> qtl : query.getAndConjunctions()){
			intersectQueryTerm(documents, qtl);
		}
		
		return documents;
	}
	
	
	


	private void intersectQueryTerm(HashSet<Posting> documents, LinkedList<QueryTerm> qtl) {
		switch(qtl.size()){
			case 0: throw new IllegalArgumentException("EmptyQuery");
			case 1: processSingleQueryTermList(documents, qtl);
				break;
			case 2: processTupleQueryTermList(documents, qtl);
				break;
			default: processMuliQueryTermList(documents, qtl);
				
		}
	}

	private void processMuliQueryTermList(HashSet<Posting> documents, LinkedList<QueryTerm> qtl) {
		PriorityQueue<QueryTerm> terms = getTermsSortedByFrequency(qtl);
		
		QueryTerm result = terms.poll().getPostings();
		
		while(!terms.isEmpty() && result != null){
			QueryTerm list = terms.poll();
			result = 
		}
			

		
	}

	private PriorityQueue<QueryTerm> getTermsSortedByFrequency(LinkedList<QueryTerm> qtl) {
		PriorityQueue<QueryTerm> terms = new PriorityQueue<QueryTerm>(new QueryTermFrequencyCompartor());
		
		for(QueryTerm qt : qtl){
			getPostingList(qt);
			terms.add(qt);
		}
				
		return terms;
	}

	private void processTupleQueryTermList(HashSet<Posting> documents, LinkedList<QueryTerm> qtl) {
		QueryTerm qt0 = qtl.get(0);	
		QueryTerm qt1 = qtl.get(1);	
		getPostingList(qt0);
		getPostingList(qt1);
		
		decideAndCallAndMethod(documents, qt0, qt1);
	}

	private void decideAndCallAndMethod(HashSet<Posting> documents, QueryTerm qt0, QueryTerm qt1) {
		if(qt0.isPositive() && qt1.isPositive()) //both positive
			documents.addAll(Intersect.and(qt0.getPostings(), qt1.getPostings()));
		else if(qt0.isPositive() && !qt1.isPositive()) //qt0 positive, qt1 negative
			documents.addAll(Intersect.andNot(qt0.getPostings(), qt1.getPostings()));
		else if(!qt0.isPositive() && qt1.isPositive()) //qt0 negative, qt1 positive
			documents.addAll(Intersect.andNot(qt1.getPostings(), qt0.getPostings()));
		else if(!qt0.isPositive() && !qt1.isPositive()) //qt0 negative, qt1 negative
			documents.addAll(Intersect.notAndNot(qt0.getPostings(), qt1.getPostings(), index.getPostings()));
	}

	private void processSingleQueryTermList(HashSet<Posting> documents, List<QueryTerm> qtl) {
		for(QueryTerm qt : qtl){
			getPostingList(qt);
			documents.addAll(qt.getPostings());
		}
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
