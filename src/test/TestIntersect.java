import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Test;

import de.hsnr.inr.sir.algorithm.Intersect;
import de.hsnr.inr.sir.dictionary.Posting;

public class TestIntersect {

	@Test
	public void test() {
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
		
		System.out.println(Intersect.and(pl1, pl2));
		
		System.out.println(Intersect.andNot(pl1, pl2));
		
		
		/*
		for(int i = 0; i < 10; i++){
			System.out.println(i + ") " + p1.next());
		}*/

		
		//fail("Not yet implemented");
	}

}
