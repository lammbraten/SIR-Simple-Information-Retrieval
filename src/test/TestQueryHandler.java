import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Ignore;
import org.junit.Test;

import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryHandler;
import de.hsnr.inr.sir.query.QueryTerm;

public class TestQueryHandler {

	@Ignore
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
		
		q = QueryHandler.parseQuery("Hexe AND Prinzessin OR Frosch AND K�nig AND Tellerlein");
		System.out.println(q);
		assertTrue(q.toString().equals("hexe AND prinzessin \nOR frosch AND k�nig AND tellerlein ;"));

		LinkedList<LinkedList<QueryTerm>> s = q.getAndConjunctions();
		
		System.out.println(s);
	}
	
	@Test public void test2(){
		Query q;
		q = QueryHandler.parseQuery("\"B�se Hexe\" AND \"h�bsche Prinzessin\"");
		System.out.println(q);
		assertTrue(q.toString().equals("\"b�se hexe \" AND \"h�bsche prinzessin \" ;"));
	}

}
