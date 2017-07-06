package de.hsnr.inr.sir;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import de.hsnr.inr.sir.algorithm.FuzzyQueryProcessor;
import de.hsnr.inr.sir.algorithm.QueryProcessor;
import de.hsnr.inr.sir.dictionary.FuzzyIndex;
import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.dictionary.KGramIndex;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryConjunction;
import de.hsnr.inr.sir.query.QueryHandler;
import de.hsnr.inr.sir.query.QueryItem;

public class SimpleInformationRetrieval {

	private String dirPath;
	private File corpus;
	private KGramIndex index;
	private Query query = null;
	private QueryProcessor qp = null;
	private int r = 2;
	
	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}
	
	public void setQuery(String query) {
		this.query = QueryHandler.parseQuery(query);
	}

	public SimpleInformationRetrieval(String dirPath, boolean fuzzy){
		this.dirPath = dirPath;
		System.out.println("Fülle Froschteich!");
		handleFiles();
		System.out.println("Hänge Spieglein an die Wand!");
		if(fuzzy)
			initFuzzy();
		else
			initBoolean();
		System.out.println("Alle Wesen an ihren Platz!");
	}
	
	private void initFuzzy(){
		setIndex(new FuzzyIndex(getCorpus()));
		setQueryProcessor(new FuzzyQueryProcessor((FuzzyIndex) getIndex()));
	}
	
	void initBoolean(){
		setIndex(new KGramIndex(getCorpus()));
		setQueryProcessor(new QueryProcessor(getIndex()));
	}

	private void handleFiles() {
		System.out.println("Lerne Geschichten auswendig!");
		setCorpus(new File(dirPath));
		checkIfDir();
	}
	
	private void checkIfDir() {
		if(!getCorpus().isDirectory())
			throw new IllegalArgumentException("Quelldatei muss ein Ordner sein!");
	}



	void askForQuery() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		query = null;

		do {
			System.out.println("Was soll im Märchen vorkommen?");
			try {
				String input = br.readLine();
				if(input.startsWith("/set_J:"))
					setJ(input.substring(input.indexOf(':')+1));
				else if(input.startsWith("/set_r:"))
					setR(input.substring(input.indexOf(':')+1));
				else
				 setQuery(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while(query == null || query.isEmpty());
	}

	private void setR(String str) {
		this.r  = Integer.parseInt(str);
	}

	private void setJ(String str) {
		index.setTreshold(Float.parseFloat(str));
	}

	public List<Posting> startInformationRetrieval() {
		 return getQueryProcessor().process(query);
	}

	public String getAlternativeQueryTerms() {
		String str = "";
		
		for(QueryItem qi : query.getQueryitems()){
			if(qi instanceof QueryConjunction)
				continue;
			str += "Orginial Suchwort:" + qi.getName() + "\n";
			for(String termStr :  index.getCorrectedTermsForString(qi.getName()))
				str += "\t" + termStr + "\n";
		}
		return str;
	}
	
	public int getMinDocsPerQuery(){
		return r;
	}

	public Index getIndex() {
		return index;
	}

	public void setIndex(KGramIndex index) {
		this.index = index;
	}

	public File getCorpus() {
		return corpus;
	}

	public void setCorpus(File corpus) {
		this.corpus = corpus;
	}

	public QueryProcessor getQueryProcessor() {
		return qp;
	}

	public void setQueryProcessor(QueryProcessor qp) {
		this.qp = qp;
	}

	




}
