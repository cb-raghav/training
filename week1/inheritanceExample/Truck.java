package inheritanceExample;

public class Truck extends Car {
	int weight; 
	
	Truck(int speed, double regularPrice, String colour, int weight) {
		super(speed, regularPrice, colour);
		this.weight = weight;
	}

	public double getSalePrice() {
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