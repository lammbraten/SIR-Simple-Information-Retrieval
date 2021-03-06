import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import de.hsnr.inr.sir.SimpleInformationRetrieval;
import de.hsnr.inr.sir.dictionary.Posting;

public class SIR_Testcases {

	private static final String TEST_DIR = "C:\\Users\\lammbraten\\Dropbox\\Master\\2.Semester\\INR\\Praktikum\\P1\\CorpusUTF8";

	static SimpleInformationRetrieval sir;
	
	@BeforeClass
	public static void build(){
		sir = new SimpleInformationRetrieval(TEST_DIR, false);
	}
	
	@Test
	public void testException(){
		sir.setQuery("DiesenTermGibtEsNicht");
		
		try{
			sir.startInformationRetrieval();
			fail("Should throw IllegalArgumentException");
		}catch(IllegalArgumentException e){
			//passed
		}catch(Exception e){
			fail("Should throw IllegalArgumentException");			
		}
	}
	
//	@Ignore("temporary")
	@Test
	public void testAtomTerms() {
		sir.setQuery("Hexe");

		List<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Der Froschk�nig")));
		assertTrue(answer.contains(new Posting("Die Loreley")));
		assertTrue(answer.contains(new Posting("H�nsel und Gretel")));
		assertFalse(answer.contains(new Posting("Hans im Gl�ck")));
	}
	
//	@Ignore("temporary")
	@Test
	public void testTupleTerms() {
		sir.setQuery("Hexe AND Prinzessin");

		List<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.isEmpty());	
	}
	
//	@Ignore("temporary")
	@Test
	public void testTupleTerms2() {
		sir.setQuery("Kater AND Prinzessin");

		List<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Der gestiefelte Kater")));
		assertFalse(answer.contains(new Posting("Aladin und die Wunderlampe")));	
	}
	
//	@Ignore("temporary")
	@Test
	public void testTupleTerms3() {
		sir.setQuery("Kuchen AND Wolf AND Wein");

		List<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Rotk�ppchen")));
	}
	
//	@Ignore("temporary")
	@Test
	public void testDNFTerms() {
		sir.setQuery("Hexe AND Prinzessin OR Frosch AND K�nig AND Tellerlein");

		List<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Der Froschk�nig")));
		assertFalse(answer.contains(new Posting("Aladin und die Wunderlampe")));	
	}
	
//	@Ignore("temporary")
	@Test
	public void testDNFTermsWithNegation() {
		sir.setQuery("Hexe AND Prinzessin OR NOT Hexe AND K�nig");

		List<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Der gestiefelte Kater")));
		assertTrue(answer.contains(new Posting("Der Drachent�ter")));
		assertTrue(answer.contains(new Posting("Schneewittchen"))); //B�se K�nigin ist zwar eine "Hexe" wird aber nicht explizit so genannt.
		assertFalse(answer.contains(new Posting("Ali Baba und die 40 R�uber")));	
	}
	
//	@Ignore("temporary")
	@Test
	public void testDNFTermsWithNegation2() {
		sir.setQuery("NOT DER AND NOT DIE AND NOT DAS");

		List<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.isEmpty());	
	}
	
//	@Ignore("temporary")
	@Test
	public void testPhraseQueries() {
		sir.setQuery("\"sieben Zwerge\"");

		List<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Schneewittchen")));
	}

//	@Ignore("temporary")
	@Test
	public void testProximityQueries() {
		sir.setQuery("\"Kuchen /5 Wein\"");

		List<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Rotk�ppchen")));
		assertTrue(answer.contains(new Posting("Die goldene Gans")));
	}
	
	@Test
	public void testCombinedQueries() {
		sir.setQuery("\"Kuchen /5 Wein\" AND Wolf");

		List<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Rotk�ppchen")));
		assertFalse(answer.contains(new Posting("Die goldene Gans")));
	}

}
