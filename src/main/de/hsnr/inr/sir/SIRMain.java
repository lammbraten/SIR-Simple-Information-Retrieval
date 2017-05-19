package de.hsnr.inr.sir;

import de.hsnr.inr.sir.dictionary.Posting;

public class SIRMain {
	
	private static final String DIR_PATH_PAR = "-p";
	private static final String FUZZY_PAR = "-f";
	private static final String BOOLEAN_PAR = "-b";
	private static final String HELP_PAR = "-h";

	private static String dir_path;
	private static boolean fuzzy;
	private static boolean help = false;

	public static void main(String[] args) {
		System.out.println("Hallo Märchenwelt!");
		if(!parseArgs(args))
			throw new IllegalArgumentException("Oh, eine Hexe hat deine Argumente verflucht!");	
		if(help)
			printHelp();
		if(dir_path == null || dir_path.isEmpty())
			throw new IllegalArgumentException("Leider konnte ich keine Märchen finden. :-( Probier's doch mal so: -p C:\\pfad\\zum\\Märchenordner");
		
		System.out.println("Die Märchen liegen in: " + dir_path);
		
		SimpleInformationRetrieval sir = new SimpleInformationRetrieval(dir_path, fuzzy);
		
		while(true){
			sir.askForQuery();
			System.out.println("Spieglein, Spieglein an der Wand, hast du solche Märchen zur Hand? \n" + sir.getQuery());
			System.out.println("\nFolgende Märchen habe ich für dich:");
			for(Posting p : sir.startInformationRetrieval())
				System.out.println("\t"+p);
		}
	}
	
	private static void printHelp() {
		System.out.println("Parameters are :");
		System.out.println("-d: \tpath of directory");
		System.out.println("-h: \thelp");
		System.out.println("-d: \tfuzyy true/false");
		System.out.println("-d: \tboolean true/false");
	}

	private static boolean parseArgs(String[] args) {
		for(int i = 0; i < (args.length - 1); i=i+2)			
			if(!parseKeyValue(args[i], args[i+1]))
				return false;
		return true;
	}

	private static boolean parseKeyValue(String key, String value) {
		if(isHelpSetHelp(key, value))
			return true;
		if(isDPsetDP(key, value))
			return true;
		if(isFuzzySetFuzzy(key, value))
			return true;
		if(isBoolSetBool(key, value))
			return true;
		return false;
	}

	private static boolean isHelpSetHelp(String key, String value) {
		if(key.equals(HELP_PAR)){
			help = true;
			return true;
		}
		return false;
	}

	private static boolean isBoolSetBool(String key, String value) {
		if(key.equals(BOOLEAN_PAR)){
			fuzzy = !Boolean.getBoolean(value);
			return true;
		}
		return false;
	}

	private static boolean isFuzzySetFuzzy(String key, String value) {
		if(key.equals(FUZZY_PAR)){
			fuzzy = Boolean.parseBoolean(value);
			return true;
		}
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
