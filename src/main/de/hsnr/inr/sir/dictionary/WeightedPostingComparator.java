package de.hsnr.inr.sir.dictionary;

import java.util.Comparator;

public class WeightedPostingComparator implements Comparator<Posting> {

	@Override
	public int compare(Posting arg0, Posting arg1) {
		if(arg0 instanceof WeightedPosting){
			WeightedPosting p1 = (WeightedPosting) arg0;
			WeightedPosting p2 = (WeightedPosting) arg1;
			float val = (p1.getWeight() - p2.getWeight());	
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
