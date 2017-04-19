package de.hsnr.inr.sir.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
		HashSet<Posting> documents = new HashSet<Posting>();
		
		for(LinkedList<QueryTerm> qtl : query.getAndConjunctions()){
			intersectQueryTerm(documents, qtl);
		}
		
		return documents;
	}
	
	
	


	private void intersectQueryTerm(HashSet<Posting> documents, LinkedList<QueryTerm> qtl) {
		switch(qtl.size()){
			case 0: 
				break;
			case 1: processSingleQueryTermList(documents, qtl);
				break;
			case 2: processTupleQueryTermList(documents, qtl);
				break;
		}

			
		
		
		
		//for(QueryTerm qt : qtl){
			//TreeMultiset<Posting> postings = index.getTerm(qt.getName()).getPostings();
		//TODO switch case	
		
		/*if(qtl.size() == 2 ) //Zwei Elemente
				documents.add(tupleIntersect(qtl));
			for(int i = )
				documents.add();*/
		
		
	}

	private void processTupleQueryTermList(HashSet<Posting> documents, LinkedList<QueryTerm> qtl) {
		QueryTerm qt0 = qtl.get(1);	
		QueryTerm qt1 = qtl.get(1);	
		LinkedList<Posting> pl0 = getPostingList(qt0);
		LinkedList<Posting> pl1 = getPostingList(qt1);
		
		if(qt0.isPositive() && qt1.isPositive()) //both positive
			documents.addAll(Intersect.and(pl0, pl1));
		else if(qt0.isPositive() && !qt1.isPositive()) //qt0 positive, qt1 negative
			documents.addAll(Intersect.andNot(pl0, pl1));
		else if(!qt0.isPositive() && qt1.isPositive()) //qt0 negative, qt1 positive
			documents.addAll(Intersect.andNot(pl1, pl0));
		else if(!qt0.isPositive() && !qt1.isPositive()) //qt0 negative, qt1 negative
			documents.addAll(Intersect.notAndNot(pl1, pl0));
	}

	private void processSingleQueryTermList(HashSet<Posting> documents, List<QueryTerm> qtl) {
		for(QueryTerm qt : qtl){
			documents.addAll(getPostingList(qt));
		}
	}

	private LinkedList<Posting> getPostingList(QueryTerm qt){
		LinkedList<Posting> pl = new LinkedList<Posting>();
		
		for(Posting p : index.getTerm(qt.getName()).getPostings())
			pl.add(p);
		
		return pl;
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
