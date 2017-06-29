import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;
import org.junit.Test;

import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.Term;
import de.hsnr.inr.sir.dictionary.VectorIndex;

public class TestVectorIndex {

	@Test
	public void test() {
		VectorIndex index = new VectorIndex(new File("C:\\Users\\lammbraten\\Dropbox\\Master\\2.Semester\\INR\\Praktikum\\P1\\CorpusUTF8"));
		

		assertTrue(index.getTerm(new Term("hexe")) != null);
		assertTrue(index.getTerm(new Term("wald")) != null);
		
		//index.readFromFile("C:\\Users\\lammbraten\\Dropbox\\Master\\2.Semester\\INR\\Praktikum\\P1\\CorpusUTF8");
		
		LinkedList<Term> q = new LinkedList<Term>();
		q.add(new Term("hexe"));
		q.add(new Term("wald"));
		q.add(new Term("hänsel"));
		q.add(new Term("gretel"));
		q.add(new Term("prinzessin"));
		q.add(new Term("frosch"));
		
		for(Posting e : index.cosineScore(q, 10))
			System.out.println(e);
	}

}
