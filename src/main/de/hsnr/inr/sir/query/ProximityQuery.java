package de.hsnr.inr.sir.query;

public class ProximityQuery extends QueryItem {
	
	private QueryItem termA;
	private QueryItem termB;
	private int dist;
	
	private static final String DISTANCE_SEPERATOR_PATTERN = "\\\\";
	private static final String DISTANCE_PATTERN = " "+DISTANCE_SEPERATOR_PATTERN+"\\d+ ";
	private static final String NAME_PATTERN = ".+"+DISTANCE_PATTERN+".+";


	ProximityQuery(String name) {
		super(name);
		
		int seperator = name.indexOf("\\");
		int distanceEnd = name.indexOf(' ', seperator);
		
		termA = QueryItem.create(name.substring(0, seperator));
		dist = Integer.parseInt(name.substring(seperator+1, distanceEnd));
		termA = QueryItem.create(name.substring(distanceEnd));
	}

	@Override
	public void invert() {
		termA.invert();
		termB.invert();
		//TODO: invert Distance?
	}
	
	@Override
	public String toString(){
		return termA + " \\" + dist + " " + termB;
	}
	
	public static QueryItem create(String name){
		if(!isParseable(name))
			throw new IllegalArgumentException();
		return new ProximityQuery(name.toLowerCase());
	}

	public static boolean isParseable(String name) {
		return name.matches(NAME_PATTERN);
	}
}
