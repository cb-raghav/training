package testPackage2;

import java.util.*;

public class printPattern {
	/*
		function to print the first 'n' lines of the pattern
		Parameters: 'n' (integer)
	*/
	public static void print(int n) {
		int spaces = (n * 2) - 2; // Compute the number of spaces to be printed before the first character 
		for(int line = 1; line <= n; line++) {
			for(int s = 0; s < spaces; s++) { // Print the spaces
				System.out.print(" ");
			}
			int numDigits = (line * 2) - 1; // Compute the number of digits to be printed in this line
			int curr = 1; // Initiaise the current digit to be printed
			boolean maxFlag = false; // Boolean flag variable to check if the maximum digit in the current line has been printed
			for(int d = 1; d <= numDigits; d++) {
				System.out.print(curr + " ");
				if(!maxFlag) {
					curr++; // Increment 'curr' if maximum digit hasn't been printed yet
				}
				else {
					curr--; // Decrement 'curr' if maximum digit has already been printed
				}
				if(curr > line) { 
					// Line number equals the maximum digit that can be printed on that line
					// Set 'maxFlag' to true if this limit has been exceeded
					maxFlag = true;
					curr -= 2;
				}
			}

			spaces -= 2; // Reduce the number of spaces to be printed at the beginning of the next line
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the number of lines: "); 
		int n = s.nextInt(); // Accept the number of lines to be printed
		System.out.println("The pattern upto " + n + " lines is: ");
		print(n);
	}
}