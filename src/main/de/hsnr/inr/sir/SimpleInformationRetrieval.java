package de.hsnr.inr.sir;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.google.common.io.Files;

import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.Term;
import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryHandler;
import de.hsnr.inr.sir.query.QueryTerm;
import de.hsnr.inr.sir.textprocessing.Tokenizer;

public class SimpleInformationRetrieval {

	private static final String DIR_PATH_PAR = "-p";

	private static String dir_path;
	
	private File corpus;
	private Index index;
	private Query query = null;
	
	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public SimpleInformationRetrieval(){
		System.out.println("Fülle Froschteich!");
		handleFiles();
		System.out.println("Hänge den Spiegel an die Wand!");
		buildIndex();
		System.out.println("Alle Wesen an ihren Platz!");
		
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
		index.write("TestIndex.txt");
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
		corpus = new File(dir_path);
		checkIfDir();
	}
	
	private void checkIfDir() {
		if(!corpus.isDirectory())
			throw new IllegalArgumentException("Quelldatei muss ein Ordner sein!");
		
		//for(File f : corpus.listFiles())
		//	System.out.println(f);
	}

	public static void main(String[] args) {
		System.out.println("Hallo Märchenwelt!");
		if(!parseArgs(args))
			throw new IllegalArgumentException("Oh, eine Hexe hat deine Argumente verflucht!");		
		if(dir_path == null || dir_path.isEmpty())
			throw new IllegalArgumentException("Leider konnte ich keine Märchen finden. :-( Probier's doch mal so: -p C:\\pfad\\zum\\Märchenordner");
		
		System.out.println("Die Märchen liegen in: " + dir_path);
		
		SimpleInformationRetrieval sir = new SimpleInformationRetrieval();
		
		while(true){
			sir.askForQuery();
			System.out.println("Spieglein, Spieglein an der Wand, hast du solche Märchen zur Hand? \n" + sir.getQuery());
			sir.startInformationRetrieval();
		}
	}

	private void askForQuery() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		query = null;

		do {
			System.out.println("Was soll im Märchen vorkommen?");
			try {
				query = QueryHandler.parseQuery(br.readLine());
			} catch (IOException e) {
	
				e.printStackTrace();
			}
		} while(query == null || query.isEmpty());
	}

	public void startInformationRetrieval() {
		HashSet<String> documents = new HashSet<String>();
		for(List<QueryTerm> qtl : query.getAndConjunctions()){
			intersectQueryTerm(documents, qtl);
		}
		System.out.println(documents);
	}

	private void intersectQueryTerm(HashSet<String> documents, List<QueryTerm> qtl) {
		for(QueryTerm qt : qtl){
			for(Posting p : index.getTerm(qt.getName()).getPostings())
			documents.add(p.getValue());
		}
	}

	private static boolean parseArgs(String[] args) {
		for(int i = 0; i < (args.length - 1); i=i+2)			
			if(!parseKeyValue(args[i], args[i+1]))
				return false;
		return true;
	}

	private static boolean parseKeyValue(String key, String value) {
		if(isDPsetDP(key, value))
			return true;
		return false;
	}

	private static boolean isDPsetDP(String key, String value) {
		if(key.equals(DIR_PATH_PAR)){
			dir_path = value;
			return true;
		}
		return false;
	}

}
