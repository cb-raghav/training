import java.util.*;

public class fibonacciSequence {
	public static void generate(int n) {
		ArrayList fib = new ArrayList();
		fib.add(0); fib.add(1);
		for(int i = 2; i < n; i++) {
			int a = (int)fib.get(i - 1);
			int b = (int)fib.get(i - 2);
			fib.add(a + b);
		}
		for(int i = 0; i < n; i++) {
			System.out.print(fib.get(i) + " ");
		}
		System.out.println();
	}
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("ERROR: command-line arguments not specified properly!");
		}
		else { 
			generate(Integer.parseInt(args[0]));
		}
	}
}