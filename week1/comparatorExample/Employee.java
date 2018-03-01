package comparatorExample;

public class Employee {
	// Data members of 'Employee'
	private String name;
	private int age;
	private double salary;

	Employee(String name, int age, double salary) { // Parameterized constructor
		this.name = name;
		this.salary = salary;
		this.age = age;
	}

	// Getter functions for the data members
	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public double getSalary() {
		return salary;
	}

	// Returns a formatted String describing the employee
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(name + ", ");
		sb.append(age + ", ");
		sb.append(salary + ")");
		String result = sb.toString();
		return result;
	}
}