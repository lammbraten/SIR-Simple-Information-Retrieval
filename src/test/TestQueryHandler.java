import static org.junit.Assert.*;

import org.junit.Test;

import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryHandler;

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
		
		q = QueryHandler.parseQuery("Hexe AND NOT NOT Prinzessin");
		System.out.println(q);
		assertTrue(q.toString().equals("hexe AND prinzessin ;"));


	}

}
