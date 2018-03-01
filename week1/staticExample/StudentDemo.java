package staticExample;

import java.util.*;

public class StudentDemo {
	
	public void storeStudentData(Student s) {
		s.setStudentName();
		s.setGender();
		s.setStudentID();
		s.setSubjects();
	} 

	public static void main(String[] args) {
		StudentDemo sd = new StudentDemo();
		Student s = new Student(); 
		sd.storeStudentData(s); // initialize a Student object 's' wusing input from the user
		ResultGenerator.generateResult(s); // generate the report card of the student 's'
	}
	
}