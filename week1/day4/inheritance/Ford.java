package week1.day4.inheritance;

public class Ford extends Car {
	int year; 
	int manufacturerDiscount;

	Ford(int speed, double regularPrice, String colour, int year, int manufacturerDiscount) {
		// Parameterized constructor
		super(speed, regularPrice, colour);
		this.year = year;
		this.manufacturerDiscount = manufacturerDiscount;
	}

	public double getSalePrice() { // implementation of abtract method
		return (((100 - manufacturerDiscount) * 1.0 / 100.0) * regularPrice); // compute sale price using the manufacturer discount provided
	}

	public void display() { // display the details of Ford
		super.display(); // call the display method of the super class to print the values of the super class variables
		System.out.println("Year: " + year);
		System.out.println("Manufacturer discount: " + manufacturerDiscount);
	}
}