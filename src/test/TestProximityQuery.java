import static org.junit.Assert.*;

import org.junit.Test;

import de.hsnr.inr.sir.query.ProximityQuery;

public class TestProximityQuery {

	@Test
	public void testParsing() {
		assertTrue(ProximityQuery.isParseable("a \\5 a"));
		assertTrue(ProximityQuery.isParseable("Hallo \\5 Welt"));
		assertTrue(ProximityQuery.isParseable("Hallo \\5000 Welt"));
		
		assertFalse(ProximityQuery.isParseable("Hallo \\ Welt"));
		assertFalse(ProximityQuery.isParseable("Hallo\\5Welt"));

	}
}
