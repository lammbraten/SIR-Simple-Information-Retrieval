import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.hsnr.inr.sir.query.QueryTokenizer;

public class TestQueryTokenizer {

	@Test
	public void test() {
		List<String> str = QueryTokenizer.tokenize("\"Hallo Welt\" Wie geht es dir?");
		
		System.out.println(str);
		try{
			System.out.println( QueryTokenizer.tokenize("\"Hallo\" Wie geht es dir?"));
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e){}
		System.out.println( QueryTokenizer.tokenize("\" Hallo Welt \" Wie geht \"es dir?\""));
		System.out.println( QueryTokenizer.tokenize("\" Hallo Welt \" Wie geht es dir?"));
		
		
		System.out.println( QueryTokenizer.tokenize("\" Hallo Welt \" Wie /k \"geht es\" dir?"));
	}

}
