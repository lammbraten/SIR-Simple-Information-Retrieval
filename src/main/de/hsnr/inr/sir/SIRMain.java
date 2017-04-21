package de.hsnr.inr.sir;

public class SIRMain {
	
	private static final String DIR_PATH_PAR = "-p";

	private static String dir_path;

	public static void main(String[] args) {
		System.out.println("Hallo Märchenwelt!");
		if(!parseArgs(args))
			throw new IllegalArgumentException("Oh, eine Hexe hat deine Argumente verflucht!");		
		if(dir_path == null || dir_path.isEmpty())
			throw new IllegalArgumentException("Leider konnte ich keine Märchen finden. :-( Probier's doch mal so: -p C:\\pfad\\zum\\Märchenordner");
		
		System.out.println("Die Märchen liegen in: " + dir_path);
		
		SimpleInformationRetrieval sir = new SimpleInformationRetrieval(dir_path);
		
		while(true){
			sir.askForQuery();
			System.out.println("Spieglein, Spieglein an der Wand, hast du solche Märchen zur Hand? \n" + sir.getQuery());
			System.out.println("\nFolgende Märchen habe ich für dich:\n" + sir.startInformationRetrieval());
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
