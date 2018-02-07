import java.util.*;

public class printPattern {
	public static void print(int n) {
		int spaces = (n * 2) - 2;
		for(int line = 1; line <= n; line++) {
			for(int s = 0; s < spaces; s++) {
				System.out.print(" ");
			}
			int numDigits = (line * 2) - 1; int curr = 1; 
			boolean maxFlag = false;
			for(int d = 1; d <= numDigits; d++) {
				System.out.print(curr + " ");
				if(!maxFlag) {
					curr++;
				}
				else {
					curr--;
				}
				if(curr > line) {
					maxFlag = true;
					curr -= 2;
				}
			}

			spaces -= 2;
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the number of lines: ");
		int n = s.nextInt();
		print(n);
	}
}