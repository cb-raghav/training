package transformcsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.*;

public class TransformCSV {
    public static String convertDate(String input, String inputFormat, String outputFormat) throws ParseException {
        SimpleDateFormat inpFormatter = new SimpleDateFormat(inputFormat);
        String result = input;
        try {
            Date inputDate = inpFormatter.parse(input); // convert the string input to a Date object
            SimpleDateFormat outFormatter = new SimpleDateFormat(outputFormat);
            result = outFormatter.format(inputDate); // reformat the input date
        }
        catch(ParseException e) {
            //System.out.println("WARNING: date is blank or unparseable");
        }
        
        return result;
    }
    
    public static String convertMoney(String input, double multiplier) {
        double amount = Double.parseDouble(input); // convert input to a double
        amount *= multiplier; // multiply by the multiplier read from the config file
        return Double.toString(amount); // convert back to a String
    }
    
    public static void main(String[] args) throws FileNotFoundException, JSONException, IOException, ParseException {
        String userHome = System.getProperty("user.home"); // returns "/Users/raghav"
        JSONTokener jsonTokener = new JSONTokener(new FileReader(userHome + "/source/training/week2/day5/config.json"));
        JSONObject jsonObject = new JSONObject(jsonTokener);
        
        // load the transformation parameters from the config file
        String inputDateFormat = jsonObject.getString("inputDateFormat");
        String targetDateFormat = jsonObject.getString("targetDateFormat");
        double amountMultiplier = Double.parseDouble(jsonObject.getString("amountMultiplier"));
        double refundMultiplier = Double.parseDouble(jsonObject.getString("refundMultiplier"));
        double taxMultiplier = Double.parseDouble(jsonObject.getString("taxMultiplier"));
        
        // input CSV file to read from
        File inputCSV = new File(userHome + "/source/training/week2/day5/input.csv");
        BufferedReader br = new BufferedReader(new FileReader(inputCSV));
        String line; int lnum = 0;
        String[] headers = null;
        
        // output file to write transformed content to
        File outputFile = new File(userHome + "/source/training/week2/day5/output.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        
        while((line = br.readLine()) != null) { // read the CSV file line-by-line
            String[] values = line.split(",");
            String output = ""; // to be written to the output CSV file
            boolean writeFlag = true; 
            JSONObject customerDetails = new JSONObject();
            String details = ""; String taxTotal = "";
            
            if(lnum == 0) { // read the headers from the first line
                headers = values;
                for(int i = 0; i < 12; i++) {
                    output += headers[i];
                    output += ",";
                }
                output += "Tax Total,"; // change the order of the headers as needed
                output += "Customer Details";
                writer.write(output + "\n"); // write the transformed line to output CSV file
            }
            else {
                for(int i = 0; i < values.length; i++) {
                    // process each of the comma-separated values in the current line of input
                    String currValue = values[i];
                    if( headers[i].equals("Invoice Date") || 
                        headers[i].equals("Start Date") ||
                        headers[i].equals("Paid On") ) 
                    {
                        // convert the date to the format specified in the config file
                        if(currValue != "") {
                            currValue = convertDate(currValue, inputDateFormat, targetDateFormat);
                        }
                    }
                    else if(headers[i].equals("Amount")) {
                        currValue = convertMoney(currValue, amountMultiplier);
                        currValue = String.format("%.4f", Double.parseDouble(currValue));
                    }
                    else if(headers[i].equals("Refunded Amount")) {
                        currValue = convertMoney(currValue, refundMultiplier);
                        currValue = String.format("%.4f", Double.parseDouble(currValue));
                    }
                    else if(headers[i].equals("Tax Total")) {
                        taxTotal = convertMoney(currValue, taxMultiplier);
                        taxTotal = String.format("%.4f", Double.parseDouble(taxTotal));
                    }
                    else if(headers[i].equals("Customer First Name")) {
                        // fetch all the customer details
                        writeFlag = false;
                        String firstName = values[i];
                        String lastName = values[i + 1];
                        String email = values[i + 2];
                        String company = values[i + 3];
                        
                        // store them in a JSON Object
                        customerDetails.put("First Name", firstName);
                        customerDetails.put("Last Name", lastName);
                        customerDetails.put("Email", email);
                        customerDetails.put("Company", company);
                        
                        // convert the JSON Object to a string
                        details = customerDetails.toString();
                        details = details.replace(":", " : ");
                        details = details.replace(",", ", ");
                        details = details.replace("\"", "\"\"");
                        details = details.replace("{", "\"{");
                        details = details.replace("}", "}\"");
                    }
                    
                    if(writeFlag) { // write to the output variable
                        output += currValue;
                        output += ",";
                    } 
                }
                output += taxTotal; // add the tax total value
                output += ",";
                output += details; // add the customer details value
                
                writer.write(output + "\n"); // write the transformed line to output CSV file
            }
            lnum++;
        }
        writer.close();
        
        outputFile.renameTo(new File(userHome + "/source/training/week2/day5/output.csv"));
        
    }
    
}
