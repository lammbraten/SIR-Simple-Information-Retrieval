import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.TreeMultiset;

import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.Term;

public class TestTerm {
	
	

	@Test
	public void testPostingList() {
		Term t = new Term("testterm");
		assertTrue(t.getPostings().isEmpty());
		
		
		TreeMultiset<Posting> postings = TreeMultiset.create();
		
		postings.add(new Posting("Doc1"));
		t.append(postings);
		assertFalse(t.getPostings().isEmpty());
		assertTrue(t.hasPosting(new Posting("Doc1")));
		assertFalse(t.hasPosting(new Posting("Doc2")));

	}

}
