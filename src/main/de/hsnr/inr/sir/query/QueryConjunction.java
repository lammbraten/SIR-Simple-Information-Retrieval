package de.hsnr.inr.sir.query;

public class QueryConjunction extends QueryItem {
	public static final String AND = "AND";
	public static final String OR = "OR";
	public static final String NOT = "NOT";
	
	QueryConjunction(String name){
		super(name);
		this.name = name;
	}
	
	static boolean isParseable(String candidate){
		if(candidate.equals(AND))
			return true;
		if(candidate.equals(OR))
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

	@Override
	public void invert() {
		if(name.equals("AND")) 
			name = "OR";
		if(name.equals("OR"))
			name = "AND";
		
	}
}
