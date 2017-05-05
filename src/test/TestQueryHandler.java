import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryHandler;
import de.hsnr.inr.sir.query.QueryItem;

public class TestQueryHandler {

	@Test
	public void test() {
		Query q;
		
		q = QueryHandler.parseQuery("Hexe");
		System.out.println(q);
		assertTrue(q.toString().equals("hexe ;"));
		
		q = QueryHandler.parseQuery("Hexe AND Prinzessin");
		System.out.println(q);
		assertTrue(q.toString().equals("hexe AND prinzessin ;"));
		
		q = QueryHandler.parseQuery("Hexe AND NOT Prinzessin");
		System.out.println(q);
		assertTrue(q.toString().equals("hexe AND NOT prinzessin ;"));
		
		q = QueryHandler.parseQuery("NOT Prinzessin AND NOT Kater");
		System.out.println(q);
		assertTrue(q.toString().equals("NOT prinzessin AND NOT kater ;"));
		
		q = QueryHandler.parseQuery("Hexe AND Prinzessin OR Frosch AND König AND Tellerlein");
		System.out.println(q);
		assertTrue(q.toString().equals("hexe AND prinzessin \nOR frosch AND könig AND tellerlein ;"));

		LinkedList<LinkedList<QueryItem>> s = q.getAndConjunctions();
		
		System.out.println(s);
	}
	
	@Test 
	public void testPhrase(){
		Query q;
		q = QueryHandler.parseQuery("\"Böse Hexe\" AND \"hübsche Prinzessin\"");
		System.out.println(q);
		assertTrue(q.toString().equals("\"böse hexe \" AND \"hübsche prinzessin \" ;"));
	}
	
	@Test 
	public void testProximity(){
		Query q;
		q = QueryHandler.parseQuery("Kuchen \\5 Wein");
		System.out.println(q);
		assertTrue(q.toString().equals("kuchen \\5 wein ;"));
	}

}
