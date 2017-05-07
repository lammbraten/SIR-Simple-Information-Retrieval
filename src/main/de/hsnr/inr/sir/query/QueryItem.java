package de.hsnr.inr.sir.query;

public abstract class QueryItem {
	
	String name;
	
	public QueryItem(String name) {
		this.name = name;
	}

	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public abstract void invert();
	
	/**
	 * Create a QueryItem for the given name. 
	 * 
	 * @param name
	 * @return -A QueryConjunction if name is parseable, \n
	 * - A QuerTerm elsewhen.
	 */
	public static QueryItem create(String name){
		//TODO: Factory or decorator?
		try{
			return QueryConjunction.create(name);
		}catch(IllegalArgumentException e) {}
		try{
			return ProximityQuery.create(name);
		}catch(IllegalArgumentException e) {}
		try{
			return PhraseQuery.create(name);
		}catch(IllegalArgumentException e) {}
		return ConcreteQueryTerm.create(name);
	}
	 
	@Override
	public String toString(){
		return name + " ";
	}
}
