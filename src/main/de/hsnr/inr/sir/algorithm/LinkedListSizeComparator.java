package de.hsnr.inr.sir.algorithm;

import java.util.Comparator;
import java.util.LinkedList;

import de.hsnr.inr.sir.dictionary.Posting;

public class LinkedListSizeComparator implements Comparator<LinkedList<?>> {

	@Override
	public int compare(LinkedList<?> o1, LinkedList<?> o2) {
		return o1.size() - o2.size();
	}

}
