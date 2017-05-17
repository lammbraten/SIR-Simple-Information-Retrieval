package de.hsnr.inr.sir.dictionary;

import java.io.Serializable;

public class JaccardDegree implements Serializable{
	
	private static final long serialVersionUID = -8267748628120370796L;
	private static final String SEPARATOR = "<->";
	private String key;
	private Term termT;
	private Term termU;
	private float degree;
	
	
	public JaccardDegree(Term termT, Term termU, String value, float degree){
		this.termT = termT;
		this.termU = termU;
		this.degree = degree;
		
		setKey(value);
	}
	
	public JaccardDegree(Term termT, Term termU, String value){
		this(termT, termU, value, calcDegree(termT, termU));

	}
	
	public JaccardDegree(Term termT, Term termU){
		this(termT, termU, calcKey(termT, termU));
	}

	public static String calcKey(Term termT, Term termU) {
		if((termT.getValue().compareTo(termU.getValue()) < 0))
			return termT.getValue() + SEPARATOR + termU.getValue();
		else
			return termU.getValue() + SEPARATOR + termT.getValue();
	}
	
	public static float calcDegree(Term termT, Term termU){
		float docsWithTAndU = calcIntersectionSize(termT, termU);		
		float docsWithTOrU = termT.getFrequence() + termU.getFrequence() - docsWithTAndU;
		
		float degree = docsWithTAndU / docsWithTOrU;
		
		return degree;
	}
	
	private static int calcIntersectionSize(Term termT, Term termU){
		int size = 0;
		for(Posting p: termU.getPostings())
			if(termT.hasPosting(p))
				size++;
		
		return size;
	}
	
	@Override 
	public String toString(){
		return key +": " + degree;		
	}

	public Term getTermT() {
		return termT;
	}

	public Term getTermU() {
		return termU;
	}

	public float getDegree() {
		return degree;
	}

	private void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
