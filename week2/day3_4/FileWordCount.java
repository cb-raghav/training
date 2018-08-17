package week2.day3_4;

import java.io.*;
import java.util.*;
import org.apache.commons.io.FileUtils;

/**
 * The FileWordCount class reads content from an input text file, 
 * lists all the words in alphabetical order with number of occurrences
 * for each distinct word, and writes the same to an output text file
 * @author raghav
 */
public class FileWordCount {

    /**
     * Parses the contents of the input file and calculates the frequency of each distinct word
     * @param inputFile the input file 
     * @param frequency a map to store the frequency of each distinct word in the input
     */
    public static void computeFrequency(File inputFile,
            Map<String, Integer> frequency) throws IOException {
        List<String> inputFileContents = FileUtils.readLines(inputFile); // read from the file
        for (String line : inputFileContents) { // parse the file contents line-by-line
            // update the frequency map for the current line using space as seperator
            String[] words = line.split(" ");
            for (int i = 0; i < words.length; i++) {
                String currWord = words[i];

                currWord = currWord.replaceAll("[^0-9A-Za-z\\-]+", ""); // remove punctuation (except '-')
                currWord = currWord.toLowerCase(); // convert to lower case
                int oldCount;
                if (frequency.get(currWord) == null) { // word isn't present in map
                    oldCount = 0;
                } else {
                    oldCount = frequency.get(currWord); // get current frequency from the map
                }
                frequency.put(currWord, oldCount + 1); // update the word's frequency in the map
            }
        }
    }

    /**
     * Writes the frequency of each distinct word in the input to an output text file
     * @param outputFile the file to which the output will be written
     * @param frequency a map that contains the frequency of each distinct word in the input
     * @throws IOException 
     */    
    public static void writeOutput(File outputFile, Map<String, Integer> frequency) throws IOException {
        List<String> outputFileContents = new ArrayList<>(); 
        // sort the words in alphabetical order
        Map<String, Integer> sortedFreq = new TreeMap< String, Integer>(frequency); 
        for (Map.Entry<String, Integer> m : sortedFreq.entrySet()) {
            // transfer each entry in the map to the output file
            String word = m.getKey();
            int count = m.getValue();
            String op = word + " - " + count;
            outputFileContents.add(op);
        }
        FileUtils.writeLines(outputFile, outputFileContents, "\n");
    }

    /**
     * Driver function that initializes the parameters 
     * for the other functions before calling them
     * @param args
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String workingDirectory = System.getProperty("user.dir"); // returns "User/raghav/source/training"
        File inputFile = new File(workingDirectory + "/week2/day3_4/wordCountInput.txt");
        File outputFile = new File(workingDirectory + "/week2/day3_4/wordCountOutput.txt");

        try {  
            Map<String, Integer> frequency = new HashMap<String, Integer>();
            computeFrequency(inputFile, frequency);
            writeOutput(outputFile, frequency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
