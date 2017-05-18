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
	
	
	public LinkedList<Posting> process(Query query){
		LinkedList<Posting> documents = new LinkedList<Posting>();
		
		for(LinkedList<QueryItem> qil : query.getAndConjunctions())
			documents.addAll(intersectQueryTerm(qil));
		
		return documents;
	}
	
	protected LinkedList<Posting> intersectQueryTerm(LinkedList<QueryItem> qil) {
		switch(qil.size()){
			case 0: throw new IllegalArgumentException("EmptyQuery");
			case 1: return processSingleQueryItem(qil.getFirst());
			case 2: return processTupleQueryItemList(qil.get(0), qil.get(1));
			default: return processMuliQueryItemList(qil);	
		}
	}

	protected LinkedList<Posting> processMuliQueryItemList(LinkedList<QueryItem> qil) {
		LinkedList<Posting> result = new LinkedList<Posting>();
		LinkedList<AbstractQueryTerm> aqtl = new LinkedList<AbstractQueryTerm>();
		
		for(QueryItem qi : qil)
			aqtl.add((AbstractQueryTerm) qi);

		PriorityQueue<AbstractQueryTerm> terms = getTermsSortedByFrequency(aqtl);

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

	protected LinkedList<Posting> processTupleQueryItemList(QueryItem qi1, QueryItem qi2) {
		if(qi1 instanceof AbstractQueryTerm && qi2 instanceof AbstractQueryTerm){
			AbstractQueryTerm qt0 = (AbstractQueryTerm) qi1;	
			AbstractQueryTerm qt1 = (AbstractQueryTerm) qi2;	
			qt0.setPostingsFromIndex(index);
			qt1.setPostingsFromIndex(index);
			
			return decideAndCallAndMethod(qt0, qt1);
		}
		throw new IllegalArgumentException("Couldn't process tuple QueryItem");
	}

	protected LinkedList<Posting> processSingleQueryItem(QueryItem qi) {
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

	protected LinkedList<Posting> decideAndCallAndMethod(AbstractQueryTerm qt0, AbstractQueryTerm qt1) {
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

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

}
