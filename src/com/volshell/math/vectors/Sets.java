//volshell : math operations of sets
package com.volshell.math.vectors;

import java.util.*;

/**
 * @author volshell
 * 
 *         本类中，集中了集合的一些操作
 */
public class Sets {
	/**
	 * @param a
	 * @param b
	 * @return this is A+B 两个集合的并集 A∪B={x|x∈A，或x∈B}
	 */
	public static <T> Set<T> union(Set<T> a, Set<T> b) {
		Set<T> result = new HashSet<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * @param a
	 * @param b
	 * @return this is A*B 两个集合的交集 A∩B={x|∈A，且x∈B}
	 */
	public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
		Set<T> result = new HashSet<T>(a);
		result.retainAll(b);
		return result;
	}

	// Subtract subset from superset:
	/**
	 * @param superset
	 * @param subset
	 * @return this is A-B
	 */
	public static <T> Set<T> difference(Set<T> superset, Set<T> subset) {
		Set<T> result = new HashSet<T>(superset);
		result.removeAll(subset);
		return result;
	}

	// Reflexive--everything not in the intersection:
	public static <T> Set<T> complement(Set<T> a, Set<T> b) {
		return difference(union(a, b), intersection(a, b));
	}
} // /:~
