package comparatorExample;

import java.util.*;

public class EmployeeExample {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		s.useDelimiter("\n");
		int numEmployees = 3;
		Employee[] empList = new Employee[numEmployees];
		for(int i = 0; i < numEmployees; i++) {
			String name; double salary; int age;
			System.out.print("\nEnter employee " + (int)(i + 1) + "'s name: ");
			name = s.next();
			System.out.print("Enter employee " + (int)(i + 1) + "'s age: ");
			age = s.nextInt();
			System.out.print("Enter employee " + (int)(i + 1) + "'s salary: ");
			salary = s.nextDouble();
			empList[i] = new Employee(name, age, salary);
		}

		int opt = 0;
		do {
			System.out.println("\n1. Sort by name");
			System.out.println("2. Sort by age");
			System.out.println("3. Sort by salary");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: "); 
			opt = s.nextInt();
			switch(opt) {
				case 1: {
					Arrays.sort(empList, new Comparator<Employee>() {
						public int compare(Employee e1, Employee e2) {
            				return (e1.getName()).compareTo(e2.getName());
        				}
					});
				}
				break;
				case 2: {
					Arrays.sort(empList, new Comparator<Employee>() {
						public int compare(Employee e1, Employee e2) {
            				return (e1.getAge() - e2.getAge());
        				}
					});
				}
				break;
				case 3: {
					Arrays.sort(empList, new Comparator<Employee>() {
						public int compare(Employee e1, Employee e2) {
            				return (int)(e1.getSalary() - e2.getSalary());
        				}
					});
				}
				break;
			}
			if(opt != 0) {
				System.out.print("\n");
				for(int i = 0; i < numEmployees; i++) {
					System.out.print(empList[i].toString());
					if(i != numEmployees - 1) {
						System.out.print(",");
					}
					System.out.println();
				}

			}
		}
		while(opt != 0);
	}
}