import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import de.hsnr.inr.sir.textprocessing.Tokenizer;

public class TestTokenizer {

	private String baspath = "C:\\Users\\lammbraten\\Dropbox\\Master\\2.Semester\\INR\\Praktikum\\P1\\CorpusUTF8";
	
	@Test
	public void test() {
		File f = new File(baspath + "\\Mann im Mond.txt");
		Tokenizer t = new Tokenizer();
		List<String> output = null;
		try {
			output = t.tokenize(f);
		} catch (IOException e) {
			e.printStackTrace();
			fail("thrown error");
		}
		
		for(String out : output)
			System.out.println(out);
		

	}

}
