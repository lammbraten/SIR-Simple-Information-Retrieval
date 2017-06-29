package de.hsnr.inr.sir;

import java.util.LinkedList;
import java.util.List;

import de.hsnr.inr.sir.algorithm.QueryProcessor;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.Term;
import de.hsnr.inr.sir.dictionary.VectorIndex;
import de.hsnr.inr.sir.query.AbstractQueryTerm;
import de.hsnr.inr.sir.query.QueryItem;

public class VectorInformationRetrieval extends SimpleInformationRetrieval {

	public VectorInformationRetrieval(String dirPath) {
		super(dirPath, false);

	}
	
	@Override
	void initBoolean(){
		System.out.println("Vector start");
		setIndex(new VectorIndex(getCorpus()));
		setQueryProcessor(new QueryProcessor(getIndex()));
	}
	
	@Override
	public List<Posting> startInformationRetrieval() {
		System.out.println("Vector processing");
		
		VectorIndex vi = (VectorIndex) this.getIndex();
		
		return vi.fastCosineScore(queryToTermList(), 10);
	}

	private LinkedList<Term> queryToTermList() {
		LinkedList<Term> termList = new LinkedList<Term>();
		for(QueryItem qi : getQuery().getQueryitems())
			if(qi instanceof AbstractQueryTerm)
				termList.add(new Term(qi.getName()));
				
		return termList;
	}
}
