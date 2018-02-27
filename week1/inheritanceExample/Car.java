package inheritanceExample;

public abstract class Car {
	int speed;
	double regularPrice;
	String colour;
	
	Car(int speed, double regularPrice, String colour) {
		this.speed = speed;
		this.regularPrice = regularPrice;
		this.colour = colour;
	}
	
	abstract double getSalePrice();

	public void display() {
		System.out.println("Speed: " + speed);
		System.out.println("Regular price: " + regularPrice);
		System.out.println("Colour: " + colour);
	}
}