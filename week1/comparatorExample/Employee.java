package comparatorExample;

public class Employee {
	private String name;
	private int age;
	private double salary;

	Employee(String name, int age, double salary) {
		this.name = name;
		this.salary = salary;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public double getSalary() {
		return salary;
	}

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