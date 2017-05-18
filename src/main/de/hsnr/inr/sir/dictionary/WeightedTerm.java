package de.hsnr.inr.sir.dictionary;

import java.io.Serializable;

public class WeightedTerm extends Term implements Serializable {

	private static final long serialVersionUID = -2817490402556439578L;
	private float weight;
	
	public WeightedTerm(String value, float weight) {
		super(value);
		this.setWeight(weight);
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	


}
