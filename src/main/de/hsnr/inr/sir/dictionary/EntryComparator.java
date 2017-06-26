package de.hsnr.inr.sir.dictionary;

import java.util.Comparator;
import java.util.Map.Entry;

public class EntryComparator implements Comparator<Entry<Posting, Float>> {

	@Override
	public int compare(Entry<Posting, Float> arg0, Entry<Posting, Float> arg1) {
		return 0 - arg0.getValue().compareTo(arg1.getValue());
	}


}
