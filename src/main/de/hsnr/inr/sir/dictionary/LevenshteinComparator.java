package de.hsnr.inr.sir.dictionary;

import java.util.Comparator;

public class LevenshteinComparator implements Comparator<String> {
	String q;
	public LevenshteinComparator(String q){
		this.q = q;
	}

	@Override
	public int compare(String arg0, String arg1) {
		
		return computeLevenshteinDistance(q, arg0) -
				computeLevenshteinDistance(q, arg1);
	}

	/**
	 * calcs and returns the minimum;
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
    private static int min3(int a, int b, int c) {                            
    	return Math.min(Math.min(a, b), c);                                      
    }                                                                            
                                                                                 
    public static int computeLevenshteinDistance(String s1, String s2) {      
		int[][] distance = new int[s1.length() + 1][s2.length() + 1];        
		                                                                         
		for (int i = 0; i <= s1.length(); i++)                                 
			distance[i][0] = i;                                                  
		for (int j = 1; j <= s2.length(); j++)                                 
			distance[0][j] = j;                                                  
		                                                                         
		for (int i = 1; i <= s1.length(); i++)                                 
			for (int j = 1; j <= s2.length(); j++)                             
				distance[i][j] = min3(                                        
						distance[i - 1][j] + 1,                                  
						distance[i][j - 1] + 1,                                  
						distance[i - 1][j - 1] + ((s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1)
					);
		
		return distance[s1.length()][s2.length()];                           
    } 
}
