package week1.day4.inheritance;

public class Truck extends Car {
	int weight; 
	
	Truck(int speed, double regularPrice, String colour, int weight) { // parameterized constructor
		super(speed, regularPrice, colour);
		this.weight = weight;
	}

	public double getSalePrice() { // implementation of abstract method
		// compute discount based on the truck's weight
		if(weight > 2000) {
			return (0.9 * regularPrice);
		}
		else {
			return (0.8 * regularPrice);
		}
	}

	public void display() {
		super.display();
		System.out.println("Weight of truck: " + weight);
	}
}