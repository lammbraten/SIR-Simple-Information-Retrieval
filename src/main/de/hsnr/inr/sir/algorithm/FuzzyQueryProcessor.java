package de.hsnr.inr.sir.algorithm;

import java.util.LinkedList;

import de.hsnr.inr.sir.dictionary.FuzzyIndex;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.WeightedPostingComparator;
import de.hsnr.inr.sir.query.AbstractQueryTerm;
import de.hsnr.inr.sir.query.ConcreteQueryTerm;
import de.hsnr.inr.sir.query.PhraseQuery;
import de.hsnr.inr.sir.query.ProximityQuery;
import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryItem;

public class FuzzyQueryProcessor extends QueryProcessor {

	public FuzzyQueryProcessor(FuzzyIndex index) {
		super(index);
	}

	public LinkedList<Posting> process(Query query){
		LinkedList<Posting> documents = new LinkedList<Posting>();
		
		for(LinkedList<QueryItem> qil : query.getAndConjunctions())
			documents = FuzzyIntersect.or(documents, intersectQueryTerm(qil));
		
		documents.sort(new WeightedPostingComparator());
		return documents;
	}
	
	@Override
	protected LinkedList<Posting> processSingleQueryItem(QueryItem qi) {
		//System.out.println("process single query items on fuzzy");
		
		if(qi instanceof ConcreteQueryTerm){
			ConcreteQueryTerm qt = (ConcreteQueryTerm) qi;
			qt.setPostingsFromIndex(getIndex());
			if(qt.isPositive())
				return qt.getPostings();
			else
				return FuzzyIntersect.not(qt, getIndex());
		}else if(qi instanceof PhraseQuery){
			PhraseQuery phq = (PhraseQuery) qi;
			phq.setPostingsFromIndex(getIndex());
			return phq.getPostings();
		}else if(qi instanceof ProximityQuery){
			ProximityQuery pq = (ProximityQuery) qi;
			pq.setPostingsFromIndex(getIndex());
			return pq.getPostings();
		}
		throw new IllegalArgumentException("Couldn't process QueryItem");
	}
	
	
	@Override
	protected LinkedList<Posting> decideAndCallAndMethod(AbstractQueryTerm qt0, AbstractQueryTerm qt1) {
		//System.out.println("decide-Method @ Fuzzy");
		
		if(qt0.isPositive() && qt1.isPositive()) //both positive
			return FuzzyIntersect.and(qt0, qt1, getIndex());
		else if(qt0.isPositive() && !qt1.isPositive()) //qt0 positive, qt1 negative
			return FuzzyIntersect.andNot(qt0.getPostings(), qt1.getPostings());
		else if(!qt0.isPositive() && qt1.isPositive()) //qt0 negative, qt1 positive
			return FuzzyIntersect.andNot(qt1.getPostings(), qt0.getPostings());
		else if(!qt0.isPositive() && !qt1.isPositive()) //qt0 negative, qt1 negative
			return FuzzyIntersect.notAndNot(qt0.getPostings(), qt1.getPostings(), getIndex().getPostings());
		throw new IllegalStateException("Something went terrible wrong!");
	}
	
	public FuzzyIndex getIndex() {
		return (FuzzyIndex) super.getIndex();
	}

	public void setIndex(FuzzyIndex index) {
		super.setIndex(index);
	}
}

