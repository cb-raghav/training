package week1.day2;

import java.util.*;

public class FibonacciSequence {
	/*
		function to generate the first 'n' numbers of the Fibonacci sequence as follows:
		0, 1, 1, 2, 3, 5, 8, 13 ...
		Parameters: 'n' (integer)
	*/
	public static void generate(int n) {
		ArrayList fib = new ArrayList();
		fib.add(0); fib.add(1); // add the first two terms of the sequence to the ArrayList
		for(int i = 2; i < n; i++) {
			// Fetch the previous two terms of the sequence
			int a = (int)fib.get(i - 1);
			int b = (int)fib.get(i - 2);
			// Compute the next term in the sequence
			fib.add(a + b);
		}
		for(int i = 0; i < n; i++) { // print the terms of the sequence
			System.out.print(fib.get(i) + " ");
		}
		System.out.println();
	}
	public static void main(String[] args) {
		if(args.length != 1) { // check if exactly one command-line argument is specified
			System.out.println("ERROR: command-line arguments not specified properly!");
		}
		else { 
			generate(Integer.parseInt(args[0]));
		}
	}
}