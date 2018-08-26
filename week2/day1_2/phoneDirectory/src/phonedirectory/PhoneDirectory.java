package phonedirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class PhoneDirectory {

    static String[][] input = new String[5][5];
    static Map<String, List<ContactDetails> > directory = new HashMap<>();
    static Map<String, String> owners = new LinkedHashMap<>();

    public static void sampleData() {
        // store the sample input data in a 2D array
        input[0][0] = "Dharam Kumar";
        input[0][1] = "Prabha Wine Shop, G N Chetty Road, Chennai";
        input[0][2] = "099221 45665";
        input[0][3] = "044 24987654";
        input[0][4] = "1312 776 341";

        input[1][0] = "Sokratis P";
        input[1][1] = "1A Viboothi Apartments, Habibullah Road, Chennai";
        input[1][2] = "098767 12343";
        input[1][3] = "044 48776543";
        input[1][4] = "1009 123 431";

        input[2][0] = "Sohag Gazi";
        input[2][1] = "6 Lambeth Avenue, Bangalore";
        input[2][2] = "087684 30053";
        input[2][3] = "052 87665012";
        input[2][4] = "1465 900 100";

        input[3][0] = "Sokratis P";
        input[3][1] = "Ashburton Grove, Homsey Road, London";
        input[3][2] = "076991 04314";
        input[3][3] = "041 34556019";
        input[3][4] = "1098 234 567";

        input[4][0] = "Ranti Martins";
        input[4][1] = "6 Vivekanandhar Theru, Dubai Kurukku Sandhu, Dubai Main Road, Dubai";
        input[4][2] = "096912 90043";
        input[4][3] = "044 7655 1232";
        input[4][4] = "1088 345 614";

        for (int i = 0; i < 5; i++) {
            String name = input[i][0];
            String address = input[i][1];
            String mobileNum = input[i][2];
            String homeNum = input[i][3];
            String workNum = input[i][4];
            storeInputData(name, address, mobileNum, homeNum, workNum);
        }
    }

    public static void loadFromJson() throws FileNotFoundException, JSONException {
        String userHome = System.getProperty("user.home"); // returns "/Users/raghav"
        JSONTokener jsonTokener = new JSONTokener(new FileReader(userHome + "/source/training/week2/day1_2/inputData.json"));
        JSONObject jsonObject = new JSONObject(jsonTokener);
        JSONArray directory = jsonObject.getJSONArray("directory");

        for (int i = 0; i < directory.length(); i++) { // read each directory entry from the JSON file
            JSONObject details = directory.getJSONObject(i);
            String name = details.getString("Name");
            String address = details.getString("Address");
            String mobileNum = details.getString("Mobile Number");
            String homeNum = details.getString("Home Number");
            String workNum = details.getString("Work Number");
            storeInputData(name, address, mobileNum, homeNum, workNum);
        }
    }

    public static void loadFromCsv() throws FileNotFoundException, IOException {
        String userHome = System.getProperty("user.home"); // returns "/Users/raghav"
        File inputCSV = new File(userHome + "/source/training/week2/day1_2/inputData.csv");
        BufferedReader br = new BufferedReader(new FileReader(inputCSV));
        String line;
        int lnum = 0;
        String[] attributes = null;

        while ((line = br.readLine()) != null) { // read the CSV file line-by-line
            String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            if (lnum != 0) { // first line with column names
                String name = values[0];
                String address = values[1];
                address = address.replace("\"", "");
                String mobileNum = values[2];
                String homeNum = values[3];
                String workNum = values[4];
                storeInputData(name, address, mobileNum, homeNum, workNum);
            }
            lnum++;
        }
    }

    public static void storeInputData(String name, String address, String mobileNum,
            String homeNum, String workNum) {
        // store the input data in collections for efficient retrieval
        ContactDetails cd = new ContactDetails(address, mobileNum, homeNum, workNum);
        owners.put(mobileNum.replace(" ", ""), name);
        owners.put(homeNum.replace(" ", ""), name);
        owners.put(workNum.replace(" ", ""), name);
        
        List<ContactDetails> listDetails = null;
        if(directory.get(name) != null) { // the directory already has entries for this name
            listDetails = directory.get(name); // fetch the existing list of details for this name
        }
        else { // create a fresh directory entry for this name
            listDetails = new ArrayList<>(); // initialise a list that can store details
        }
        listDetails.add(cd); // add the fresh entry to the list
        directory.put(name, listDetails); // update the directory
    }

    public static void queryName(String searchName) {
        boolean found = false;
        searchName = searchName.toLowerCase(); // for case-insensitive search
        
        // search the directory for full / partial matches to the search term
        for (Entry< String, List<ContactDetails> > entry : directory.entrySet()) {
            String name = entry.getKey();
            String nameWithoutCase = name.toLowerCase();
            if (nameWithoutCase.startsWith(searchName)) { // match(es) found
                found = true;
                List<ContactDetails> listDetails = entry.getValue();

                // display the details of all persons having this name
                for (int i = 0; i < listDetails.size(); i++) {
                    ContactDetails cd = listDetails.get(i);
                    System.out.println("\nName: " + name);
                    System.out.println("Address: " + cd.getAddress());
                    System.out.println("Mobile Number: " + cd.getMobileNum());
                    System.out.println("Home Number: " + cd.getHomeNum());
                    System.out.println("Work Number: " + cd.getWorkNum());
                }
            }
        }
        if (!found) { // no matches found
            System.out.println("\nNo matching entries found for the name entered!");
        }
    }

    
    public static void queryNumber(String searchNumber) {
        searchNumber = searchNumber.replace(" ", ""); // trim the spaces
        String searchName = owners.get(searchNumber);
        // get the name of the person that this number belongs to
        if (searchName != null) { // match found
            List<ContactDetails> listDetails = directory.get(searchName);
            boolean found = false;
            ContactDetails requiredDetails = null;

            for (int i = 0; i < listDetails.size() && !found; i++) {
                ContactDetails cd = listDetails.get(i);
                String mobileNum = cd.getMobileNum().replace(" ", "");
                String homeNum = cd.getHomeNum().replace(" ", "");
                String workNum = cd.getWorkNum().replace(" ", "");
                if(searchNumber.equals(mobileNum)
                    || searchNumber.equals(homeNum)
                    || searchNumber.equals(workNum)) {
                    requiredDetails = cd;
                    found = true;
                }
            }

            // display the details of the match
            System.out.println("\nName: " + searchName);
            System.out.println("Address: " + requiredDetails.getAddress());
            System.out.println("Mobile Number: " + requiredDetails.getMobileNum());
            System.out.println("Home Number: " + requiredDetails.getHomeNum());
            System.out.println("Work Number: " + requiredDetails.getWorkNum());
        } else {
            System.out.println("\nNo matching entries found for the phone number entered!");
        }  
    }

    public static void main(String[] args) throws FileNotFoundException, JSONException, IOException {
        // choose where the data will be loaded from
        int inpChoice = 0;
        boolean acceptChoice = true;
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        System.out.println("PHONE DIRECTORY");
        System.out.println("1. Use the in-built sample data");
        System.out.println("2. Load the sample data from a JSON file");
        System.out.println("3. Load the sample data from a CSV file");
        do {
            System.out.print("Enter your choice: ");
            inpChoice = s.nextInt();
            if (inpChoice == 1) {
                System.out.println("In-built sample data was loaded ...");
                acceptChoice = false;
                sampleData();
            } else if (inpChoice == 2) {
                System.out.println("Sample data was loaded from the JSON file ...");
                acceptChoice = false;
                loadFromJson();
            } else if (inpChoice == 3) {
                System.out.println("Sample data was loaded from the CSV file ...");
                acceptChoice = false;
                loadFromCsv();
            } else {
                System.out.println("Invalid choice entered! Please re-enter.");
            }
        } while (acceptChoice);

        // menu-driven program for users to query the phone directory for details
        int opt = 0;
        do {
            System.out.println("\n1. Retrieve the details of person(s) matching the given name");
            System.out.println("2. Retrieve the details of the person matching the given phone number");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            opt = s.nextInt();
            if (opt == 1) {
                String searchName = "";
                System.out.print("Enter a name: ");
                searchName = s.next();

                queryName(searchName);
            } else if (opt == 2) {
                String searchNumber = "";
                System.out.print("Enter a phone number: ");
                searchNumber = s.next();

                queryNumber(searchNumber);
            } else { // terminate the program
                opt = 0;
                directory.clear();
                owners.clear();
                System.out.println("\nExiting ...");
            }
            //System.out.println();
        } while (opt != 0);
    }
}
