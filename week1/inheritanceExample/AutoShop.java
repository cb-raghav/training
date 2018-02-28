package inheritanceExample;

public class AutoShop {
	public static void main(String[] args) {
		Sedan sedan = new Sedan(50, 900000, "gold", 15);
		Ford ford = new Ford(65, 600000, "blue", 2005, 25);
		Truck truck = new Truck(40, 700000, "black", 1500);
		System.out.println("Sales price of sedan: " + sedan.getSalePrice());
		sedan.display();
		System.out.println();
		System.out.println("Sales price of ford: " + ford.getSalePrice());
		ford.display();
		System.out.println();
		System.out.println("Sales price of truck: " + truck.getSalePrice());
		truck.display();
		System.out.println();
	}
}