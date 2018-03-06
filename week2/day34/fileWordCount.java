package week2.day34;

import java.io.*;
import java.util.*;

public class fileWordCount {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String userHome = System.getProperty("user.home"); // returns "User/raghav"
        File inputFile = new File(userHome + "/source/training/week2/day34/input.txt");
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line; 
       
        Map<String, Integer> frequency = new HashMap<String, Integer>();
        while((line = br.readLine()) != null) { // read line-by-line from the input file
            String[] words = line.split(" ");
            for(int i = 0; i < words.length; i++) {
                String currWord = words[i];
                currWord = currWord.replaceAll("[^0-9A-Za-z\\-]+", ""); // remove punctuation (except '-')
                currWord = currWord.toLowerCase(); // convert to lower case
                int oldCount;
                if(frequency.get(currWord) == null) { // word isn't present in map
                    oldCount = 0;
                }
                else {
                    oldCount = frequency.get(currWord); // get current frequency from the map
                }
                frequency.put(currWord, oldCount + 1); // update the word's frequency in the map
            }
        }
        
        File outputFile = new File(userHome + "/source/training/week2/day34/output.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        Map< String, Integer> sortedFreq = new TreeMap< String, Integer>(frequency); // sort the words
        for(Map.Entry<String, Integer> m : sortedFreq.entrySet()) {
            // transfer each entry in the map to the output file
            String word = m.getKey();
            int count = m.getValue();
            String op = word + " - " + count;
            System.out.println(op);
            writer.write(op + "\n");
        }
        writer.close();
    }
}