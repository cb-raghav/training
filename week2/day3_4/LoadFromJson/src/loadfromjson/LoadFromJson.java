package loadfromjson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.*;

public class LoadFromJson {

    static class Subject {

        String subjectName;
        int subjectScore;

        Subject(String subjectName, int subjectScore) {
            this.subjectName = subjectName;
            this.subjectScore = subjectScore;
        }

        public String getName() {
            return subjectName;
        }

        public int getScore() {
            return subjectScore;
        }
    }

    static class Student {

        String studentName;
        String studentID;
        String std;
        String DOJ;
        Subject[] marks;

        Student(String studentName, String studentID, String std, String DOJ, Subject[] marks) {
            this.studentName = studentName;
            this.studentID = studentID;
            this.std = std;
            this.DOJ = DOJ;
            this.marks = marks;
        }

        public void display() {
            System.out.println("		STUDENT DETAILS");
            System.out.println("Student name: " + studentName);
            System.out.println("Student ID: " + studentID);
            System.out.println("Class: " + std);
            System.out.println("Date of joining: " + DOJ);
            System.out.println("Marks scored: ");
            for (int i = 0; i < marks.length; i++) {
                System.out.println(marks[i].getName() + " - " + marks[i].getScore());
            }
        }
    }

    static class Teacher {

        String teacherName;
        String teacherID;
        String DOJ;
        int salary;
        String[] classes;

        Teacher(String teacherName, String teacherID, String DOJ, int salary, String[] classes) {
            this.teacherName = teacherName;
            this.teacherID = teacherID;
            this.DOJ = DOJ;
            this.salary = salary;
            this.classes = classes;
        }

        public void display() {
            System.out.println("		TEACHER DETAILS");
            System.out.println("Teacher name: " + teacherName);
            System.out.println("Teacher ID: " + teacherID);
            System.out.println("Date of joining: " + DOJ);
            System.out.println("Salary: " + salary);
            System.out.print("Classes handled: ");
            for (int i = 0; i < classes.length; i++) {
                System.out.print(classes[i]);
                if (i != classes.length - 1) {
                    System.out.print(", ");
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException, JSONException {
        String userHome = System.getProperty("user.home"); // returns "/Users/raghav"
        JSONTokener jsonTokener = new JSONTokener(new FileReader(userHome + "/source/training/week2/day3_4/students-teachers.json"));
        JSONObject jsonObject = new JSONObject(jsonTokener);

        // Fetch the 'Student' and 'Teacher' JSON Objects
        JSONObject student = jsonObject.getJSONObject("Student");
        JSONObject teacher = jsonObject.getJSONObject("Teacher");

        // Initialise the data members of 'Student'
        String studentName = student.getString("Name");
        String studentID = student.getString("ID");
        String std = student.getString("Std");
        String studentDOJ = student.getString("Date Of Joining");
        JSONArray subjectMarks = student.getJSONArray("Marks");
        int numSubjects = subjectMarks.length();
        Subject[] marks = new Subject[numSubjects];
        for (int i = 0; i < numSubjects; i++) {
            JSONObject currSubject = subjectMarks.getJSONObject(i);
            String subjectName = currSubject.getString("Subject");
            int subjectScore = currSubject.getInt("Mark");
            marks[i] = new Subject(subjectName, subjectScore);
        }

        // Initialise an object of class 'Student' and display the details
        Student s = new Student(studentName, studentID, std, studentDOJ, marks);
        s.display();
        System.out.println();

        // Initialise the data members of 'Teacher'
        String teacherName = teacher.getString("Name");
        String teacherID = teacher.getString("ID");
        String teacherDOJ = teacher.getString("Date Of Joining");
        int salary = teacher.getInt("Salary");
        JSONArray inchargeClasses = teacher.getJSONArray("Classes Taking Care Of");
        int numClasses = inchargeClasses.length();
        String[] classes = new String[numClasses];
        for (int i = 0; i < numClasses; i++) {
            classes[i] = inchargeClasses.getString(i);
        }

        // Initialise an object of class 'Teacher' and display the details
        Teacher t = new Teacher(teacherName, teacherID, teacherDOJ, salary, classes);
        t.display();
        System.out.println();
    }
}
