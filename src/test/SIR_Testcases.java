import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.hsnr.inr.sir.SimpleInformationRetrieval;
import de.hsnr.inr.sir.algorithm.QueryProcessor;
import de.hsnr.inr.sir.dictionary.Posting;

public class SIR_Testcases {

	private static final String TEST_DIR = "C:\\Users\\lammbraten\\Dropbox\\Master\\2.Semester\\INR\\Praktikum\\P1\\CorpusUTF8";
	QueryProcessor qp;
	static SimpleInformationRetrieval sir;
	
	@BeforeClass
	public static void build(){
		sir = new SimpleInformationRetrieval(TEST_DIR);
	}
	
//	@Ignore("temporary")
	@Test
	public void testAtomTerms() {
		sir.setQuery("Hexe");

		HashSet<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Der Froschkönig")));
		assertTrue(answer.contains(new Posting("Die Loreley")));
		assertTrue(answer.contains(new Posting("Hänsel und Gretel")));
		assertFalse(answer.contains(new Posting("Hans im Glück")));
	}
	
//	@Ignore("temporary")
	@Test
	public void testTupleTerms() {
		sir.setQuery("Hexe AND Prinzessin");

		HashSet<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.isEmpty());	
	}
	
//	@Ignore("temporary")
	@Test
	public void testTupleTerms2() {
		sir.setQuery("Kater AND Prinzessin");

		HashSet<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Der gestiefelte Kater")));
		assertFalse(answer.contains(new Posting("Aladin und die Wunderlampe")));	
	}
	
//	@Ignore("temporary")
	@Test
	public void testDNFTerms() {
		sir.setQuery("Hexe AND Prinzessin OR Frosch AND König AND Tellerlein");

		HashSet<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Der Froschkönig")));
		assertFalse(answer.contains(new Posting("Aladin und die Wunderlampe")));	
	}
	
//	@Ignore("temporary")
	@Test
	public void testDNFTermsWithNegation() {
		sir.setQuery("Hexe AND Prinzessin OR NOT Hexe AND König");

		HashSet<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Der gestiefelte Kater")));
		assertTrue(answer.contains(new Posting("Der Drachentöter")));
		assertTrue(answer.contains(new Posting("Schneewittchen"))); //Böse Königin ist zwar eine "Hexe" wird aber nicht explizit so genannt.
		assertFalse(answer.contains(new Posting("Ali Baba und die 40 Räuber")));	
	}
	
//	@Ignore("temporary")
	@Test
	public void testDNFTermsWithNegation2() {
		sir.setQuery("NOT DER AND NOT DIE AND NOT DAS");

		HashSet<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.isEmpty());	
	}
	
	@Ignore("temporary")
	@Test
	public void testPhraseQueries() {
		sir.setQuery("\"sieben Zwerge\"");

		HashSet<Posting> answer = sir.startInformationRetrieval();
		assertTrue(answer.contains(new Posting("Schneewittchen")));
	}

}
