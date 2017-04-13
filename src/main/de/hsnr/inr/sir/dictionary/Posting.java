package de.hsnr.inr.sir.dictionary;

public class Posting implements Comparable<Object>{
	private String value;
	
	public Posting(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Posting){
			Posting p = (Posting) o;
			return value.compareTo(p.getValue());			
		}
		throw new ClassCastException("Couldn't compare these classes");
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Posting){
			Posting p = (Posting) o;
			return value.equals(p.getValue());			
		}
		throw new ClassCastException("Couldn't compare these classes");
	}
	
	@Override
	public String toString(){
		return value;
	}

}
