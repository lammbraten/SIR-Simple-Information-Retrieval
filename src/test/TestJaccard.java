import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.io.Files;

import de.hsnr.inr.sir.SimpleInformationRetrieval;
import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.dictionary.JaccardDegree;
import de.hsnr.inr.sir.dictionary.JaccardIndex;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.Term;
import de.hsnr.inr.sir.textprocessing.Tokenizer;

public class TestJaccard {
	private static final String TEST_DIR = "C:\\Users\\lammbraten\\Dropbox\\Master\\2.Semester\\INR\\Praktikum\\P1\\CorpusUTF8";
	private static JaccardIndex index;
	private static File corpus;

	@BeforeClass
	public static void build(){
		handleFiles();
		buildIndex();
	}

	@Test
	public void test() {
		index.calcJaccardDegreeMatrix();
			
		//System.out.println(index.getDegreeList());
		System.out.println(index.getDegreeList().size());
		assertFalse(index.getDegreeList().isEmpty());
	}

	
	private static void buildIndex() {
		System.out.println("Decke das Tischlein!");
		index = new JaccardIndex();
		for(File f : corpus.listFiles()){
			try {
				index.addAll(extractTerms(f));
			} catch (IOException e) {
				System.err.println("M�rchen " + f.getName() + " ist verflucht und konnte nicht ge�ffnet werden!");
			}
		}
		index.buildPostingList();
		index.write("TestIndex.txt");
	}
	
	private static List<Term> extractTerms(File f) throws IOException{
		System.out.println("Lese: " + Files.getNameWithoutExtension(f.getName()));
		LinkedList<Term> terms = new LinkedList<Term>(); 
		int position = 0; 
		for(String termStr : Tokenizer.tokenize(f)){
			terms.add(new Term(termStr, new Posting(Files.getNameWithoutExtension(f.getName()), position)));
			position++;
		}
		return terms;
	}
	
	private static void handleFiles() {
		System.out.println("Lerne Geschichten auswendig!");
		corpus = new File(TEST_DIR);

	}
}