import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import de.hsnr.inr.sir.algorithm.FuzzyQueryProcessor;
import de.hsnr.inr.sir.dictionary.FuzzyIndex;
import de.hsnr.inr.sir.query.Query;
import de.hsnr.inr.sir.query.QueryHandler;

public class SIR_FuzzyTestcases {

	FuzzyQueryProcessor fqp;
	private static final String TEST_DIR = "C:\\Users\\lammbraten\\Dropbox\\Master\\2.Semester\\INR\\Praktikum\\P1\\CorpusUTF8-smaller";

	//@Ignore
	@Test
	public void testFuzzyQueryProcessor() {
		File corpus = new File(TEST_DIR);
		FuzzyIndex index = new FuzzyIndex(corpus);
		
		/*FuzzyIndex index = null;
		try {
			index = FuzzyIndex.readFromFile("..\\Index.bin");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		*/
		fqp = new FuzzyQueryProcessor(index);
		
		Query q = QueryHandler.parseQuery("Hexe AND Wald");
		
		System.out.println(fqp.process(q));
		
		
	}
	
	@Ignore
	@Test
	public void testWrite(){
		File corpus = new File(TEST_DIR);
		FuzzyIndex index = new FuzzyIndex(corpus);
		
		index.writeToFile("..\\Index");
		System.out.println(index.jaccardHistogrammToString());
	}
	
	@Ignore
	@Test
	public void testRead(){
		
		FuzzyIndex i = null;
		try {
			i = FuzzyIndex.readFromFile("..\\Index.bin");
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		i.buildJaccardHistogram(20);
		System.out.println(i.jaccardHistogrammToString());
	}

}
