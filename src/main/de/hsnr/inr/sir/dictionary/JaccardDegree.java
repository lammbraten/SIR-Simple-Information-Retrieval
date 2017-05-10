package de.hsnr.inr.sir.dictionary;

public class JaccardDegree {
	
	private static final String SEPARATOR = "<->";
	private String value;
	private Term termT;
	private Term termU;
	private float degree;
	
	
	public JaccardDegree(Term termT, Term termU, String value, float degree){
		this.termT = termT;
		this.termU = termU;
		this.degree = degree;
		
		setValue(value);
	}
	
	public JaccardDegree(Term termT, Term termU, String value){
		this(termT, termU, value, calcDegree(termT, termU));

	}
	
	public JaccardDegree(Term termT, Term termU){
		this(termT, termU, calcValue(termT, termU));
	}

	public static String calcValue(Term termT, Term termU) {
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
		return value +": " + degree;		
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

	private void setValue(String val) {
		value = val;
	}
	
	public String getValue() {
		return value;
	}
}
