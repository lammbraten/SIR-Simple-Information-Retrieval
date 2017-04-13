package de.hsnr.inr.sir;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryHandler;
import de.hsnr.inr.sir.textprocessing.Tokenizer;

public class SimpleInformationRetrieval {

	private static final String DIR_PATH_PAR = "-p";

	private static String dir_path;
	
	private File corpus;
	//Statisch private QueryHandler qh;
	
	public SimpleInformationRetrieval(){
		System.out.println("Verarbeitung gestart!");
		handleFiles();
		System.out.println("Dateien eingelesen!");
		
	}

	private void handleFiles() {
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
		if(!parseArgs(args))
			throw new IllegalArgumentException("Fehler beim parsen der Argumente!");		
		if(dir_path == null || dir_path.isEmpty())
			throw new IllegalArgumentException("Fehler! Kein Ordner-Pfad agegeben! -p C:\\pfad\\zum\\Ordner");
		
		System.out.println("Hallo Welt");
		System.out.println("Ordnerpfad: " + dir_path);
		
		SimpleInformationRetrieval sir = new SimpleInformationRetrieval();

		sir.askForQuery();
		sir.startInformationRetrieval();
	}

	private void askForQuery() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Query q = null;
		System.out.println("Was soll im Märchen vorkommen?");


		try {
			q = QueryHandler.parseQuery(br.readLine());
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println(q);

		
	}

	private void startInformationRetrieval() {
		
		
		
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
