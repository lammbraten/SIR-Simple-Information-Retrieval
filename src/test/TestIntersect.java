import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

import de.hsnr.inr.sir.algorithm.Intersect;
import de.hsnr.inr.sir.dictionary.Occurrence;
import de.hsnr.inr.sir.dictionary.Posting;

public class TestIntersect {


	@Test
	public void testAnd() {
		LinkedList<Posting> pl1 = new LinkedList<Posting>();
		pl1.add(new Posting("Test1"));
		pl1.add(new Posting("Test2"));
		//pl1.add(new Posting("Test3"));
		//pl1.add(new Posting("Test5"));
		//pl1.add(new Posting("Test18"));
		
		LinkedList<Posting> pl2 = new LinkedList<Posting>();
		pl2.add(new Posting("Test1"));
		//pl2.add(new Posting("Test3"));
		//pl2.add(new Posting("Test6"));
		
		LinkedList<Posting> postings = Intersect.and(pl1, pl2);
		
		System.out.println("AND: " + postings);
		/*assertTrue(postings.contains((new Posting("Test1"))));
		assertTrue(postings.contains((new Posting("Test3"))));
		assertFalse(postings.contains((new Posting("Test6"))));*/
		
		assertTrue(postings.contains((new Posting("Test1"))));
		assertFalse(postings.contains((new Posting("Test2"))));
	}
	

	@Test
	public void testAndNot() {
		LinkedList<Posting> pl1 = new LinkedList<Posting>();
		pl1.add(new Posting("Test1"));
		pl1.add(new Posting("Test2"));
		pl1.add(new Posting("Test3"));
		pl1.add(new Posting("Test5"));
		pl1.add(new Posting("Test18"));
		
		LinkedList<Posting> pl2 = new LinkedList<Posting>();
		pl2.add(new Posting("Test1"));
		pl2.add(new Posting("Test3"));
		pl2.add(new Posting("Test6"));
		
		LinkedList<Posting> postings = Intersect.andNot(pl1, pl2);
		
		System.out.println("ANDNOT: " + postings);
		assertTrue(postings.contains((new Posting("Test2"))));
		assertTrue(postings.contains((new Posting("Test5"))));
		assertTrue(postings.contains((new Posting("Test18"))));
		assertFalse(postings.contains((new Posting("Test6"))));
	}
	

	@Test
	public void testNot() {
		LinkedList<Posting> pl1 = new LinkedList<Posting>();
		pl1.add(new Posting("Test1"));
		pl1.add(new Posting("Test2"));
		pl1.add(new Posting("Test3"));
		pl1.add(new Posting("Test5"));
		pl1.add(new Posting("Test18"));
		
		LinkedList<Posting> pl2 = new LinkedList<Posting>();
		pl2.add(new Posting("Test1"));
		pl2.add(new Posting("Test18"));

		
		LinkedList<Posting> postings = Intersect.not(pl2, pl1);
		
		System.out.println("NOT: " + postings);
		assertTrue(postings.contains((new Posting("Test2"))));
		assertTrue(postings.contains((new Posting("Test5"))));
		assertFalse(postings.contains((new Posting("Test18"))));
		assertFalse(postings.contains((new Posting("Test1"))));
	}
	
	@Test
	public void testNotAndNot() {
		LinkedList<Posting> plall = new LinkedList<Posting>();
		plall.add(new Posting("Test1"));
		plall.add(new Posting("Test18"));
		plall.add(new Posting("Test2"));
		plall.add(new Posting("Test3"));
		plall.add(new Posting("Test5"));
		plall.add(new Posting("Test9"));

		
		LinkedList<Posting> pl1 = new LinkedList<Posting>();
		pl1.add(new Posting("Test1"));
		pl1.add(new Posting("Test3"));
		
		LinkedList<Posting> pl2 = new LinkedList<Posting>();
		pl2.add(new Posting("Test1"));
		pl2.add(new Posting("Test18"));

		
		LinkedList<Posting> postings = Intersect.notAndNot(pl1, pl2, plall);
		
		System.out.println("NOTANDNOT: " + postings);
		assertTrue(postings.contains((new Posting("Test2"))));
		assertTrue(postings.contains((new Posting("Test5"))));
		assertFalse(postings.contains((new Posting("Test18"))));
		assertFalse(postings.contains((new Posting("Test1"))));
	}
	
	@Test
	public void testPositional() {
		LinkedList<Posting> pl1 = new LinkedList<Posting>();

		pl1.add(new Posting("Test1", new LinkedList<Integer>(Arrays.asList(1, 2, 5, 50, 600))));
		pl1.add(new Posting("Test2", new LinkedList<Integer>(Arrays.asList(1, 2, 5))));
		
		pl1.add(new Posting("Test3", new LinkedList<Integer>(Arrays.asList(10))));


		LinkedList<Posting> pl2 = new LinkedList<Posting>();
		pl2.add(new Posting("Test1", new LinkedList<Integer>(Arrays.asList(6, 49))));
		pl1.add(new Posting("Test2", new LinkedList<Integer>(Arrays.asList(100, 200, 500, 600))));
		
		pl1.add(new Posting("Test4", new LinkedList<Integer>(Arrays.asList(11))));
		
		LinkedList<Posting> occurrences = Intersect.positional(pl1, pl2, 2);
		
		System.out.println("Positional: " + occurrences);

		
		assertTrue(occurrences.contains((new Occurrence("Test1", 5, 6))));
		assertTrue(occurrences.contains((new Occurrence("Test1", 50, 49))));
		assertFalse(occurrences.contains((new Occurrence("Test2", 100, 200))));
	}
	

}
