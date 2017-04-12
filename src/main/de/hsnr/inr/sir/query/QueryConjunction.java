package de.hsnr.inr.sir.query;

public class QueryConjunction extends QueryItem {
	private static final String and = "AND";
	private static final String or = "OR";
	private static final String not = "NOT";
	
	QueryConjunction(String name){
		super(name);
		this.name = name;
	}
	
	static boolean isParseable(String candidate){
		if(candidate.equals(and))
			return true;
		if(candidate.equals(or))
			return true;
		if(candidate.equals(not))
			return true;
		return false;
	}
	/**
	 * Creates a QueryItem if the name is valid
	 * @return
	 */
	public static QueryItem create(String name){
		if(!isParseable(name))
			throw new IllegalArgumentException();
		return new QueryConjunction(name);
	}
}
