package de.hsnr.inr.sir;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import de.hsnr.inr.sir.algorithm.QueryProcessor;
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

	public SimpleInformationRetrieval(String dirPath){
		this.dirPath = dirPath;
		System.out.println("Fülle Froschteich!");
		handleFiles();
		System.out.println("Hänge Spieglein an die Wand!");
		index = new Index(corpus);
		System.out.println("Alle Wesen an ihren Platz!");
		qp = new QueryProcessor(index);
	}

	private void handleFiles() {
		System.out.println("Lerne Geschichten auswendig!");
		corpus = new File(dirPath);
		checkIfDir();
	}
	
	private void checkIfDir() {
		if(!corpus.isDirectory())
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

	public LinkedList<Posting> startInformationRetrieval() {
		 return qp.process(query);
	}





}
