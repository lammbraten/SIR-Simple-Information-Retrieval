package de.hsnr.inr.sir.algorithm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.hsnr.inr.sir.dictionary.Occurrence;
import de.hsnr.inr.sir.dictionary.Posting;

/**
 * Class that contains the boolean operators for query processing: 
 *
 */
public class Intersect {
	
	public static LinkedList<Posting> or(LinkedList<Posting> pl1, LinkedList<Posting> pl2) {
		HashSet<Posting> postings = new HashSet<Posting>();
		
		postings.addAll(pl1);
		postings.addAll(pl2);
		
		return new LinkedList<Posting>(postings);
	}

	public static LinkedList<Posting> and(List<Posting> pl1, List<Posting> pl2){
		LinkedList<Posting> answer = new LinkedList<Posting>();
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

	public static LinkedList<Posting> notAndNot(LinkedList<Posting> pl1, LinkedList<Posting> pl2, LinkedList<Posting> plAll) {
		return andNot(not(pl1, plAll), pl2);
	}

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
			} else 
				doc0 = hasNextSetNext(p0);
		}
		
		return answer;
	}

	/**
	 * if Term A in PostingList pl1 and Term B in PostingList pl2 have a maximum distance of k then they will added to the return-list; 
	 * @param pl1: PostingList1
	 * @param pl2: PostingList2
	 * @param k: maximum distance
	 * @return list of matched postings;
	 */
	public static LinkedList<Posting> positional(LinkedList<Posting> pl1, LinkedList<Posting> pl2, int k){
		LinkedList<Posting> answer = new LinkedList<Posting>();
		Iterator<Posting> p1 = pl1.iterator();
		Iterator<Posting> p2 = pl2.iterator();
		
		Posting doc1 = hasNextSetNext(p1); //p1
		Posting doc2 = hasNextSetNext(p2); //p2
		
		while(doc1 != null && doc2 != null){
			if(doc1.equals(doc2)){
				LinkedList<Integer> l = new LinkedList<Integer>();
				LinkedList<Integer> pp1 = new LinkedList<Integer>(doc1.getPositions());
				LinkedList<Integer> pp2 = new LinkedList<Integer>(doc2.getPositions());
				while(!pp1.isEmpty()){
					while(!pp2.isEmpty()){
						if(Math.abs(pp1.getFirst() - pp2.getFirst()) <= k){
							l.add(pp2.getFirst());
						} else if (pp2.getFirst() > pp1.getFirst()){
							break;
						}
						pp2.removeFirst();	//pp2 <- next(pp2);
					}
					while(!l.isEmpty() && Math.abs(l.getFirst() - pp1.getFirst()) > k){
						l.removeFirst(); //do DELETE(l[0])
					}
					for(int ps : l){
						answer.add(new Occurrence(doc1.getName(), pp1.getFirst(), ps)); //do ADD(answer, <docID(p1), pos(pp1), ps>)  
					}
					pp1.removeFirst();
				}
				doc1 = hasNextSetNext(p1);
				doc2 = hasNextSetNext(p2);
			} else if(doc1.compareTo(doc2) < 1){
				doc1 = hasNextSetNext(p1);
			} else {
				doc2 = hasNextSetNext(p2);
			}
		}
		
		return answer;
	}
	
	protected static Posting hasNextSetNext(Iterator<Posting> p){
		if(p.hasNext())
			return p.next();
		return null;
	}	

	protected static void concatenate(LinkedList<Posting> answer, Iterator<Posting> p) {
		while(p.hasNext())
			answer.add(p.next());
	}
}
