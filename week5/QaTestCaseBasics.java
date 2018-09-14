package com.chargebee.qa.app.test;

import com.chargebee.APIException;
import com.chargebee.Environment;
import com.chargebee.Result;
import com.chargebee.models.Customer;
import com.chargebee.qa.app.TestRunner;
import com.chargebee.qa.core.SeleniumBase;
import com.chargebee.qa.javaclient.JavaClientRunner;
import com.chargebee.qa.util.DumpUtil;
import java.io.IOException;
import org.testng.annotations.*;

public class QaTestCaseBasics //extends SeleniumBase 
{
    @BeforeMethod
    public void methodToExecAfterMethods(){
        System.out.println("This method runs before executing each method");
    }
    
    @AfterMethod
    public void methodToExecBeforeMethods(){
        System.out.println("This method runs after executing each method");
    }

    @AfterClass
    public void methodToExecAfterClass(){

    System.out.println("This method runs after the class is executed");
    }

    @BeforeClass
    public void methodToExecBeforeClass(){

    System.out.println("This method runs before the class is executed");
    }
        
    @Test(description="Simple test case")
    public void methodToTestOne(){
        System.out.println("First test method is running");
    }

    @Test(description = "Testing with two methods")
    public void methodToTestTwo(){
        System.out.println("Second test method is running");
    }
    
    @Test(description = "Expect some exception from this test case",
        expectedExceptions = {NumberFormatException.class})
    public void testNumberFormatException() {
        String stringToParse="asd";
        int n = Integer.parseInt(stringToParse); // This will throw NumberFormatException
    }
    
    @Test(description = "Expect some exception from this test case too")
            //expectedExceptions = {ArithmeticException.class})
    public void testArithmeticException() {
        int x = 5, y = 0; int ans = x / y;
        System.out.println(ans);
    }
   
    @Test(description = "Expect an API exception from your test case")
//        expectedExceptions = {APIException.class})
    public void testApiException() throws IOException {
        System.setProperty("com.chargebee.api.domain.suffix", "devcb.in");
        System.setProperty("com.chargebee.api.protocol", "https");
        Environment.configure("raghav1-test", "test_p8pUnQKRy9EZMFl5aAi8vplpVrWr10cuT");
        
        Customer.retrieve("xxx").request();
    }
    
    public static void main(String[] args) throws Throwable {
        new JavaClientRunner()
        .testThisClass()
        .testMethod("methodToTestOne")
        .run();
    }
}