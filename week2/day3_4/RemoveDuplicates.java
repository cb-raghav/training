package week2.day3_4;

import java.io.*;
import java.util.*;
import org.apache.commons.csv.*;

public class RemoveDuplicates {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String userHome = System.getProperty("user.home"); // returns "User/raghav"
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        
        System.out.print("Enter the full path of the input CSV file: ");
        String filePath = s.next(); // Accept the file path 
        
        File inputCSV = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(inputCSV));
        String line; 
        
        File outputFile = new File(userHome + "/source/training/week2/day34/CsvDuplicatesOutput.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        
        HashSet<String> lineSet = new HashSet<>();
        while((line = br.readLine()) != null) { // read the CSV file line-by-line
            if(lineSet.add(line)) {
                writer.write(line + "\n");
                System.out.println(line);
            }
        }
        writer.close();
        
        outputFile.renameTo(new File(userHome + "/source/training/week2/day34/CsvDuplicatesOutput.csv"));
    }
}