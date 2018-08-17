package week2.day1_2.generics;

public class PrintType {
    static class Person {
        String name;
        int age;
        
        Person(String name, int age) {
            this.name = name; this.age = age;
        }
        
        public String toString() {
            String result = "";
            result += name; result += ", "; result += age;
            return result;
        }
    }
    
    
    public static <T> void display(T t) {
        System.out.println("\nType of parameter: " + t.getClass().getSimpleName());
        System.out.println("Value of parameter: " + t.toString());
    }    
    
    public static void main(String[] args) {
        display(11);
        display("TEST STRING");
        display(4.5);
        
        Integer i = new Integer(14); display(i);
        String s = "whatever"; display(s);
        Float f = new Float(5.67); display(f);
        Double d = new Double(6.57645746); display(d);
        Person p = new Person("Raghav", 21); display(p);
        
        d++; f++;
        System.out.println(d + ", " + f);
    }
}