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
		String str;
		if(o instanceof String){
			str = (String) o;
			return value.compareTo(str);			
		}
		throw new ClassCastException("Couldn't compare these classes");
	}
}
