import java.util.*;

public class Animal {
	String name; String colour; 
	int age; 
	static int count = 0;

	Animal() {
		count++;
	}

	public void dispData() {
		System.out.println("\nName: " + name);
		System.out.println("Colour: " + colour);
		System.out.println("Age: " + age + "\n");
		System.out.println("Number of animals created: " + count + "\n");
	}

	public void acceptData() {
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the animal's name: ");
		name = s.next();
		System.out.print("Enter the animal's colour: ");
		colour = s.next();
		System.out.print("Enter the animal's age: ");
		age = s.nextInt();
	}

	public static void main(String[] args) {
		while(true) {
			Animal a = new Animal();
			a.acceptData();
			a.dispData();
		}
	}
}