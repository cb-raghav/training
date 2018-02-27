package anonInnerExample;

import java.util.*;

public class MobileExample {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		s.useDelimiter("\n");
		for(int i = 0; i < 5; i++) {
			System.out.print("\nEnter the mobile name: ");
			String name = s.next();
			Mobile m = new Mobile(name, 50) {

				public void remainingCharge() {
					Random r = new Random();
					int remCharge = r.nextInt(99) + 1;
					System.out.println("Remaining charge: " + remCharge + "%");
				}
			};
			m.name();
			m.remainingCharge();
		}
	}
}