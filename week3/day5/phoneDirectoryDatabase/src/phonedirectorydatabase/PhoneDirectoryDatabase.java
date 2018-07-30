package phonedirectorydatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.json.*;

public class PhoneDirectoryDatabase {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/phone_directory";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
    
    public static void loadFromCsv() throws FileNotFoundException, IOException, SQLException {
        String userHome = System.getProperty("user.home"); // returns "/Users/raghav"
        File inputCSV = new File(userHome + "/source/training/week2/day12/inputData.csv");
        BufferedReader br = new BufferedReader(new FileReader(inputCSV));
        String line; int lnum = 0;
        String[] attributes = null;
        
        while((line = br.readLine()) != null) { // read the CSV file line-by-line
            String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            if(lnum != 0) { // first line with column names
                String name = values[0];
                String address = values[1]; address = address.replace("\"", "");
                String mobileNum = values[2];
                String homeNum = values[3];
                String workNum = values[4];
                storeInDatabase(name, address, mobileNum, homeNum, workNum); // store the details in the DB
            }
            lnum++;
        }
    }
    
    public static void loadFromJson() throws FileNotFoundException, JSONException, SQLException {
        String userHome = System.getProperty("user.home"); // returns "/Users/raghav"
        JSONTokener jsonTokener = new JSONTokener(new FileReader(userHome + "/source/training/week2/day12/inputData.json"));
        JSONObject jsonObject = new JSONObject(jsonTokener);
        JSONArray directory = jsonObject.getJSONArray("directory");
        
        for(int i = 0; i < directory.length(); i++) { // read each directory entry from the JSON file
            JSONObject details = directory.getJSONObject(i);
            String name = details.getString("Name");
            String address = details.getString("Address");
            String mobileNum = details.getString("Mobile Number");
            String homeNum = details.getString("Home Number");
            String workNum = details.getString("Work Number");
            storeInDatabase(name, address, mobileNum, homeNum, workNum); // store the details in the DB
        }
    }
    
    public static void storeInDatabase(String name, String address, String mobileNum, 
            String homeNum, String workNum) throws SQLException 
    { 
	Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Load the INSERT query into the prepared statement
            String insertSQL = "INSERT INTO directory VALUES(?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(insertSQL);
            
            // Set parameters for the INSERT query
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, mobileNum);
            preparedStatement.setString(4, homeNum);
            preparedStatement.setString(5, workNum);
            
            // Execute the prepared statement 
            int numRec = preparedStatement.executeUpdate();
            
            System.out.println("\nRecord was inserted into the 'directory' table!");
        }
        catch(SQLException se){
            //Handle errors for JDBC
            String message = se.toString();
            if(message.contains("MySQLIntegrityConstraintViolationException")) {
                // record violates primary key constraint
                //System.out.println("ERROR: the record already exists in the 'directory' table");
            }
        }
        catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        finally{
            //finally block used to close resources
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(conn != null) {
                conn.close();
            }
        } // end try-catch-finally 
    }
    
    public static void displayResultSet(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String address = rs.getString("address");
        String mobileNum = rs.getString("mobileNum");
        String homeNum = rs.getString("homeNum");
        String workNum = rs.getString("workNum");

        System.out.println("\nName: " + name);
        System.out.println("Address: " + address);
        System.out.println("Mobile Number: " + mobileNum);
        System.out.println("Home Number: " + homeNum);
        System.out.println("Work Number: " + workNum);
    }
    
    public static boolean query(int queryType, String searchTerm, String searchTerm2) throws SQLException {
        String searchName = "", searchNumber = "", searchAddr = "";
        boolean queryResult = false;
        if(queryType == 1) { // query based on name
            searchName = searchTerm.toLowerCase();
        }
        else if(queryType == 2) { // query based on number 
            searchNumber = searchTerm.replace(" ", "");
        }
        else if(queryType == 3) { // query based on name and address
            searchName = searchTerm.toLowerCase();
            searchAddr = searchTerm2.replace(" ", "");
        }
        
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            String selectSQL = "";
            if(queryType == 1) { // query based on name
                // Load the SELECT query into the prepared statement
                selectSQL = "SELECT * FROM directory WHERE (lower(name) LIKE ?)";
                preparedStatement = conn.prepareStatement(selectSQL);

                // Set parameters for the SELECT query
                preparedStatement.setString(1, searchName + "%");
            }
            else if(queryType == 2) { // query based on number
                // Load the SELECT query into the prepared statement
                selectSQL = "SELECT * FROM directory WHERE (REPLACE(mobileNum, ' ', '')) = ? OR "
                        + "(REPLACE(homeNum, ' ', '')) = ? OR "
                        + "(REPLACE(workNum, ' ', '')) = ?";
                preparedStatement = conn.prepareStatement(selectSQL);

                // Set parameters for the SELECT query
                preparedStatement.setString(1, searchNumber);
                preparedStatement.setString(2, searchNumber);
                preparedStatement.setString(3, searchNumber);
            }
            else if(queryType == 3) { // query based on name and address
                // Load the SELECT query into the prepared statement
                selectSQL = "SELECT * FROM directory WHERE (lower(name) LIKE ?) AND "
                        + "(REPLACE(address, ' ', '')) = ?";
                preparedStatement = conn.prepareStatement(selectSQL);
                
                // Set parameters for the SELECT query
                preparedStatement.setString(1, searchName + "%");
                preparedStatement.setString(2, searchAddr);
            }
            
            // Execute the prepared statement and fetch the result set
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next() == false) {
                queryResult = false;
                System.out.println("\nERROR: no matching records found!");
            }
            else {
                queryResult = true;
                if(queryType == 1 || queryType == 2) {
                    do {
                        displayResultSet(rs);
                    }
                    while(rs.next());
                }
            }
        }
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        finally{
            //finally block used to close resources
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(conn != null) {
                conn.close();
            }
        } // end try-catch-finally 
        return queryResult;
    }

    public static void updateRecord(String searchName, String searchAddr) throws SQLException {
        if(query(3, searchName, searchAddr)) { // record to be updated was found
            System.out.println("\nRecord found!");
            // choose the attribute to be updated
            Scanner s = new Scanner(System.in);
            s.useDelimiter("\n");
            
            int updChoice = 0;
            do {
                System.out.println("\n1. Update the name");
                System.out.println("2. Update the mobile number");
                System.out.println("3. Update the home number");
                System.out.println("4. Update the work number");
                System.out.print("Enter your choice: ");
                updChoice = s.nextInt();
                if(updChoice < 1 || updChoice > 4) {
                    System.out.println("\nERROR: Invalid choice! Please re-enter ...");
                }
            }
            while(updChoice < 1 || updChoice > 4);
            
            String updParam = ""; String updValue = ""; boolean validInput = false;
            do {
                switch(updChoice) {
                    case 1: { 
                        System.out.print("Enter the new name: ");
                        updParam = "name";
                    }
                    break;
                    case 2: {
                        System.out.print("Enter the new mobile number: ");
                        updParam = "mobileNum";
                    }
                    break;
                    case 3: {
                        System.out.print("Enter the new home number: ");
                        updParam = "homeNum";
                    }
                    break;
                    case 4: {
                        System.out.print("Enter the new work number: ");
                        updParam = "workNum";
                    }
                    break;
                }
                updValue = s.next();

                if(updChoice == 1) {
                    if(updValue.matches("[a-zA-Z \\.]+")) {
                        validInput = true;
                    }
                    else {
                        validInput = false;
                    }
                } 
                else {
                    if(updValue.matches("[0-9 ]+")) {
                        validInput = true;
                    }
                    else {
                        validInput = false;
                    }
                }
                if(!validInput) {
                    System.out.println("\nERROR: Invalid value entered! Please re-enter ...\n");
                }
            }
            while(!validInput);
            
            // updParam --> attribute to be updated; updValue --> updated value of attribute 
            
            // update the chosen attribute
            Connection conn = null;
            PreparedStatement preparedStatement = null;
            try {
                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");

                // Open a connection
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                
                // Load the UPDATE query into the prepared statement
                String updateSQL = "UPDATE directory SET " + updParam + " = ? "
                        + "WHERE (lower(name) LIKE ?) AND "
                        + "(REPLACE(address, ' ', '')) = ?";
                preparedStatement = conn.prepareStatement(updateSQL);
                
                // Set parameters for the UPDATE query
                preparedStatement.setString(1, updValue);
                preparedStatement.setString(2, (searchName.toLowerCase()) + "%");
                preparedStatement.setString(3, searchAddr.replace(" ", "")); 
                
                // Execute the prepared statement 
                int numRec = preparedStatement.executeUpdate();
                System.out.println("\nThe '" + updParam + "' field was updated successfully!");
            }
            catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace();
            }
            catch(Exception e){
                //Handle errors for Class.forName
                e.printStackTrace();
            }
            finally{
                //finally block used to close resources
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(conn != null) {
                    conn.close();
                }
            } // end try-catch-finally 
        }
    }
    
    public static void addRecord() throws SQLException {
        String name = "", address = "";
        String mobileNum = "", homeNum = "", workNum = "";
        boolean validInput = false;
        
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        do {
            System.out.print("\nEnter the name: ");
            name = s.next();
            if(name == null || name.isEmpty()) {
                System.out.println("ERROR: name can't be null or empty! Please re-enter... ");
            }
            else {
                validInput = true;
            }
        }
        while(!validInput);
        validInput = false;
        do {
            System.out.print("Enter the address: ");
            address = s.next();
            if(address == null || address.isEmpty()) {
                System.out.println("\nERROR: address can't be null or empty! Please re-enter... ");
            }
            else {
                validInput = true;
            }
        }
        while(!validInput);
                
        System.out.print("Enter the mobile number: ");
        mobileNum = s.next();
        System.out.print("Enter the home number: ");
        homeNum = s.next();
        System.out.print("Enter the work number: ");
        workNum = s.next();
        
        storeInDatabase(name, address, mobileNum, homeNum, workNum);
    }
    
    public static void main(String[] args) throws FileNotFoundException, JSONException, IOException, SQLException {
        // choose where the data will be loaded from
        int inpChoice = 0; boolean acceptChoice = true;
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        System.out.println("PHONE DIRECTORY");
        System.out.println("1. Load the sample data from a JSON file");
        System.out.println("2. Load the sample data from a CSV file");
        do {
            System.out.print("Enter your choice: ");
            inpChoice = s.nextInt();
            if(inpChoice == 1) {
                System.out.println("\nSample data was loaded from the JSON file ...");
                acceptChoice = false;
                loadFromJson();
            }
            else if(inpChoice == 2) {
                System.out.println("\nSample data was loaded from the CSV file ...");
                acceptChoice = false;
                loadFromCsv();
            }
            else {
                System.out.println("\nInvalid choice entered! Please re-enter.\n");
            }
        }
        while(acceptChoice);
        
        
        // menu-driven program for users to query the phone directory for details and update it
        int opt = 0; 
        do {
            System.out.println("\n1. Retrieve the details of person(s) matching the given name");
            System.out.println("2. Retrieve the details of the person matching the given phone number");
            System.out.println("3. Update / add details for an existing record");
            System.out.println("4. Add a new record");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            opt = s.nextInt();
            if(opt == 1) {
                String searchName = "";
                System.out.print("Enter a name: ");
                searchName = s.next();
                
                query(1, searchName, "");
            }
            else if(opt == 2) {
                String searchNumber = "";
                System.out.print("Enter a phone number: ");
                searchNumber = s.next();
                
                query(2, searchNumber, "");
            }
            
            else if(opt == 3) {
                String searchName = "", searchAddr = "";
                System.out.print("Enter the name: ");
                searchName = s.next();
                System.out.print("Enter the address: ");
                searchAddr = s.next();
                updateRecord(searchName, searchAddr);
            }
            else if(opt == 4) {
                addRecord();
            }
            else { // terminate the program
                opt = 0;
                System.out.println("\nExiting ...");
            }
            //System.out.println();
        }
        while(opt != 0);
        
    }
    
}
