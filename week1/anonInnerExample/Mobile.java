package anonInnerExample;

public class Mobile {
	private String name;
	private int remainingCharge;

	Mobile(String name, int remainingCharge) {
		this.name = name;
		this.remainingCharge = remainingCharge;
	}

	public void remainingCharge() {
		System.out.println("Remaining charge: " + remainingCharge + "%");
	}

	public void name() {
		System.out.println("Name of mobile: " + name);
	}
}