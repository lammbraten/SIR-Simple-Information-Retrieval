import static org.junit.Assert.*;

import org.junit.Test;

import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryHandler;

public class TestQueryHandler {

	@Test
	public void test() {
		Query q = QueryHandler.parseQuery("Hallo Welt");
		assertTrue(q.toString().equals("hallo welt ;"));
		q = QueryHandler.parseQuery("Hexe");
		assertTrue(q.toString().equals("hexe ;"));
		q = QueryHandler.parseQuery("Hexe AND Prinzessin");
		assertTrue(q.toString().equals("hexe AND prinzessin ;"));
		System.out.println(q);

	}

}
