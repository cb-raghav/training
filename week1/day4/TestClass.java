// A simple Java program to demonstrate how diamond
// problem is handled in case of default methods
 
interface GPI
{
    // default method
    default void show()
    {
        System.out.println("Default GPI");
    }
}
 
interface PI1 extends GPI { 
    default void show() {
        System.out.println("PI1's show function");
    }
}
 
interface PI2 extends GPI { 
    
}
 
// Implementation class code
class TestClass implements PI1, PI2
{
    public static void main(String args[])
    {
        TestClass d = new TestClass();
        d.show();
    }
}