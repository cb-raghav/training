package week2.day3_4;

import java.io.*;
import java.util.*;
import org.apache.commons.io.FileUtils;

/**
 * The SearchFile class takes a text file and a search term as input, lists the
 * start indices of the search term's occurrences on each line, and writes the
 * same to an output text file
 *
 * @author raghav
 */
public class SearchFile {
    
    /**
     * Computes the start indices of the search term's occurrences on 
     * each line of the input file 
     * @param inputFile the input file 
     * @param searchTerm the word whose start indices will be tracked
     * @return a list of Strings in the format "<line-no> : <start-index-1>, <start-index-2>, ..."
     * @throws IOException 
     */
    public static List<String> getIndices(File inputFile, String searchTerm) throws IOException {
        List<String> inputFileContents = FileUtils.readLines(inputFile); // read from the file
        List<String> outputFileContents = new ArrayList<>();

        int lineNumber = 1;
        for (String line : inputFileContents) { // parse the file contents line-by-line
            StringBuilder sb = new StringBuilder();
            sb.append("Line #" + (lineNumber++) + ": <");
            int index = line.indexOf(searchTerm); // get the index of the first occurrence
            while (index >= 0) {
                sb.append(index);
                index = line.indexOf(searchTerm, index + 1);
                if (index != -1) {
                    sb.append(", ");
                }
            }
            sb.append(">\n");
            outputFileContents.add(sb.toString());
        }

        return outputFileContents;
    }

    /**
     * Driver function that accepts input from the user and 
     * initializes parameters for the other functions before calling them
     * @param args
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String workingDirectory = System.getProperty("user.dir"); // Returns "User/raghav/source/training"
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");

        // Accept the file path and the search term
        System.out.print("Enter the full path of the input file: ");
        String filePath = s.next();
        System.out.print("Enter the search word: ");
        String searchTerm = s.next();

        // Initialise the input and output files / collections
        File inputFile = new File(filePath);
        File outputFile = new File(workingDirectory + "/week2/day3_4/" + searchTerm + "-locations.txt");

        List<String> outputFileContents = getIndices(inputFile, searchTerm);
        FileUtils.writeLines(outputFile, outputFileContents, "\n");
    }
}
