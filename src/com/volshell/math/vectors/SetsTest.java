package com.volshell.math.vectors;

import java.util.HashSet;
import java.util.Set;

public class SetsTest {
	private static Set<Integer> setA = new HashSet<Integer>();
	private static Set<Integer> setB = new HashSet<Integer>();

	private static void initSets() {

		for (int i = 0; i < 10; i++) {
			setA.add(i);
			setB.add(i + 3);
		}
	}

	public static void main(String[] args) {
		initSets();
		System.out.println("setA is  " + setA);
		System.out.println("setB is  " + setB);
		System.out.println("setA uion setB is  " + Sets.union(setA, setB));
		System.out.println("setA difference  setB is "
				+ Sets.difference(setA, setB));
		System.out.println("setA  intersection setB is "
				+ Sets.intersection(setA, setB));
		System.out.println("setA complement setB is "
				+ Sets.complement(setA, setB));
	}
}
