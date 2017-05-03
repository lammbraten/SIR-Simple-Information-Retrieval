package de.hsnr.inr.sir.dictionary;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Posting implements Comparable<Object>{
	private String name;
	private LinkedList<Integer> positions;
	
	public Posting(String name){
		this.name = name;
		this.positions = new LinkedList<Integer>();
	}
	
	public Posting(String name, LinkedList<Integer> positions){
		this(name);
		this.setPositions(positions);
	}
	
	public Posting(String name, int position) {
		this(name);
		this.positions.add(position);
	}

	/**
	 * Only do this when both list are sorted to save runtime.
	 * @param other List
	 */
	public void mergePositions(Posting other){
		if(positions.getLast() < other.positions.getFirst()){
			this.positions.addAll(other.positions);
		} else if(positions.getFirst() > other.positions.getLast()){
			other.positions.addAll(positions);
			positions = other.positions;
		} else {
			other.positions.addAll(positions);
			setPositions(other.positions);
		}
	}
	
	/**
	 * Set positions and sort them
	 */
	private void setPositions(LinkedList<Integer> positions) {
		this.positions = positions;
		Collections.sort(this.positions);
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
		String str = name+": ";
		for(int p : positions)
			str += p +", ";
		return str;
	}
	
	@Override
	public int hashCode(){
		return name.hashCode();
	}

	public LinkedList<Integer> getPositions() {
		return positions;
	}


}
