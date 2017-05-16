import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.Term;

public class TestTerm {

	@Test
	public void testPostingList() {
		Term t = new Term("testterm");
		assertTrue(t.getPostings().isEmpty());
		
		
		LinkedList<Posting> postings = new LinkedList<Posting>();
		
		postings.add(new Posting("Doc1"));
		//t.append(postings);
		assertFalse(t.getPostings().isEmpty());
		assertTrue(t.hasPosting(new Posting("Doc1")));
		assertFalse(t.hasPosting(new Posting("Doc2")));

	}

}
