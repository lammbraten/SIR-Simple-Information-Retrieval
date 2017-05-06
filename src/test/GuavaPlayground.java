import org.junit.Test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

public class GuavaPlayground {
	
	@Test
	public void testTreeMultiset() {
		System.out.println("======================");
		System.out.println("\nTesting SortedMultiset\n");
		System.out.println("======================");

		SortedMultiset<String> ms = TreeMultiset.create();
		

		
		System.out.println("\n");
		ms.add("to");
		System.out.println("Elements: " + ms.elementSet());
		System.out.println("Entries" + ms.entrySet());
		System.out.println("Size: " + ms.size());
		System.out.println("Stream: " + ms.stream().toArray().length);


		System.out.println("\n");
		ms.add("to");
		System.out.println("Elements: " + ms.elementSet());
		System.out.println("Entries" + ms.entrySet());
		System.out.println("Size: " + ms.size());
		System.out.println("Stream: " + ms.stream().toArray().length);
		
		System.out.println("\n");
		ms.remove("to");
		System.out.println("Elements: " + ms.elementSet());
		System.out.println("Entries" + ms.entrySet());
		System.out.println("Size: " + ms.size());
		System.out.println("Stream: " + ms.stream().toArray().length);
		
		System.out.println("\n");
		ms.add("be");
		System.out.println("Elements: " + ms.elementSet());
		System.out.println("Entries" + ms.entrySet());
		System.out.println("Size: " + ms.size());
		System.out.println("Stream: " + ms.stream().toArray().length);
		
		System.out.println("\n");
		ms.add("be");
		ms.add("i", 5);
		ms.add("not");
		ms.add("caeser");
		ms.add("i");
		System.out.println("Elements: " + ms.elementSet());
		System.out.println("Entries" + ms.entrySet());
		System.out.println("Size: " + ms.size());
		System.out.println("Stream: " + ms.stream().toArray().length);
		
		System.out.println(ms.count("be"));
	}
	

	@Test
	public void testHashMultiset() {
		System.out.println("======================");
		System.out.println("\nTesting HashMultiset\n");
		System.out.println("======================");

		Multiset<Integer> ms = HashMultiset.create();
		
		System.out.println("\n");
		ms.add(1);
		System.out.println("Elements: " + ms.elementSet());
		System.out.println("Entries" + ms.entrySet());
		System.out.println("Size: " + ms.size());
		System.out.println("Stream: " + ms.stream().toArray().length);


		System.out.println("\n");
		ms.add(1);
		System.out.println("Elements: " + ms.elementSet());
		System.out.println("Entries" + ms.entrySet());
		System.out.println("Size: " + ms.size());
		System.out.println("Stream: " + ms.stream().toArray().length);
		
		System.out.println("\n");
		ms.remove(1);
		System.out.println("Elements: " + ms.elementSet());
		System.out.println("Entries" + ms.entrySet());
		System.out.println("Size: " + ms.size());
		System.out.println("Stream: " + ms.stream().toArray().length);
		
		System.out.println("\n");
		ms.add(2);
		System.out.println("Elements: " + ms.elementSet());
		System.out.println("Entries" + ms.entrySet());
		System.out.println("Size: " + ms.size());
		System.out.println("Stream: " + ms.stream().toArray().length);
		
		System.out.println("\n");
		ms.add(2);
		ms.add(5, 5);
		ms.add(4);
		ms.add(42);
		ms.add(5);
		System.out.println("Elements: " + ms.elementSet());
		System.out.println("Entries" + ms.entrySet());
		System.out.println("Size: " + ms.size());
		System.out.println("Stream: " + ms.stream().toArray().length);
	}

}
