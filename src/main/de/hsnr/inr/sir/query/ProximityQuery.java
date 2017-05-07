package de.hsnr.inr.sir.query;

import de.hsnr.inr.sir.algorithm.Intersect;
import de.hsnr.inr.sir.dictionary.Index;


public class ProximityQuery extends AbstractQueryTerm {
	
	private AbstractQueryTerm termA;
	private AbstractQueryTerm termB;
	private int dist;
	
	private static final String DISTANCE_SEPERATOR_PATTERN = "/";
	private static final String DISTANCE_PATTERN = " "+DISTANCE_SEPERATOR_PATTERN+"\\d+ ";
	private static final String NAME_PATTERN = "\".+"+DISTANCE_PATTERN+".+\"";

	ProximityQuery(String name) {
		super(name);
		
		int seperator = name.indexOf(DISTANCE_SEPERATOR_PATTERN);
		int distanceEnd = name.indexOf(' ', seperator);
		
		termA = (AbstractQueryTerm) QueryItem.create(name.substring(1, seperator));
		dist = Integer.parseInt(name.substring(seperator+1, distanceEnd));
		termB = (AbstractQueryTerm) QueryItem.create(name.substring(distanceEnd, name.length()-1));
	}

	@Override
	public void invert() {
		termA.invert();
		termB.invert();
		//TODO: invert Distance?
	}
	
	@Override
	public String toString(){
		return termA + DISTANCE_SEPERATOR_PATTERN + dist + " " + termB;
	}
	
	public static QueryItem create(String name){
		if(!isParseable(name))
			throw new IllegalArgumentException();
		return new ProximityQuery(name.toLowerCase());
	}

	/**
	 * Proximity Queries have to be in this form: </br>
	 * "TERMA_A /k TERM_B" </br>
	 * with k as distance, to be parseable.
	 */
	public static boolean isParseable(String name) {
		return name.matches(NAME_PATTERN);
	}

	@Override
	public void setPostingsFromIndex(Index index) {
		if(isGhost()){
			termA.setPostingsFromIndex(index);
			termB.setPostingsFromIndex(index);
			
			setPostings(Intersect.positional(termA.getPostings(), termB.getPostings(), dist));
		}
	}
}
