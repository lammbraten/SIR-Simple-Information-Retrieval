package de.hsnr.inr.sir.dictionary;

import java.util.HashMap;

public class JaccardIndex extends Index{
	
	public static final float JACCARD_THRESHOLD = 0.5f;
	private HashMap<String, JaccardDegree> degreeList = new HashMap<String, JaccardDegree>();

	public void calcJaccardDegreeMatrix(){
		for(Term t : this.dictionary){
			for(Term u : this.dictionary){
				String val = JaccardDegree.calcValue(t, u);

				if(!degreeList.containsKey(val)){
					float degree = JaccardDegree.calcDegree(t, u);
					if(degree > JACCARD_THRESHOLD)
						degreeList.put(val, new JaccardDegree(t, u, val, degree));
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

}
