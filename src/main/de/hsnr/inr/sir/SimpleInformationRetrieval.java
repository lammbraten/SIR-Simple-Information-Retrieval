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
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryHandler;

public class SimpleInformationRetrieval {

	private String dirPath;
	private File corpus;
	private Index index;
	private Query query = null;
	private QueryProcessor qp = null;
	
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
		setIndex(new Index(getCorpus()));
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
				 setQuery(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while(query == null || query.isEmpty());
	}

	public List<Posting> startInformationRetrieval() {
		 return getQueryProcessor().process(query);
	}

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
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
