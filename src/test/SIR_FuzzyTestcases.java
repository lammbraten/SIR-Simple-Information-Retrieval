import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import de.hsnr.inr.sir.algorithm.FuzzyQueryProcessor;
import de.hsnr.inr.sir.algorithm.QueryProcessor;
import de.hsnr.inr.sir.dictionary.FuzzyIndex;
import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryHandler;

public class SIR_FuzzyTestcases {

	FuzzyQueryProcessor fqp;
	private static final String TEST_DIR = "C:\\Users\\lammbraten\\Dropbox\\Master\\2.Semester\\INR\\Praktikum\\P1\\CorpusUTF8-small";

	@Ignore
	@Test
	public void testFuzzyQueryProcessor() {
		File corpus = new File(TEST_DIR);
		FuzzyIndex index = new FuzzyIndex(corpus);
		fqp = new FuzzyQueryProcessor(index);
		
		Query q = QueryHandler.parseQuery("Hexe AND oder");
		
		System.out.println(fqp.process(q));
		
		
	}
	
	@Test
	public void testWrite(){
		File corpus = new File(TEST_DIR);
		FuzzyIndex index = new FuzzyIndex(corpus);
		
		index.writeToFile("..\\Index");
		System.out.println(index.jaccardHistogrammToString());
	}
	
	@Test
	public void testRead(){
		
		FuzzyIndex i = null;
		try {
			i = (FuzzyIndex) Index.readFromFile("..\\Index.bin");
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		i.buildJaccardHistogram(20);
		System.out.println(i.jaccardHistogrammToString());
	}

}
