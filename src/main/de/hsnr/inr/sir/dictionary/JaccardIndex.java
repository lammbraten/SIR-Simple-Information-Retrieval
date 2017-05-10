package de.hsnr.inr.sir.dictionary;

import java.util.HashMap;

public class JaccardIndex extends Index{
	
	public static final float JACCARD_THRESHOLD = 0.05f;
	private HashMap<String, JaccardDegree> degreeList = new HashMap<String, JaccardDegree>();

	public void calcJaccardDegreeMatrix(){
		for(Term t : this.dictionary){
			for(Term u : this.dictionary){
				String val = JaccardDegree.calcValue(t, u);
				if(!degreeList.containsKey(val)){
					try{
						degreeList.put(val, new JaccardDegree(t, u, val));
					} catch (IllegalArgumentException e){}
				}
			}
		}
	}

}
