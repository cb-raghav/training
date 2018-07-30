package week2.day3_4;

import java.io.*;
import java.util.*;

public class SearchFile {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String userHome = System.getProperty("user.home"); // returns "User/raghav"
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        // Accept the file path and the search term
        System.out.print("Enter the full path of the input file: ");
        String filePath = s.next();
        System.out.print("Enter the search word: ");
        String term = s.next();
        
        File inputFile = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line; 
        
        File outputFile = new File(userHome + "/source/training/week2/day34/" + term + "-locations.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        int lineNumber = 1;
        while((line = br.readLine()) != null) { // read the file line-by-line
            StringBuilder sb = new StringBuilder();
            sb.append("Line #" + (lineNumber++) + ": <");
            int index = line.indexOf(term); // get the index of the first occurrence
            while(index >= 0) {
                sb.append(index);
                index = line.indexOf(term, index + 1);
                if(index != -1) {
                    sb.append(", ");
                }
            }
            sb.append(">\n");
            writer.write(sb.toString());
            System.out.println(sb);
        }
        writer.close();
    }
}