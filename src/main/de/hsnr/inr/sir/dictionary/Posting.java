package de.hsnr.inr.sir.dictionary;

public class Posting implements Comparable<Object>{
	private String name;
	
	public Posting(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Posting){
			Posting p = (Posting) o;
			return name.compareTo(p.getName());			
		}
		throw new ClassCastException("Couldn't compare these classes");
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Posting){
			Posting p = (Posting) o;
			return name.equals(p.getName());			
		}
		throw new ClassCastException("Couldn't compare these classes");
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public int hashCode(){
		return name.hashCode();
	}

}
