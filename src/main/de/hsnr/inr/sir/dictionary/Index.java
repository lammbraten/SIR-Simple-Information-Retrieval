package de.hsnr.inr.sir.dictionary;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import com.google.common.io.Files;

import de.hsnr.inr.sir.textprocessing.Tokenizer;

public class Index {
	
	//TODO: implement tree or other magic?
	protected LinkedList<Term> dictionary;
	protected LinkedList<Posting> postings;
	
	public Index() {
		this.dictionary =  new LinkedList<Term>();
	}
	
	public Index(File corpus){
		this();
		for(File f : corpus.listFiles()){
			try {
				addAll(extractTerms(f));
			} catch (IOException e) {
				System.err.println("Couldn't open file " + f + "\n" + e);
			}
		}
		
		buildPostingList();
		//write("TestIndex.txt");
		
	}

	private List<Term> extractTerms(File f) throws IOException{
		LinkedList<Term> terms = new LinkedList<Term>(); 
		int position = 0; 
		for(String termStr : Tokenizer.tokenize(f)){
			terms.add(new Term(termStr, new Posting(Files.getNameWithoutExtension(f.getName()), position)));
			position++;
		}
		return terms;
	}

	public void buildPostingList(){
		postings = new LinkedList<Posting>();
		for(Term t : dictionary)
			for(Posting p : t.getPostings())
				if(!postings.contains(p))
					postings.add(p);
		
	}

	public void add(Term t){
		int index = this.dictionary.indexOf(t);
		if(index != -1)
			dictionary.get(index).append(t.getPostings());
		else{
			dictionary.add(t);
		}
	}

	public void addAll(List<Term> terms){
		for(Term t : terms)
			add(t);
	}
	
	public Term getTerm(String termval){
		return getTerm(new Term(termval));
	}
	
	public Term getTerm(Term t){
		int index = this.dictionary.indexOf(t);
		if(index != -1)
			return this.dictionary.get(index);
		throw new IllegalArgumentException("No such term found");
	}
	
	@Override
	public String toString(){
		String dict = "";
		for(Term t : dictionary)
			dict += t + "\n";
		return dict;
	}
	
	public void write(String filename){
		try{
		    PrintWriter writer = new PrintWriter(filename, "UTF-8");
		    writer.write(this.toString());
		    writer.close();
		} catch (IOException e) {
		   System.err.println("Couldn't write Index to File " + filename);
		}
	}

	public LinkedList<Posting> getPostings() {
		return postings;
	}

	public void setPostings(LinkedList<Posting> postings) {
		this.postings = postings;
	}
	
	public LinkedList<Term> getAllTermsIn(Posting p){
		LinkedList<Term> termlist = new LinkedList<Term>();
		for(Term t : dictionary)
			if(t.hasPosting(p))
				termlist.add(t);
		
		return termlist;
	}
}
