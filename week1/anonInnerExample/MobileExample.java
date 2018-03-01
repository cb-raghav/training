package anonInnerExample;

import java.util.*;

public class MobileExample {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		s.useDelimiter("\n");
		for(int i = 0; i < 5; i++) {
			System.out.print("\nEnter the mobile name: ");
			String name = s.next(); // Accept the name of the mobile
			Mobile m = new Mobile(name, 50) { // Anonymous inner class for creating objects of type 'Mobile'
				public void remainingCharge() { // Method override
					Random r = new Random();
					int remCharge = r.nextInt(99) + 1; // Each mobile is given a random charge percentage from 1 to 99 
					System.out.println("Remaining charge: " + remCharge + "%"); // Display the remaining charge
				}
			};
			m.name();
			m.remainingCharge();
		}
	}
}