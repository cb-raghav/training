package staticExample;

import java.util.*;

public class ResultGenerator {
	/*
		Prints the report card of a given student
		Parameters: 'student' (Student object)
	*/
	public static void generateResult(Student student) {
		float total = 0, average = 0;
		Subject s = student.getSubjects(); 
		String[] subjects = s.getNames();
		float[] marks = s.getMarks();
		int numSubjects = marks.length;
		
		System.out.println("Student name: " + student.getStudentName());
		System.out.println("Student ID: " + student.getStudentID());
		System.out.println("\nMarks scored: ");
		for(int i = 0; i < numSubjects; i++) {
			total += marks[i];
			System.out.println(subjects[i] + ": " + marks[i]);
		}
		average = total / numSubjects;
		System.out.println("\nTotal marks scored: " + total);
		System.out.println("Average marks scored: " + average);
	}
}
