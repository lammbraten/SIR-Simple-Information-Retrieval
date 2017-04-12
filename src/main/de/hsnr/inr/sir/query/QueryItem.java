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
	
	/**
	 * Create a QueryItem for the given name. 
	 * @param name
	 * @return -A QueryConjunction if name is parseable, \n
	 * - A QuerTerm elsewhen.
	 */
	public static QueryItem create(String name){
		try{
			return QueryConjunction.create(name);
		}catch(IllegalArgumentException e) {
			return QueryTerm.create(name.toLowerCase());			
		}
	}
	 
	@Override
	public String toString(){
		return name + " ";
	}
	

}
