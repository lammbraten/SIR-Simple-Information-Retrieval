import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import de.hsnr.inr.sir.algorithm.FuzzyQueryProcessor;
import de.hsnr.inr.sir.dictionary.FuzzyIndex;
import de.hsnr.inr.sir.query.QueryHandler;

public class SIR_FuzzyTestcases {

	FuzzyQueryProcessor fqp;
	private static final String TEST_DIR = "C:\\Users\\lammbraten\\Dropbox\\Master\\2.Semester\\INR\\Praktikum\\P1\\CorpusUTF8";

	//@Ignore
	@Test
	public void testFuzzyQueryProcessor() {
		File corpus = new File(TEST_DIR);
		FuzzyIndex index = new FuzzyIndex(corpus);
		
		System.out.println(index.jaccardHistogrammToString());
		System.out.println(index.fuzzyAffiliationHistogrammToString());		

		fqp = new FuzzyQueryProcessor(index);
	
		System.out.println("Hexe:" + fqp.process(QueryHandler.parseQuery("Hexe")));		
		System.out.println("Wald:" + fqp.process(QueryHandler.parseQuery("Wald")));	
		System.out.println("Gretel:" + fqp.process(QueryHandler.parseQuery("Gretel")));	
		System.out.println("Hexe AND Wald" + fqp.process(QueryHandler.parseQuery("Hexe AND Wald")));		
		System.out.println("Hexe AND NOT Wald" + fqp.process(QueryHandler.parseQuery("Hexe AND NOT Wald")));
		System.out.println("Hexe AND NOT Gretel" + fqp.process(QueryHandler.parseQuery("Hexe AND NOT Gretel")));
		System.out.println("Hexe OR Wald" + fqp.process(QueryHandler.parseQuery("Hexe OR Wald")));	
		System.out.println("Prinzessin AND Frosch" + fqp.process(QueryHandler.parseQuery("Prinzessin AND Frosch")));	
		System.out.println("Hexe OR Frosch AND Prinzessin" + fqp.process(QueryHandler.parseQuery("Hexe OR Frosch AND Prinzessin")));	
		
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
