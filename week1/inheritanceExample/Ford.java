package inheritanceExample;

public class Ford extends Car {
	int year; 
	int manufacturerDiscount;

	Ford(int speed, double regularPrice, String colour, int year, int manufacturerDiscount) {
		super(speed, regularPrice, colour);
		this.year = year;
		this.manufacturerDiscount = manufacturerDiscount;
	}

	public double getSalePrice() {
		return (((100 - manufacturerDiscount) * 1.0 / 100.0) * regularPrice);
	}

	public void display() {
		super.display();
		System.out.println("Year: " + year);
		System.out.println("Manufacturer discount: " + manufacturerDiscount);
	}
}