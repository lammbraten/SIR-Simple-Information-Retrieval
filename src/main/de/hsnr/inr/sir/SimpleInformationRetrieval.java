package de.hsnr.inr.sir;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.google.common.io.Files;

import de.hsnr.inr.sir.algorithm.QueryProcessor;
import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.Term;
import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryHandler;
import de.hsnr.inr.sir.textprocessing.Tokenizer;

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
		buildIndex();
		System.out.println("Alle Wesen an ihren Platz!");
		qp = new QueryProcessor(index);
	}

	private void buildIndex() {
		System.out.println("Decke das Tischlein!");
		index = new Index();
		for(File f : corpus.listFiles()){
			try {
				index.addAll(extractTerms(f));
			} catch (IOException e) {
				System.err.println("Märchen " + f.getName() + " ist verflucht und konnte nicht geöffnet werden!");
			}
		}
		index.buildPostingList();
		//index.write("TestIndex.txt");
	}
	
	private List<Term> extractTerms(File f) throws IOException{
		System.out.println("Lese: " + Files.getNameWithoutExtension(f.getName()));
		LinkedList<Term> terms = new LinkedList<Term>(); 
		for(String termStr : Tokenizer.tokenize(f))
			terms.add(new Term(termStr, new Posting(Files.getNameWithoutExtension(f.getName()))));
		return terms;
	}

	private void handleFiles() {
		System.out.println("Lerne Geschichten auswendig!");
		corpus = new File(dirPath);
		checkIfDir();
	}
	
	private void checkIfDir() {
		if(!corpus.isDirectory())
			throw new IllegalArgumentException("Quelldatei muss ein Ordner sein!");
		
		//for(File f : corpus.listFiles())
		//	System.out.println(f);
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

	public HashSet<Posting> startInformationRetrieval() {
		 return qp.process(query);
	}





}
