package anonInnerExample;

public class Mobile {
	private String name; // name of the mobile
	private int remainingCharge; // charge remaining 

	Mobile(String name, int remainingCharge) { // Parameterized constructor
		this.name = name;
		this.remainingCharge = remainingCharge;
	}

	public void remainingCharge() { // Display the remaining charge in the mobile
		System.out.println("Remaining charge: " + remainingCharge + "%");
	}

	public void name() { // Display the name of the mobile
		System.out.println("Name of mobile: " + name);
	}
}