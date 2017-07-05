import java.io.File;

import org.junit.Test;

import de.hsnr.inr.sir.dictionary.KGramIndex;

public class TestKGram {
	
	private static final String TEST_DIR = "C:\\Users\\lammbraten\\Dropbox\\Master\\2.Semester\\INR\\Praktikum\\P1\\CorpusUTF8";


	@Test
	public void test() {
		KGramIndex kgIndex = new KGramIndex(new File(TEST_DIR), 2);
		System.out.println(KGramIndex.calcKGrams("Hallo", 4));
		
		System.out.println(kgIndex.getCorrectedTermsForString("fosch"));
	}

}
