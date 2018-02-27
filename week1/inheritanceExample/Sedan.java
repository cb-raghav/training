package inheritanceExample;

public class Sedan extends Car {
	int length;

	Sedan(int speed, double regularPrice, String colour, int length) {
		super(speed, regularPrice, colour);
		this.length = length;
	}

	public double getSalePrice() {
		if(length > 50) {
			return (0.95 * regularPrice);
		}
		else {
			return (0.9 * regularPrice);
		}
	}

	public void display() {
		super.display();
		System.out.println("Length of sedan: " + length);
	}
}