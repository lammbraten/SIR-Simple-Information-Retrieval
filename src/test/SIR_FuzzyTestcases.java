import java.io.File;

import org.junit.Test;

import de.hsnr.inr.sir.algorithm.FuzzyQueryProcessor;
import de.hsnr.inr.sir.algorithm.QueryProcessor;
import de.hsnr.inr.sir.dictionary.FuzzyIndex;
import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryHandler;

public class SIR_FuzzyTestcases {

	FuzzyQueryProcessor fqp;
	private static final String TEST_DIR = "C:\\Users\\lammbraten\\Dropbox\\Master\\2.Semester\\INR\\Praktikum\\P1\\CorpusUTF8-smaller";

	
	@Test
	public void testFuzzyQueryProcessor() {
		File corpus = new File(TEST_DIR);
		FuzzyIndex index = new FuzzyIndex(corpus);
		fqp = new FuzzyQueryProcessor(index);
		
		Query q = QueryHandler.parseQuery("Hexe AND Wald");
		
		fqp.process(q);
		
	}

}
