package de.hsnr.inr.sir.algorithm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.hsnr.inr.sir.dictionary.Posting;

public class Intersect {
	
	public static HashSet<Posting> and(List<Posting> pl1, List<Posting> pl2){
		HashSet<Posting> answer = new HashSet<Posting>();
		Iterator<Posting> p1 = pl1.iterator();
		Iterator<Posting> p2 = pl2.iterator();
		
		Posting doc1 = hasNextSetNext(p1);
		Posting doc2 = hasNextSetNext(p2);
		
		while(doc1 != null && doc2 != null){
			if(doc1.equals(doc2)){
				answer.add(doc1);
				doc1 = hasNextSetNext(p1);
				doc2 = hasNextSetNext(p2);
			} else if(doc1.compareTo(doc2) < 1){
				doc1 = hasNextSetNext(p1);
			}else{
				doc2 = hasNextSetNext(p2);
			}
		}
		
		return answer;
	}

	public static LinkedList<Posting> andNot(LinkedList<Posting> pl1, LinkedList<Posting> pl2) {
		LinkedList<Posting> answer = new LinkedList<Posting>();
		Iterator<Posting> p1 = pl1.iterator();
		Iterator<Posting> p2 = pl2.iterator();
		
		Posting doc1 = hasNextSetNext(p1);
		Posting doc2 = hasNextSetNext(p2);
		
		while(doc1 != null && doc2 != null){
			if(doc1.equals(doc2)){
				doc1 = hasNextSetNext(p1);
				doc2 = hasNextSetNext(p2);
			} else if(doc1.compareTo(doc2) < 1){
				answer.add(doc1);
				doc1 = hasNextSetNext(p1);
			}else{
				doc2 = hasNextSetNext(p2);
			}
		}
		
		if(doc1 != null){
			answer.add(doc1);
			concatenate(answer, p1);
		}
		
		return answer;
	}

	//TODO OR

	public static LinkedList<Posting> notAndNot(LinkedList<Posting> pl1, LinkedList<Posting> pl2, LinkedList<Posting> plAll) {
		return andNot(not(pl1, plAll), pl2);
	}

	//TODO OR

	public static LinkedList<Posting> not(LinkedList<Posting> pl, LinkedList<Posting> plAll) {
		LinkedList<Posting> answer = new LinkedList<Posting>(plAll); 
		Iterator<Posting> p0 = plAll.iterator();
		Iterator<Posting> p1 = pl.iterator();
		Posting doc0 = hasNextSetNext(p0);
		Posting doc1 = hasNextSetNext(p1);
		
		while(doc0 != null && doc1 != null){
			if(doc0.equals(doc1)){
				answer.remove(doc1);
				doc0 = hasNextSetNext(p0);
				doc1 = hasNextSetNext(p1);
			} else //if(doc0.compareTo(doc1) < 1){
				//answer.add(doc0);
				doc0 = hasNextSetNext(p0);
			//}
		}
		
		return answer;
	}

	
	private static Posting hasNextSetNext(Iterator<Posting> p){
		if(p.hasNext())
			return p.next();
		return null;
	}	

	private static void concatenate(LinkedList<Posting> answer, Iterator<Posting> p) {
		while(p.hasNext())
			answer.add(p.next());
	}
}
