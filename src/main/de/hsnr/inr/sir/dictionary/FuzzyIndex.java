package de.hsnr.inr.sir.dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import de.hsnr.inr.sir.query.QueryItem;

public class FuzzyIndex extends KGramIndex  implements Serializable{
	

	private static final long serialVersionUID = -8843673026522862647L;

	public static final float JACCARD_THRESHOLD = 0.5f;
	private static final int DEFAULT_HISTOGRAMM_SIZE = 10;
	private static final Float OGAWA_THRESHOLD = 0.5f;
	private HashMap<String, JaccardDegree> jaccardDegreeMap = new HashMap<String, JaccardDegree>();
	private HashMap<Posting, HashMap<Term, Float>> fuzzyAffiliationDegree = new HashMap<Posting, HashMap<Term, Float>>();
	private int jaccardRejected = 0;
	private int[] jaccardHistogramm;
	private int[] fuzzyAffiliationHistogramm;
	
	
	public FuzzyIndex(File corpus) {
		super(corpus);

		calcJaccardDegreeMatrix();
		calcFuzzyAffiliationDegreeMatrix();
		
		buildJaccardHistogram(DEFAULT_HISTOGRAMM_SIZE);
		buildfuzzyAffiliationHistogram(DEFAULT_HISTOGRAMM_SIZE);
	}
	
	@Override
	public LinkedList<Posting> getPostings(String name) {
		LinkedList<Posting> postingList = new LinkedList<Posting>();
		Term term = new Term(name);
		
		for(Posting p : documents){
			HashMap<Term, Float> hm = fuzzyAffiliationDegree.get(p);
			if(hm.get(term) != null && hm.get(term) > OGAWA_THRESHOLD)
				postingList.add(new WeightedPosting(p, hm.get(term)));
		}

		postingList.sort(new PostingNameComparator());
		
		return postingList;
	}

	
	
	public String jaccardHistogrammToString(){
		String str = "";
		for(int i = 0; i < jaccardHistogramm.length; i++){
			str += String.format("%02d", i) + ") " + String.format("%08d",jaccardHistogramm[i]) + "\n";
		}
		return str;
	}
	
	public String fuzzyAffiliationHistogrammToString(){
		String str = "";
		
		for(int i = 0; i < fuzzyAffiliationHistogramm.length; i++){
			str += String.format("%02d", i) + ") " + String.format("%08d",fuzzyAffiliationHistogramm[i]) + "\n";
		}
		return str;
	}
	
	public void calcJaccardDegreeMatrix(){
		for(Term t : this.dictionary){
			for(Term u : this.dictionary){
				String key = JaccardDegree.calcKey(t, u);

				if(!jaccardDegreeMap.containsKey(key)){
					float degree = JaccardDegree.calcDegree(t, u);
					if(degree >= JACCARD_THRESHOLD)
						jaccardDegreeMap.put(key, new JaccardDegree(t, u, key, degree));
					else
						jaccardRejected++;
				}
			}
		}
	}
	
	public void buildJaccardHistogram(int size){
		jaccardHistogramm = new int[size];
		for(int i = 0; i < size; i++)
			jaccardHistogramm[i] = 0;
		int pos;
		jaccardHistogramm[0] = jaccardRejected;
		for(String key : jaccardDegreeMap.keySet()){
			pos = calcHistogrammPosition(size, jaccardDegreeMap.get(key).getDegree());
			jaccardHistogramm[pos] += 1;
		}
	}
	
	
	public void buildfuzzyAffiliationHistogram(int size){
		fuzzyAffiliationHistogramm = new int[size];
		for(int i = 0; i < size; i++)
			fuzzyAffiliationHistogramm[i] = 0;
		
		int pos;		
		for(HashMap<Term, Float> p : fuzzyAffiliationDegree.values())
			for(Term t : p.keySet()){
				pos = calcHistogrammPosition(size, p.get(t));
				fuzzyAffiliationHistogramm[pos] += 1;
			}
	}
	
	private static int calcHistogrammPosition(int size, float value){
		int pos = (int) (value * size);
		if(pos == size)	//value == 1 -> IndexOutOfBounds
			return pos-1;
		return pos;
	}
	
	
	/**
	 * W(D,t) = 1 - (PRODUCT(1-c(u,t)) (for each Term u in Document d))
	 */
	public void calcFuzzyAffiliationDegreeMatrix(){
		for(Posting p : this.documents){
			fuzzyAffiliationDegree.put(p, new HashMap<Term, Float>());
		}
	
		float product;
		HashMap<Term, Float> degrees;
		for(Term u : this.dictionary){	
			for(Term t : this.dictionary){
				for(Posting p : u.getPostings()){
					degrees = fuzzyAffiliationDegree.get(p);
					if(degrees.get(t) == null){
						product = 1f - (1f - getJaccardDegreeOf(u, t)); 
						degrees.put(t, product);
					}else{
						product = 1f - degrees.get(t);
						product = 1f - (product * (1f - getJaccardDegreeOf(u, t))); 
						degrees.put(t, product);
					}
				}				
			}
		}
	}

	public HashMap<String, JaccardDegree> getJaccardDegreeMap() {
		return jaccardDegreeMap;
	}

	public void setJaccardDegreeMap(HashMap<String, JaccardDegree> jaccardDegreeMap) {
		this.jaccardDegreeMap = jaccardDegreeMap;
	}
	
	public float getJaccardDegreeOf(Term t, Term u){
		JaccardDegree jaccardDegree = jaccardDegreeMap.get(JaccardDegree.calcKey(t, u));
		
		if(jaccardDegree == null)
			return 0.0f;
		
		return jaccardDegree.getDegree();
	}
	
	/**
	 * (Ogawa)
	 * W(D,t)
	 * @param Posting p <-> document D
	 * @param Term t
	 * @return
	 */
	public float getFuzzyAffiliationDegree(Posting p, Term t){
		return fuzzyAffiliationDegree.get(p).get(t);
	}
	
	/**
	 * (Ogawa)
	 * W(D,t)
	 * @param Posting p <-> document D
	 * @param Term t
	 * @return
	 */
	public float getFuzzyAffiliationDegree(Posting p, QueryItem i){
		return getFuzzyAffiliationDegree(p, new Term(i.getName()));
	}
	
	public static FuzzyIndex readFromFile(String path) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
		FuzzyIndex active = (FuzzyIndex) ois.readObject();
		ois.close();
		return active;

	}
	
}
