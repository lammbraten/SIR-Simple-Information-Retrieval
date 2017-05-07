package de.hsnr.inr.sir.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.query.PhraseQuery;
import de.hsnr.inr.sir.query.ProximityQuery;
import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryItem;
import de.hsnr.inr.sir.query.AbstractQueryTerm;
import de.hsnr.inr.sir.query.ConcreteQueryTerm;

public class QueryProcessor {
	
	private Index index;
	
	public QueryProcessor(Index index){
		this.setIndex(index);
	}
	
	
	public HashSet<Posting> process(Query query){
		LinkedList<Posting> documents = new LinkedList<Posting>();
		
		for(LinkedList<QueryItem> qil : query.getAndConjunctions()){
			documents.addAll(intersectQueryTerm(qil));
		}
		
		return new HashSet<Posting>(documents);
	}
	
	
	


	private LinkedList<Posting> intersectQueryTerm(LinkedList<QueryItem> qil) {
		switch(qil.size()){
			case 0: throw new IllegalArgumentException("EmptyQuery");
			case 1: return processSingleQueryItem(qil.getFirst());
			case 2: return processTupleQueryItemList(qil.get(0), qil.get(1));
			default: return processMuliQueryItemList(qil);
				
		}
	}

	private LinkedList<Posting> processMuliQueryItemList(LinkedList<QueryItem> qil) {
		//Filtern

		LinkedList<ConcreteQueryTerm> qtl = new LinkedList<ConcreteQueryTerm>();
		LinkedList<PhraseQuery> phql = new LinkedList<PhraseQuery>();
		LinkedList<ProximityQuery> pql = new LinkedList<ProximityQuery>();
		LinkedList<Posting> result = new LinkedList<Posting>();
		
		assignQueryItemToMatchingList(qil, qtl, phql, pql);
		//process queryterms

		LinkedList<AbstractQueryTerm> aqtl = new LinkedList<AbstractQueryTerm>();
		aqtl.addAll(qtl);
		aqtl.addAll(phql);
		aqtl.addAll(pql);
		
		PriorityQueue<AbstractQueryTerm> terms = getTermsSortedByFrequency(aqtl);
		//process phrasequeries
		//process proximityqueries
		

		while(!terms.isEmpty()){
			AbstractQueryTerm qt0 = terms.poll();
			result = qt0.getPostings();
			if(!terms.isEmpty()){
				AbstractQueryTerm qt1 = terms.poll();
				result = decideAndCallAndMethod(qt0, qt1);
				terms.add(new ConcreteQueryTerm(result));
			}
		}
		return result;
	}


	/**
	 * Filters the list of query items to process them separately
	 * @param queryItems
	 * @param queryTerms
	 * @param phraseQueries
	 * @param proximityQueries
	 */
	private void assignQueryItemToMatchingList(LinkedList<QueryItem> queryItems, LinkedList<ConcreteQueryTerm> queryTerms,
			LinkedList<PhraseQuery> phraseQueries, LinkedList<ProximityQuery> proximityQueries){
		for(QueryItem qi : queryItems){
			try {
				if(qi instanceof ConcreteQueryTerm)
					queryTerms.add((ConcreteQueryTerm) qi);
				else if(qi instanceof PhraseQuery)
					phraseQueries.add((PhraseQuery) qi);
				else if(qi instanceof ProximityQuery)
					proximityQueries.add((ProximityQuery) qi);
				else
					throw new Exception("Couldn't assign QueryItem to a processing list");
			} catch (Exception e) {
				System.err.println(e);
				e.printStackTrace();
			}
		}
	}

	private LinkedList<Posting> processTupleQueryItemList(QueryItem qi1, QueryItem qi2) {
		if(qi1 instanceof AbstractQueryTerm && qi2 instanceof AbstractQueryTerm){
			AbstractQueryTerm qt0 = (AbstractQueryTerm) qi1;	
			AbstractQueryTerm qt1 = (AbstractQueryTerm) qi2;	
			qt0.setPostingsFromIndex(index);
			qt1.setPostingsFromIndex(index);
			
			return decideAndCallAndMethod(qt0, qt1);
		}
		throw new IllegalArgumentException("Couldn't process tuple QueryItem");
	}

	private LinkedList<Posting> processSingleQueryItem(QueryItem qi) {
		if(qi instanceof ConcreteQueryTerm){
			ConcreteQueryTerm qt = (ConcreteQueryTerm) qi;
			qt.setPostingsFromIndex(index);
			if(qt.isPositive())
				return qt.getPostings();
			else
				return Intersect.not(qt.getPostings(), index.getPostings());
		}else if(qi instanceof PhraseQuery){
			PhraseQuery phq = (PhraseQuery) qi;
			phq.setPostingsFromIndex(index);
			return phq.getPostings();
		}else if(qi instanceof ProximityQuery){
			ProximityQuery pq = (ProximityQuery) qi;
			pq.setPostingsFromIndex(index);
			return pq.getPostings();
		}
		throw new IllegalArgumentException("Couldn't process QueryItem");
	}


	private PriorityQueue<AbstractQueryTerm> getTermsSortedByFrequency(LinkedList<AbstractQueryTerm> qtl) {
		PriorityQueue<AbstractQueryTerm> terms = new PriorityQueue<AbstractQueryTerm>(new QueryTermFrequencyCompartor());
		
		for(AbstractQueryTerm qt : qtl){
			qt.setPostingsFromIndex(index);
			terms.add(qt);
		}
				
		return terms;
	}

	private LinkedList<Posting> decideAndCallAndMethod(AbstractQueryTerm qt0, AbstractQueryTerm qt1) {
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

	/*
	private void getPostingList(ConcreteQueryTerm qt){
		if(qt.isGhost())
			qt.setPostings(index.getTerm(qt.getName()).getPostings());
	}
	*/
	
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
