package week1.day4.inheritance;

public abstract class Car {
	// Data members
	int speed;
	double regularPrice;
	String colour;
	
	Car(int speed, double regularPrice, String colour) { // paramterized constructor
		this.speed = speed;
		this.regularPrice = regularPrice;
		this.colour = colour;
	}
	
	abstract double getSalePrice(); // abstract method

	public void display() { // display the car's details
		System.out.println("Speed: " + speed);
		System.out.println("Regular price: " + regularPrice);
		System.out.println("Colour: " + colour);
	}
}