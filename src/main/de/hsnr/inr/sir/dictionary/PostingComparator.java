package de.hsnr.inr.sir.dictionary;

import java.util.Comparator;

public class PostingComparator implements Comparator<Posting> {

	@Override
	public int compare(Posting p1, Posting p2) {
		return p1.compareTo(p2);
	}

}
