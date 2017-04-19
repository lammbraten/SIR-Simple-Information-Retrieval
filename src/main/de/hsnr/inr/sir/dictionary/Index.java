package de.hsnr.inr.sir.dictionary;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.TreeMultiset;

public class Index {
	
	/**
	 * TODO: Baum implementieren?
	 */
	private LinkedList<Term> dictionary;
	private LinkedList<Posting> postings;

	
	public Index(){
		this.dictionary =  new LinkedList<Term>();
	}
	
	public void buildPostingList(){
		postings = new LinkedList<Posting>();
		for(Term t : dictionary)
			for(Posting p : t.getPostings())
				if(!postings.contains(p))
					postings.add(p);
		
		postings.sort(new PostingComparator());
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
}
