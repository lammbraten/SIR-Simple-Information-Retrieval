import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

import de.hsnr.inr.sir.SimpleInformationRetrieval;
import de.hsnr.inr.sir.dictionary.Index;
import de.hsnr.inr.sir.dictionary.JaccardDegree;
import de.hsnr.inr.sir.dictionary.FuzzyIndex;
import de.hsnr.inr.sir.dictionary.Posting;
import de.hsnr.inr.sir.dictionary.Term;
import de.hsnr.inr.sir.textprocessing.Tokenizer;

public class TestJaccard {
	private static final String TEST_DIR = "C:\\Users\\lammbraten\\Dropbox\\Master\\2.Semester\\INR\\Praktikum\\P1\\CorpusUTF8";
	private static FuzzyIndex index;
	private static File corpus;

	@BeforeClass
	public static void build(){
		handleFiles();
		buildIndex();
	}

//	@Ignore
	@Test
	public void test() {
		index.calcJaccardDegreeMatrix();
			
		//System.out.println(index.getDegreeList());
		System.out.println(index.getJaccardDegreeMap().size());
		assertFalse(index.getJaccardDegreeMap().isEmpty());
		
		index.calcFuzzyAffiliationDegreeMatrix();

		System.out.println(index);
		LinkedList<Posting> postings = index.getPostings();
		Posting p1 = postings.getLast();
		Posting p2 = postings.getFirst();
		
	//	for(Term t : index.getAllTermsIn(p1)){
	//		System.out.println(t.getValue() + ": " + index.getOgawaDegreeOf(p2, t));
	//	}
		System.out.println(p1.getName());
		System.out.println(p2.getName());
	}

	@Test
	public void quickTest() {
		FuzzyIndex index = new FuzzyIndex();
		Posting d1 = new Posting("D1");
		Posting d2 = new Posting("D2");
		Posting d3 = new Posting("D3");
		
		Term t1 = new Term("Veranstaltung");
		t1.append(Lists.newLinkedList(Arrays.asList(d1, d2, d3)));
		Term t2 = new Term("Sehenswürdigkeit");
		t2.append(Lists.newLinkedList(Arrays.asList(d1, d2)));
		Term t3 = new Term("Tipp");
		t3.append(Lists.newLinkedList(Arrays.asList(d1, d3)));
		Term t4 = new Term("Krefeld");
		t4.append(Lists.newLinkedList(Arrays.asList(d2)));
		
		index.add(t1);
		index.add(t2);
		index.add(t3);
		index.add(t4);
		
		index.buildPostingList();
		index.calcJaccardDegreeMatrix();
		index.calcFuzzyAffiliationDegreeMatrix();
		
		System.out.println(index);
		
	}	
	
	
	private static void buildIndex() {
		System.out.println("Decke das Tischlein!");
		index = new FuzzyIndex();
		for(File f : corpus.listFiles()){
			try {
				index.addAll(extractTerms(f));
			} catch (IOException e) {
				System.err.println("Märchen " + f.getName() + " ist verflucht und konnte nicht geöffnet werden!");
			}
		}
		index.buildPostingList();
		//index.write("TestIndex.txt");
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
