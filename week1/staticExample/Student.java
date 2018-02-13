package staticExample;

import java.util.*;

public class Student {
	int studentID;
	String studentName; String gender; 
	Subject subjects;
	Scanner scanner;

	Student() {
		subjects = new Subject(3);
		scanner = new Scanner(System.in);
		scanner.useDelimiter("\n");
	}

	public void setStudentName() {
		//Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the student's name: ");
		studentName = scanner.next();
	}

	public void setStudentID() {
		//Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the student's ID: ");
		studentID = scanner.nextInt();
	}

	public void setGender() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the student's gender: ");
		gender = scanner.next();
	}

	public void setSubjects() {
		subjects.setNames();
		subjects.setMarks();
	}

	public String getStudentName() {
		return studentName;
	}

	public int getStudentID() {
		return studentID;
	}

	public String getGender() {
		return gender;
	}

	public Subject getSubjects() {
		return subjects;
	}
}