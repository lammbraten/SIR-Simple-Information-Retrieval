import static org.junit.Assert.*;

import org.junit.Test;

import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.dictionary.Term;

public class TestIndex {

	@Test
	public void testTermList() {
		Index index = new Index();
		Term t= new Term("testTerm");
		index.add(t);
		assertTrue(index.getTerm(new Term("testTerm")) != null);
	}
}
