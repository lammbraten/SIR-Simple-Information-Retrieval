package de.hsnr.inr.sir;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hsnr.inr.sir.textprocessing.Tokenizer;

public class SimpleInformationRetrieval {

	private static final String DIR_PATH_PAR = "-p";

	private static String dir_path;
	
	private File corpus;
	
	public SimpleInformationRetrieval(){
		System.out.println("Verarbeitung gestart!");
		handleFiles();
		
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
		sir.startInformationRetrieval();
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
