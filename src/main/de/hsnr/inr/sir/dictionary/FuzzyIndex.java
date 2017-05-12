package de.hsnr.inr.sir.dictionary;

import java.util.HashMap;

public class FuzzyIndex extends Index{

	public static final float JACCARD_THRESHOLD = 0.05f;
	private HashMap<String, JaccardDegree> degreeList = new HashMap<String, JaccardDegree>();

	public void calcJaccardDegreeMatrix(){
		for(Term t : this.dictionary){
			for(Term u : this.dictionary){
				String key = JaccardDegree.calcKey(t, u);

				if(!degreeList.containsKey(key)){
					float degree = JaccardDegree.calcDegree(t, u);
					if(degree >= JACCARD_THRESHOLD)
						degreeList.put(key, new JaccardDegree(t, u, key, degree));
				}
			}
		}
	}

	public HashMap<String, JaccardDegree> getDegreeList() {
		return degreeList;
	}

	public void setDegreeList(HashMap<String, JaccardDegree> degreeList) {
		this.degreeList = degreeList;
	}
	
	public float getJaccardDegreeOf(Term t, Term u){

		return 0.0f;
	}

}
