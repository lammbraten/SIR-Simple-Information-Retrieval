package de.hsnr.inr.sir.dictionary;

import java.util.Comparator;

public class WeightedPostingComparator implements Comparator<WeightedPosting> {

	@Override
	public int compare(WeightedPosting arg0, WeightedPosting arg1) {
		return arg0.compareTo(arg1);
	}
}
