/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chargebee.qa.app.test;

import com.chargebee.APIException;
import com.chargebee.Environment;
import com.chargebee.Result;
import com.chargebee.models.Customer;
import com.chargebee.qa.app.TestRunner;
import com.chargebee.qa.core.QATimeMachine;
import com.chargebee.qa.core.SeleniumBase;
import static com.chargebee.qa.data.Constants.APR;
import com.chargebee.qa.diff.DemoDataFileDiff;
import com.chargebee.qa.javaclient.JavaClientRunner;
import com.chargebee.qa.util.DumpUtil;
import java.io.IOException;
import org.testng.annotations.*;

/**
 *
 * @author raghav
 */
public class SimpleTestCase //extends SeleniumBase 
{
    private QATimeMachine t;
    
    public static void main(String[] args) throws Throwable {
        //DumpUtil.throwIfBaseFileNotExists = false; // Uncomment when running for the 1st time only
        new JavaClientRunner()
                .testThisClass()
                .testMethod("testCreateCustomerDumpJson")
                .run();
    }

    @BeforeClass
    public void load() throws Exception {
        t = new QATimeMachine().updateServerTime(true);
        JavaClientRunner.siteSetup();
    }
    
    @Test(description = "Testing \'dump and compare JSON\'")
    public void testCreateCustomerDumpJson() throws Exception {
        t.gotoo(1, APR, 2013);
        Result result = Customer.create()
                  .firstName("John")
                  .lastName("Doe")
                  .email("john@test.com")
                  .billingAddressFirstName("John")
                  .billingAddressLastName("Doe")
                  .billingAddressLine1("PO Box 9999")
                  .billingAddressCity("Walnut")
                  .billingAddressState("California")
                  .billingAddressZip("91789")
                  .billingAddressCountry("US").request();
        System.out.println(result);
        DumpUtil.dumpAndCheck(result, "create_customer_api_response");
        t.reset(false);
    }
    
    @Test(description = "Testing \'dump and compare text\'")
    public void testCreateCustomerDumpText() throws Exception {
        t.gotoo(1, APR, 2013);
        Result result = Customer.create()
                  .firstName("John")
                  .lastName("Doe")
                  .email("john@test.com")
                  .billingAddressFirstName("John")
                  .billingAddressLastName("Doe")
                  .billingAddressLine1("PO Box 9999")
                  .billingAddressCity("Walnut")
                  .billingAddressState("California")
                  .billingAddressZip("91789")
                  .billingAddressCountry("US").request();
        String content = result.toString();
        String fileNameWithExtension = "create_customer_api_response.txt";
        DumpUtil.dumpAndCheckFile(content, fileNameWithExtension);
        t.reset(false);
    }
    
    @Test(description = "Testing \'dump and compare CSV\'")
    public void testDumpCsv() throws Exception {
//        t.gotoo(1, APR, 2013);

        String srcPath = "/Users/raghav/work/chargebee-app/qa_data/basedata/dumpfiles/SimpleTestCase/testDumpCsv"; //test_base.csv
        String destPath = "/Users/raghav/work/chargebee-app/qa_data/newdata/dumpfiles/SimpleTestCase/testDumpCsv"; // test_new.csv

        DemoDataFileDiff.diffData(srcPath, destPath, null, true);
        t.reset(false);
    }
    
    @Test(description = "Test to dump string")
    public void testDumpString() throws Exception {
        String content = "Hello world, this is a text file to dump and compare text.";
        String fileNameWithExtension = "hello_world.txt";
        DumpUtil.dumpAndCheckFile(content, fileNameWithExtension);
    }
}
