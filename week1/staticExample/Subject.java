package staticExample;

import java.util.*;

public class Subject {
	String[] names;
	float[] marks;
	Scanner scanner;

	Subject(int n) {
		names = new String[n];
		marks = new float[n];
		scanner = new Scanner(System.in);
		scanner.useDelimiter("\n");
	}

	public void setNames() {
		for(int i = 0; i < names.length; i++) {
			System.out.print("Enter the name of subject " + (int)(i + 1) + ": ");
			names[i] = scanner.next();
		}
		System.out.println();
	}

	public void setMarks() {
		for(int i = 0; i < marks.length; i++) {
			System.out.print("Enter the marks obtained in subject " + (int)(i + 1) + ": ");
			marks[i] = scanner.nextFloat();
		}
		System.out.println();
	}

	public String[] getNames() {
		/*for(int i = 0; i < names.length; i++) {
			System.out.println("Subject " + (int)(i + 1) + ": " + names[i]);
		}
		System.out.println();*/
		return names;
	}

	public float[] getMarks() {
		/*for(int i = 0; i < marks.length; i++) {
			System.out.println("Marks obtained  " + (int)(i + 1) + ": " + names[i]);
		}
		System.out.println();*/
		return marks;
	}
}