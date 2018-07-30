package week1.day3;

import java.util.*;

public class Animal {
	// Member variables
	String name; 
	String colour; 
	int age; 
	static int count = 0; // static variable to keep track of the number of 'Animal' objects created

	Animal() { // Default construtor called whenever an object is created
		count++; // Increment the count of objects
	}

	public void dispData() { // Display the details of the animal
		System.out.println("\nName: " + name);
		System.out.println("Colour: " + colour);
		System.out.println("Age: " + age + "\n");
		System.out.println("Number of animals created: " + count + "\n");
	}

	public void acceptData() { // Accept the details of the animal
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the animal's name: ");
		name = s.next();
		System.out.print("Enter the animal's colour: ");
		colour = s.next();
		System.out.print("Enter the animal's age: ");
		age = s.nextInt();
	}

	public static void main(String[] args) {
		while(true) { // Keep accepting input from the user
			Animal a = new Animal();
			a.acceptData();
			a.dispData();
		}
	}
}