package de.hsnr.inr.sir.dictionary;

public class WeightedPosting extends Posting {
	
	private float weight;
	

	public WeightedPosting(Posting p, float weight) {
		super(p.name);
		setWeight(weight);
	}


	public float getWeight() {
		return weight;
	}


	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString(){
		return super.name + ", weight: " + weight; 
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof WeightedPosting){
			WeightedPosting p = (WeightedPosting) o;
			float val = (p.weight - this.weight);	
			if(val == 0)
				return 0;
			if(val < 0)
				return -1;
			if(val > 0)
				return 1;
		}
		throw new ClassCastException("Couldn't compare these classes");
	}

}
